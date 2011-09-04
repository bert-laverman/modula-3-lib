/**
 * 
 */
package nl.rakis.co.backend.model;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 * @author bertl
 *
 */
@MappedSuperclass
public class AbstractNamedEntityWithState
  extends AbstractNamedEntity
{

  @ManyToOne
  @JoinColumn(name = "st_id")
  private State state;

  public AbstractNamedEntityWithState() {
    super();
  }

  public State getState() {
    return state;
  }

  public void setState(State state) {
    this.state = state;
  }

}
