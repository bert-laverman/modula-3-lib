package nl.rakis.sql;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import nl.rakis.sql.ddl.SchemaGenerator;
import nl.rakis.sql.ddl.model.Type;

public interface DbDriver
{

  /**
   * @param props
   */
  void setProperties(Properties props);

  /**
   * @param server
   * @param port
   * @param dbName
   * @return
   */
  String buildUrl(String server, int port, String dbName);

  /**
   * @param server
   * @param dbName
   * @return
   */
  String buildUrl(String server, String dbName);

  /**
   * @param server
   * @param port
   * @param dbName
   * @param user
   * @param pwd
   * @return
   */
  String buildUrl(String server, int port, String dbName, String user,
                  String pwd);

  /**
   * @param server
   * @param dbName
   * @param user
   * @param pwd
   * @return
   */
  String buildUrl(String server, String dbName, String user, String pwd);

  /**
   * @throws ClassNotFoundException
   */
  void init()
    throws ClassNotFoundException;

  /**
   * @param url
   * @return
   * @throws SQLException
   */
  Connection getDb(String url)
    throws SQLException;

  /**
   * @param url
   * @param username
   * @param password
   * @return
   * @throws SQLException
   */
  Connection getDb(String url, String username, String password)
    throws SQLException;

  /**
   * @return
   */
  SchemaGenerator getSchemaCreator();

  /**
   * @param writer
   * @return
   */
  SchemaGenerator getSchemaWriter(PrintWriter writer);

  /**
   * @param out
   * @return
   */
  SchemaGenerator getSchemaXmlWriter(PrintWriter writer);

  /**
   * @param type
   * @return
   */
  String type2String(Type type);

  /**
   * @param name
   * @return
   */
  Type string2Type(String name);
}
