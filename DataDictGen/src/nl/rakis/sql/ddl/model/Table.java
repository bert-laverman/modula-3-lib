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
  implements Comparable<Table>
{

  /**
   * 
   */
  private static final long          serialVersionUID  = 1L;

  private transient boolean          dirty_            = true;

  private List<Column>               columns_          = new ArrayList<Column>();
  private Map<String, Column>        columnMap_        = new HashMap<String, Column>();

  private List<Constraint>           constraints_      = new ArrayList<Constraint>();

  private Map<String, Constraint>    constraintMap_    = new HashMap<String, Constraint>();

  private PrimaryKeyConstraint       primaryKey_;

  private List<ForeignKeyConstraint> foreignKeys_      = new ArrayList<ForeignKeyConstraint>();

  private List<UniqueConstraint>     uniqueKeys_       = new ArrayList<UniqueConstraint>();

  private List<CheckConstraint>      checkConstraints_ = new ArrayList<CheckConstraint>();

  private List<Index>                indices_          = new ArrayList<Index>();

  private List<Index>                nonKeyIndices_    = new ArrayList<Index>();

  private Map<String, Index>         indexMap_         = new TreeMap<String, Index>();

  /**
   * 
   */
  public Table() {
    super();
  }

  /**
   * @param catalog
   * @param schema
   * @param name
   */
  public Table(Schema schema, String name) {
    super(schema, name);
  }

  /**
   * @param name
   */
  public Table(String name) {
    super(name);
  }

  private void rebuild() {
    final boolean isDirty = this.dirty_;

    if (isDirty || (this.columns_.size() != this.columnMap_.size())) {
      this.columnMap_.clear();
      for (Column column : this.columns_) {
        this.columnMap_.put(column.getName().toLowerCase(), column);
        column.setTable(this);
      }
    }

    int n = this.foreignKeys_.size() + this.uniqueKeys_.size() + this.checkConstraints_.size();
    if (this.primaryKey_ != null) {
      n += 1;
    }
    if (isDirty || (n != this.constraints_.size())) {
      this.constraints_.clear();
      this.constraintMap_.clear();

      if (this.primaryKey_ != null) {
        this.constraints_.add(this.primaryKey_);
        this.constraintMap_.put(this.primaryKey_.getName(), this.primaryKey_);
      }
      for (Constraint constraint : this.foreignKeys_) {
        this.constraints_.add(constraint);
        this.constraintMap_.put(constraint.getName(), constraint);
      }
      for (Constraint constraint : this.uniqueKeys_) {
        this.constraints_.add(constraint);
        this.constraintMap_.put(constraint.getName(), constraint);
      }
      for (Constraint constraint : this.checkConstraints_) {
        this.constraints_.add(constraint);
        this.constraintMap_.put(constraint.getName(), constraint);
      }
    }

    if (isDirty || (this.indexMap_.size() != this.indices_.size())) {
      this.indexMap_.clear();
      this.nonKeyIndices_.clear();
      for (Index index : this.indices_) {
        index.setSchema(getSchema());
        index.setTable(this);

        this.indexMap_.put(index.getName(), index);
        if (!this.constraintMap_.containsKey(index.getName())) {
          this.nonKeyIndices_.add(index);
        }
      }
    }
    setDirty(false);
  }

  /**
   * @param dirty
   *          the dirty to set
   */
  public void setDirty(boolean dirty) {
    dirty_ = dirty;
  }

  /**
   * @return the dirty
   */
  public boolean isDirty() {
    return dirty_;
  }

  /**
   * @param columns
   *          the columns to set
   */
  public void setColumns(List<Column> columns) {
    this.columns_ = columns;
    setDirty(true);
  }

  /**
   * @return the columns
   */
  @XmlElementWrapper(name = "columns", required = true, nillable = true)
  @XmlElement(name = "column", required = false)
  public Collection<Column> getColumns() {
    rebuild();

    return this.columns_;
  }

  /**
   * NOTE this method preserves cleanliness
   * 
   * @param column
   */
  public void addColumn(Column column) {
    this.columns_.add(column);
    this.columnMap_.put(column.getName().toLowerCase(), column);
    column.setTable(this);
  }

  public Column getColumn(String name) {
    rebuild();

    Column result = null;

    if (this.columnMap_.containsKey(name.toLowerCase())) {
      result = this.columnMap_.get(name.toLowerCase());
    }
    return result;
  }

  /**
   * @param constraints
   *          the constraints to set
   */
  public void setConstraints(List<Constraint> constraints) {
    this.constraints_ = constraints;

    setDirty(true);
  }

  /**
   * @return the constraints
   */
  public List<Constraint> getConstraints() {
    rebuild();

    return constraints_;
  }

  /**
   * @param constraint
   */
  public void addConstraint(Constraint constraint) {
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
      this.checkConstraints_.add((CheckConstraint) constraint);
      break;
    }
  }

  /**
   * @param string
   * @return
   */
  public ColumnedConstraint getColumnedConstraint(String string) {
    rebuild();

    ColumnedConstraint result = null;

    if (this.constraintMap_.containsKey(string)) {
      result = (ColumnedConstraint) this.constraintMap_.get(string);
    }
    return result;
  }

  /**
   * @param primaryKey
   *          the primaryKey to set
   */
  public void setPrimaryKey(PrimaryKeyConstraint primaryKey) {
    this.primaryKey_ = primaryKey;
    if (this.primaryKey_ != null) {
      this.primaryKey_.setTable(this);
    }
    setDirty(true);
  }

  /**
   * @return the primaryKey
   */
  @XmlElement(name = "primary-key", required = true, nillable = true)
  public PrimaryKeyConstraint getPrimaryKey() {
    return primaryKey_;
  }

  /**
   * @param foreignKeys
   *          the foreignKeys to set
   */
  public void setForeignKeys(List<ForeignKeyConstraint> foreignKeys) {
    this.foreignKeys_ = foreignKeys;

    setDirty(true);
  }

  /**
   * @return the foreignKeys
   */
  @XmlElementWrapper(name = "foreign-keys", required = true, nillable = true)
  @XmlElement(name = "foreign-key")
  public List<ForeignKeyConstraint> getForeignKeys() {
    return this.foreignKeys_;
  }

  /**
   * @param uniqueKeys
   *          the uniqueKeys to set
   */
  public void setUniqueKeys(List<UniqueConstraint> uniqueKeys) {
    this.uniqueKeys_ = uniqueKeys;

    setDirty(true);
  }

  /**
   * @return the uniqueKeys
   */
  @XmlElementWrapper(name = "unique-keys")
  @XmlElement(name = "unique-key")
  public List<UniqueConstraint> getUniqueKeys() {
    return this.uniqueKeys_;
  }

  /**
   * @param checkConstraints the checkConstraints to set
   */
  public void setCheckConstraints(List<CheckConstraint> checkConstraints) {
    this.checkConstraints_ = checkConstraints;

    setDirty(true);
  }

  /**
   * @return the checkConstraints
   */
  @XmlElementWrapper(name = "check-constraints")
  @XmlElement(name = "check-constraint")
  public List<CheckConstraint> getCheckConstraints() {
    return checkConstraints_;
  }

  /**
   * @param indices
   *          the indices to set
   */
  public void setIndices(List<Index> indices) {
    this.indices_ = indices;

    setDirty(true);
  }

  /**
   * @return the indices
   */
  @XmlElementWrapper(name = "indices", required = true, nillable = true)
  @XmlElement(name = "index")
  public List<Index> getIndices() {
    rebuild();

    return indices_;
  }

  /**
   * @param index
   */
  public void addIndex(Index index) {
    index.setSchema(getSchema());
    index.setTable(this);

    this.indices_.add(index);
  }

  /**
   * @return the nonKeyIndices
   */
  public List<Index> getNonKeyIndices() {
    rebuild();

    return nonKeyIndices_;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  @Override
  public int compareTo(Table that) {
    return this.getName().compareTo(that.getName());
  }

  /**
   * Two tables are the same if they have the same columns and constraints.
   *
   * @param that
   * @return
   */
  public boolean same(Table that) {
    boolean result = true;

    if (this.columnMap_.keySet().equals(that.columnMap_.keySet())) {
      for (Column column: this.columns_) {
        if (!column.same(that.getColumn(column.getName()))) {
          result = false;
          break;
        }
      }
    }

    return result;
  }
}
