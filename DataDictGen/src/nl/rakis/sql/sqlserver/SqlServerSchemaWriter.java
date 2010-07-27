/**
 * 
 */
package nl.rakis.sql.sqlserver;

import java.io.PrintWriter;

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
public class SqlServerSchemaWriter
  extends SqlServerSchemaWriterBase
{

  /**
   * 
   */
  public SqlServerSchemaWriter() {
    super();
  }

  /**
   * @param writer
   */
  public SqlServerSchemaWriter(DbDriver driver, PrintWriter writer) {
    super(driver, writer);
  }

  /*
   * (non-Javadoc)
   * 
   * @see nl.rakis.sql.ddl.SchemaGenerator#create(java.io.PrintWriter,
   * nl.rakis.sql.ddl.model.Table)
   */
  @Override
  public void create(Table... tables) {
    for (Table table : tables) {
      final String ddlString = getCreateDdl(table);
      final PrintWriter pw = getWriter();

      pw.println(ddlString);
      pw.println("GO");
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
      final PrintWriter pw = getWriter();

      pw.println(ddlString);
      pw.println("GO");
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
      final PrintWriter pw = getWriter();

      pw.println(ddlString);
      pw.println("GO");
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
      final PrintWriter pw = getWriter();

      pw.println(ddlString);
      pw.println("GO");
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see nl.rakis.sql.ddl.SchemaGenerator#create(nl.rakis.sql.ddl.model.View[])
   */
  @Override
  public void create(View... views) {
    for (View view : views) {
      final String ddlString = getCreateDdl(view);
      final PrintWriter pw = getWriter();

      pw.println(ddlString);
      pw.println("GO");
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
      final PrintWriter pw = getWriter();

      pw.println(ddlString);
      pw.println("GO");
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
      final PrintWriter pw = getWriter();

      pw.println(ddlString);
      pw.println("GO");
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
      final PrintWriter pw = getWriter();

      pw.println(ddlString);
      pw.println("GO");
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
      final PrintWriter pw = getWriter();

      pw.println(ddlString);
      pw.println("GO");
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see nl.rakis.sql.ddl.SchemaGenerator#drop(nl.rakis.sql.ddl.model.View[])
   */
  @Override
  public void drop(View... views) {
    for (View view : views) {
      final String ddlString = getDropDdl(view);
      final PrintWriter pw = getWriter();

      pw.println(ddlString);
      pw.println("GO");
    }
  }

}
