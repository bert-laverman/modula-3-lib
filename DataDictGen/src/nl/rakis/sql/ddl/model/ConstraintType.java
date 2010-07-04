/**
 * 
 */
package nl.rakis.sql.ddl.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author bertl
 * 
 */
public enum ConstraintType
{
  CHECK("CHECK", true, false, false),
  PRIMARY_KEY("PRIMARY KEY", false, true, false),
  FOREIGN_KEY("FOREIGN KEY", false, true, true),
  UNIQUE("UNIQUE", false, true, false);

  private static Map<String, ConstraintType> typeMap_ = new HashMap<String, ConstraintType>();

  private String                             name_;
  private boolean                            hasCheck_;
  private boolean                            hasColumns_;
  private boolean                            hasRef_;

  ConstraintType(String name, boolean hasCheck, boolean hasColumns,
                 boolean hasRef)
  {
    this.name_ = name;
  }

  public String getName()
  {
    return this.name_;
  }

  public boolean hasCheck()
  {
    return this.hasCheck_;
  }

  public boolean hasColumns()
  {
    return this.hasColumns_;
  }

  public boolean hasRef()
  {
    return this.hasRef_;
  }

  private void addToMap()
  {
    typeMap_.put(name_.toLowerCase(), this);
  }

  public static ConstraintType getType(String name)
  {
    if (typeMap_.size() == 0) {
      for (ConstraintType type : ConstraintType.values()) {
        type.addToMap();
      }
    }
    name = name.toLowerCase();
    if (typeMap_.containsKey(name)) {
      return typeMap_.get(name);
    }
    return null;
  }

}
