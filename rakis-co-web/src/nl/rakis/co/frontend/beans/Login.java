package nl.rakis.co.frontend.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class Login
{
//  @ManagedProperty("#{facesContext.externalContext.request}")
//  private HttpServletRequest request;

  private String username;
  private String password;

  public Login() {
    super();
  }

  public void doLogin() {
//    request.login(username, username);
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  
}
