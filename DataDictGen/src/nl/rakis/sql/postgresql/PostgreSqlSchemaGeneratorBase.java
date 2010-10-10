/**
 * 
 */
package nl.rakis.sql.postgresql;

import java.io.PrintWriter;
import java.sql.Connection;

import nl.rakis.sql.DbDriver;
import nl.rakis.sql.ddl.SchemaGeneratorBase;
import nl.rakis.sql.ddl.model.Column;
import nl.rakis.sql.ddl.model.Constraint;
import nl.rakis.sql.ddl.model.Index;
import nl.rakis.sql.ddl.model.Table;
import nl.rakis.sql.ddl.model.View;

/**
 * @author bertl
 *
 */
public abstract class PostgreSqlSchemaGeneratorBase
  extends SchemaGeneratorBase
{

  /**
   * 
   */
  public PostgreSqlSchemaGeneratorBase() {
    super();
  }

  /**
   * @param driver
   * @param writer
   */
  public PostgreSqlSchemaGeneratorBase(DbDriver driver, PrintWriter writer) {
    super(driver, writer);
  }

  /**
   * @param driver
   * @param db
   */
  public PostgreSqlSchemaGeneratorBase(DbDriver driver, Connection db) {
    super(driver, db);

    this.setSqlFormatted(false);
  }

  /*
   * (non-Javadoc)
   * 
   * @see nl.rakis.sql.ddl.SchemaGeneratorBase#appendDef(java.lang.StringBuffer,
   * nl.rakis.sql.ddl.model.Column)
   */
  public void appendDef(StringBuffer buf, Column column) {
    appendName(buf, column);
    buf.append(' ');
    if (column.getSequence() == null) {
      buf.append(getDriver().buildTypeString(column.getType()));
    }
    else {
      switch (column.getType().getClazz()) {
      case INT: buf.append("serial"); break;
      case LONG: buf.append("serial8"); break;
      default:
        throw new NullPointerException("PostgreSqlSchemaGeneratorBase.appendDef(..., Column): Can only generate sequencing column for INT and LONG");
      }
    }
    buf.append(' ').append(column.isNullable() ? "NULL" : "NOT NULL");
    if (column.getDefault() != null) {
      buf.append(" DEFAULT ").append(column.getDefault());
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * nl.rakis.sql.ddl.SchemaGeneratorBase#getCreateDdl(nl.rakis.sql.ddl.model
   * .Table)
   */
  @Override
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

  /*
   * (non-Javadoc)
   * 
   * @see
   * nl.rakis.sql.ddl.SchemaGeneratorBase#getDropDdl(nl.rakis.sql.ddl.model.
   * Table)
   */
  @Override
  public String getDropDdl(Table table) {
    StringBuffer buf = new StringBuffer();
  
    buf.append("DROP TABLE ");
    appendName(buf, table);
  
    return buf.toString();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * nl.rakis.sql.ddl.SchemaGeneratorBase#getCreateDdl(nl.rakis.sql.ddl.model
   * .Column[])
   */
  @Override
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

  /*
   * (non-Javadoc)
   * 
   * @see
   * nl.rakis.sql.ddl.SchemaGeneratorBase#getDropDdl(nl.rakis.sql.ddl.model.
   * Column[])
   */
  @Override
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

  /*
   * (non-Javadoc)
   * 
   * @see
   * nl.rakis.sql.ddl.SchemaGeneratorBase#getCreateDdl(nl.rakis.sql.ddl.model
   * .Constraint[])
   */
  @Override
  public String getCreateDdl(Constraint... constraints) {
    StringBuffer buf = new StringBuffer();
  
    if (constraints.length > 0) {
      if (constraints[0].getTable() == null) {
        throw new NullPointerException("getCreateDdl(Constraint...): Don't know table of constraint \""+constraints[0].getName()+"\"");
      }
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

  /*
   * (non-Javadoc)
   * 
   * @see
   * nl.rakis.sql.ddl.SchemaGeneratorBase#getDropDdl(nl.rakis.sql.ddl.model.
   * Constraint[])
   */
  @Override
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

  /*
   * (non-Javadoc)
   * 
   * @see
   * nl.rakis.sql.ddl.SchemaGeneratorBase#getCreateDdl(nl.rakis.sql.ddl.model
   * .Index)
   */
  @Override
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

  /*
   * (non-Javadoc)
   * 
   * @see
   * nl.rakis.sql.ddl.SchemaGeneratorBase#getDropDdl(nl.rakis.sql.ddl.model.
   * Index)
   */
  @Override
  public String getDropDdl(Index index) {
    StringBuffer buf = new StringBuffer();
  
    buf.append("DROP INDEX ");
    appendName(buf, index);
  
    return buf.toString();
  }

  /* (non-Javadoc)
   * @see nl.rakis.sql.ddl.SchemaGeneratorBase#getCreateDdl(nl.rakis.sql.ddl.model.View)
   */
  @Override
  public String getCreateDdl(View view) {
    StringBuffer buf = new StringBuffer();
  
    buf.append("CREATE VIEW ");
    appendName(buf, view);
    buf.append(" AS ").append(view.getDefinition());
  
    return buf.toString();
  }

  /* (non-Javadoc)
   * @see nl.rakis.sql.ddl.SchemaGeneratorBase#getDropDdl(nl.rakis.sql.ddl.model.View)
   */
  @Override
  public String getDropDdl(View view) {
    StringBuffer buf = new StringBuffer();
  
    buf.append("DROP VIEW ");
    appendName(buf, view);
  
    return buf.toString();
  }

}
