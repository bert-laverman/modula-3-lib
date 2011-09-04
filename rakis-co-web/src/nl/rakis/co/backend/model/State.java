package nl.rakis.co.backend.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: COState
 * 
 */
@Entity
@Table(name = "co_state")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "st_id", columnDefinition = "uuid")),
    @AttributeOverride(name = "name", column = @Column(name = "st_name", length = 256, nullable = false, unique = true)),
    @AttributeOverride(name = "description", column = @Column(name = "st_description", length = 4000, nullable = true)) })
public class State
  extends AbstractNamedEntity
  implements Serializable
{

  private static final long serialVersionUID = 1L;

  public State() {
    super();
  }

}
