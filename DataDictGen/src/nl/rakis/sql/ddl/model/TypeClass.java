/**
 * 
 */
package nl.rakis.sql.ddl.model;

/**
 * @author bertl
 * 
 */
public enum TypeClass
{
  BIT(false, true, false, false),
  VARBIT(false, true, false, false),
  BYTE,
  SHORT,
  INT,
  LONG,

  REAL(false, true, false, false),
  FLOAT,
  DOUBLE,

  DECIMAL(false, false, true, true),

  SMALLMONEY,
  MONEY,

  CHAR(false, true, false, false),
  NCHAR(true, true, false, false),
  VARCHAR(false, true, false, false),
  NVARCHAR(true, true, false, false),
  CLOB,
  NCLOB(true, false, false, false),

  BINARY(false, true, false, false),
  VARBINARY(false, true, false, false),
  BLOB,

  DATE,
  TIME,
  TIMESTAMP,
  BOOLEAN;

  private boolean nationalized_ = false;
  private boolean hasLength_    = false;
  private boolean hasPrecision_ = false;
  private boolean hasScale_     = false;

  TypeClass() {
  }

  TypeClass(boolean nationalized, boolean hasLength, boolean hasPrecision, boolean hasScale) {
    this.nationalized_ = nationalized;
    this.hasLength_ = hasLength;
    this.hasPrecision_ = hasPrecision;
    this.hasScale_ = hasScale;
  }

  /**
   * @return the nationalized
   */
  public boolean isNationalized() {
    return nationalized_;
  }

  /**
   * @return
   */
  public boolean hasLength() {
    return hasLength_;
  }

  /**
   * @return hasPrecision
   */
  public boolean hasPrecision() {
    return hasPrecision_;
  }

  /**
   * @return
   */
  public boolean hasScale() {
    return hasScale_;
  }

}
