/**
 * 
 */
package nl.rakis.sql.ddl;

import nl.rakis.sql.DbDriver;

/**
 * @author bertl
 * 
 */
public abstract class SchemaCreatorBase
  implements SchemaGenerator
{
  private DbDriver db_;

  /**
   * 
   */
  public SchemaCreatorBase()
  {
  }

  /**
   * @param db
   */
  public SchemaCreatorBase(DbDriver db)
  {
    this.db_ = db;
  }

  /**
   * @param db the db to set
   */
  public void setDb(DbDriver db)
  {
    this.db_ = db;
  }

  /**
   * @return the db
   */
  public DbDriver getDb()
  {
    return db_;
  }
}
