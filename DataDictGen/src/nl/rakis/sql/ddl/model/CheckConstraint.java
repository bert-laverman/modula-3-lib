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
  private String            expression_;

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
  public CheckConstraint(Table table, Schema schema, String name) {
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
   * @param expression
   *          the expression to set
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

  /**
   * Two CheckConstraints are the same if they check the same thing.
   * 
   * @param that
   * @return
   */
  public boolean same(CheckConstraint that) {
    return EqualsUtil.equals(this.expression_, that.expression_);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    boolean result = (obj != null) && (obj instanceof CheckConstraint);

    if (result) {
      CheckConstraint that = (CheckConstraint) obj;

      result = super.equals(that) && same(that);
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
    return getType().hashCode() + HashUtil.safeHash(getName()) +
           HashUtil.safeHash(this.expression_);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuffer buf = new StringBuffer();

    buf.append("CONSTRAINT ");
    if (getName() != null) {
      buf.append(getName());
    }
    buf.append(' ').append(getType().getName());
    if (this.expression_ != null) {
      buf.append(' ').append(this.expression_);
    }
    return buf.toString();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * nl.rakis.sql.ddl.model.Constraint#compareTo(nl.rakis.sql.ddl.model.Constraint
   * )
   */
  @Override
  public int compareTo(Constraint o) {
    int result = super.compareTo(o);

    if ((o instanceof CheckConstraint) && (result == 0)) {
      result = CompareUtil.compare(this.expression_,
                                   ((CheckConstraint) o).expression_);
    }
    return result;
  }

}
