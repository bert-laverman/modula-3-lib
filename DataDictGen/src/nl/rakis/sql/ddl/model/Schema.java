/**
 * 
 */
package nl.rakis.sql.ddl.model;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

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
  private static final long  serialVersionUID = 1L;

  private Collection<Table>  tables_          = new TreeSet<Table>();

  private Map<String, Table> tableMap_        = new TreeMap<String, Table>();

  private Collection<View>   views_           = new TreeSet<View>();

  private Map<String, View>  viewMap_         = new TreeMap<String, View>();

  /**
   * 
   */
  public Schema() {
    super();
  }

  /**
   * @param name
   */
  public Schema(String name) {
    super(name);
  }

  public void fixReferences() {
    for (Table table: tables_) {
      table.fixReferences(this);
    }
    for (View view: views_) {
      view.fixReferences(this);
    }
  }

  /**
   * @param tables
   *          the tables to set
   */
  public void setTables(Collection<Table> tables) {
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
  public Collection<Table> getTables() {
    if (this.tableMap_.size() != this.tables_.size()) {
      this.tableMap_.clear();
      for (Table table : this.tables_) {
        this.tableMap_.put(table.getName().toLowerCase(), table);
        table.setSchema(this);
        for (Constraint constraint : table.getConstraints()) {
          constraint.setSchema(this);
        }
      }
    }

    return tables_;
  }

  /**
   * @param table
   */
  public void addTable(Table table) {
    this.tables_.add(table);
    this.tableMap_.put(table.getName().toLowerCase(), table);
    table.setSchema(this);
    for (Constraint constraint : table.getConstraints()) {
      constraint.setSchema(this);
    }
  }

  /**
   * @param name
   * @return
   */
  public Table getTable(String name) {
    if (this.tableMap_.size() != this.tables_.size()) {
      this.tableMap_.clear();
      for (Table table : this.tables_) {
        this.tableMap_.put(table.getName().toLowerCase(), table);
        table.setSchema(this);
        for (Constraint constraint : table.getConstraints()) {
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

  /**
   * @param views the views to set
   */
  public void setViews(Collection<View> views) {
    views_ = views;
  }

  /**
   * @return the views
   */
  @XmlElementWrapper(name = "views", required = true, nillable = true)
  @XmlElement(name = "view", required = false)
  public Collection<View> getViews() {
    return views_;
  }

  /**
   * @param view
   */
  public void addView(View view) {
    this.views_.add(view);
    this.viewMap_.put(view.getName().toLowerCase(), view);
    view.setSchema(this);
  }

  /**
   * @param name
   * @return
   */
  public View getView(String name) {
    if (this.viewMap_.size() != this.views_.size()) {
      this.viewMap_.clear();
      for (View view : this.views_) {
        this.viewMap_.put(view.getName().toLowerCase(), view);
        view.setSchema(this);
      }
    }

    View result = null;
    name = name.toLowerCase();

    if (this.viewMap_.containsKey(name)) {
      result = this.viewMap_.get(name);
    }
    return result;
  }

}
