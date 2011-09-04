package nl.rakis.co.backend.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import nl.rakis.co.backend.model.AbstractNamedEntity;

/**
 * Entity implementation class for Entity: CODomain
 * 
 */
@Entity
@Table(name = "co_domain")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "dom_id", columnDefinition = "uuid")),
    @AttributeOverride(name = "name", column = @Column(name = "dom_name", length = 256, nullable = false, unique = true)),
    @AttributeOverride(name = "description", column = @Column(name = "dom_description", length = 4000, nullable = true)) })
public class CODomain
  extends AbstractNamedEntity
  implements Serializable
{

  @ManyToMany
  @JoinTable(name = "co_domain_acl", joinColumns = { @JoinColumn(name = "dom_id") }, inverseJoinColumns = { @JoinColumn(name = "acr_id") })
  private List<COAccessRight> acl              = new ArrayList<COAccessRight>();

  private static final long   serialVersionUID = 1L;

  public CODomain() {
    super();
  }

  public List<COAccessRight> getAcl() {
    return acl;
  }

  public void setAcl(List<COAccessRight> acl) {
    this.acl = acl;
  }

}
