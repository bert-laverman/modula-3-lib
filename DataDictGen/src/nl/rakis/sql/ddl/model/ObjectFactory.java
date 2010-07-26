/**
 * 
 */
package nl.rakis.sql.ddl.model;

import javax.xml.bind.annotation.XmlRegistry;

/**
 * @author bertl
 * 
 */
@XmlRegistry
public class ObjectFactory
{

  public static Schema createSchema() {
    return new Schema();
  }

  public static Table createTable() {
    return new Table();
  }

  public static PrimaryKeyConstraint createPrimaryKeyConstraint() {
    return new PrimaryKeyConstraint();
  }

  public static ForeignKeyConstraint createForeignKeyConstraint() {
    return new ForeignKeyConstraint();
  }

  public static UniqueConstraint createUniqueConstraint() {
    return new UniqueConstraint();
  }

  public static CheckConstraint createCheckConstraint() {
    return new CheckConstraint();
  }

  public static Index createIndex() {
    return new Index();
  }

  public static Column createColumn() {
    return new Column();
  }

  public static Type createType() {
    return new Type();
  }
}
