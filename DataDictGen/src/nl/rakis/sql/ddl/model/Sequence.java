/**
 * 
 */
package nl.rakis.sql.ddl.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import nl.rakis.util.CompareUtil;
import nl.rakis.util.EqualsUtil;

/**
 * @author bertl
 * 
 */
@XmlType(name = "SequenceType")
@XmlAccessorType(XmlAccessType.NONE)
public class Sequence
  extends NamedObject
  implements Comparable<Sequence>
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

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  @Override
  public int compareTo(Sequence o) {
    return CompareUtil.compare(getName(), o.getName());
  }

  /**
   * @param that
   * @return
   */
  public boolean same(Sequence that) {
    return EqualsUtil.equals(this.start_, that.start_) &&
           EqualsUtil.equals(this.increment_, that.increment_);
  }

  /* (non-Javadoc)
   * @see nl.rakis.sql.ddl.model.NamedObject#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    boolean result = (obj != null) && (obj instanceof Sequence);

    if (result) {
      Sequence that = (Sequence) obj;

      result = super.equals(that) && same(that);
    }

    return result;
  }

}
