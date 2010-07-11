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
@XmlType(name = "UniqueConstraintType", factoryClass = ObjectFactory.class, factoryMethod = "createUniqueConstraint")
@XmlAccessorType(NONE)
public class UniqueConstraint
  extends ColumnedConstraint
{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public UniqueConstraint()
  {
    super();

    setType(ConstraintType.UNIQUE);
  }

  /**
   * @param table
   * @param type
   */
  public UniqueConstraint(Table table)
  {
    super(table, ConstraintType.UNIQUE);
  }

  /**
   * @param table
   * @param name
   * @param type
   */
  public UniqueConstraint(Table table, String name)
  {
    super(table, name, ConstraintType.UNIQUE);
  }

  /**
   * @param table
   * @param catalog
   * @param schema
   * @param name
   * @param type
   */
  public UniqueConstraint(Table table, Schema schema, String name)
  {
    super(table, schema, name, ConstraintType.UNIQUE);
  }

}
