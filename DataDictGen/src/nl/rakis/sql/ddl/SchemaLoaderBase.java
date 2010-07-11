/**
 * 
 */
package nl.rakis.sql.ddl;

import java.io.Reader;
import java.sql.Connection;

import nl.rakis.sql.DbDriver;

/**
 * @author bertl
 * 
 */
public abstract class SchemaLoaderBase
  implements SchemaLoader
{

  private DbDriver   driver_;

  private Connection db_;

  private Reader     reader_;

  /**
   * 
   */
  public SchemaLoaderBase() {
    super();
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
   * @param reader
   *          the reader to set
   */
  public void setReader(Reader reader) {
    reader_ = reader;
  }

  /**
   * @return the reader
   */
  public Reader getReader() {
    return reader_;
  }

}
