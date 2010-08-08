/**
 * 
 */
package nl.rakis.sql.ddl.model;

import static javax.xml.bind.annotation.XmlAccessType.NONE;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import nl.rakis.util.EqualsUtil;

/**
 * @author bertl
 * 
 */
@XmlType(name = "ForeignKeyConstraintType")
@XmlAccessorType(NONE)
public class ForeignKeyConstraint
  extends ColumnedConstraint
{

  /**
   * 
   */
  private static final long  serialVersionUID = 1L;

  private String             refTableName_;

  private String             refKeyName_;

  private ColumnedConstraint reference_       = null;

  private ReferenceAction    updateRule_      = null;

  private ReferenceAction    deleteRule_      = null;

  public ForeignKeyConstraint() {
    super();

    setType(ConstraintType.FOREIGN_KEY);
  }

  /**
   * @param table
   * @param type
   */
  public ForeignKeyConstraint(Table table, ConstraintType type) {
    super(table, type);
  }

  /**
   * @param table
   * @param type
   */
  public ForeignKeyConstraint(Table table) {
    super(table, ConstraintType.FOREIGN_KEY);
  }

  /**
   * @param table
   * @param name
   * @param type
   */
  public ForeignKeyConstraint(Table table, String name) {
    super(table, name, ConstraintType.FOREIGN_KEY);
  }

  /**
   * @param table
   * @param catalog
   * @param schema
   * @param name
   * @param type
   */
  public ForeignKeyConstraint(Table table, Schema schema, String name) {
    super(table, schema, name, ConstraintType.FOREIGN_KEY);
  }

  /**
   * @param refTableName
   *          the refTableName to set
   */
  public void setRefTableName(String refTableName) {
    refTableName_ = refTableName;
  }

  /**
   * @return the refTableName
   */
  @XmlElement(name = "ref-table", required = true, nillable = false)
  public String getRefTableName() {
    return refTableName_;
  }

  /**
   * @param refKeyName
   *          the refKeyName to set
   */
  public void setRefKeyName(String refKeyName) {
    refKeyName_ = refKeyName;
  }

  /**
   * @return the refKeyName
   */
  @XmlElement(name = "ref-key", required = true, nillable = false)
  public String getRefKeyName() {
    return refKeyName_;
  }

  /**
   * @param reference
   *          the reference to set
   */
  public void setReference(ColumnedConstraint reference) {
    this.reference_ = reference;
    this.refTableName_ = reference.getTable().getName();
    this.refKeyName_ = reference.getName();
  }

  /**
   * @return the reference
   */
  public ColumnedConstraint getReference() {
    if (this.reference_ == null) {
      this.reference_ = getSchema().getTable(this.refTableName_)
          .getColumnedConstraint(this.refKeyName_);
    }
    return reference_;
  }

  /**
   * @param updateRule
   *          the updateRule to set
   */
  public void setUpdateRule(ReferenceAction updateRule) {
    updateRule_ = updateRule;
  }

  /**
   * @return the updateRule
   */
  @XmlElement(name = "update-rule", required = false)
  public ReferenceAction getUpdateRule() {
    return updateRule_;
  }

  /**
   * @param deleteRule
   *          the deleteRule to set
   */
  public void setDeleteRule(ReferenceAction deleteRule) {
    deleteRule_ = deleteRule;
  }

  /**
   * @return the deleteRule
   */
  @XmlElement(name = "delete-rule", required = false)
  public ReferenceAction getDeleteRule() {
    return deleteRule_;
  }

  /**
   * Two Foreign Key Constraints are the same if they are the same as
   * ColumnedConstraint and have the same rules
   * 
   * @param that
   * @return
   */
  public boolean same(ForeignKeyConstraint that) {
    return same((ColumnedConstraint) that) &&
           EqualsUtil.equals(this.updateRule_, that.updateRule_) &&
           EqualsUtil.equals(this.deleteRule_, that.deleteRule_);
  }

  /*
   * (non-Javadoc)
   * 
   * @see nl.rakis.sql.ddl.model.NamedObject#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    boolean result = (obj != null) && (obj instanceof ForeignKeyConstraint);

    // To be equal:
    // - Equal as ColumnedConstraint and same
    // - Refer to the same table/columns
    if (result) {
      ForeignKeyConstraint that = (ForeignKeyConstraint) obj;

      result = super.equals(that) && same(that) &&
               EqualsUtil.equals(this.reference_, that.reference_);
    }

    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see nl.rakis.sql.ddl.model.NamedObject#toString()
   */
  @Override
  public String toString() {
    // TODO Auto-generated method stub
    return super.toString();
  }

}
