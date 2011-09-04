package nl.rakis.co.backend.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.*;

import nl.rakis.co.backend.model.AbstractNamedEntity;

/**
 * Entity implementation class for Entity: COGroup
 * 
 */
@Entity
@Table(name = "co_domain")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "grp_id", columnDefinition = "uuid")),
    @AttributeOverride(name = "name", column = @Column(name = "grp_name", length = 256, nullable = false, unique = true)) })
public class COGroup
  extends AbstractNamedEntity
  implements Serializable
{

  private static final long   serialVersionUID = 1L;

  @ManyToMany
  @MapKey(name = "username")
  @JoinTable(name = "co_grp_user", joinColumns = { @JoinColumn(name = "grp_id") }, inverseJoinColumns = { @JoinColumn(name = "prs_id") })
  private Map<String, COUser> users            = new HashMap<String, COUser>();

  public COGroup() {
    super();
  }

  public Map<String, COUser> getUsers() {
    return users;
  }

  public void setUsers(Map<String, COUser> users) {
    this.users = users;
  }

}
