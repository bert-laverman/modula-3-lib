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
@XmlType(name = "CheckType", factoryClass = ObjectFactory.class, factoryMethod = "createCheckConstraint")
@XmlAccessorType(NONE)
public class CheckConstraint
  extends Constraint
{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @XmlElement(name = "expression", required = true, nillable = false)
  private String expression_;

  /**
   * 
   */
  public CheckConstraint() {
    super();

    setType(ConstraintType.CHECK);
  }

  /**
   * @param table
   * @param type
   */
  public CheckConstraint(Table table) {
    super(table, ConstraintType.CHECK);
  }

  /**
   * @param table
   * @param schema
   * @param name
   * @param type
   */
  public CheckConstraint(Table table, Schema schema, String name)
  {
    super(table, schema, name, ConstraintType.CHECK);
  }

  /**
   * @param table
   * @param name
   * @param type
   */
  public CheckConstraint(Table table, String name) {
    super(table, name, ConstraintType.CHECK);
  }

  /**
   * @param expression the expression to set
   */
  public void setExpression(String expression) {
    expression_ = expression;
  }

  /**
   * @return the expression
   */
  public String getExpression() {
    return expression_;
  }

}
