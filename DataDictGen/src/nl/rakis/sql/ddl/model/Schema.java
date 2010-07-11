/**
 * 
 */
package nl.rakis.sql.ddl.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author bertl
 * 
 */
@XmlRootElement(name = "dbSchema", namespace = "http://rakis.nl/sql/ddl/model")
@XmlType(name = "SchemaType", factoryClass = ObjectFactory.class, factoryMethod = "createSchema")
@XmlAccessorType(XmlAccessType.NONE)
public class Schema
  extends NamedObject
{

  /**
   * 
   */
  private static final long          serialVersionUID = 1L;

  private List<Table>                tables_          = new ArrayList<Table>();

  private Map<String, Table>         tableMap_        = new TreeMap<String, Table>();

  /**
   * @param tables the tables to set
   */
  public void setTables(Collection<Table> tables)
  {
    this.tables_.clear();
    for (Table table : tables) {
      addTable(table);
    }
  }

  /**
   * @return the tables
   */
  @XmlElementWrapper(name = "tables", required = true, nillable = true)
  @XmlElement(name = "table", required = false)
  public Collection<Table> getTables()
  {
    if (this.tableMap_.size() != this.tables_.size()) {
      this.tableMap_.clear();
      for (Table table: this.tables_){ 
        this.tableMap_.put(table.getName().toLowerCase(), table);
        table.setSchema(this);
        for (Constraint constraint: table.getConstraints()) {
          constraint.setSchema(this);
        }
      }
    }

    return tables_;
  }

  /**
   * @param table
   */
  public void addTable(Table table)
  {
    this.tables_.add(table);
    this.tableMap_.put(table.getName().toLowerCase(), table);
    table.setSchema(this);
    for (Constraint constraint: table.getConstraints()) {
      constraint.setSchema(this);
    }
  }

  /**
   * @param name
   * @return
   */
  public Table getTable(String name)
  {
    if (this.tableMap_.size() != this.tables_.size()) {
      this.tableMap_.clear();
      for (Table table: this.tables_){ 
        this.tableMap_.put(table.getName().toLowerCase(), table);
        table.setSchema(this);
        for (Constraint constraint: table.getConstraints()) {
          constraint.setSchema(this);
        }
      }
    }

    Table result = null;
    name = name.toLowerCase();

    if (this.tableMap_.containsKey(name)) {
      result = this.tableMap_.get(name);
    }
    return result;
  }

}
