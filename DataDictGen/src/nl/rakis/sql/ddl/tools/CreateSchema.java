/**
 * 
 */
package nl.rakis.sql.ddl.tools;

import nl.rakis.sql.SqlTool;
import nl.rakis.sql.ddl.model.Schema;

/**
 * @author bertl
 * 
 */
public class CreateSchema
{
  public static void main(String[] args)
  {
    SqlTool.init(args);

    try {
      System.err.println("Loading schema");
      Schema schema = SqlTool.getInputSchemaLoader().load(SqlTool.getInputSchemaName());

      System.err.println("Generating schema");
      schema.setName(SqlTool.getOutputSchemaName());
      SqlTool.getSchemaGenerator().create(schema);
    }
    catch (Exception e) {
      SqlTool.error(e.getMessage());
      e.printStackTrace();
    }
    finally {
      SqlTool.cleanup();
    }
  }
}
