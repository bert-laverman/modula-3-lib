/**
 * 
 */
package nl.rakis.sql.jtds;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import nl.rakis.sql.DbDriverBase;
import nl.rakis.sql.ddl.SchemaLoader;
import nl.rakis.sql.ddl.SchemaGenerator;
import nl.rakis.sql.ddl.SchemaWriterBase;
import nl.rakis.sql.ddl.model.Type;
import nl.rakis.sql.ddl.model.TypeClass;
import nl.rakis.sql.iso.ISOSchemaLoader;
import nl.rakis.sql.sqlserver.SqlServerSchemaGenerator;
import nl.rakis.sql.sqlserver.SqlServerSchemaWriter;

/**
 * @author bertl
 * 
 */
public class JTDSDriver
  extends DbDriverBase
{

  /**
   * 
   */
  public JTDSDriver() {
    super();
  }

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
  public SchemaGenerator getSchemaGenerator(Connection db)
  {
    SchemaGenerator result = new SqlServerSchemaGenerator(this, db);

    return result;
  }

  /* (non-Javadoc)
   * @see nl.rakis.sql.DbDriver#getSchemaWriter(java.io.PrintWriter)
   */
  @Override
  public SchemaGenerator getSchemaWriter(PrintWriter writer)
  {
    SchemaWriterBase result = new SqlServerSchemaWriter(this, writer);

    return result;
  }

  /* (non-Javadoc)
   * @see nl.rakis.sql.DbDriver#getSchemaLoader()
   */
  @Override
  public SchemaLoader getSchemaLoader(Connection db) {
    ISOSchemaLoader loader = new ISOSchemaLoader();
    loader.setDriver(this);
    loader.setDb(db);

    return loader;
  }

  private static final class N2TMapHolder
  {
    private static final Map<String, TypeClass> map_ = new HashMap<String, TypeClass>();

    static {
      map_.put("bit",            TypeClass.BIT);
      map_.put("bigint",         TypeClass.LONG);      // 8 bytes
      map_.put("int",            TypeClass.INT);       // 4 bytes
      map_.put("smallint",       TypeClass.SHORT);     // 2 bytes
      map_.put("tinyint",        TypeClass.BYTE);      // 1 byte
      map_.put("float",          TypeClass.REAL);      // 1-24: 4 bytes, 25-53: 8 bytes
      map_.put("real",           TypeClass.REAL);      // == float(24)
      map_.put("numeric",        TypeClass.DECIMAL);   // max precision 38
      map_.put("dec",            TypeClass.DECIMAL);   // max precision 38
      map_.put("decimal",        TypeClass.DECIMAL);   // max precision 38
      map_.put("money",          TypeClass.MONEY);     // 8 bytes, 4 digits after the comma
      map_.put("smallmoney",     TypeClass.SMALLMONEY);// 4 bytes, 4 digits after the comma

      map_.put("char",           TypeClass.CHAR);
      map_.put("nchar",          TypeClass.NCHAR);
      map_.put("varchar",        TypeClass.VARCHAR);
      map_.put("nvarchar",       TypeClass.NVARCHAR);
      map_.put("text",           TypeClass.VARCHAR);   // == varchar(max)
      map_.put("ntext",          TypeClass.NVARCHAR);  // == nvarchar(max)

      map_.put("binary",         TypeClass.BINARY);
      map_.put("varbinary",      TypeClass.VARBINARY);
      map_.put("image",          TypeClass.VARBINARY); // == varbinary(max)

      map_.put("date",           TypeClass.DATE);      // 0001-01-01 through 9999-12-31
      map_.put("time",           TypeClass.TIME);      // 00:00:00.0000000 through 23:59:59.9999999
      map_.put("smalldatetime",  TypeClass.TIMESTAMP); // 1900-01-01 to 2079-06-06, 00:00:00 to 23:59:59
      map_.put("timestamp",      TypeClass.TIMESTAMP); // 1753-01-01 to 9999-12-31, 00:00:00 to 23:59:59.997
      map_.put("datetime",       TypeClass.TIMESTAMP); // 1753-01-01 to 9999-12-31, 00:00:00 to 23:59:59.997
      map_.put("datetime2",      TypeClass.TIMESTAMP); // 0001-01-01 to 9999-12-31, 00:00:00 to 23:59:59.9999999
      map_.put("datetimeoffset", TypeClass.TIMESTAMP); // 0001-01-01 to 9999-12-31, 00:00:00 to 23:59:59.9999999
    }

    private static final Set<String> maxedVars_ = new HashSet<String>();

    static {
      maxedVars_.add("ntext");
      maxedVars_.add("image");
    }
  }

  /* (non-Javadoc)
   * @see nl.rakis.sql.DbDriverBase#getName2TypeMap()
   */
  @Override
  public Map<String, TypeClass> getName2TypeMap()
  {
    return N2TMapHolder.map_;
  }

  /* (non-Javadoc)
   * @see nl.rakis.sql.DbDriver#getMaxedVars()
   */
  @Override
  public Set<String> getMaxedVars() {
    return N2TMapHolder.maxedVars_;
  }

  private static final class T2NMapHolder
  {
    private static final Map<TypeClass, String> map_ = new HashMap<TypeClass, String>();

    static {
      map_.put(TypeClass.BIT,       "bit");
      map_.put(TypeClass.LONG,       "bigint");
      map_.put(TypeClass.INT,       "int");
      map_.put(TypeClass.SHORT,       "smallint");
      map_.put(TypeClass.BYTE,       "tinyint");
//      map_.put(TypeClass.REAL,      "float");
      map_.put(TypeClass.REAL,      "real");
//      map_.put(TypeClass.NUMBER,    "numeric");
//      map_.put(TypeClass.DECIMAL,   "dec");
      map_.put(TypeClass.DECIMAL,   "decimal");
      map_.put(TypeClass.MONEY,   "money");
      map_.put(TypeClass.SMALLMONEY,   "smallmoney");

      map_.put(TypeClass.CHAR,      "char");
      map_.put(TypeClass.NCHAR,     "nchar");
      map_.put(TypeClass.VARCHAR,   "varchar");
      map_.put(TypeClass.NVARCHAR,  "nvarchar");
//      map_.put(TypeClass.VARCHAR,   "text");
//      map_.put(TypeClass.NVARCHAR,  "ntext");

      map_.put(TypeClass.BINARY,    "binary");
      map_.put(TypeClass.VARBINARY, "varbinary");
//      map_.put(TypeClass.VARBINARY, "image");

      map_.put(TypeClass.DATE,      "date");
      map_.put(TypeClass.TIME,      "time");
//      map_.put(TypeClass.TIMESTAMP, "smalldatetime");
//      map_.put(TypeClass.TIMESTAMP, "timestamp");
      map_.put(TypeClass.TIMESTAMP, "datetime");
//      map_.put(TypeClass.TIMESTAMP, "datetime2");
//      map_.put(TypeClass.TIMESTAMP, "datetimeoffset");
    }
  }

  /* (non-Javadoc)
   * @see nl.rakis.sql.DbDriverBase#getType2NameMap()
   */
  @Override
  public Map<TypeClass, String> getType2NameMap()
  {
    return T2NMapHolder.map_;
  }

  /* (non-Javadoc)
   * @see nl.rakis.sql.DbDriver#buildTypeString(nl.rakis.sql.ddl.model.Type)
   */
  @Override
  public String buildTypeString(Type type)
  {
    StringBuffer buf = new StringBuffer();
 
    final TypeClass clazz = type.getClazz();

    // Take care of special case
    if (((clazz == TypeClass.VARCHAR) || (clazz == TypeClass.NVARCHAR) || (clazz == TypeClass.VARBINARY)) && (type.getLength() == -1)) {
      switch (clazz) {

      case VARCHAR:
        buf.append("text");
        break;

      case NVARCHAR:
        buf.append("ntext");
        break;

      case VARBINARY:
        buf.append("image");
        break;

      }
    }
    else {
      buf.append(type2String(clazz));
  
      if (clazz.hasLength()) {
          Integer len = type.getLength();
  
          if (len != null) {
            buf.append('(');
            if (len == -1) {
              buf.append("MAX");
            }
            else {
              buf.append(len.toString());
              // SQL Server cannot do this?
              //if (type.isCountInChars()) {
              //  buf.append(" CHAR");
              //}
              //else {
              //  buf.append(" BYTE");
              //}
            }
            buf.append(')');
          }
      }
      if (clazz.hasPrecision()) {
        Integer prec = type.getPrecision();
        Integer scale = type.getScale();
  
        if ((prec != null) || (scale != null))
        {
          buf.append('(');
          if (prec != null) {
            buf.append(prec.toString());
          }
          if (scale != null) {
            buf.append(';').append(scale.toString());
          }
          buf.append(')');
        }
      }
    }
    return buf.toString();
  }

}
