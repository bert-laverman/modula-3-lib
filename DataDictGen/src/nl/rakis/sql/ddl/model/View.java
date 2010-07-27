/**
 * 
 */
package nl.rakis.sql.ddl.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * @author bertl
 * 
 */
@XmlType(name = "ViewType")
@XmlAccessorType(XmlAccessType.NONE)
public class View
  extends SchemaObject
{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String            definition_;

  /**
   * 
   */
  private View() {
    super();
  }

  /**
   * @param schema
   * @param name
   */
  private View(Schema schema, String name) {
    super(schema, name);
  }

  /**
   * @param name
   */
  private View(String name) {
    super(name);
  }

  /**
   * @param definition
   *          the definition to set
   */
  public void setDefinition(String definition) {
    definition_ = definition;
  }

  /**
   * @return the definition
   */
  public String getDefinition() {
    return definition_;
  }

}
