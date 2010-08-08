/**
 * 
 */
package nl.rakis.sql.ddl.model;

import static javax.xml.bind.annotation.XmlAccessType.NONE;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import nl.rakis.util.CompareUtil;

/**
 * @author bertl
 * 
 */
@XmlType(name = "TypeType", factoryClass = ObjectFactory.class, factoryMethod = "createType")
@XmlAccessorType(NONE)
public class Type
  implements Serializable, Comparable<Type>
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

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return buildTypeString(this.clazz_.name());
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    boolean result = false;

    if ((obj != null) && (obj instanceof Type)) {
      Type that = (Type) obj;

      result = this.clazz_.equals(that.clazz_) &&
               (this.countInChars_ == that.countInChars_);

      if (result) {
        result = (this.precision_ != null) ? this.precision_
            .equals(that.precision_) : (that.precision_ == null);
      }
      if (result) {
        result = (this.scale_ != null) ? this.scale_.equals(that.scale_)
            : (that.scale_ == null);
      }
      if (result) {
        result = (this.length_ != null) ? this.length_.equals(that.length_)
            : (that.length_ == null);
      }
    }
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    int result = this.clazz_.hashCode();

    if (this.precision_ != null) {
      result ^= (this.precision_ << 8);
    }
    if (this.scale_ != null) {
      result ^= (this.scale_ << 16);
    }
    if (this.length_ != null) {
      result ^= this.length_;
    }
    if (this.countInChars_) {
      result ^= 0xaaaaaaaa;
    }

    return result;
  }

  /**
   * @param name
   * @return
   */
  public String buildTypeString(String typeName) {
    StringBuffer buf = new StringBuffer();

    buf.append(typeName);
    if (this.clazz_.hasLength() && (this.length_ != null)) {
      buf.append('(');
      if (this.length_ == -1) {
        buf.append("MAX");
      }
      else {
        buf.append(Integer.toString(this.length_));
        if (this.countInChars_) {
          buf.append(" CHARS");
        }
        else {
          buf.append(" BYTES");
        }
      }
      buf.append(')');
    }
    else if (this.clazz_.hasPrecision() || this.clazz_.hasScale()) {
      buf.append('(');
      if (this.clazz_.hasPrecision() && (this.precision_ != null)) {
        buf.append(this.precision_.toString());
      }
      if (this.clazz_.hasScale() && (this.scale_ != null)) {
        buf.append(';').append(this.scale_.toString());
      }
      buf.append(')');
    }

    return buf.toString();
  }

  /* (non-Javadoc)
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  @Override
  public int compareTo(Type o) {
    int result = this.clazz_.compareTo(o.clazz_);

    if (result == 0) {
      result = CompareUtil.compare(this.length_, o.length_);
    }
    if (result == 0) {
      result = CompareUtil.compare(this.scale_, o.scale_);
    }
    if (result == 0) {
      result = CompareUtil.compare(this.precision_, o.precision_);
    }
    if (result == 0) {
      result = CompareUtil.compare(this.countInChars_, o.countInChars_);
    }
    return result;
  }

}
