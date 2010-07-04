/**
 * 
 */
package nl.rakis.sql.ddl.model;

import static javax.xml.bind.annotation.XmlAccessType.NONE;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import nl.rakis.sql.DbDriver;

/**
 * @author bertl
 * 
 */
@XmlType(name = "ColumnType")
@XmlAccessorType(NONE)
public class Column
  extends NamedObject
{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public static final int   MAX              = -1;

  private Table             table_;

  @XmlElement(name = "type")
  private Type              type_;

  @XmlElement(name = "precision", required = false)
  private Integer           precision_;

  @XmlElement(name = "scale", required = false)
  private Integer           scale_;

  @XmlElement(name = "length", required = false)
  private Integer           length_;

  @XmlAttribute(name = "null", required = true)
  private boolean           nullable_;

  @XmlAttribute(name = "nchar", required = true)
  private boolean           countInChars_;

  /**
   * 
   */
  public Column()
  {
    super();
  }

  /**
   * @param name
   */
  public Column(String name)
  {
    super(name);
  }

  /**
   * @return the type
   */
  public Type getType()
  {
    return this.type_;
  }

  /**
   * @param table the table to set
   */
  public void setTable(Table table)
  {
    this.table_ = table;
  }

  /**
   * @return the table
   */
  public Table getTable()
  {
    return table_;
  }

  /**
   * @param type the type to set
   */
  public void setType(Type type)
  {
    this.type_ = type;
  }

  /**
   * @return the precision
   */
  public Integer getPrecision()
  {
    return this.precision_;
  }

  /**
   * @param precision the precision to set
   */
  public void setPrecision(Integer precision)
  {
    this.precision_ = precision;
  }

  /**
   * @return the scale
   */
  public Integer getScale()
  {
    return this.scale_;
  }

  /**
   * @param scale the scale to set
   */
  public void setScale(Integer scale)
  {
    this.scale_ = scale;
  }

  /**
   * @return the charLength
   */
  public Integer getLength()
  {
    return this.length_;
  }

  /**
   * @param length the length to set
   */
  public void setLength(Integer length)
  {
    this.length_ = length;
  }

  /**
   * @return the nullable
   */
  public boolean isNullable()
  {
    return this.nullable_;
  }

  /**
   * @param nullable the nullable to set
   */
  public void setNullable(boolean nullable)
  {
    this.nullable_ = nullable;
  }

  /**
   * @param countInChars the countInChars to set
   */
  public void setCountInChars(boolean countInChars)
  {
    this.countInChars_ = countInChars;
  }

  /**
   * @return the countInChars
   */
  public boolean isCountInChars()
  {
    return countInChars_;
  }

  public static Column fromInformationSchema(DbDriver driver, ResultSet rs)
    throws SQLException
  {
    Column result = new Column();

    result.setName(rs.getString("column_name"));

    final String typeName = rs.getString("data_type").toLowerCase();
    final Type type = driver.string2Type(typeName);

    if (type == null) {
      System.err.println("Unknown type: " + typeName);
    }
    result.setType(type);

    final Integer charLength = (Integer) rs
        .getObject("character_maximum_length");
    final Integer byteLength = (Integer) rs.getObject("character_octet_length");

    if (charLength != null) {
      result.setCountInChars(true);
      result.setLength(charLength);
    }
    else if (byteLength != null) {
      result.setCountInChars(false);
      result.setLength(byteLength);
    }

    result.setPrecision((Integer) rs.getObject("numeric_precision"));
    result.setScale((Integer) rs.getObject("numeric_scale"));
    result.setNullable(rs.getString("is_nullable").equalsIgnoreCase("yes"));

    return result;
  }

  /**
   * @return
   */
  public String getTypeString()
  {
    StringBuffer buf = new StringBuffer();

    buf.append(this.type_.getName());
    switch (this.type_.getTypeClass()) {
    case CHARACTER:
      if (this.type_.hasLength()) {
        if (getLength() != null) {
          buf.append('(');
          if (getLength() == -1) {
            buf.append("MAX");
          }
          else {
            buf.append(getLength().toString()).append(' ');
            if (isCountInChars()) {
              buf.append("CHAR");
            }
            else {
              buf.append("BYTE");
            }
          }
          buf.append(')');
        }
      }
      break;

    case NUMERIC:
      if (this.type_.hasLength() &&
          ((getLength() != null) || (getScale() != null)))
      {
        buf.append('(');
        if (getLength() != null) {
          buf.append(getLength().toString());
        }
        if (getPrecision() != null) {
          buf.append(';').append(getPrecision().toString());
        }
        buf.append(')');
      }
      break;

    case BINARY:
      break;
    }
    return buf.toString();
  }
}
