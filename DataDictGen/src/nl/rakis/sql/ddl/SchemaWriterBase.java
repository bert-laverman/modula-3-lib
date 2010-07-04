/**
 * 
 */
package nl.rakis.sql.ddl;

import java.io.PrintWriter;

/**
 * @author bertl
 *
 */
public abstract class SchemaWriterBase
  implements SchemaGenerator
{
  private PrintWriter writer_;

  /**
   * 
   */
  public SchemaWriterBase()
  {
  }

  /**
   * @param writer
   */
  public SchemaWriterBase(PrintWriter writer)
  {
    this.writer_ = writer;
  }

  /**
   * @param writer the writer to set
   */
  public void setWriter(PrintWriter writer)
  {
    this.writer_ = writer;
  }

  /**
   * @return the writer
   */
  public PrintWriter getWriter()
  {
    return writer_;
  }
}
