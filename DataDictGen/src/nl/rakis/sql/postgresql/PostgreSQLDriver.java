/**
 * 
 */
package nl.rakis.sql.postgresql;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import nl.rakis.sql.DbDriver;
import nl.rakis.sql.DbDriverBase;
import nl.rakis.sql.ddl.SchemaGenerator;
import nl.rakis.sql.ddl.SchemaLoader;
import nl.rakis.sql.ddl.model.ReferenceAction;
import nl.rakis.sql.ddl.model.Type;
import nl.rakis.sql.ddl.model.TypeClass;
import nl.rakis.sql.iso.ISOSchemaLoader;

/**
 * @author bertl
 * 
 */
public class PostgreSQLDriver
  extends DbDriverBase
  implements DbDriver
{

  @Override
  public String buildUrl(String server, int port, String dbName) {
    return "jdbc:postgresql://" + server + ":" + Integer.toString(port) + "/" +
           dbName;
  }

  @Override
  public String buildUrl(String server, String dbName) {
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

  /*
   * (non-Javadoc)
   * 
   * @see nl.rakis.sql.DbDriver#getSchemaCreator()
   */
  @Override
  public SchemaGenerator getSchemaGenerator(Connection db) {
    return new PostgreSqlSchemaGenerator(this, db);
  }

  /*
   * (non-Javadoc)
   * 
   * @see nl.rakis.sql.DbDriver#getSchemaWriter(java.io.PrintWriter)
   */
  @Override
  public SchemaGenerator getSchemaWriter(PrintWriter writer) {
    return new PostgreSqlSchemaWriter(this, writer);
  }

  /*
   * (non-Javadoc)
   * 
   * @see nl.rakis.sql.DbDriver#getSchemaLoader(java.sql.Connection)
   */
  @Override
  public SchemaLoader getSchemaLoader(Connection db) {
    ISOSchemaLoader loader = new PostgreSqlSchemaLoader(); // TODO
    loader.setDriver(this);
    loader.setDb(db);

    return loader;
  }

  private static final class N2TMapHolder
    {
      private static final Map<String, TypeClass>       tMap_      = new HashMap<String, TypeClass>();
    
      static {
        tMap_.put("bit", TypeClass.BIT);
        tMap_.put("varbit", TypeClass.VARBIT); // BIT VARYING
        tMap_.put("bit varying", TypeClass.VARBIT); // BIT VARYING
  
        tMap_.put("boolean", TypeClass.BOOLEAN);
  
        tMap_.put("bigserial", TypeClass.LONG); // auto-increment
        tMap_.put("serial", TypeClass.INT); // auto-increment
        tMap_.put("serial4", TypeClass.INT); // auto-increment
        tMap_.put("serial8", TypeClass.LONG); // auto-increment
  
        tMap_.put("bigint", TypeClass.LONG); // 8 bytes
        tMap_.put("int", TypeClass.INT); // 4 bytes
        tMap_.put("integer", TypeClass.INT); // 4 bytes
        tMap_.put("int1", TypeClass.BYTE); // 1 bytes
        tMap_.put("int2", TypeClass.SHORT); // 2 bytes
        tMap_.put("int4", TypeClass.INT); // 4 bytes
        tMap_.put("int8", TypeClass.LONG); // 8 bytes
        tMap_.put("smallint", TypeClass.SHORT); // 2 bytes
  //      tMap_.put("tinyint", TypeClass.BYTE); // 1 byte
        tMap_.put("float", TypeClass.FLOAT); // 1-24: 4 bytes, 25-53: 8 bytes
        tMap_.put("float4", TypeClass.FLOAT); // 1-24: 4 bytes, 25-53: 8 bytes
        tMap_.put("double precision", TypeClass.DOUBLE); // 1-24: 4 bytes, 25-53: 8 bytes
        tMap_.put("float8", TypeClass.DOUBLE); // 1-24: 4 bytes, 25-53: 8 bytes
  //      tMap_.put("real", TypeClass.REAL); // == float(24)
        tMap_.put("numeric", TypeClass.DECIMAL); // max precision 38
  //      tMap_.put("dec", TypeClass.DECIMAL); // max precision 38
        tMap_.put("decimal", TypeClass.DECIMAL); // max precision 38
        tMap_.put("money", TypeClass.MONEY); // 8 bytes, 4 digits after the comma
  //      tMap_.put("smallmoney", TypeClass.SMALLMONEY);// 4 bytes, 4 digits after
  //                                                    // the comma
    
        tMap_.put("char", TypeClass.CHAR);
        tMap_.put("character", TypeClass.CHAR);
  //      tMap_.put("nchar", TypeClass.NCHAR);
        tMap_.put("character varying", TypeClass.VARCHAR);
        tMap_.put("varchar", TypeClass.VARCHAR);
  //      tMap_.put("nvarchar", TypeClass.NVARCHAR);
        tMap_.put("text", TypeClass.VARCHAR); // == varchar(max)
  //      tMap_.put("ntext", TypeClass.NVARCHAR); // == nvarchar(max)
    
        tMap_.put("bytea", TypeClass.BINARY);
  //      tMap_.put("varbinary", TypeClass.VARBINARY);
  //      tMap_.put("image", TypeClass.VARBINARY); // == varbinary(max)
    
        tMap_.put("date", TypeClass.DATE); // 0001-01-01 through 9999-12-31
        tMap_.put("time", TypeClass.TIME); // 00:00:00.0000000 through
        // 23:59:59.9999999
        tMap_.put("time with timezone", TypeClass.TIME); // 00:00:00.0000000 through
        // 23:59:59.9999999
        tMap_.put("time without timezone", TypeClass.TIME); // 00:00:00.0000000 through
        // 23:59:59.9999999
  //      tMap_.put("smalldatetime", TypeClass.TIMESTAMP); // 1900-01-01 to
                                                         // 2079-06-06, 00:00:00
                                                         // to 23:59:59
        tMap_.put("timestamp with time zone", TypeClass.TIMESTAMP); // 1753-01-01 to 9999-12-31,
        // 00:00:00 to 23:59:59.997
        tMap_.put("timestamp", TypeClass.TIMESTAMP); // 1753-01-01 to 9999-12-31,
        // 00:00:00 to 23:59:59.997
  //      tMap_.put("datetime", TypeClass.TIMESTAMP); // 1753-01-01 to 9999-12-31,
                                                    // 00:00:00 to 23:59:59.997
  //      tMap_.put("datetime2", TypeClass.TIMESTAMP); // 0001-01-01 to 9999-12-31,
                                                     // 00:00:00 to
                                                     // 23:59:59.9999999
  //      tMap_.put("datetimeoffset", TypeClass.TIMESTAMP); // 0001-01-01 to
                                                          // 9999-12-31, 00:00:00
                                                          // to 23:59:59.9999999
        // interval
        tMap_.put("oid", TypeClass.INT); // 4 bytes

        // GEO types
        // box
        // circle
        // line
        // lseg
        // path
        // point
        // polygon
  
        // Hardware types
        // cidr (IPv4 or ipv6 addr)
        // inet (IPv4 or ipv6 addr)
        // macaddr
  
        // Text search
        // tsquery
        // tsvector
  
        // Internal types
        // txid_snapshot
        // uuid
  
        // Useful
        // xml
      }
    
      private static final Set<String>                  maxedVars_ = new HashSet<String>();
    
      static {
        maxedVars_.add("text");
        maxedVars_.add("bytea");
      }
    
      private static final Map<String, ReferenceAction> raMap_     = new HashMap<String, ReferenceAction>();
    
      static {
        raMap_.put("NO ACTION", ReferenceAction.NO_ACTION);
        raMap_.put("CASCADE", ReferenceAction.CASCADE);
        raMap_.put("SET NULL", ReferenceAction.SET_NULL);
        raMap_.put("SET DEFAULT", ReferenceAction.SET_DEFAULT);
      }
    }

  /*
   * (non-Javadoc)
   * 
   * @see nl.rakis.sql.DbDriverBase#getName2TypeMap()
   */
  @Override
  public Map<String, TypeClass> getName2TypeMap() {
    return N2TMapHolder.tMap_;
  }

  /*
   * (non-Javadoc)
   * 
   * @see nl.rakis.sql.DbDriver#getMaxedVars()
   */
  @Override
  public Set<String> getMaxedVars() {
    return N2TMapHolder.maxedVars_;
  }

  /*
   * (non-Javadoc)
   * 
   * @see nl.rakis.sql.DbDriverBase#getName2ReferenceActionMap()
   */
  @Override
  public Map<String, ReferenceAction> getName2ReferenceActionMap() {
    return N2TMapHolder.raMap_;
  }

  private static final class T2NMapHolder
  {
    private static final Map<TypeClass, String>       tMap_   = new HashMap<TypeClass, String>();
  
    static {
      tMap_.put(TypeClass.BOOLEAN, "boolean");

      tMap_.put(TypeClass.BIT, "bit");
      tMap_.put(TypeClass.VARBIT, "varbit");
  
      tMap_.put(TypeClass.LONG, "bigint");
      tMap_.put(TypeClass.INT, "int");
      tMap_.put(TypeClass.SHORT, "smallint");
      tMap_.put(TypeClass.BYTE, "tinyint");
      // tMap_.put(TypeClass.REAL, "float");
      tMap_.put(TypeClass.REAL, "real");
      // tMap_.put(TypeClass.DECIMAL, "dec");
      tMap_.put(TypeClass.DECIMAL, "numeric");
      tMap_.put(TypeClass.MONEY, "money");
      tMap_.put(TypeClass.SMALLMONEY, "money");
  
      tMap_.put(TypeClass.CHAR, "char");
      tMap_.put(TypeClass.NCHAR, "nchar");
      tMap_.put(TypeClass.VARCHAR, "varchar");
      tMap_.put(TypeClass.NVARCHAR, "varchar");
      //tMap_.put(TypeClass.VARCHAR, "text");
      //tMap_.put(TypeClass.NVARCHAR, "ntext");
  
      // tMap_.put(TypeClass.BINARY, "binary");
      tMap_.put(TypeClass.VARBINARY, "bytea");
      // tMap_.put(TypeClass.VARBINARY, "image");
  
      tMap_.put(TypeClass.DATE, "date");
      tMap_.put(TypeClass.TIME, "time");
      // tMap_.put(TypeClass.TIMESTAMP, "smalldatetime");
      // tMap_.put(TypeClass.TIMESTAMP, "timestamp");
      tMap_.put(TypeClass.TIMESTAMP, "timestamp");
      // tMap_.put(TypeClass.TIMESTAMP, "datetime2");
      // tMap_.put(TypeClass.TIMESTAMP, "datetimeoffset");
    }
  
    private static final Map<ReferenceAction, String> arMap_ = new HashMap<ReferenceAction, String>();
  
    static {
      arMap_.put(ReferenceAction.NO_ACTION, "NO ACTION");
      arMap_.put(ReferenceAction.CASCADE, "CASCADE");
      arMap_.put(ReferenceAction.SET_NULL, "SET NULL");
      arMap_.put(ReferenceAction.SET_DEFAULT, "SET DEFAULT");
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see nl.rakis.sql.DbDriverBase#getType2NameMap()
   */
  @Override
  public Map<TypeClass, String> getType2NameMap() {
    return T2NMapHolder.tMap_;
  }

  /*
   * (non-Javadoc)
   * 
   * @see nl.rakis.sql.DbDriverBase#getReferenceAction2StringMap()
   */
  @Override
  public Map<ReferenceAction, String> getReferenceAction2StringMap() {
    return T2NMapHolder.arMap_;
  }

  /*
   * (non-Javadoc)
   * 
   * @see nl.rakis.sql.DbDriver#buildTypeString(nl.rakis.sql.ddl.model.Type)
   */
  @Override
  public String buildTypeString(Type type) {
    StringBuffer buf = new StringBuffer();

    final TypeClass clazz = type.getClazz();

    // Take care of special case
    if (((clazz == TypeClass.VARCHAR) || (clazz == TypeClass.NVARCHAR) || (clazz == TypeClass.VARBINARY)) &&
        (type.getLength() == -1))
    {
      switch (clazz) {

      case VARCHAR:
      case NVARCHAR:
        buf.append("text");
        break;

      case VARBINARY:
        buf.append("bytea");
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
            // if (type.isCountInChars()) {
            // buf.append(" CHAR");
            // }
            // else {
            // buf.append(" BYTE");
            // }
          }
          buf.append(')');
        }
      }
      if (clazz.hasPrecision()) {
        Integer prec = type.getPrecision();
        Integer scale = type.getScale();

        if ((prec != null) || (scale != null)) {
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
