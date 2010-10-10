/**
 * 
 */
package nl.rakis.sql.postgresql;

import java.io.PrintWriter;

import nl.rakis.sql.DbDriver;

/**
 * @author bertl
 *
 */
public class PostgreSqlSchemaWriter
  extends PostgreSqlSchemaGeneratorBase
{

  /**
   * 
   */
  public PostgreSqlSchemaWriter() {
    super();
  }

  /**
   * @param writer
   */
  public PostgreSqlSchemaWriter(DbDriver driver, PrintWriter writer) {
    super(driver, writer);
  }

  
  /* (non-Javadoc)
   * @see nl.rakis.sql.ddl.SchemaGeneratorBase#executeDdl(java.lang.String)
   */
  @Override
  protected void executeDdl(String ddl) {
    final PrintWriter pw = getWriter();

    pw.print(ddl);
    pw.println(';');
    pw.println();
  }

}
