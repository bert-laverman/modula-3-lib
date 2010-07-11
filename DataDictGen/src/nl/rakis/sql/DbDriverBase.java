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
import nl.rakis.sql.ddl.model.TypeClass;

/**
 * @author bertl
 * 
 */
public abstract class DbDriverBase
  implements DbDriver
{

  private Properties props_ = null;

  @Override
  public void setProperties(Properties props)
  {
    this.props_ = props;
  }

  protected String getProp(String name)
  {
    String result = null;

    if (this.props_ != null) {
      result = this.props_.getProperty(name);
    }
    return (result == null) ? "" : result;
  }

  protected Integer getIntProp(String name)
  {
    try {
      return new Integer(getProp(name));
    }
    catch (NumberFormatException e) {
      // IGNORE bad or missing value
    }
    return null;
  }

  protected Boolean getBoolProp(String name)
  {
    return getProp(name).equalsIgnoreCase("true");
  }

  /* (non-Javadoc)
   * @see nl.rakis.sql.DbDriver#getSchemaXmlWriter(java.io.PrintWriter)
   */
  @Override
  public SchemaGenerator getSchemaXmlWriter(PrintWriter writer)
  {
    return new SchemaXmlWriter(writer);
  }

  /**
   * @return
   */
  public abstract Map<String, TypeClass> getName2TypeMap();

  /**
   * @return
   */
  public abstract Map<TypeClass, String> getType2NameMap();

  /* (non-Javadoc)
   * @see nl.rakis.sql.DbDriver#string2Type(java.lang.String)
   */
  @Override
  public TypeClass string2Type(String name)
  {
    Map<String, TypeClass> map = getName2TypeMap();

    return map.containsKey(name) ? map.get(name) : null;
  }

  /* (non-Javadoc)
   * @see nl.rakis.sql.DbDriver#type2String(nl.rakis.sql.ddl.model.TypeClass)
   */
  @Override
  public String type2String(TypeClass type)
  {
    Map<TypeClass, String> map = getType2NameMap();

    return map.containsKey(type) ? map.get(type) : null;
  }

  /* (non-Javadoc)
   * @see nl.rakis.sql.DbDriver#getSchemaXmlReader(java.io.Reader)
   */
  @Override
  public SchemaLoader getSchemaXmlReader(Reader reader) {
    SchemaXmlLoader loader = new SchemaXmlLoader();
    loader.setDriver(this);
    loader.setReader(reader);
  
    return loader;
  }

}
