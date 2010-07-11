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
@XmlType(name = "TypeType", factoryClass = ObjectFactory.class, factoryMethod = "createType")
@XmlAccessorType(NONE)
public class Type
  implements Serializable
{

  /**
   * 
   */
  private static final long serialVersionUID = 1743148573208352239L;

  @XmlElement(name = "typeClass", required = true)
  private TypeClass         clazz_;

  @XmlElement(name = "precision", required = false)
  private Integer           precision_;

  @XmlElement(name = "scale", required = false)
  private Integer           scale_;

  @XmlElement(name = "length", required = false)
  private Integer           length_;

  @XmlElement(name = "national", required = true)
  private boolean           countInChars_;

  /**
   * 
   */
  public Type() {
    super();
  }

  /**
   * @param clazz
   */
  public Type(TypeClass clazz) {
    this.clazz_ = clazz;
  }

  public void setClazz(TypeClass clazz) {
    clazz_ = clazz;
  }

  public TypeClass getClazz() {
    return clazz_;
  }

  /**
   * @return the precision
   */
  public Integer getPrecision() {
    return this.precision_;
  }

  /**
   * @param precision
   *          the precision to set
   */
  public void setPrecision(Integer precision) {
    this.precision_ = precision;
  }

  /**
   * @return the scale
   */
  public Integer getScale() {
    return this.scale_;
  }

  /**
   * @param scale
   *          the scale to set
   */
  public void setScale(Integer scale) {
    this.scale_ = scale;
  }

  /**
   * @return the charLength
   */
  public Integer getLength() {
    return this.length_;
  }

  /**
   * @param length
   *          the length to set
   */
  public void setLength(Integer length) {
    this.length_ = length;
  }

  /**
   * @param countInChars
   *          the countInChars to set
   */
  public void setCountInChars(boolean countInChars) {
    this.countInChars_ = countInChars;
  }

  /**
   * @return the countInChars
   */
  public boolean isCountInChars() {
    return countInChars_;
  }

}
