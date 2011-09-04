package nl.rakis.co.backend.model;

import java.io.Serializable;
import javax.persistence.*;

import nl.rakis.co.backend.model.AbstractUuidEntity;

/**
 * Entity implementation class for Entity: StateTransition
 * 
 */
@Entity
@Table(name = "co_state_transition")
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "sttr_id", columnDefinition = "uuid")) })
public class StateTransition
  extends AbstractUuidEntity
  implements Serializable
{

  private static final long serialVersionUID = 1L;

  @ManyToOne(optional = false)
  @JoinColumn(name = "stm_id")
  private StateModel        model;

  @ManyToOne(optional = true)
  @JoinColumn(name = "st_id_src", referencedColumnName = "st_id")
  private State             source;

  @ManyToOne(optional = true)
  @JoinColumn(name = "st_id_dst", referencedColumnName = "st_id")
  private State             destination;

  @Column(name = "sttr_guard", length = 4000, nullable = true)
  private String            guard;

  public StateTransition() {
    super();
  }

  public State getSource() {
    return source;
  }

  public void setSource(State source) {
    this.source = source;
  }

  public State getDestination() {
    return destination;
  }

  public void setDestination(State destination) {
    this.destination = destination;
  }

  public String getGuard() {
    return guard;
  }

  public void setGuard(String guard) {
    this.guard = guard;
  }

  public StateModel getModel() {
    return model;
  }

  public void setModel(StateModel model) {
    this.model = model;
  }

}
