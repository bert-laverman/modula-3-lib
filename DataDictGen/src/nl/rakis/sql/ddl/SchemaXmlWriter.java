/**
 * 
 */
package nl.rakis.sql.ddl;

import java.io.PrintWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import nl.rakis.sql.ddl.model.Column;
import nl.rakis.sql.ddl.model.Constraint;
import nl.rakis.sql.ddl.model.ForeignKeyConstraint;
import nl.rakis.sql.ddl.model.Index;
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
public class SchemaXmlWriter
  extends SchemaWriterBase
{

  private JAXBContext context_ = null;
  private Marshaller  marshaller_;

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
      this.marshaller_ = this.context_.createMarshaller();
    }
  }

  /**
   * 
   */
  public SchemaXmlWriter()
  {
    super();
  }

  /**
   * @param writer
   */
  public SchemaXmlWriter(PrintWriter writer)
  {
    super(writer);
  }

  /* (non-Javadoc)
   * @see nl.rakis.sql.ddl.SchemaGenerator#create(nl.rakis.sql.ddl.model.Schema)
   */
  @Override
  public void create(Schema schema)
  {
    try {
      init();
      this.marshaller_.marshal(schema, getWriter());
    }
    catch (JAXBException e) {
      e.printStackTrace();
    }
  }

  /* (non-Javadoc)
   * @see nl.rakis.sql.ddl.SchemaGenerator#create(nl.rakis.sql.ddl.model.Table)
   */
  @Override
  public void create(Table table)
  {
    try {
      init();
      this.marshaller_.marshal(table, getWriter());
    }
    catch (JAXBException e) {
      e.printStackTrace();
    }
  }

  /* (non-Javadoc)
   * @see nl.rakis.sql.ddl.SchemaGenerator#create(nl.rakis.sql.ddl.model.Column)
   */
  @Override
  public void create(Column column)
  {
    try {
      init();
      this.marshaller_.marshal(column, getWriter());
    }
    catch (JAXBException e) {
      e.printStackTrace();
    }
  }

  /* (non-Javadoc)
   * @see nl.rakis.sql.ddl.SchemaGenerator#create(nl.rakis.sql.ddl.model.Constraint)
   */
  @Override
  public void create(Constraint cons)
  {
    try {
      init();
      this.marshaller_.marshal(cons, getWriter());
    }
    catch (JAXBException e) {
      e.printStackTrace();
    }
  }

  /* (non-Javadoc)
   * @see nl.rakis.sql.ddl.SchemaGenerator#create(nl.rakis.sql.ddl.model.Index)
   */
  @Override
  public void create(Index index)
  {
    try {
      init();
      this.marshaller_.marshal(index, getWriter());
    }
    catch (JAXBException e) {
      e.printStackTrace();
    }
  }

  /* (non-Javadoc)
   * @see nl.rakis.sql.ddl.SchemaGenerator#drop(nl.rakis.sql.ddl.model.Table)
   */
  @Override
  public void drop(Table table)
  {
    // IGNORE
  }

  /* (non-Javadoc)
   * @see nl.rakis.sql.ddl.SchemaGenerator#drop(nl.rakis.sql.ddl.model.Column)
   */
  @Override
  public void drop(Column cons)
  {
    // IGNORE
  }

  /* (non-Javadoc)
   * @see nl.rakis.sql.ddl.SchemaGenerator#drop(nl.rakis.sql.ddl.model.Constraint)
   */
  @Override
  public void drop(Constraint cons)
  {
    // IGNORE
  }

  /* (non-Javadoc)
   * @see nl.rakis.sql.ddl.SchemaGenerator#drop(nl.rakis.sql.ddl.model.Index)
   */
  @Override
  public void drop(Index index)
  {
    // IGNORE
  }

}
