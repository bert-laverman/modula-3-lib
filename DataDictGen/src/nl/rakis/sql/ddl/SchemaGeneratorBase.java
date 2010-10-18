/**
 * 
 */
package nl.rakis.sql.ddl;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import nl.rakis.sql.DbDriver;
import nl.rakis.sql.ddl.model.CheckConstraint;
import nl.rakis.sql.ddl.model.Column;
import nl.rakis.sql.ddl.model.ColumnedConstraint;
import nl.rakis.sql.ddl.model.Constraint;
import nl.rakis.sql.ddl.model.ForeignKeyConstraint;
import nl.rakis.sql.ddl.model.Index;
import nl.rakis.sql.ddl.model.NamedObject;
import nl.rakis.sql.ddl.model.Schema;
import nl.rakis.sql.ddl.model.SchemaObject;
import nl.rakis.sql.ddl.model.Table;
import nl.rakis.sql.ddl.model.UniqueConstraint;
import nl.rakis.sql.ddl.model.View;

/**
 * @author bertl
 * 
 */
public abstract class SchemaGeneratorBase
  implements SchemaGenerator
{
  public static final Table[]      NO_TABLES      = {};
  public static final Column[]     NO_COLUMNS     = {};
  public static final Constraint[] NO_CONSTRAINTS = {};
  public static final Index[]      NO_INDICES     = {};
  public static final View[]       NO_VIEWS       = {};

  private DbDriver                 driver_;
  private PrintWriter              writer_;
  private Connection               db_;
  private boolean                  sqlFormatted_  = true;

  /**
   * 
   */
  public SchemaGeneratorBase() {
  }

  /**
   * @param writer
   */
  public SchemaGeneratorBase(DbDriver driver, PrintWriter writer) {
    this.driver_ = driver;
    this.writer_ = writer;

    this.sqlFormatted_ = driver.isSqlFormatted();
  }

  /**
   * @param driver
   * @param db
   */
  public SchemaGeneratorBase(DbDriver driver, Connection db) {
    this.driver_ = driver;
    this.setDb(db);

    this.sqlFormatted_ = false;
  }

  /**
   * @param driver
   *          the driver to set
   */
  public void setDriver(DbDriver driver) {
    driver_ = driver;
  }

  /**
   * @return the driver
   */
  public DbDriver getDriver() {
    return driver_;
  }

  /**
   * @param writer
   *          the writer to set
   */
  public void setWriter(PrintWriter writer) {
    this.writer_ = writer;
  }

  /**
   * @return the writer
   */
  public PrintWriter getWriter() {
    return writer_;
  }

  /**
   * @param db
   *          the db to set
   */
  public void setDb(Connection db) {
    db_ = db;
  }

  /**
   * @return the db
   */
  public Connection getDb() {
    return db_;
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

  /**
   * @param buf
   * @param name
   */
  public void appendName(StringBuffer buf, String name) {
    buf.append(name);
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

  public abstract void appendDef(StringBuffer buf, Column column);

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
  public abstract String getCreateDdl(Table table);

  /**
   * @param table
   * @return
   */
  public abstract String getDropDdl(Table table);

  /**
   * NOTE: All columns are expected to be from the same table!
   * 
   * @param columns
   * @return
   */
  public abstract String getCreateDdl(Column... columns);

  /**
   * NOTE: All columns are expected to be from the same table!
   * 
   * @param columns
   * @return
   */
  public abstract String getDropDdl(Column... columns);

  /**
   * NOTE: All constraints are expected to be from the same table!
   * 
   * @param constraints
   * @return
   */
  public abstract String getCreateDdl(Constraint... constraints);

  /**
   * NOTE: All columns are expected to be from the same table!
   * 
   * @param constraints
   * @return
   */
  public abstract String getDropDdl(Constraint... constraints);

  /**
   * @param index
   * @return
   */
  public abstract String getCreateDdl(Index index);

  /**
   * @param index
   * @return
   */
  public abstract String getDropDdl(Index index);

  /**
   * @param view
   * @return
   */
  public abstract String getCreateDdl(View view);

  /**
   * @param view
   * @return
   */
  public abstract String getDropDdl(View view);

  /**
   * @param ddl
   */
  protected void executeDdl(String ddl) {
    try {
      Statement st = getDb().createStatement();
      st.executeUpdate(ddl);
    }
    catch (SQLException e) {
      System.err.println("Exception while executing DDL statement \""+ddl+"\"");
      e.printStackTrace();
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see nl.rakis.sql.ddl.SchemaGenerator#create(nl.rakis.sql.ddl.model.Schema)
   */
  @Override
  public void create(Schema schema) {
    create(schema.getTables().toArray(NO_TABLES));
    for (Table table : schema.getTables()) {
      create(table.getForeignKeys().toArray(NO_CONSTRAINTS));
      for (UniqueConstraint uk : table.getUniqueKeys()) {
        if ((table.getPrimaryKey() != null) &&
            !uk.getName().equals(table.getPrimaryKey().getName()))
        {
          create(uk);
        }
      }
      create(table.getCheckConstraints().toArray(NO_CONSTRAINTS));
      create(table.getNonKeyIndices().toArray(NO_INDICES));
    }
    create(schema.getViews().toArray(NO_VIEWS));
  }

  @Override
  public void create(Table... tables) {
    for (Table table : tables) {
      final String ddlString = getCreateDdl(table);
  
      executeDdl(ddlString);
    }
  }

  @Override
  public void create(Column... columns) {
    if (columns.length > 0) {
      final String ddlString = getCreateDdl(columns);
  
      executeDdl(ddlString);
    }
  }

  @Override
  public void create(Constraint... constraints) {
    if (constraints.length > 0) {
      final String ddlString = getCreateDdl(constraints);
  
      executeDdl(ddlString);
    }
  }

  @Override
  public void create(Index... indices) {
    for (Index index : indices) {
      final String ddlString = getCreateDdl(index);
  
      executeDdl(ddlString);
    }
  }

  @Override
  public void create(View... views) {
    for (View view : views) {
      final String ddlString = getCreateDdl(view);
  
      executeDdl(ddlString);
    }
  }

  @Override
  public void drop(Table... tables) {
    for (Table table : tables) {
      final String ddlString = getDropDdl(table);
  
      executeDdl(ddlString);
    }
  }

  @Override
  public void drop(Column... columns) {
    if (columns.length > 0) {
      final String ddlString = getDropDdl(columns);
  
      executeDdl(ddlString);
    }
  }

  @Override
  public void drop(Constraint... constraints) {
    if (constraints.length > 0) {
      final String ddlString = getDropDdl(constraints);
  
      executeDdl(ddlString);
    }
  }

  @Override
  public void drop(Index... indices) {
    for (Index index : indices) {
      final String ddlString = getDropDdl(index);
  
      executeDdl(ddlString);
    }
  }

  @Override
  public void drop(View... views) {
    for (View view : views) {
      final String ddlString = getDropDdl(view);
  
      executeDdl(ddlString);
    }
  }
}
