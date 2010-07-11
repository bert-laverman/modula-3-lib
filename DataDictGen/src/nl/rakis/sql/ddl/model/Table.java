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
@XmlType(name = "TableType", factoryClass = ObjectFactory.class, factoryMethod = "createTable")
@XmlAccessorType(NONE)
public class Table
  extends SchemaObject
{

  /**
   * 
   */
  private static final long          serialVersionUID = 1L;

  private List<Column>               columns_         = new ArrayList<Column>();
  private Map<String, Column>        columnMap_       = new HashMap<String, Column>();

  private List<Constraint>           constraints_     = new ArrayList<Constraint>();

  private Map<String, Constraint>    constraintMap_ = new HashMap<String, Constraint>();

  private PrimaryKeyConstraint       primaryKey_;

  private List<ForeignKeyConstraint> foreignKeys_     = new ArrayList<ForeignKeyConstraint>();

  private List<UniqueConstraint>     uniqueKeys_      = new ArrayList<UniqueConstraint>();

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
    this.columns_.addAll(columns);
  }

  /**
   * @return the columns
   */
  @XmlElementWrapper(name = "columns", required = true, nillable = true)
  @XmlElement(name = "column", required = false)
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
    if (this.columns_.size() != this.columnMap_.size()) {
      this.columnMap_.clear();
      for (Column column: this.columns_) {
        this.columnMap_.put(column.getName(), column);
      }
    }

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
    this.constraints_.clear();
    for (Constraint constraint: constraints) {
      addConstraint(constraint);
    }
  }

  /**
   * @return the constraints
   */
  public List<Constraint> getConstraints()
  {
    int n = this.foreignKeys_.size() + this.uniqueKeys_.size();
    if (this.primaryKey_ != null) {
      n += 1;
    }
    if (n != this.constraints_.size()) {
      this.constraints_.clear();
      this.constraintMap_.clear();

      if (this.primaryKey_ != null) {
        this.constraints_.add(this.primaryKey_);
        this.constraintMap_.put(this.primaryKey_.getName(), this.primaryKey_);
      }
      for (Constraint constraint: this.foreignKeys_) {
        this.constraints_.add(constraint);
        this.constraintMap_.put(constraint.getName(), constraint);
      }
      for (Constraint constraint: this.uniqueKeys_) {
        this.constraints_.add(constraint);
        this.constraintMap_.put(constraint.getName(), constraint);
      }
    }
    return constraints_;
  }

  /**
   * @param constraint
   */
  public void addConstraint(Constraint constraint)
  {
    constraint.setTable(this);

    this.constraints_.add(constraint);
    this.constraintMap_.put(constraint.getName(), constraint);

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
   * @param string
   * @return
   */
  public ColumnedConstraint getColumnedConstraint(String string) {
    ColumnedConstraint result = null;
  
    if (this.constraintMap_.containsKey(string)) {
      result = (ColumnedConstraint) this.constraintMap_.get(string);
    }
    return result;
  }

  /**
   * @param primaryKey the primaryKey to set
   */
  public void setPrimaryKey(PrimaryKeyConstraint primaryKey) {
    this.primaryKey_ = primaryKey;
    if (this.primaryKey_ != null) {
      this.primaryKey_.setTable(this);
    }
  }

  /**
   * @return the primaryKey
   */
  @XmlElement(name = "primary-key", required = true, nillable = true)
  public PrimaryKeyConstraint getPrimaryKey()
  {
    return primaryKey_;
  }

  /**
   * @param foreignKeys the foreignKeys to set
   */
  public void setForeignKeys(List<ForeignKeyConstraint> foreignKeys) {
    this.foreignKeys_ = foreignKeys;
    for (ForeignKeyConstraint key: this.foreignKeys_) {
      key.setTable(this);
    }
  }

  /**
   * @return the foreignKeys
   */
  @XmlElementWrapper(name = "foreign-keys", required = true, nillable = true)
  @XmlElement(name = "foreign-key")
  public List<ForeignKeyConstraint> getForeignKeys()
  {
    return this.foreignKeys_;
  }

  /**
   * @param uniqueKeys the uniqueKeys to set
   */
  public void setUniqueKeys(List<UniqueConstraint> uniqueKeys) {
    this.uniqueKeys_ = uniqueKeys;
    for (UniqueConstraint constraint: this.uniqueKeys_) {
      constraint.setTable(this);
    }
  }

  /**
   * @return the uniqueKeys
   */
  @XmlElementWrapper(name = "unique-keys")
  @XmlElement(name = "unique-key")
  public List<UniqueConstraint> getUniqueKeys()
  {
    return this.uniqueKeys_;
  }

  /**
   * @param indices the indices to set
   */
  public void setIndices(List<Index> indices)
  {
    this.indices_ = indices;
    for (Index index: this.indices_) {
      index.setTable(this);
    }
  }

  /**
   * @return the indices
   */
  @XmlElementWrapper(name = "indices", required = true, nillable = true)
  @XmlElement(name = "index")
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

}
