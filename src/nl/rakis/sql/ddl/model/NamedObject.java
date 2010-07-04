/**
 * 
 */
package nl.rakis.sql.ddl.model;

import static javax.xml.bind.annotation.XmlAccessType.NONE;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author bertl
 * 
 */
@XmlType(name = "NamedObjectType")
@XmlAccessorType(NONE)
public class NamedObject
  implements Serializable
{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @XmlElement(name = "name", required = true)
  private String            name_            = null;

  /**
   * 
   */
  public NamedObject()
  {
    super();
  }

  /**
   * @param name
   */
  public NamedObject(String name)
  {
    this.name_ = name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name)
  {
    this.name_ = name;
  }

  /**
   * @return the name
   */
  public String getName()
  {
    return name_;
  }

}
