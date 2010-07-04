/**
 * 
 */
package nl.rakis.sql.ddl.model;

import static javax.xml.bind.annotation.XmlAccessType.NONE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

/**
 * @author bertl
 * 
 */
@XmlType(name = "TableType")
@XmlAccessorType(NONE)
public class Table
  extends SchemaObject
{

  /**
   * 
   */
  private static final long          serialVersionUID = 1L;

  @XmlElementWrapper(name = "columns", required = true, nillable = true)
  @XmlElement(name = "column", required = false)
  private List<Column>               columns_         = new ArrayList<Column>();
  private Map<String, Column>        columnMap_       = new HashMap<String, Column>();

  private List<Constraint>           constraints_     = new ArrayList<Constraint>();

  @XmlElement(name = "primary-key", required = true, nillable = true)
  private PrimaryKeyConstraint       primaryKey_;

  @XmlElementWrapper(name = "foreign-keys", required = true, nillable = true)
  @XmlElement(name = "foreign-key")
  private List<ForeignKeyConstraint> foreignKeys_     = new ArrayList<ForeignKeyConstraint>();

  @XmlElementWrapper(name = "unique-keys")
  @XmlElement(name = "unique-key")
  private List<UniqueConstraint>     uniqueKeys_      = new ArrayList<UniqueConstraint>();

  @XmlElementWrapper(name = "indices", required = true, nillable = true)
  @XmlElement(name = "index")
  private List<Index>                indices_         = new ArrayList<Index>();

  private Map<String, Index>         indexMap_        = new TreeMap<String, Index>();

  /**
   * 
   */
  public Table()
  {
    super();
  }

  /**
   * @param catalog
   * @param schema
   * @param name
   */
  public Table(Schema schema, String name)
  {
    super(schema, name);
  }

  /**
   * @param name
   */
  public Table(String name)
  {
    super(name);
  }

  /**
   * @param columns the columns to set
   */
  public void setColumns(Collection<Column> columns)
  {
    this.columns_.clear();
    this.columnMap_.clear();
    for (Column column : columns) {
      addColumn(column);
    }
  }

  /**
   * @return the columns
   */
  public Collection<Column> getColumns()
  {
    return this.columns_;
  }

  public void addColumn(Column column)
  {
    this.columns_.add(column);
    this.columnMap_.put(column.getName().toLowerCase(), column);
    column.setTable(this);
  }

  public Column getColumn(String name)
  {
    Column result = null;

    if (this.columnMap_.containsKey(name.toLowerCase())) {
      result = this.columnMap_.get(name.toLowerCase());
    }
    return result;
  }

  /**
   * @param constraints the constraints to set
   */
  public void setConstraints(List<Constraint> constraints)
  {
    this.constraints_ = constraints;
  }

  /**
   * @return the constraints
   */
  public List<Constraint> getConstraints()
  {
    return constraints_;
  }

  /**
   * @param constraint
   */
  public void addConstraint(Constraint constraint)
  {
    constraint.setSchema(getSchema());
    constraint.setTable(this);

    this.constraints_.add(constraint);

    switch (constraint.getType()) {
    case PRIMARY_KEY:
      this.primaryKey_ = (PrimaryKeyConstraint) constraint;
      break;

    case FOREIGN_KEY:
      this.foreignKeys_.add((ForeignKeyConstraint) constraint);
      break;

    case UNIQUE:
      this.uniqueKeys_.add((UniqueConstraint) constraint);
      break;

    case CHECK:
      //TODO
      break;
    }
  }

  /**
   * @return the primaryKey
   */
  public PrimaryKeyConstraint getPrimaryKey()
  {
    return primaryKey_;
  }

  /**
   * @return the foreignKeys
   */
  public List<ForeignKeyConstraint> getForeignKeys()
  {
    return this.foreignKeys_;
  }

  /**
   * @return the uniqueKeys
   */
  public List<UniqueConstraint> getUniqueKeys()
  {
    return this.uniqueKeys_;
  }

  /**
   * @param indices the indices to set
   */
  public void setIndices(List<Index> indices)
  {
    this.indices_.clear();
    this.indexMap_.clear();

    for (Index index : indices) {
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

  public void addIndex(Index index)
  {
    index.setSchema(getSchema());
    index.setTable(this);

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
