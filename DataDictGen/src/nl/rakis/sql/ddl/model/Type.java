/**
 * 
 */
package nl.rakis.sql.ddl.model;

import static nl.rakis.sql.ddl.model.TypeClass.CHARACTER;
import static nl.rakis.sql.ddl.model.TypeClass.LOGICAL;
import static nl.rakis.sql.ddl.model.TypeClass.NUMERIC;
import static nl.rakis.sql.ddl.model.TypeClass.TEMPORAL;

/**
 * @author bertl
 * 
 */
public enum Type
{
  INT("int", NUMERIC),
  REAL("real", NUMERIC),
  NUMBER("number", NUMERIC, false, true, true),
  DECIMAL("decimal", NUMERIC, false, true, true),
  CHAR("char", CHARACTER, false, true, false),
  NCHAR("nchar", CHARACTER, true, true, false),
  VARCHAR("varchar", CHARACTER, false, true, false),
  NVARCHAR("nvarchar", CHARACTER, true, true, false),
  CLOB("clob", CHARACTER),
  NCLOB("nclob", CHARACTER, true, false, false),
  BINARY("binary", TypeClass.BINARY, false, true, false),
  VARBINARY("varbinary", TypeClass.BINARY, false, true, false),
  BLOB("blob", TypeClass.BINARY),
  DATE("date", TEMPORAL),
  TIME("time", TEMPORAL),
  TIMESTAMP("datetime", TEMPORAL),
  BOOLEAN("boolean", LOGICAL);

  private String    name_;
  private TypeClass typeClass_;
  private boolean   nationalized_ = false;
  private boolean   hasLength_    = false;
  private boolean   hasScale_     = false;

  Type(String name, TypeClass typeClass)
  {
    this.name_ = name;
    this.typeClass_ = typeClass;
  }

  Type(String name, TypeClass typeClass, boolean nationalized,
       boolean hasLength, boolean hasScale)
  {
    this.name_ = name;
    this.typeClass_ = typeClass;
    this.nationalized_ = nationalized;
    this.hasLength_ = hasLength;
    this.hasScale_ = hasScale;
  }

  /* (non-Javadoc)
   * @see java.lang.Enum#toString()
   */
  @Override
  public String toString()
  {
    return this.getName();
  }

  /**
   * @return the name
   */
  public String getName()
  {
    return name_;
  }

  /**
   * @return the typeClass
   */
  public TypeClass getTypeClass()
  {
    return typeClass_;
  }

  /**
   * @return the nationalized
   */
  public boolean isNationalized()
  {
    return nationalized_;
  }

  /**
   * @return
   */
  public boolean hasLength()
  {
    return hasLength_;
  }

  /**
   * @return
   */
  public boolean hasScale()
  {
    return hasScale_;
  }

}
