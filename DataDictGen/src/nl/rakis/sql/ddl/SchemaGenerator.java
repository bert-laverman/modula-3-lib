/**
 * 
 */
package nl.rakis.sql.ddl;

import nl.rakis.sql.ddl.model.Column;
import nl.rakis.sql.ddl.model.Constraint;
import nl.rakis.sql.ddl.model.Index;
import nl.rakis.sql.ddl.model.Schema;
import nl.rakis.sql.ddl.model.Table;

/**
 * @author bertl
 * 
 */
public interface SchemaGenerator
{
  void drop(Table table);
  void drop(Column cons);
  void drop(Constraint cons);
  void drop(Index index);

  void create(Schema schema);
  void create(Table table);
  void create(Column cons);
  void create(Constraint cons);
  void create(Index index);
}
