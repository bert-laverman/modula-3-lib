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

/**
 * @author bertl
 * 
 */
@XmlRootElement(name = "dbSchema", namespace = "http://rakis.nl/sql/ddl/model")
@XmlAccessorType(XmlAccessType.NONE)
public class Schema
  extends NamedObject
{

  /**
   * 
   */
  private static final long          serialVersionUID = 1L;

  @XmlElementWrapper(name = "tables", required = true, nillable = true)
  @XmlElement(name = "table", required = false)
  private List<Table>                tables_          = new ArrayList<Table>();

  private Map<String, Table>         tableMap_        = new TreeMap<String, Table>();

  private List<ForeignKeyConstraint> foreignKeys_     = new ArrayList<ForeignKeyConstraint>();

  private Map<String, Constraint>    constraints_     = new TreeMap<String, Constraint>();

  private List<Index>                indices_         = new ArrayList<Index>();

  private Map<String, Index>         indexMap_        = new TreeMap<String, Index>();

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
  public Collection<Table> getTables()
  {
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
  }

  /**
   * @param name
   * @return
   */
  public Table getTable(String name)
  {
    Table result = null;
    name = name.toLowerCase();

    if (this.tableMap_.containsKey(name)) {
      result = this.tableMap_.get(name);
    }
    return result;
  }

  /**
   * @param tableConstraints the tableConstraints to set
   */
  public void setConstraints(Collection<Constraint> tableConstraints)
  {
    this.constraints_.clear();
    for (Constraint constraint : tableConstraints) {
      addConstraint(constraint);
    }
  }

  /**
   * @return the tableConstraints
   */
  public Collection<Constraint> getConstraints()
  {
    return constraints_.values();
  }

  /**
   * @param constraint
   */
  public void addConstraint(Constraint constraint)
  {
    constraint.setSchema(this);
    this.constraints_.put(constraint.getName().toLowerCase(), constraint);
    switch (constraint.getType()) {
    case FOREIGN_KEY:
      this.foreignKeys_.add((ForeignKeyConstraint) constraint);
      break;

    default:
      // IGNORE
      break;
    }
  }

  /**
   * @param name
   * @return
   */
  public Constraint getConstraint(String name)
  {
    Constraint result = null;
    name = name.toLowerCase();

    if (this.constraints_.containsKey(name)) {
      result = this.constraints_.get(name);
    }
    return result;
  }

  /**
   * @return the foreignKeys
   */
  public List<ForeignKeyConstraint> getForeignKeys()
  {
    return this.foreignKeys_;
  }

  /**
   * @param indices the indices to set
   */
  public void setIndices(List<Index> indices)
  {
    this.indices_.clear();
    this.indexMap_.clear();

    for (Index index: indices) {
      addIndex(index);
    }
  }

  /**
   * @return the indices
   */
  public List<Index> getIndices()
  {
    return indices_;
  }

  public void addIndex(Index index) {
    this.indices_.add(index);
    this.indexMap_.put(index.getName(), index);
  }

  /**
   * @param indexMap the indexMap to set
   */
  public void setIndexMap(Map<String, Index> indexMap)
  {
    this.indices_.clear();
    this.indexMap_.clear();

    this.indices_.addAll(indexMap.values());
    this.indexMap_.putAll(indexMap);
  }

  /**
   * @return the indexMap
   */
  public Map<String, Index> getIndexMap()
  {
    return indexMap_;
  }

}
