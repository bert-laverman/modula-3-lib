/**
 * 
 */
package nl.rakis.sql.ddl;

import nl.rakis.sql.ddl.model.Column;
import nl.rakis.sql.ddl.model.Constraint;
import nl.rakis.sql.ddl.model.Index;
import nl.rakis.sql.ddl.model.Schema;
import nl.rakis.sql.ddl.model.Table;
import nl.rakis.sql.ddl.model.View;

/**
 * @author bertl
 * 
 */
public interface SchemaGenerator
{
  void drop(Table...tables);
  void drop(Column...columns);
  void drop(Constraint...constraints);
  void drop(Index...indices);
  void drop(View...views);

  void create(Schema schema);
  void create(Table...tables);
  void create(Column...columns);
  void create(Constraint...constraints);
  void create(Index...indices);
  void create(View...views);
}
