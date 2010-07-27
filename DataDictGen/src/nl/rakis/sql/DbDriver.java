package nl.rakis.sql;

import java.io.PrintWriter;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Set;

import nl.rakis.sql.ddl.SchemaGenerator;
import nl.rakis.sql.ddl.SchemaLoader;
import nl.rakis.sql.ddl.model.ReferenceAction;
import nl.rakis.sql.ddl.model.Type;
import nl.rakis.sql.ddl.model.TypeClass;

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
  SchemaGenerator getSchemaGenerator(Connection db);

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
   * @param db
   * @return
   */
  SchemaLoader getSchemaLoader(Connection db);

  /**
   * @return
   */
  SchemaLoader getSchemaXmlReader(Reader reader);

  /**
   * @param type
   * @return
   */
  String type2String(TypeClass type);

  /**
   * @param name
   * @return
   */
  TypeClass string2Type(String name);

  /**
   * @param action
   * @return
   */
  String referenceAction2String(ReferenceAction action);

  /**
   * @param action
   * @return
   */
  ReferenceAction string2ReferenceAction(String action);

  /**
   * @return the set of typenames which have size "MAX".
   */
  Set<String> getMaxedVars();

  /**
   * @param type
   * @return
   */
  String buildTypeString(Type type);

  /**
   * @return
   */
  boolean isSqlFormatted();

  /**
   * @param sqlFormatted
   */
  void setSqlFormatted(boolean sqlFormatted);

}
