package nl.rakis.co.backend.model;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: COString
 * 
 */
@Entity
@Table(name = "co_string")
@IdClass(COStringPK.class)
public class COString
  implements Serializable
{

  @Id
  @Column(name = "str_key", length = 256, nullable = false)
  private String            key;
  @Id
  @Column(name = "str_locale", length = 5, nullable = false)
  private String            locale;
  @Column(name = "str_value", length = 256, nullable = false)
  private String            value;
  private static final long serialVersionUID = 1L;

  public COString() {
    super();
  }

  public String getKey() {
    return this.key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getLocale() {
    return this.locale;
  }

  public void setLocale(String locale) {
    this.locale = locale;
  }

  public String getValue() {
    return this.value;
  }

  public void setValue(String value) {
    this.value = value;
  }

}
