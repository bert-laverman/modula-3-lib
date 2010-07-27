/**
 * 
 */
package nl.rakis.sql.sqlserver;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import nl.rakis.sql.DbDriver;
import nl.rakis.sql.ddl.model.Column;
import nl.rakis.sql.ddl.model.Constraint;
import nl.rakis.sql.ddl.model.Index;
import nl.rakis.sql.ddl.model.Table;
import nl.rakis.sql.ddl.model.View;

/**
 * @author bertl
 *
 */
public class SqlServerSchemaGenerator
  extends SqlServerSchemaWriterBase
{

  /**
   * 
   */
  public SqlServerSchemaGenerator() {
    super();
  }

  /**
   * @param driver
   * @param writer
   */
  public SqlServerSchemaGenerator(DbDriver driver, Connection db) {
    super(driver, db);
  }

  private void executeDdl(String ddl) {
    try {
      Statement st = getDb().createStatement();
      st.executeUpdate(ddl);
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /* (non-Javadoc)
   * @see nl.rakis.sql.ddl.SchemaGenerator#create(nl.rakis.sql.ddl.model.Table[])
   */
  @Override
  public void create(Table... tables) {
    for (Table table : tables) {
      final String ddlString = getCreateDdl(table);

      executeDdl(ddlString);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see nl.rakis.sql.ddl.SchemaGenerator#create(java.io.PrintWriter,
   * nl.rakis.sql.ddl.model.Column)
   */
  @Override
  public void create(Column... columns) {
    if (columns.length > 0) {
      final String ddlString = getCreateDdl(columns);

      executeDdl(ddlString);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see nl.rakis.sql.ddl.SchemaGenerator#create(java.io.PrintWriter,
   * nl.rakis.sql.ddl.model.Constraint)
   */
  @Override
  public void create(Constraint... constraints) {
    if (constraints.length > 0) {
      final String ddlString = getCreateDdl(constraints);

      executeDdl(ddlString);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see nl.rakis.sql.ddl.SchemaGenerator#create(nl.rakis.sql.ddl.model.Index)
   */
  @Override
  public void create(Index... indices) {
    for (Index index : indices) {
      final String ddlString = getCreateDdl(index);

      executeDdl(ddlString);
    }
  }

  /* (non-Javadoc)
   * @see nl.rakis.sql.ddl.SchemaGenerator#create(nl.rakis.sql.ddl.model.View[])
   */
  @Override
  public void create(View... views) {
    for (View view : views) {
      final String ddlString = getCreateDdl(view);

      executeDdl(ddlString);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see nl.rakis.sql.ddl.SchemaGenerator#drop(java.io.PrintWriter,
   * nl.rakis.sql.ddl.model.Table)
   */
  @Override
  public void drop(Table... tables) {
    for (Table table : tables) {
      final String ddlString = getDropDdl(table);

      executeDdl(ddlString);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see nl.rakis.sql.ddl.SchemaGenerator#drop(java.io.PrintWriter,
   * nl.rakis.sql.ddl.model.Column)
   */
  @Override
  public void drop(Column... columns) {
    if (columns.length > 0) {
      final String ddlString = getDropDdl(columns);

      executeDdl(ddlString);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see nl.rakis.sql.ddl.SchemaGenerator#drop(java.io.PrintWriter,
   * nl.rakis.sql.ddl.model.Constraint)
   */
  @Override
  public void drop(Constraint... constraints) {
    if (constraints.length > 0) {
      final String ddlString = getDropDdl(constraints);

      executeDdl(ddlString);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see nl.rakis.sql.ddl.SchemaGenerator#drop(nl.rakis.sql.ddl.model.Index)
   */
  @Override
  public void drop(Index... indices) {
    for (Index index : indices) {
      final String ddlString = getDropDdl(index);

      executeDdl(ddlString);
    }
  }

  /* (non-Javadoc)
   * @see nl.rakis.sql.ddl.SchemaGenerator#drop(nl.rakis.sql.ddl.model.View[])
   */
  @Override
  public void drop(View... views) {
    for (View view : views) {
      final String ddlString = getDropDdl(view);

      executeDdl(ddlString);
    }
  }

}
