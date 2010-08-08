/**
 * 
 */
package nl.rakis.sql.ddl.model;

import static javax.xml.bind.annotation.XmlAccessType.NONE;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import nl.rakis.util.EqualsUtil;

/**
 * @author bertl
 * 
 */
@XmlType(name = "ConstraintType")
@XmlAccessorType(NONE)
abstract public class Constraint
  extends SchemaObject
  implements Comparable<Constraint>
{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private Table             table_;

  private ConstraintType    type_;

  /**
   * 
   */
  public Constraint() {
    super();
  }

  /**
   * 
   */
  public Constraint(Table table, ConstraintType type) {
    super();

    this.table_ = table;
    this.type_ = type;
  }

  /**
   * @param name
   */
  public Constraint(Table table, String name, ConstraintType type) {
    super(name);

    this.table_ = table;
    this.type_ = type;
  }

  /**
   * @param catalog
   * @param schema
   * @param name
   */
  public Constraint(Table table, Schema schema, String name, ConstraintType type)
  {
    super(schema, name);

    this.table_ = table;
    this.type_ = type;
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
  public void setType(ConstraintType type) {
    this.type_ = type;
  }

  /**
   * @return the type
   */
  public ConstraintType getType() {
    return type_;
  }

  /*
   * (non-Javadoc)
   * 
   * @see nl.rakis.sql.ddl.model.NamedObject#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    boolean result = (obj != null) && (obj instanceof Constraint);

    // To be the same constraint:
    // - Same name
    // - Same schema
    if (result) {
      result = super.equals(obj);
    }
    // furthermore:
    // - Same table
    // - Same type
    if (result) {
      Constraint that = (Constraint) obj;

      result = EqualsUtil.equals(this.table_, that.table_) && same(that);
    }
    return result;
  }

  /**
   * Compares the Constraint type only. Table and name not important.
   * 
   * @param that
   * @return
   */
  public boolean same(Constraint that) {
    return EqualsUtil.equals(this.type_, that.type_);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  @Override
  public int compareTo(Constraint o) {
    return getName().compareTo(o.getName());
  }

}
