package nl.rakis.co.backend.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: COAccessRight
 * 
 */
@Entity
@Table(name = "co_access_right")
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "acr_id", columnDefinition = "uuid")) })
public class COAccessRight
  extends AbstractUuidEntity
  implements Serializable
{

  private static final long serialVersionUID = 1L;

  @Column(name = "acr_op", length = 256)
  private String operation;

  @ManyToOne(optional = true)
  @JoinColumn(name = "prs_id", referencedColumnName = "prs_id")
  private COUser user;

  @ManyToOne(optional = true)
  @JoinColumn(name = "grp_id", referencedColumnName = "grp_id")
  private COGroup group;

  public COAccessRight() {
    super();
  }

  public String getOperation() {
    return operation;
  }

  public void setOperation(String operation) {
    this.operation = operation;
  }

  public COUser getUser() {
    return user;
  }

  public void setUser(COUser user) {
    this.user = user;
  }

  public COGroup getGroup() {
    return group;
  }

  public void setGroup(COGroup group) {
    this.group = group;
  }

}
