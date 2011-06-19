/**
 * 
 */
package nl.rakis.sql.ddl.tools;

import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import nl.rakis.sql.SqlTool;
import nl.rakis.sql.ddl.model.Column;
import nl.rakis.sql.ddl.model.Schema;
import nl.rakis.sql.ddl.model.Table;
import nl.rakis.sql.ddl.model.TypeClass;

/**
 * @author bertl
 * 
 */
public class CopySchemaData
{

  private static final String NAME_LENGTH_COLUMN = "nameLength.column";
  private static final String NAME_LENGTH_TABLE  = "nameLength.table";
  private static int          maxLenColumn       = 255;
  private static int          maxLenTable        = 255;

  public static void main(String[] args) {
    SqlTool.init(args);
    if (SqlTool.haveProp(NAME_LENGTH_TABLE)) {
      maxLenTable = SqlTool.getIntProp(NAME_LENGTH_TABLE);
    }
    if (SqlTool.haveProp(NAME_LENGTH_COLUMN)) {
      maxLenColumn = SqlTool.getIntProp(NAME_LENGTH_COLUMN);
    }

    try {
      System.err.print("Loading source schema");
      Schema srcSchema = SqlTool.getInputSchemaLoader()
          .load(SqlTool.getInputSchemaName());
      System.err.println(" (" + srcSchema.getTables().size() + " tables)");

      System.err.print("Loading target schema");
      Schema dstSchema = SqlTool.getOutputSchemaLoader()
          .load(SqlTool.getOutputSchemaName());
      System.err.println(" (" + dstSchema.getTables().size() + " tables)");

      System.err.println("Setting up connections");
      Connection inputDb = SqlTool.getInputConnection();
      Connection outputDb = SqlTool.getOutputConnection();

      System.err.println("Transferring data");
      outputDb.setAutoCommit(false);
      List<Table> allTables = new ArrayList<Table>();
      for (Table table : srcSchema.getTables()) {
        if (!table.getName().startsWith("Link")) {
          allTables.add(table);
        }
      }
      for (Table table : srcSchema.getTables()) {
        if (table.getName().startsWith("Link")) {
          allTables.add(table);
        }
      }
      for (Table table : allTables) {
        copyTableData(table, dstSchema, inputDb, outputDb);
        outputDb.commit();
      }
    }
    catch (Exception e) {
      SqlTool.error(e.getMessage());
      e.printStackTrace();
    }
    finally {
      SqlTool.cleanup();
    }
  }

  private static final class Tuple
  {
    public String from;
    public String to;
    public boolean convertMoney = false;
    public boolean copyClob = false;
  }

  private static void copyTableData(Table table, Schema targetSchema,
                                    Connection inputDb, Connection outputDb)
  {
    String name = table.getName();
    if (name.length() > maxLenTable) {
      name = name.substring(0, maxLenTable);
    }

    Table targetTable = targetSchema.getTable(name);
    if (targetTable == null) {
      // Check rename list
      String renameTo = SqlTool.getProp("rename.table." + name);
      if (renameTo != null) {
        targetTable = targetSchema.getTable(renameTo);
      }
      if (targetTable == null) {
        System.err.print("No table to receive data for \"" + name + "\"");
        if (renameTo != null) {
          System.err.println(" (also tried suggested alternative \"" +
                             renameTo + "\")");
        }
        System.err.println();
      }
    }

    if (targetTable != null) {
      List<Tuple> cols = new ArrayList<CopySchemaData.Tuple>(table.getColumns()
          .size());
      for (Column column : table.getColumns()) {
        String colName = column.getName();
        if (colName.length() > maxLenColumn) {
          colName = colName.substring(0, maxLenColumn);
        }
        Tuple tuple = new Tuple();
        tuple.from = column.getName();
        Column targetColumn = targetTable.getColumn(colName);
        if (targetColumn == null) {
          String renameTo = SqlTool.getProp("rename.column." + colName);
          if (renameTo != null) {
            targetColumn = targetTable.getColumn(renameTo);
          }
          if (targetColumn == null) {
            System.err.print("No column in target Table \"" +
                             targetTable.getName() +
                             "\" to receive data of \"" + name + "." + colName +
                             "\"");
            if (renameTo != null) {
              System.err.println(" (also tried suggested alternative \"" +
                                 renameTo + "\")");
            }
            System.err.println();
          }
        }

        final TypeClass clazz = column.getType().getClazz();

        if (targetColumn != null) {
          tuple.to = targetColumn.getName();
          if ((clazz == TypeClass.MONEY) || (targetColumn.getType().getClazz() == TypeClass.MONEY)) {
            tuple.convertMoney = true;
          }
        }

        if ((clazz == TypeClass.CLOB) || (clazz == TypeClass.NCLOB) ||
            (((clazz == TypeClass.VARCHAR) || (clazz == TypeClass.NVARCHAR)) && (column.getType().getLength() == -1))) {
          tuple.copyClob = true;
        }
        cols.add(tuple);
      }

      // System.err.println("Mapping for table \""+name+"\" (target table \""+targetTable.getName()+"\")");
      // for (Tuple tuple: cols) {
      // System.err.println("  \""+tuple.from+"\" => \""+tuple.to+"\"");
      // }
      StringBuffer buf = new StringBuffer();
      boolean doComma = false;
      buf.append("INSERT INTO ").append(targetTable.getName()).append("(");
      for (Tuple tuple : cols) {
        if (doComma) {
          buf.append(',');
        }
        else {
          doComma = true;
        }
        buf.append(tuple.to);
      }
      buf.append(")VALUES(");
      doComma = false;
      for (int i = 0; i < cols.size(); i++) {
        if (doComma) {
          buf.append(',');
        }
        else {
          doComma = true;
        }
        if (cols.get(i).convertMoney) {
          buf.append("CAST(? AS money)");
        }
        else {
          buf.append('?');
        }
      }
      buf.append(')');

      Statement inputSt = null;
      ResultSet rs = null;
      PreparedStatement outputSt = null;
      NumberFormat nf = NumberFormat.getNumberInstance();
      nf.setGroupingUsed(false);

      int rows = 0;
      try {
        inputSt = inputDb.createStatement();
        rs = inputSt.executeQuery("SELECT * FROM [" + table.getName()+"]");
        outputSt = outputDb.prepareStatement(buf.toString());

        while (rs.next()) {
          for (int i = 0; i < cols.size(); i++) {
            Tuple thisCol = cols.get(i);
            if (thisCol.convertMoney) {
//              PGmoney data = new PGmoney(rs.getDouble(thisCol.from));
//              outputSt.setObject(i+1, data);
              Double value = rs.getDouble(thisCol.from);
              String newValue = (value == null) ? null : nf.format(value);
              outputSt.setString(i+1, newValue);
            }
            else if (thisCol.copyClob) {
              Clob value =  rs.getClob(thisCol.from);
              outputSt.setClob(i+1, value);
            }
            else {
              outputSt.setObject(i+1, rs.getObject(thisCol.from));
            }
          }
          outputSt.executeUpdate();
//          outputSt.close();
          rows += 1;
        }
      }
      catch (SQLException e) {
        System.err.println("Query: \""+buf.toString()+"\"");
        e.printStackTrace();
        try {
          outputDb.rollback();
        }
        catch (SQLException e1) {
          e1.printStackTrace();
        }
      }
      finally {
        if (outputSt != null) {
          try {
            outputSt.close();
          }
          catch (SQLException e) {
          }
        }
        if (rs != null) {
          try {
            rs.close();
          }
          catch (SQLException e) {
          }
        }
        if (inputSt != null) {
          try {
            inputSt.close();
          }
          catch (SQLException e) {
          }
        }
      }
      System.err.println("Copied "+rows+" row(s) from "+table.getName()+" to "+targetTable.getName());
    }
  }
}
