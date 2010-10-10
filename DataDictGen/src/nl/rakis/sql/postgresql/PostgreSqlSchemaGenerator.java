/**
 * 
 */
package nl.rakis.sql.postgresql;

import java.sql.Connection;

import nl.rakis.sql.DbDriver;

/**
 * @author bertl
 *
 */
public class PostgreSqlSchemaGenerator
  extends PostgreSqlSchemaGeneratorBase
{

  /**
   * 
   */
  public PostgreSqlSchemaGenerator() {
    super();
  }

  /**
   * @param driver
   * @param writer
   */
  public PostgreSqlSchemaGenerator(DbDriver driver, Connection db) {
    super(driver, db);
  }

}
