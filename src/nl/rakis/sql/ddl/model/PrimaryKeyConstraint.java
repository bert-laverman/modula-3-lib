/**
 * 
 */
package nl.rakis.sql.ddl.model;

import static javax.xml.bind.annotation.XmlAccessType.NONE;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * @author bertl
 * 
 */
@XmlType(name = "PrimaryKeyType")
@XmlAccessorType(NONE)
public class PrimaryKeyConstraint
  extends ColumnedConstraint
{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public PrimaryKeyConstraint()
  {
    super();
  }

  /**
   * @param table
   * @param type
   */
  public PrimaryKeyConstraint(Table table)
  {
    super(table, ConstraintType.PRIMARY_KEY);
  }

  /**
   * @param table
   * @param name
   * @param type
   */
  public PrimaryKeyConstraint(Table table, String name)
  {
    super(table, name, ConstraintType.PRIMARY_KEY);
  }

  /**
   * @param table
   * @param catalog
   * @param schema
   * @param name
   * @param type
   */
  public PrimaryKeyConstraint(Table table, Schema schema, String name)
  {
    super(table, schema, name, ConstraintType.PRIMARY_KEY);
  }

}
