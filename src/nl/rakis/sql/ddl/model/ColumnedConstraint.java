/**
 * 
 */
package nl.rakis.sql.ddl.model;

import static javax.xml.bind.annotation.XmlAccessType.NONE;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

/**
 * @author bertl
 * 
 */
@XmlType(name = "ColumnedConstraintType")
@XmlAccessorType(NONE)
abstract public class ColumnedConstraint
  extends Constraint
{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @XmlElementWrapper(name = "columnNames", nillable = true, required = true)
  @XmlElement(name = "columnName", required = false)
  private List<String>      columnNames_     = new ArrayList<String>();

  private List<Column>      columns_         = new ArrayList<Column>();

  /**
   * 
   */
  public ColumnedConstraint()
  {
    super();
  }

  /**
   * @param type
   */
  public ColumnedConstraint(Table table, ConstraintType type)
  {
    super(table, type);
  }

  /**
   * @param name
   * @param type
   */
  public ColumnedConstraint(Table table, String name, ConstraintType type)
  {
    super(table, name, type);
  }

  /**
   * @param catalog
   * @param schema
   * @param name
   * @param type
   */
  public ColumnedConstraint(Table table, Schema schema, String name,
                            ConstraintType type)
  {
    super(table, schema, name, type);
  }

  /**
   * @param columns the columns to set
   */
  public void setColumns(List<Column> columns)
  {
    this.columns_ = columns;
  }

  /**
   * @return the columns
   */
  public List<Column> getColumns()
  {
    if (this.columnNames_.size() > this.columns_.size()) {
      this.columns_.clear();
      for (String columnName : this.columnNames_) {
        this.columns_.add(this.getTable().getColumn(columnName));
      }
    }
    return columns_;
  }

  public void addColumn(Column column)
  {
    this.columns_.add(column);
    this.columnNames_.add(column.getName());
  }

  public void addColumn(String name)
  {
    this.columns_.add(this.getTable().getColumn(name));
    this.columnNames_.add(name);
  }

}
