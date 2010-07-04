/**
 * 
 */
package nl.rakis.sql.postgresql;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

import nl.rakis.sql.DbDriver;
import nl.rakis.sql.DbDriverBase;
import nl.rakis.sql.ddl.SchemaGenerator;
import nl.rakis.sql.ddl.model.Type;

/**
 * @author bertl
 * 
 */
public class PostgreSQLDriver
  extends DbDriverBase
  implements DbDriver
{

  @Override
  public String buildUrl(String server, int port, String dbName)
  {
    return "jdbc:postgresql://" + server + ":" + Integer.toString(port) + "/" +
           dbName;
  }

  @Override
  public String buildUrl(String server, String dbName)
  {
    return "jdbc:postgresql://" + server + "/" + dbName;
  }

  @Override
  public String buildUrl(String server, int port, String dbName, String user,
                         String pwd)
  {
    return "jdbc:postgresql://" + server + ":" + Integer.toString(port) + "/" +
           dbName + "?user=" + user + "&password=" + pwd;
  }

  @Override
  public String buildUrl(String server, String dbName, String user, String pwd)
  {
    return "jdbc:postgresql://" + server + "/" + dbName + "?user=" + user +
           "&password=" + pwd;
  }

  /*
   * (non-Javadoc)
   * 
   * @see nl.rakis.sql.DbDriver#getDb(java.lang.String)
   */
  @Override
  public Connection getDb(String url)
    throws SQLException
  {
    return DriverManager.getConnection(url);
  }

  /*
   * (non-Javadoc)
   * 
   * @see nl.rakis.sql.DbDriver#getDb(java.lang.String, java.lang.String,
   * java.lang.String)
   */
  @Override
  public Connection getDb(String url, String username, String password)
    throws SQLException
  {
    return DriverManager.getConnection(url, username, password);
  }

  /*
   * (non-Javadoc)
   * 
   * @see nl.rakis.sql.DbDriver#init()
   */
  @Override
  public void init()
    throws ClassNotFoundException
  {
    Class.forName("org.postgresql.Driver");
  }

  /* (non-Javadoc)
   * @see nl.rakis.sql.DbDriver#getSchemaCreator()
   */
  @Override
  public SchemaGenerator getSchemaCreator()
  {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see nl.rakis.sql.DbDriver#getSchemaWriter(java.io.PrintWriter)
   */
  @Override
  public SchemaGenerator getSchemaWriter(PrintWriter writer)
  {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see nl.rakis.sql.DbDriverBase#getName2TypeMap()
   */
  @Override
  public Map<String, Type> getName2TypeMap()
  {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see nl.rakis.sql.DbDriverBase#getType2NameMap()
   */
  @Override
  public Map<Type, String> getType2NameMap()
  {
    // TODO Auto-generated method stub
    return null;
  }

}
