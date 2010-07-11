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

  private String             updateRule_      = null;

  private String             deleteRule_      = null;

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
   * @param refTableName the refTableName to set
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
   * @param refKeyName the refKeyName to set
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
      this.reference_ = getSchema().getTable(this.refTableName_).getColumnedConstraint(this.refKeyName_);
    }
    return reference_;
  }

  /**
   * @param updateRule
   *          the updateRule to set
   */
  public void setUpdateRule(String updateRule) {
    updateRule_ = updateRule;
  }

  /**
   * @return the updateRule
   */
  @XmlElement(name = "update-rule", required = false)
  public String getUpdateRule() {
    return updateRule_;
  }

  /**
   * @param deleteRule
   *          the deleteRule to set
   */
  public void setDeleteRule(String deleteRule) {
    deleteRule_ = deleteRule;
  }

  /**
   * @return the deleteRule
   */
  @XmlElement(name = "delete-rule", required = false)
  public String getDeleteRule() {
    return deleteRule_;
  }

}
