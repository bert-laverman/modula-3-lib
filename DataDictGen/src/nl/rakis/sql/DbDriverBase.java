/**
 * 
 */
package nl.rakis.sql;

import java.io.PrintWriter;
import java.io.Reader;
import java.util.Map;
import java.util.Properties;

import nl.rakis.sql.ddl.SchemaGenerator;
import nl.rakis.sql.ddl.SchemaLoader;
import nl.rakis.sql.ddl.SchemaXmlLoader;
import nl.rakis.sql.ddl.SchemaXmlWriter;
import nl.rakis.sql.ddl.model.ReferenceAction;
import nl.rakis.sql.ddl.model.TypeClass;

/**
 * @author bertl
 * 
 */
/**
 * @author bertl
 * 
 */
public abstract class DbDriverBase
  implements DbDriver
{

  private Properties props_ = null;
  private boolean    sqlFormatted_;

  /**
   * 
   */
  public DbDriverBase() {
    super();
  }

  @Override
  public void setProperties(Properties props) {
    this.props_ = props;
    this.sqlFormatted_ = getBoolProp(SqlTool.SQL_FORMATTED);
  }

  protected String getProp(String name) {
    String result = null;

    if (this.props_ != null) {
      result = this.props_.getProperty(name);
    }
    return (result == null) ? "" : result;
  }

  protected Integer getIntProp(String name) {
    try {
      return new Integer(getProp(name));
    }
    catch (NumberFormatException e) {
      // IGNORE bad or missing value
    }
    return null;
  }

  protected Boolean getBoolProp(String name) {
    try {
      return getProp(name).equalsIgnoreCase("true");
    }
    catch (NullPointerException e) {
      // IGNORE
    }
    return false;
  }

  /*
   * (non-Javadoc)
   * 
   * @see nl.rakis.sql.DbDriver#getSchemaXmlWriter(java.io.PrintWriter)
   */
  @Override
  public SchemaGenerator getSchemaXmlWriter(PrintWriter writer) {
    return new SchemaXmlWriter(this, writer);
  }

  /**
   * @return
   */
  public abstract Map<String, TypeClass> getName2TypeMap();

  /*
   * (non-Javadoc)
   * 
   * @see nl.rakis.sql.DbDriver#string2Type(java.lang.String)
   */
  @Override
  public TypeClass string2Type(String name) {
    Map<String, TypeClass> map = getName2TypeMap();

    return map.containsKey(name) ? map.get(name) : null;
  }

  /**
   * @return
   */
  public abstract Map<TypeClass, String> getType2NameMap();

  /*
   * (non-Javadoc)
   * 
   * @see nl.rakis.sql.DbDriver#type2String(nl.rakis.sql.ddl.model.TypeClass)
   */
  @Override
  public String type2String(TypeClass type) {
    Map<TypeClass, String> map = getType2NameMap();

    return map.containsKey(type) ? map.get(type) : null;
  }

  /**
   * @return
   */
  public abstract Map<String, ReferenceAction> getName2ReferenceActionMap();

  /*
   * (non-Javadoc)
   * 
   * @see nl.rakis.sql.DbDriver#string2ReferenceAction(java.lang.String)
   */
  @Override
  public ReferenceAction string2ReferenceAction(String action) {
    Map<String, ReferenceAction> map = getName2ReferenceActionMap();

    return map.containsKey(action) ? map.get(action) : null;
  }

  /**
   * @return
   */
  public abstract Map<ReferenceAction, String> getReferenceAction2StringMap();

  /*
   * (non-Javadoc)
   * 
   * @seenl.rakis.sql.DbDriver#referenceAction2String(nl.rakis.sql.ddl.model.
   * ReferenceAction)
   */
  @Override
  public String referenceAction2String(ReferenceAction action) {
    Map<ReferenceAction, String> map = getReferenceAction2StringMap();

    return map.containsKey(action) ? map.get(action) : null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see nl.rakis.sql.DbDriver#getSchemaXmlReader(java.io.Reader)
   */
  @Override
  public SchemaLoader getSchemaXmlReader(Reader reader) {
    SchemaXmlLoader loader = new SchemaXmlLoader();
    loader.setDriver(this);
    loader.setReader(reader);

    return loader;
  }

  /*
   * (non-Javadoc)
   * 
   * @see nl.rakis.sql.DbDriver#isSqlFormatted()
   */
  @Override
  public boolean isSqlFormatted() {
    return sqlFormatted_;
  }

  /*
   * (non-Javadoc)
   * 
   * @see nl.rakis.sql.DbDriver#setSqlFormatted(boolean)
   */
  @Override
  public void setSqlFormatted(boolean sqlFormatted) {
    sqlFormatted_ = sqlFormatted;
  }

}
