package nl.rakis.co.backend.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: COClass
 * 
 */
@Entity
@Table(name = "co_class")
@AttributeOverrides({
  @AttributeOverride(name = "id", column = @Column(name = "cl_id", columnDefinition = "uuid")),
  @AttributeOverride(name = "name", column = @Column(name = "cl_name", length = 256, nullable = false)),
  @AttributeOverride(name = "description", column = @Column(name = "cl_description", length = 4000, nullable = true))
})
public class COClass
  extends AbstractNamedEntityWithState
  implements Serializable
{

  private static final long serialVersionUID = 1L;

  @ManyToOne(optional = true)
  @JoinColumn(name = "cl_id_parent", referencedColumnName = "cl_id")
  private COClass parent;

  @OneToMany(mappedBy = "parent")
  @MapKey(name = "name")
  private Map<String, COClass> children = new HashMap<String, COClass>();

  public COClass() {
    super();
  }

  public COClass getParent() {
    return parent;
  }

  public void setParent(COClass parent) {
    this.parent = parent;
  }

  public Map<String, COClass> getChildren() {
    return children;
  }

  public void setChildren(Map<String, COClass> children) {
    this.children = children;
  }

}
