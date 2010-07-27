/**
 * 
 */
package nl.rakis.sql.ddl.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * @author bertl
 * 
 */
@XmlType(name = "SequenceType")
@XmlAccessorType(XmlAccessType.NONE)
public class Sequence
  extends NamedObject
{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @XmlAttribute(name = "start", required = false)
  private Integer           start_           = null;

  @XmlAttribute(name = "increment", required = false)
  private Integer           increment_       = null;

  /**
   * 
   */
  public Sequence() {
    super();
  }

  /**
   * @param name
   */
  public Sequence(String name) {
    super(name);
  }

  /**
   * @param start
   *          the start to set
   */
  public void setStart(Integer start) {
    start_ = start;
  }

  /**
   * @return the start
   */
  public Integer getStart() {
    return start_;
  }

  /**
   * @param increment
   *          the increment to set
   */
  public void setIncrement(Integer increment) {
    increment_ = increment;
  }

  /**
   * @return the increment
   */
  public Integer getIncrement() {
    return increment_;
  }

}
