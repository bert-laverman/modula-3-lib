/**
 * 
 */
package nl.rakis.sql.jtds;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import nl.rakis.sql.DbDriver;
import nl.rakis.sql.DbDriverBase;
import nl.rakis.sql.ddl.SchemaGenerator;
import nl.rakis.sql.ddl.SchemaWriterBase;
import nl.rakis.sql.ddl.model.Type;
import nl.rakis.sql.sqlserver.SchemaWriter;

/**
 * @author bertl
 * 
 */
public class JTDSDriver
  extends DbDriverBase
  implements DbDriver
{

  @Override
  public String buildUrl(String server, int port, String dbName)
  {
    return "jdbc:jtds:sqlserver://" + server + ":" + Integer.toString(port) +
           "/" + dbName;
  }

  @Override
  public String buildUrl(String server, String dbName)
  {
    return "jdbc:jtds:sqlserver://" + server + "/" + dbName;
  }

  @Override
  public String buildUrl(String server, int port, String dbName, String user,
                         String pwd)
  {
    return "jdbc:jtds:sqlserver://" + server + ":" + Integer.toString(port) +
           "/" + dbName;
  }

  @Override
  public String buildUrl(String server, String dbName, String user, String pwd)
  {
    return "jdbc:jtds:sqlserver://" + server + "/" + dbName;
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
    Class.forName("net.sourceforge.jtds.jdbc.Driver");
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
    SchemaWriterBase result = new SchemaWriter(writer);

    return result;
  }

  private static final class N2TMapHolder
  {
    private static final Map<String, Type> map_ = new HashMap<String, Type>();

    static {
      map_.put("bit",            Type.BOOLEAN);
      map_.put("bigint",         Type.INT);       // 8 bytes
      map_.put("int",            Type.INT);       // 4 bytes
      map_.put("smallint",       Type.INT);       // 2 bytes
      map_.put("tinyint",        Type.INT);       // 1 byte
      map_.put("float",          Type.REAL);      // 1-24: 4 bytes, 25-53: 8 bytes
      map_.put("real",           Type.REAL);      // == float(24)
      map_.put("numeric",        Type.NUMBER);    // max precision 38
      map_.put("dec",            Type.DECIMAL);   // max precision 38
      map_.put("decimal",        Type.DECIMAL);   // max precision 38
      map_.put("money",          Type.DECIMAL);   // 8 bytes, 4 digits after the comma
      map_.put("smallmoney",     Type.DECIMAL);   // 4 bytes, 4 digits after the comma

      map_.put("char",           Type.CHAR);
      map_.put("nchar",          Type.NCHAR);
      map_.put("varchar",        Type.VARCHAR);
      map_.put("nvarchar",       Type.NVARCHAR);
      map_.put("text",           Type.VARCHAR);   // == varchar(max)
      map_.put("ntext",          Type.NVARCHAR);  // == nvarchar(max)

      map_.put("binary",         Type.BINARY);
      map_.put("varbinary",      Type.VARBINARY);
      map_.put("image",          Type.VARBINARY); // == varbinary(max)

      map_.put("date",           Type.DATE);      // 0001-01-01 through 9999-12-31
      map_.put("time",           Type.TIME);      // 00:00:00.0000000 through 23:59:59.9999999
      map_.put("smalldatetime",  Type.TIMESTAMP); // 1900-01-01 to 2079-06-06, 00:00:00 to 23:59:59
      map_.put("timestamp",      Type.TIMESTAMP); // 1753-01-01 to 9999-12-31, 00:00:00 to 23:59:59.997
      map_.put("datetime",       Type.TIMESTAMP); // 1753-01-01 to 9999-12-31, 00:00:00 to 23:59:59.997
      map_.put("datetime2",      Type.TIMESTAMP); // 0001-01-01 to 9999-12-31, 00:00:00 to 23:59:59.9999999
      map_.put("datetimeoffset", Type.TIMESTAMP); // 0001-01-01 to 9999-12-31, 00:00:00 to 23:59:59.9999999
    }
  }

  /* (non-Javadoc)
   * @see nl.rakis.sql.DbDriverBase#getName2TypeMap()
   */
  @Override
  public Map<String, Type> getName2TypeMap()
  {
    return N2TMapHolder.map_;
  }

  private static final class T2NMapHolder
  {
    private static final Map<Type, String> map_ = new HashMap<Type, String>();

    static {
//      map_.put(Type.INT,       "bit");
//      map_.put(Type.INT,       "bigint");
      map_.put(Type.INT,       "int");
//      map_.put(Type.INT,       "smallint");
//      map_.put(Type.INT,       "tinyint");
//      map_.put(Type.REAL,      "float");
      map_.put(Type.REAL,      "real");
      map_.put(Type.NUMBER,    "numeric");
//      map_.put(Type.DECIMAL,   "dec");
      map_.put(Type.DECIMAL,   "decimal");
//      map_.put(Type.DECIMAL,   "money");
//      map_.put(Type.DECIMAL,   "smallmoney");

      map_.put(Type.CHAR,      "char");
      map_.put(Type.NCHAR,     "nchar");
      map_.put(Type.VARCHAR,   "varchar");
      map_.put(Type.NVARCHAR,  "nvarchar");
//      map_.put(Type.VARCHAR,   "text");
//      map_.put(Type.NVARCHAR,  "ntext");

      map_.put(Type.BINARY,    "binary");
      map_.put(Type.VARBINARY, "varbinary");
//      map_.put(Type.VARBINARY, "image");

      map_.put(Type.DATE,      "date");
      map_.put(Type.TIME,      "time");
//      map_.put(Type.TIMESTAMP, "smalldatetime");
//      map_.put(Type.TIMESTAMP, "timestamp");
      map_.put(Type.TIMESTAMP, "datetime");
//      map_.put(Type.TIMESTAMP, "datetime2");
//      map_.put(Type.TIMESTAMP, "datetimeoffset");
    }
  }

  /* (non-Javadoc)
   * @see nl.rakis.sql.DbDriverBase#getType2NameMap()
   */
  @Override
  public Map<Type, String> getType2NameMap()
  {
    return T2NMapHolder.map_;
  }

}
