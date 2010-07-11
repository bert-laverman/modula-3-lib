/**
 * 
 */
package nl.rakis.sql.ddl.tools;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import nl.rakis.sql.DbDriver;
import nl.rakis.sql.ddl.SchemaGenerator;
import nl.rakis.sql.ddl.SchemaLoader;
import nl.rakis.sql.ddl.model.Column;
import nl.rakis.sql.ddl.model.ForeignKeyConstraint;
import nl.rakis.sql.ddl.model.Schema;
import nl.rakis.sql.ddl.model.Table;
import nl.rakis.sql.ddl.model.TypeClass;
import nl.rakis.sql.jtds.JTDSDriver;

/**
 * @author bertl
 * 
 */
public class FindFKs
{

  /**
   * 
   */
  private static final String OUTPUT_ = "C:/temp/test.sql";
  private static final String DBNAME_ = "CMS_GP";
  //  private static final String DBNAME_ = "webapps";
  private static final String SERVER_ = "localhost";
  //  private static final String SERVER_ = "dune";
  private static final String USER_   = "sa";
  //private static final String USER_   = "postgres";
  private static final String PWD_    = "Krwa2Krwa";
  private static final String SCHEMA_ = "dbo";

  private static DbDriver     driver  = new JTDSDriver();
  //  private static DbDriver          driver  = new PostgreSQLDriver();

  private static PrintWriter  out     = null;

  public static void main(String[] args)
  {
    System.err.println("Starting up");
    try {
      driver.init();
      out = new PrintWriter(OUTPUT_);
    }
    catch (ClassNotFoundException e) {
      System.err.println("Unable to load jTDS Driver");
      System.exit(1);
    }
    catch (FileNotFoundException e) {
      System.err.println("Unable to open output file: " + e.getMessage());
      System.exit(2);
    }

    String url = driver.buildUrl(SERVER_, DBNAME_);
    //"jdbc:jtds:sqlserver://localhost/CMS;instance=SQLEXPRESS";
    try {
      System.err.println("Opening connection");
      Connection db = driver.getDb(url, USER_, PWD_);
      SchemaLoader loader = driver.getSchemaLoader(db);

      Schema schema = loader.load(SCHEMA_);

      System.err.println("Closing connection");
      db.close();

      SchemaGenerator gen = driver.getSchemaWriter(out);

      for (Table table : schema.getTables()) {
        for (Column column : table.getColumns()) {
          if (column.getType().getClazz() == TypeClass.INT) {
            // Possible FK
            String colName = column.getName();
            if (colName.toLowerCase().startsWith(table.getName().toLowerCase()))
            {
              colName = colName.substring(table.getName().length());
            }

            // Let's skip obvious PK's
            if (colName.equalsIgnoreCase("id")) {
              continue;
            }
            Table ref = schema.getTable(colName);
            if ((ref != null) && (ref.getPrimaryKey() != null)) {
              ForeignKeyConstraint fk = new ForeignKeyConstraint(
                                                                 table,
                                                                 table
                                                                     .getName() +
                                                                     "_" +
                                                                     ref
                                                                         .getName() +
                                                                     "_FK");
              fk.addColumn(column);
              fk.setReference(ref.getPrimaryKey());

              gen.create(fk);
            }
            else if (ref != null) { // && ref.getPrimaryKey() == null
              System.err.println("Possible match for " + table.getName() + "." +
                                 column.getName() + ", but no PK in " +
                                 ref.getName());
            }
            else {
              System.err.println("No match for " + table.getName() + "." +
                                 column.getName());
            }
          }
        }
      }
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
    finally {
      out.close();
    }
  }
}
