/**
 * 
 */
package nl.rakis.sql.ddl.model;

import static javax.xml.bind.annotation.XmlAccessType.NONE;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import nl.rakis.util.EqualsUtil;

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
  public SchemaObject() {
    super();
  }

  /**
   * @param name
   */
  public SchemaObject(String name) {
    super(name);
  }

  /**
   * @param catalog
   * @param schema
   * @param name
   */
  public SchemaObject(Schema schema, String name) {
    super(name);

    this.schema_ = schema;
  }

  /**
   * @param schema
   *          the schema to set
   */
  public void setSchema(Schema schema) {
    this.schema_ = schema;
  }

  /**
   * @return the schema
   */
  public Schema getSchema() {
    return schema_;
  }

  /**
   * Two schema objects the same? Can't decide without knowing what they are,
   * so no.
   * 
   * @param that
   * @return
   */
  public boolean same(SchemaObject that) {
    return false;
  }

  /*
   * (non-Javadoc)
   * 
   * @see nl.rakis.sql.ddl.model.NamedObject#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    boolean result = (obj != null) && (obj instanceof SchemaObject);

    // As for being equal, all we can check is schema and object name
    if (result) {
      SchemaObject that = (SchemaObject) obj;

      result = EqualsUtil
          .equals(this.schema_.getName(), that.schema_.getName()) &&
               EqualsUtil.equals(this.getName(), that.getName());
    }
    return result;
  }

}
