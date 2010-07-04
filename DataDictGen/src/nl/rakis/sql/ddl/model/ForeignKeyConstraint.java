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

  @XmlElement(name = "ref-name")
  private String             refName_;

  private ColumnedConstraint reference_       = null;

  public ForeignKeyConstraint()
  {
    super();
  }

  /**
   * @param table
   * @param type
   */
  public ForeignKeyConstraint(Table table, ConstraintType type)
  {
    super(table, type);
  }

  /**
   * @param table
   * @param type
   */
  public ForeignKeyConstraint(Table table)
  {
    super(table, ConstraintType.FOREIGN_KEY);
  }

  /**
   * @param table
   * @param name
   * @param type
   */
  public ForeignKeyConstraint(Table table, String name)
  {
    super(table, name, ConstraintType.FOREIGN_KEY);
  }

  /**
   * @param table
   * @param catalog
   * @param schema
   * @param name
   * @param type
   */
  public ForeignKeyConstraint(Table table, Schema schema, String name)
  {
    super(table, schema, name, ConstraintType.FOREIGN_KEY);
  }

  /**
   * @param reference the reference to set
   */
  public void setReference(ColumnedConstraint reference)
  {
    this.reference_ = reference;
    this.refName_ = reference.getName();
  }

  /**
   * @return the reference
   */
  public ColumnedConstraint getReference()
  {
    if ((this.reference_ == null) && (this.refName_ != null)) {
      //TODO
    }
    return reference_;
  }

}
