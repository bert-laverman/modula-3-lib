/**
 * 
 */
package nl.rakis.sql.ddl.model;

import static javax.xml.bind.annotation.XmlAccessType.NONE;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import nl.rakis.util.EqualsUtil;

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

  /**
   * Can't decide on the sameness of NamedObjects.
   * @param that
   * @return
   */
  public boolean same(NamedObject that) {
    return false;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    boolean result = (obj != null) && (obj instanceof NamedObject);

    // Equality: All we can check is the name
    if (result) {
      NamedObject that = (NamedObject) obj;
      result = EqualsUtil.equals(this.name_, that .name_);
    }
    return result;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return (this.name_ == null) ? 0 : this.name_.hashCode();
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return this.name_;
  }

}
