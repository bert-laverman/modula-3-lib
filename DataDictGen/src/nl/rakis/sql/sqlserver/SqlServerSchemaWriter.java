/**
 * 
 */
package nl.rakis.sql.sqlserver;

import java.io.PrintWriter;

import nl.rakis.sql.DbDriver;

/**
 * @author bertl
 * 
 */
public class SqlServerSchemaWriter
  extends SqlServerSchemaGeneratorBase
{

  /**
   * 
   */
  public SqlServerSchemaWriter() {
    super();
  }

  /**
   * @param writer
   */
  public SqlServerSchemaWriter(DbDriver driver, PrintWriter writer) {
    super(driver, writer);
  }

  
  /* (non-Javadoc)
   * @see nl.rakis.sql.ddl.SchemaGeneratorBase#executeDdl(java.lang.String)
   */
  @Override
  protected void executeDdl(String ddl) {
    final PrintWriter pw = getWriter();

    pw.println(ddl);
    pw.println("GO");
  }

}
