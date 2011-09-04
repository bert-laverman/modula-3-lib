package nl.rakis.co.backend.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

/**
 * Entity implementation class for Entity: CODomainClass
 * 
 */
@Entity
@Table(name = "co_domain_class")
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "domcl_id", columnDefinition = "uuid")) })
public class CODomainClass
  extends AbstractUuidEntity
  implements Serializable
{

  private static final long serialVersionUID = 1L;

  @ManyToOne(optional = false)
  @JoinColumn(name = "dom_id", referencedColumnName = "dom_id")
  private CODomain          domain;

  @ManyToOne(optional = false)
  @JoinColumn(name = "cl_id", referencedColumnName = "cl_id")
  private COClass           coClass;

  @ManyToOne(optional = true)
  @JoinColumn(name = "stm_id", referencedColumnName = "stm_id")
  private StateModel        stateModel;

  public CODomainClass() {
    super();
  }

  public CODomain getDomain() {
    return domain;
  }

  public void setDomain(CODomain domain) {
    this.domain = domain;
  }

  public COClass getCoClass() {
    return coClass;
  }

  public void setCoClass(COClass coClass) {
    this.coClass = coClass;
  }

  public StateModel getStateModel() {
    return stateModel;
  }

  public void setStateModel(StateModel stateModel) {
    this.stateModel = stateModel;
  }

}
