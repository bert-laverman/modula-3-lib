/**
 * 
 */
package nl.rakis.sql.ddl.tools;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import nl.rakis.sql.DbDriver;
import nl.rakis.sql.ddl.SchemaLoader;
import nl.rakis.sql.ddl.model.Schema;
import nl.rakis.sql.jtds.JTDSDriver;

/**
 * @author bertl
 * 
 */
public class GenXML
{

  /**
   * 
   */
  private static final String OUTPUT_ = "test.xml";
  private static final String DBNAME_ = "CMS_GP";
  //  private static final String DBNAME_ = "webapps";
  private static final String SERVER_ = "10.0.0.14";
  //  private static final String SERVER_ = "dune";
  private static final String USER_   = "sa";
  //private static final String USER_   = "postgres";
  private static final String PWD_    = "Krwa2Krwa";
  private static final String SCHEMA_ = "dbo";

  private static DbDriver     driver  = new JTDSDriver();
  //  private static DbDriver          driver  = new PostgreSQLDriver();

  private static PrintWriter  out     = null;

  public static void main(String[] args)
  {
    System.err.println("Starting up");
    try {
      driver.init();
      out = new PrintWriter(OUTPUT_);
    }
    catch (ClassNotFoundException e) {
      System.err.println("Unable to load jTDS Driver");
      System.exit(1);
    }
    catch (FileNotFoundException e) {
      System.err.println("Unable to open output file: " + e.getMessage());
      System.exit(2);
    }

    String url = driver.buildUrl(SERVER_, DBNAME_);
    //"jdbc:jtds:sqlserver://localhost/CMS;instance=SQLEXPRESS";
    try {
      System.err.println("Opening connection");
      Connection db = driver.getDb(url, USER_, PWD_);
      SchemaLoader loader = driver.getSchemaLoader(db);

      Schema schema = loader.load(SCHEMA_);

      System.err.println("Closing connection");
      db.close();

      driver.getSchemaXmlWriter(out).create(schema);
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
    finally {
      out.close();
    }
  }
}
