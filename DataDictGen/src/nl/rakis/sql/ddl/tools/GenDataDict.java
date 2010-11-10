/**
 * 
 */
package nl.rakis.sql.ddl.tools;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Collection;

import nl.rakis.sql.DbDriver;
import nl.rakis.sql.SqlTool;
import nl.rakis.sql.ddl.model.Column;
import nl.rakis.sql.ddl.model.ColumnedConstraint;
import nl.rakis.sql.ddl.model.Constraint;
import nl.rakis.sql.ddl.model.ForeignKeyConstraint;
import nl.rakis.sql.ddl.model.Schema;
import nl.rakis.sql.ddl.model.Table;

/**
 * @author bertl
 * 
 */
public class GenDataDict
{

  private static PrintWriter out    = null;
  private static DbDriver    driver = null;

  private static void printTag(String tag, String... args) {
    out.print('<');
    out.print(tag);

    if (args.length > 0) {
      out.print('>');

      for (int i = 0; i < args.length; i++) {
        if (i > 0) {
          out.print(' ');
        }
        out.print(args[i]);
      }

      out.print('<');
      out.print('/');
      out.print(tag);
    }
    else {
      out.print('/');
    }
    out.println('>');
  }

  private static void printOpen(String tag, boolean doLn) {
    out.print('<');
    out.print(tag);
    out.print('>');
    if (doLn) {
      out.println();
    }
  }

  private static void printOpen(String tag) {
    printOpen(tag, false);
  }

  private static void printClose(String tag, boolean doLn) {
    out.print('<');
    out.print('/');
    out.print(tag);
    out.print('>');
    if (doLn) {
      out.println();
    }
  }

  private static void printClose(String tag) {
    printClose(tag, true);
  }

  private static void dumpColumns(Collection<Column> columns) {
    printOpen("table", true);
    printOpen("tr");
    printTag("th", "Name");
    printTag("th", "Type");
    printTag("th", "Is nullable");
    printTag("th", "Description");
    printClose("tr");

    for (Column column : columns) {
      printOpen("tr", true);
      printTag("td", column.getName());
      printTag("td", driver.buildTypeString(column.getType()));
      printTag("td", column.isNullable() ? "NULL" : "NOT NULL");
      printClose("tr");
    }

    printClose("table");
  }

  private static String buildDefinition(Constraint cons) {
    StringBuffer buf = new StringBuffer();

    if (cons instanceof ColumnedConstraint) {
      ColumnedConstraint ccons = (ColumnedConstraint) cons;

      buf.append('(');
      boolean doComma = false;

      for (String columnName : ccons.getColumnNames()) {
        if (doComma) {
          buf.append(',');
        }
        else {
          doComma = true;
        }
        buf.append(columnName);
      }
      buf.append(')');

      if (cons instanceof ForeignKeyConstraint) {
        ColumnedConstraint ref = ((ForeignKeyConstraint) cons).getReference();

        buf.append(" REFERENCES ").append(ref.getTable().getName()).append('(');
        doComma = false;

        for (String columnName : ref.getColumnNames()) {
          if (doComma) {
            buf.append(',');
          }
          else {
            doComma = true;
          }
          buf.append(columnName);
        }
        buf.append(')');
      }
    }

    return buf.toString();
  }

  private static void dumpConstraints(Collection<Constraint> constraints) {
    printOpen("table border=0", true);

    for (Constraint cons : constraints) {
      printOpen("tr", true);
      printTag("th", "Name");
      printTag("td", cons.getName());
      printClose("tr");

      final String consType = cons.getType().getName();
      printOpen("tr", true);
      printTag("th", "Type");
      printTag("td", consType);
      printClose("tr");

      printOpen("tr", true);
      printTag("th", "Definition");
      printTag("td", buildDefinition(cons));
      printClose("tr");

      //printTag("tr");
    }
    printClose("table");
  }

  private static void printTableHeader(String schema, String table) {
    printTag("h3", "Table", table);
    printTag("h4", "Summary");
    printTag("p", "Description of table", table, "goes here.");
  }

  private static void printSchema(Schema schema)
    throws SQLException
  {
    for (Table table : schema.getTables()) {
      printTableHeader(schema.getName(), table.getName());

      printTag("h4", "Columns");
      dumpColumns(table.getColumns());
      printTag("h4", "Constraints");
      dumpConstraints(table.getConstraints());
    }
  }

  public static void main(String[] args) {
    SqlTool.init(args);

    try {
      System.err.println("Loading schema");
      Schema schema = SqlTool.getInputSchemaLoader().load(SqlTool
                                                         .getInputSchemaName());

      out = SqlTool.getWriter();
      driver = SqlTool.getInputDriver();
      printSchema(schema);
    }
    catch (Exception e) {
      SqlTool.error(e.getMessage());
      e.printStackTrace();
    }
    finally {
      SqlTool.cleanup();
    }
  }
}
