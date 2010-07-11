/**
 * 
 */
package nl.rakis.sql.ddl;

import java.sql.SQLException;

import nl.rakis.sql.ddl.model.Schema;

/**
 * @author bertl
 *
 */
public interface SchemaLoader
{

  public abstract Schema load(String name)
    throws SQLException;

}
