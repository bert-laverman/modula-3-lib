/**
 * 
 */
package nl.rakis.co.backend.model;

import java.util.UUID;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

/**
 * @author bertl
 *
 */
@MappedSuperclass
public class AbstractUuidEntity
{

  @Id
  private UUID id = UUID.randomUUID();

  private transient boolean persistent = false;

  public AbstractUuidEntity() {
    super();
  }

  @PostPersist
  public void postPersist() {
    setPersistent(true);
  }

  @PostLoad
  public void postLoad() {
    setPersistent(true);
  }

  @PostRemove
  public void postRemove() {
    setPersistent(false);
  }

  @PostUpdate
  public void postUpdate() {
    setPersistent(true);
  }

  // Accessors

  public UUID getId() {
    return this.id;
  }

  public void setId(UUID guid) {
    this.id = guid;
  }

  public boolean isPersistent() {
    return persistent;
  }

  public void setPersistent(boolean persistent) {
    this.persistent = persistent;
  }

}
