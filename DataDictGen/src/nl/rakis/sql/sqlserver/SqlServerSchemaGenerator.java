/**
 * 
 */
package nl.rakis.sql.sqlserver;

import java.sql.Connection;

import nl.rakis.sql.DbDriver;

/**
 * @author bertl
 *
 */
public class SqlServerSchemaGenerator
  extends SqlServerSchemaGeneratorBase
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

}
