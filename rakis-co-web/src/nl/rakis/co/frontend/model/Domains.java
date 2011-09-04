package nl.rakis.co.frontend.model;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.rakis.co.backend.model.CODomain;

@ManagedBean
@SessionScoped
@Named("domains")
public class Domains
  implements Serializable
{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @PersistenceContext(name = "rakisCo", unitName = "rakisCo")
  private EntityManager em;

  public Domains() {
  }

  public List<CODomain> getDomains() {
    List<CODomain> result = null;

    System.err.println("Querying");
    result = em.createQuery("SELECT d FROM "+CODomain.class.getName()+" d", CODomain.class).getResultList();
    System.err.println("Returning a list of length "+result.size());

    return result;
  }

  public int getSize() {
    return getDomains().size();
  }
}
