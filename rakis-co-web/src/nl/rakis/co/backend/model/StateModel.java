package nl.rakis.co.backend.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import nl.rakis.co.backend.model.AbstractNamedEntityWithState;

/**
 * Entity implementation class for Entity: StateModel
 * 
 */
@Entity
@Table(name = "co_state_model")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "stm_id", columnDefinition = "uuid")),
    @AttributeOverride(name = "name", column = @Column(name = "stm_name", length = 256, nullable = false, unique = true)),
    @AttributeOverride(name = "description", column = @Column(name = "stm_description", length = 4000, nullable = true)) })
public class StateModel
  extends AbstractNamedEntityWithState
  implements Serializable
{

  private static final long     serialVersionUID = 1L;

  @OneToMany(mappedBy = "model")
  private List<StateTransition> transitions      = new ArrayList<StateTransition>();

  @ManyToMany
  @JoinTable(name = "co_stm_st", joinColumns = { @JoinColumn(name = "stm_id") }, inverseJoinColumns = { @JoinColumn(name = "st_id") })
  private Set<State>            states           = new HashSet<State>();

  public StateModel() {
    super();
  }

  public List<StateTransition> getTransitions() {
    return transitions;
  }

  public void setTransitions(List<StateTransition> transitions) {
    this.transitions = transitions;
  }

  public Set<State> getStates() {
    return states;
  }

  public void setStates(Set<State> states) {
    this.states = states;
  }

}
