/**
 * 
 */
package nl.rakis.sql.sqlserver;

import java.io.PrintWriter;
import java.sql.Connection;

import nl.rakis.sql.DbDriver;
import nl.rakis.sql.ddl.SchemaWriterBase;
import nl.rakis.sql.ddl.model.CheckConstraint;
import nl.rakis.sql.ddl.model.Column;
import nl.rakis.sql.ddl.model.ColumnedConstraint;
import nl.rakis.sql.ddl.model.Constraint;
import nl.rakis.sql.ddl.model.ForeignKeyConstraint;
import nl.rakis.sql.ddl.model.Index;
import nl.rakis.sql.ddl.model.NamedObject;
import nl.rakis.sql.ddl.model.SchemaObject;
import nl.rakis.sql.ddl.model.Sequence;
import nl.rakis.sql.ddl.model.Table;
import nl.rakis.sql.ddl.model.View;

/**
 * @author bertl
 * 
 */
public abstract class SqlServerSchemaWriterBase
  extends SchemaWriterBase
{

  private boolean sqlFormatted_ = true;

  /**
   * 
   */
  public SqlServerSchemaWriterBase() {
    super();
  }

  /**
   * @param driver
   * @param writer
   */
  public SqlServerSchemaWriterBase(DbDriver driver, PrintWriter writer) {
    super(driver, writer);

    this.setSqlFormatted(driver.isSqlFormatted());
  }

  /**
   * @param driver
   * @param db
   */
  public SqlServerSchemaWriterBase(DbDriver driver, Connection db) {
    super(driver, db);

    this.setSqlFormatted(false);
  }

  /**
   * @param buf
   * @param name
   */
  public void appendName(StringBuffer buf, String name) {
    buf.append('[').append(name).append(']');
  }

  /**
   * @param buf
   * @param object
   */
  public void appendName(StringBuffer buf, NamedObject object) {
    appendName(buf, object.getName());
  }

  /**
   * @param buf
   * @param object
   */
  public void appendName(StringBuffer buf, SchemaObject object) {
    if (object.getSchema() != null) {
      appendName(buf, object.getSchema());
      buf.append('.');
    }
    appendName(buf, (NamedObject) object);
  }

  /**
   * @param column
   */
  public void appendDef(StringBuffer buf, Column column) {
    appendName(buf, column);
    buf.append(' ').append(getDriver().buildTypeString(column.getType()))
        .append(' ').append(column.isNullable() ? "NULL" : "NOT NULL");
    if (column.getSequence() != null) {
      Sequence seq = column.getSequence();

      buf.append(" IDENTITY(");
      if (seq.getStart() != null) {
        buf.append(seq.getStart().toString());
      }
      else {
        buf.append('1');
      }
      buf.append(',');
      if (seq.getIncrement() != null) {
        buf.append(seq.getIncrement().toString());
      }
      else {
        buf.append('1');
      }
      buf.append(')');
    }
    else if (column.getDefault() != null) {
      buf.append(" DEFAULT ").append(column.getDefault());
    }
  }

  /**
   * @param buf
   * @param constraint
   */
  public void appendDef(StringBuffer buf, Constraint constraint) {
    final boolean doFormat = isSqlFormatted();

    buf.append("CONSTRAINT ");
    appendName(buf, constraint.getName());
    if (doFormat) {
      buf.append("\n    ");
    }
    else {
      buf.append(' ');
    }
    buf.append(constraint.getType().getName());
    if (constraint instanceof ColumnedConstraint) {
      ColumnedConstraint colCons = (ColumnedConstraint) constraint;

      buf.append('(');
      boolean doComma = false;
      for (String columnName : colCons.getColumnNames()) {
        if (doComma) {
          buf.append(',');
        }
        else {
          doComma = true;
        }
        appendName(buf, columnName);
      }
      buf.append(')');

      if (constraint instanceof ForeignKeyConstraint) {
        ForeignKeyConstraint key = (ForeignKeyConstraint) constraint;

        if (doFormat) {
          buf.append("\n    ");
        }
        else {
          buf.append(' ');
        }
        final ColumnedConstraint refCons = key.getReference();

        buf.append("REFERENCES ");
        appendName(buf, refCons.getTable());
        buf.append('(');
        doComma = false;
        for (String columnName : refCons.getColumnNames()) {
          if (doComma) {
            buf.append(',');
          }
          else {
            doComma = true;
          }
          appendName(buf, columnName);
        }
        buf.append(')');

        if (key.getDeleteRule() != null) {
          String action = getDriver()
              .referenceAction2String(key.getDeleteRule());
          if (action != null) {
            if (doFormat) {
              buf.append("\n      ");
            }
            else {
              buf.append(' ');
            }
            buf.append("ON DELETE ").append(action);
          }
        }

        if (key.getUpdateRule() != null) {
          String action = getDriver()
              .referenceAction2String(key.getUpdateRule());
          if (action != null) {
            if (doFormat) {
              buf.append("\n      ");
            }
            else {
              buf.append(' ');
            }
            buf.append("ON UPDATE ").append(action);
          }
        }
      }
    }
    else if (constraint instanceof CheckConstraint) {
      CheckConstraint chkCons = (CheckConstraint) constraint;

      buf.append(' ').append(chkCons.getExpression());
    }
  }

  /**
   * @param table
   * @return
   */
  public String getCreateDdl(Table table) {
    StringBuffer buf = new StringBuffer();

    buf.append("CREATE TABLE ");
    appendName(buf, table);
    buf.append('(');
    boolean doComma = false;
    for (Column column : table.getColumns()) {
      if (doComma) {
        buf.append(',');
      }
      else {
        doComma = true;
      }
      if (isSqlFormatted()) {
        buf.append("\n  ");
      }
      appendDef(buf, column);
    }
    // add Primary key constraint
    if (table.getPrimaryKey() != null) {
      if (doComma) {
        buf.append(',');
      }
      if (isSqlFormatted()) {
        buf.append("\n  ");
      }
      appendDef(buf, table.getPrimaryKey());
    }
    buf.append(')');

    return buf.toString();
  }

  /**
   * @param table
   * @return
   */
  public String getDropDdl(Table table) {
    StringBuffer buf = new StringBuffer();

    buf.append("DROP TABLE ");
    appendName(buf, table);

    return buf.toString();
  }

  /**
   * NOTE: All columns are expected to be from the same table!
   * 
   * @param columns
   * @return
   */
  public String getCreateDdl(Column... columns) {
    StringBuffer buf = new StringBuffer();

    if (columns.length > 0) {
      buf.append("ALTER TABLE ");
      appendName(buf, columns[0].getTable());
      buf.append(" ADD ");
      boolean doComma = false;
      for (Column column : columns) {
        if (doComma) {
          buf.append(',');
        }
        else {
          doComma = true;
        }
        if (isSqlFormatted()) {
          buf.append("\n  ");
        }
        appendDef(buf, column);
      }
    }
    return buf.toString();
  }

  /**
   * NOTE: All columns are expected to be from the same table!
   * 
   * @param columns
   * @return
   */
  public String getDropDdl(Column... columns) {
    StringBuffer buf = new StringBuffer();

    if (columns.length > 0) {
      buf.append("ALTER TABLE ");
      appendName(buf, columns[0].getTable());
      buf.append(" DROP COLUMN ");
      boolean doComma = false;
      for (Column column : columns) {
        if (doComma) {
          buf.append(',');
        }
        else {
          doComma = true;
        }
        if (isSqlFormatted()) {
          buf.append("\n  ");
        }
        appendName(buf, column);
      }
    }
    return buf.toString();
  }

  /**
   * NOTE: All constraints are expected to be from the same table!
   * 
   * @param constraints
   * @return
   */
  public String getCreateDdl(Constraint... constraints) {
    StringBuffer buf = new StringBuffer();

    if (constraints.length > 0) {
      buf.append("ALTER TABLE ");
      appendName(buf, constraints[0].getTable());
      buf.append(" ADD ");
      boolean doComma = false;
      for (Constraint constraint : constraints) {
        if (doComma) {
          buf.append(',');
        }
        else {
          doComma = true;
        }
        if (isSqlFormatted()) {
          buf.append("\n  ");
        }
        appendDef(buf, constraint);
      }
    }
    return buf.toString();
  }

  /**
   * NOTE: All columns are expected to be from the same table!
   * 
   * @param constraints
   * @return
   */
  public String getDropDdl(Constraint... constraints) {
    StringBuffer buf = new StringBuffer();

    if (constraints.length > 0) {
      buf.append("ALTER TABLE ");
      appendName(buf, constraints[0].getTable());
      buf.append(" DROP CONSTRAINT ");
      boolean doComma = false;
      for (Constraint constraint : constraints) {
        if (doComma) {
          buf.append(',');
        }
        else {
          doComma = true;
        }
        if (isSqlFormatted()) {
          buf.append("\n  ");
        }
        appendName(buf, constraint);
      }
    }
    return buf.toString();
  }

  /**
   * @param index
   * @return
   */
  public String getCreateDdl(Index index) {
    StringBuffer buf = new StringBuffer();

    buf.append("CREATE ");
    if (index.isUnique()) {
      buf.append("UNIQUE ");
    }
    buf.append("INDEX ");
    appendName(buf, index.getName());
    buf.append(" ON ");
    appendName(buf, index.getTable());
    buf.append('(');

    boolean doComma = false;
    for (String columnName : index.getColumnNames()) {
      if (doComma) {
        buf.append(',');
      }
      else {
        doComma = true;
      }
      appendName(buf, columnName);
    }
    buf.append(')');

    return buf.toString();
  }

  /**
   * @param index
   * @return
   */
  public String getDropDdl(Index index) {
    StringBuffer buf = new StringBuffer();

    buf.append("DROP INDEX ");
    appendName(buf, index);

    return buf.toString();
  }

  /**
   * @param view
   * @return
   */
  public String getCreateDdl(View view) {
    StringBuffer buf = new StringBuffer();

    buf.append("CREATE VIEW ");
    appendName(buf, view);
    buf.append(" AS ").append(view.getDefinition());

    return buf.toString();
  }

  /**
   * @param view
   * @return
   */
  public String getDropDdl(View view) {
    StringBuffer buf = new StringBuffer();

    buf.append("DROP VIEW ");
    appendName(buf, view);

    return buf.toString();
  }

  /**
   * @param sqlFormatted
   *          the sqlFormatted to set
   */
  public void setSqlFormatted(boolean sqlFormatted) {
    System.err.println("setting formatting to " + sqlFormatted);
    sqlFormatted_ = sqlFormatted;
  }

  /**
   * @return the sqlFormatted
   */
  public boolean isSqlFormatted() {
    return sqlFormatted_;
  }

}
