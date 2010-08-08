/**
 * 
 */
package nl.rakis.sql.ddl.model;

import static javax.xml.bind.annotation.XmlAccessType.NONE;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import nl.rakis.util.CompareUtil;
import nl.rakis.util.EqualsUtil;
import nl.rakis.util.HashUtil;

/**
 * @author bertl
 * 
 */
@XmlType(name = "ColumnType")
@XmlAccessorType(NONE)
public class Column
  extends NamedObject
  implements Comparable<Column>
{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public static final int   MAX              = -1;

  private Table             table_;

  @XmlElement(name = "type")
  private Type              type_;

  @XmlElement(name = "nullable", required = true, nillable = false)
  private boolean           nullable_;

  @XmlElement(name = "default-value", required = false, nillable = false)
  private String            default_;

  @XmlElement(name = "sequence", required = false, nillable = false)
  private Sequence          sequence_;

  /**
   * 
   */
  public Column() {
    super();
  }

  /**
   * @param name
   */
  public Column(String name) {
    super(name);
  }

  /**
   * @return the type
   */
  public Type getType() {
    return this.type_;
  }

  /**
   * @param table
   *          the table to set
   */
  public void setTable(Table table) {
    this.table_ = table;
  }

  /**
   * @return the table
   */
  public Table getTable() {
    return table_;
  }

  /**
   * @param type
   *          the type to set
   */
  public void setType(Type type) {
    this.type_ = type;
  }

  /**
   * @return the nullable
   */
  public boolean isNullable() {
    return this.nullable_;
  }

  /**
   * @param nullable
   *          the nullable to set
   */
  public void setNullable(boolean nullable) {
    this.nullable_ = nullable;
  }

  /**
   * @param _default
   *          the default to set
   */
  public void setDefault(String _default) {
    default_ = _default;
  }

  /**
   * @return the default
   */
  public String getDefault() {
    return default_;
  }

  /**
   * @param sequence
   *          the sequence to set
   */
  public void setSequence(Sequence sequence) {
    sequence_ = sequence;
  }

  /**
   * @return the sequence
   */
  public Sequence getSequence() {
    return sequence_;
  }

  /**
   * Two Columns are the same if they have the same type and have the same properties
   * @param that
   * @return
   */
  public boolean same(Column that) {
    return this.type_.equals(that.type_) &&
           (this.nullable_ == that.nullable_) &&
           EqualsUtil.equals(this.default_, that.default_) &&
           EqualsUtil.equals(this.sequence_, that.sequence_);
  }

  /*
   * (non-Javadoc)
   * 
   * @see nl.rakis.sql.ddl.model.NamedObject#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    boolean result = false;

    if ((obj != null) && (obj instanceof Column)) {
      Column that = (Column) obj;

      result = super.equals(that) && same(that);
    }
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see nl.rakis.sql.ddl.model.NamedObject#hashCode()
   */
  @Override
  public int hashCode() {
    return HashUtil.safeHash(getName()) + HashUtil.safeHash(type_) +
           HashUtil.safeHash(default_) + HashUtil.safeHash(sequence_);
  }

  /*
   * (non-Javadoc)
   * 
   * @see nl.rakis.sql.ddl.model.NamedObject#toString()
   */
  @Override
  public String toString() {
    return "Column " + getName();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  @Override
  public int compareTo(Column o) {
    int result = CompareUtil.compare(getName(), o.getName());

    if (result == 0) {
      result = CompareUtil.compare(this.nullable_, o.nullable_);
    }
    if (result == 0) {
      result = CompareUtil.compare(this.type_, o.type_);
    }
    if (result == 0) {
      result = CompareUtil.compare(this.default_, o.default_);
    }
    if (result == 0) {
      result = CompareUtil.compare(this.sequence_, o.sequence_);
    }
    return result;
  }

}
