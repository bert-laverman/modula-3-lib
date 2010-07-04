/**
 * 
 */
package nl.rakis.sql.ddl.model;

import static javax.xml.bind.annotation.XmlAccessType.NONE;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * @author bertl
 * 
 */
@XmlType(name = "SchemaObjectType")
@XmlAccessorType(NONE)
public abstract class SchemaObject
  extends NamedObject
  implements Serializable
{
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private Schema            schema_          = null;

  /**
   * 
   */
  public SchemaObject()
  {
    super();
  }

  /**
   * @param name
   */
  public SchemaObject(String name)
  {
    super(name);
  }

  /**
   * @param catalog
   * @param schema
   * @param name
   */
  public SchemaObject(Schema schema, String name)
  {
    super(name);

    this.schema_ = schema;
  }

  /**
   * @param schema the schema to set
   */
  public void setSchema(Schema schema)
  {
    this.schema_ = schema;
  }

  /**
   * @return the schema
   */
  public Schema getSchema()
  {
    return schema_;
  }
}
