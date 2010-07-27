/**
 * 
 */
package nl.rakis.sql.ddl;

import java.io.PrintWriter;
import java.sql.Connection;

import nl.rakis.sql.DbDriver;
import nl.rakis.sql.ddl.model.Column;
import nl.rakis.sql.ddl.model.Constraint;
import nl.rakis.sql.ddl.model.Index;
import nl.rakis.sql.ddl.model.Schema;
import nl.rakis.sql.ddl.model.Table;
import nl.rakis.sql.ddl.model.UniqueConstraint;
import nl.rakis.sql.ddl.model.View;

/**
 * @author bertl
 * 
 */
public abstract class SchemaWriterBase
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

  /**
   * 
   */
  public SchemaWriterBase() {
  }

  /**
   * @param writer
   */
  public SchemaWriterBase(DbDriver driver, PrintWriter writer) {
    this.driver_ = driver;
    this.writer_ = writer;
  }

  /**
   * @param driver
   * @param db
   */
  public SchemaWriterBase(DbDriver driver, Connection db) {
    this.driver_ = driver;
    this.setDb(db);
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
}
