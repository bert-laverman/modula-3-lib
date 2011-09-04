package nl.rakis.co.backend.model;

import static javax.persistence.InheritanceType.JOINED;

import java.io.Serializable;
import javax.persistence.*;

import nl.rakis.co.backend.model.AbstractUuidEntity;

/**
 * Entity implementation class for Entity: Person
 * 
 */
@Entity
@Table(name = "co_person")
@Inheritance(strategy = JOINED)
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "prs_id", columnDefinition = "uuid")) })
public class COPerson
  extends AbstractUuidEntity
  implements Serializable
{

  private static final long serialVersionUID = 1L;

  @Column(name = "prs_firstname", length = 256)
  private String firstName;

  @Column(name = "prs_interfix", length = 256)
  private String interfix;

  @Column(name = "prs_lastname", length = 256)
  private String lastName;

  @Column(name = "prs_email", length = 256, nullable = false)
  private String email;

  public COPerson() {
    super();
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getInterfix() {
    return interfix;
  }

  public void setInterfix(String interfix) {
    this.interfix = interfix;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

}
