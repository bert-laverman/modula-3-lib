/**
 * 
 */
package nl.rakis.sql.ddl.model;

/**
 * @author bertl
 * 
 */
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
