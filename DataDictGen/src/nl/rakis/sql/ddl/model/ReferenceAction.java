/**
 * 
 */
package nl.rakis.sql.ddl.model;

/**
 * @author bertl
 * 
 */
public enum ReferenceAction
{
  NO_ACTION(true, true),
  CASCADE(true, true),
  SET_NULL(true, true),
  SET_DEFAULT(true, true);

  private boolean forUpdate_;
  private boolean forDelete_;

  /**
   * @param forUpdate
   * @param forDelete
   */
  private ReferenceAction(boolean forUpdate, boolean forDelete) {
    this.forUpdate_ = forUpdate;
    this.forDelete_ = forDelete;
  }

  /**
   * @return the forUpdate
   */
  public boolean isForUpdate() {
    return forUpdate_;
  }

  /**
   * @return the forDelete
   */
  public boolean isForDelete() {
    return forDelete_;
  }

}
