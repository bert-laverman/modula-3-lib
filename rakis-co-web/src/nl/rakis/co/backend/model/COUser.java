package nl.rakis.co.backend.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.*;

import nl.rakis.co.backend.model.COPerson;

/**
 * Entity implementation class for Entity: COUser
 * 
 */
@Entity
@Table(name = "co_user")
public class COUser
  extends COPerson
  implements Serializable
{

  private static final long serialVersionUID = 1L;

  @Column(name = "prs_username", nullable = false, unique = true, length = 256)
  private String            username;

  @Column(name = "prs_pwdhash", nullable = false, length = 64)
  private String            pwdHash;

  @ManyToMany(mappedBy = "users")
  @MapKey(name = "name")
  private Map<String, COGroup> groups = new HashMap<String, COGroup>();

  public COUser() {
    super();
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPwdHash() {
    return pwdHash;
  }

  public void setPwdHash(String pwdHash) {
    this.pwdHash = pwdHash;
  }

  public Map<String, COGroup> getGroups() {
    return groups;
  }

  public void setGroups(Map<String, COGroup> groups) {
    this.groups = groups;
  }

}
