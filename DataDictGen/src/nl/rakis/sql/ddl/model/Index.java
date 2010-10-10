/**
 * 
 */
package nl.rakis.sql.ddl.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import nl.rakis.util.EqualsUtil;

/**
 * @author bertl
 * 
 */
@XmlType(name = "IndexType")
public class Index
  extends SchemaObject
{

  /**
   * 
   */
  private static final long serialVersionUID = -9178308923671590884L;

  private Table             table_;

  @XmlElementWrapper(name = "columnNames", nillable = true, required = true)
  @XmlElement(name = "columnName", required = false)
  private List<String>      columnNames_     = new ArrayList<String>();

  private List<Column>      columns_         = new ArrayList<Column>();

  @XmlElement(name = "descending", nillable = false)
  private boolean           descending_;

  @XmlElement(name = "unique", nillable = false)
  private boolean           unique_;

  @XmlElement(name = "constraintName", nillable = false, required = false)
  private String            constraintName_;

  private UniqueConstraint  constraint_;

  /**
   * 
   */
  public Index() {
    super();
  }

  /**
   * @param type
   */
  public Index(Table table) {
    super();

    this.setTable(table);
  }

  /**
   * @param name
   * @param type
   */
  public Index(Table table, String name) {
    super(name);

    this.setTable(table);
  }

  /**
   * @param catalog
   * @param schema
   * @param name
   */
  public Index(Table table, Schema schema, String name) {
    super(schema, name);

    this.setTable(table);
  }

  /**
   * @param columns
   *          the columns to set
   */
  public void setColumns(List<Column> columns) {
    this.columns_ = columns;
  }

  /**
   * @return the columns
   */
  public List<Column> getColumns() {
    if (this.columnNames_.size() > this.columns_.size()) {
      this.columns_.clear();
      for (String columnName : this.columnNames_) {
        this.columns_.add(this.getTable().getColumn(columnName));
      }
    }
    return columns_;
  }

  public void addColumn(Column column) {
    this.columns_.add(column);
    this.columnNames_.add(column.getName());
  }

  public void addColumn(String name) {
    this.columns_.add(this.getTable().getColumn(name));
    this.columnNames_.add(name);
  }

  /**
   * @param table
   *          the table to set
   */
  public void setTable(Table table) {
    this.table_ = table;
  }

  /**
   * @return the table
   */
  public Table getTable() {
    if (table_ == null) {
      throw new NullPointerException("Index.getTable(): Don't know table of INDEX \""+getName()+"\"");
    }
    return table_;
  }

  /**
   * @return the columnNames
   */
  public List<String> getColumnNames() {
    return columnNames_;
  }

  /**
   * @param columnNames
   *          the columnNames to set
   */
  public void setColumnNames(List<String> columnNames) {
    columnNames_ = columnNames;
  }

  /**
   * @param descending
   *          the descending to set
   */
  public void setDescending(boolean descending) {
    this.descending_ = descending;
  }

  /**
   * @return the descending
   */
  public boolean isDescending() {
    return descending_;
  }

  /**
   * @param unique
   *          the unique to set
   */
  public void setUnique(boolean unique) {
    this.unique_ = unique;
  }

  /**
   * @return the unique
   */
  public boolean isUnique() {
    return unique_;
  }

  /**
   * @param constraintName
   *          the constraintName to set
   */
  public void setConstraintName(String constraintName) {
    this.constraintName_ = constraintName;
  }

  /**
   * @return the constraintName
   */
  public String getConstraintName() {
    return constraintName_;
  }

  /**
   * @param constraint
   *          the constraint to set
   */
  public void setConstraint(UniqueConstraint constraint) {
    this.constraint_ = constraint;
    this.constraintName_ = constraint.getName();
  }

  /**
   * @return the constraint
   */
  public UniqueConstraint getConstraint() {
    return constraint_;
  }

  /* (non-Javadoc)
   * @see nl.rakis.sql.ddl.model.SchemaObject#fixReferences(nl.rakis.sql.ddl.model.Schema)
   */
  @Override
  public void fixReferences(Schema schema) {
    super.fixReferences(schema);
    
  }

  /**
   * Two indices are the same if they have the same uniqueness and direction,
   * and cover the same column names.
   * 
   * @param that
   * @return
   */
  public boolean same(Index that) {
    return (this.descending_ == that.descending_) &&
           (this.unique_ == that.unique_) &&
           EqualsUtil.equals(this.columnNames_, that.columnNames_);
  }

  /*
   * (non-Javadoc)
   * 
   * @see nl.rakis.sql.ddl.model.SchemaObject#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    boolean result = (obj != null) && (obj instanceof Index);

    // Two indices are the equal if:
    // - They are equal as SchemaObject
    // - Are on the same table
    // - Are the same
    if (result) {
      Index that = (Index) obj;

      result = super.equals(that) &&
               EqualsUtil.equals(this.table_, that.table_) && same(that);
    }

    return result;
  }

}
