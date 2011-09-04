/**
 * 
 */
package nl.rakis.co.backend.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * @author bertl
 *
 */
@MappedSuperclass
public class AbstractNamedEntity
  extends AbstractUuidEntity
{

  @Column
  private String name;
  @Column
  private String description;

  public AbstractNamedEntity() {
    super();
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

}
