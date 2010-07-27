/**
 * 
 */
package nl.rakis.sql.ddl.model;

import static javax.xml.bind.annotation.XmlAccessType.NONE;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author bertl
 * 
 */
@XmlType(name = "ColumnType")
@XmlAccessorType(NONE)
public class Column
  extends NamedObject
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
}
