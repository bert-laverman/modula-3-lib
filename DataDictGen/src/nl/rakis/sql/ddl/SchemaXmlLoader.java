/**
 * 
 */
package nl.rakis.sql.ddl;

import java.sql.SQLException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import nl.rakis.sql.ddl.model.Column;
import nl.rakis.sql.ddl.model.ForeignKeyConstraint;
import nl.rakis.sql.ddl.model.PrimaryKeyConstraint;
import nl.rakis.sql.ddl.model.Schema;
import nl.rakis.sql.ddl.model.Table;
import nl.rakis.sql.ddl.model.Type;
import nl.rakis.sql.ddl.model.TypeClass;
import nl.rakis.sql.ddl.model.UniqueConstraint;

/**
 * @author bertl
 * 
 */
public class SchemaXmlLoader
  extends SchemaLoaderBase
{

  private JAXBContext context_ = null;
  private Unmarshaller  unMarshaller_;

  /**
   * @param driver
   * @param db
   */
  public SchemaXmlLoader() {
    super();
  }

  private void init()
    throws JAXBException
  {
    if (this.context_ == null) {
      this.context_ = JAXBContext.newInstance(Schema.class, Table.class,
                                              Column.class, Type.class,
                                              TypeClass.class,
                                              PrimaryKeyConstraint.class,
                                              ForeignKeyConstraint.class,
                                              UniqueConstraint.class);
      this.unMarshaller_ = this.context_.createUnmarshaller();
    }
  }

  /**
   * @param <T>
   * @param docClass
   * @param reader
   * @return
   * @throws JAXBException
   */
  public Schema unmarshal()
    throws JAXBException
  {
    init();

    return (Schema) this.unMarshaller_.unmarshal (getReader());
  }

  /*
   * (non-Javadoc)
   * 
   * @see nl.rakis.sql.ddl.SchemaLoader#load(java.lang.String)
   */
  @Override
  public Schema load(String name)
    throws SQLException
  {
    Schema result = null;

    try {
      result = unmarshal();
      result.fixReferences();
    }
    catch (JAXBException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return result;
  }

}
