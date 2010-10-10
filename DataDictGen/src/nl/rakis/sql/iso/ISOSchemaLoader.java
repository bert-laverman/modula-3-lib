/**
 * 
 */
package nl.rakis.sql.iso;

import static nl.rakis.sql.ddl.model.ConstraintType.CHECK;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import nl.rakis.sql.ddl.SchemaLoader;
import nl.rakis.sql.ddl.SchemaLoaderBase;
import nl.rakis.sql.ddl.model.CheckConstraint;
import nl.rakis.sql.ddl.model.Column;
import nl.rakis.sql.ddl.model.ColumnedConstraint;
import nl.rakis.sql.ddl.model.ConstraintType;
import nl.rakis.sql.ddl.model.ForeignKeyConstraint;
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
public abstract class ISOSchemaLoader
  extends SchemaLoaderBase
  implements SchemaLoader
{
  private static final String   SELECT_TABLES_QUERY_      = ""
                                                            + "SELECT * FROM information_schema.tables "
                                                            + "WHERE table_schema = ?";
  protected static final String SELECT_TABCOLS_QUERY_     = ""
                                                            + "SELECT column_name,column_default,is_nullable,data_type,character_maximum_length,character_octet_length,numeric_precision,numeric_precision_radix,numeric_scale,datetime_precision,is_updatable "
                                                            + "FROM information_schema.columns "
                                                            + "WHERE table_schema=? AND table_name=? "
                                                            + "ORDER BY ordinal_position";
  private static final String   SELECT_CONSTRAINTS_QUERY_ = ""
                                                            + "SELECT * FROM information_schema.table_constraints "
                                                            + "WHERE table_schema=? AND table_name=?";
  private static final String   SELECT_CHECK_CLAUSE_      = ""
                                                            + "SELECT check_clause "
                                                            + "FROM information_schema.check_constraints "
                                                            + "WHERE (constraint_schema=?)AND(constraint_name=?)";
  private static final String   SELECT_CONSCOLS_QUERY_    = ""
                                                            + "SELECT column_name "
                                                            + "FROM information_schema.key_column_usage "
                                                            + "WHERE constraint_schema=? AND constraint_name=? "
                                                            + "ORDER BY ordinal_position";
  private static final String   SELECT_REFCONS_QUERY_     = ""
                                                            + "SELECT uk.table_name,fk.unique_constraint_name,fk.update_rule,fk.delete_rule "
                                                            + "FROM information_schema.referential_constraints fk "
                                                            + "INNER JOIN information_schema.table_constraints uk ON (uk.constraint_schema=fk.unique_constraint_schema)AND(uk.constraint_name=fk.unique_constraint_name) "
                                                            + "WHERE fk.constraint_schema=? AND fk.constraint_name=?";

  public ISOSchemaLoader() {
    super();
  }

  private PreparedStatement getConsRef = null;

  @SuppressWarnings("unused")
  protected void fixReferredConstraint(ForeignKeyConstraint constraint)
    throws SQLException
  {
    if (getConsRef == null) {
      getConsRef = this.getDb().prepareStatement(SELECT_REFCONS_QUERY_);
    }
    getConsRef.setString(1, constraint.getSchema().getName());
    getConsRef.setString(2, constraint.getName());

    ResultSet rs = null;

    try {
      rs = getConsRef.executeQuery();

      if (rs.next()) {
        final String tableName = rs.getString(1);
        final String consName = rs.getString(2);
        final Table refTable = constraint.getSchema().getTable(tableName);
        final ColumnedConstraint key = refTable.getColumnedConstraint(consName);

        if ((refTable != null) && (key != null)) {
          constraint.setReference(key);
          constraint.setUpdateRule(getDriver()
              .string2ReferenceAction(rs.getString(3)));
          constraint.setDeleteRule(getDriver()
              .string2ReferenceAction(rs.getString(4)));
        }
        else if (refTable == null) {
          System.err
              .println("Cannot find referred table \"" + tableName + "\"");
        }
        else {
          System.err.println("Cannot find referred key \"" + consName +
                             "\" in table \"" + tableName + "\"");
        }
      }
    }
    finally {
      if (rs != null) {
        rs.close();
      }
    }
  }

  protected void fixReferences(Schema schema)
    throws SQLException
  {
    
    for (Table table : schema.getTables()) {
      for (ForeignKeyConstraint fk : table.getForeignKeys()) {
        fixReferredConstraint(fk);
      }
    }
    schema.fixReferences();
  }

  private PreparedStatement getConsCols = null;

  protected void getConstraintColumns(ColumnedConstraint constraint)
    throws SQLException
  {
    if (getConsCols == null) {
      getConsCols = this.getDb().prepareStatement(SELECT_CONSCOLS_QUERY_);
    }
    getConsCols.setString(1, constraint.getSchema().getName());
    getConsCols.setString(2, constraint.getName());

    ResultSet rs = null;

    try {
      rs = getConsCols.executeQuery();

      while (rs.next()) {
        constraint.addColumn(rs.getString(1));
      }
    }
    finally {
      if (rs != null) {
        rs.close();
      }
    }
  }

  private PreparedStatement getCheckClause_ = null;

  protected void getCheckClause(CheckConstraint check)
    throws SQLException
  {
    if (getCheckClause_ == null) {
      getCheckClause_ = this.getDb().prepareStatement(SELECT_CHECK_CLAUSE_);
    }
    getCheckClause_.setString(1, check.getSchema().getName());
    getCheckClause_.setString(2, check.getName());

    ResultSet rs = null;

    try {
      rs = getCheckClause_.executeQuery();

      if (rs.next()) {
        check.setExpression(rs.getString(1));
      }
    }
    finally {
      if (rs != null) {
        rs.close();
      }
    }
  }

  private PreparedStatement getCons = null;

  protected void loadConstraints(Schema schema, Table table)
    throws SQLException
  {
    if (getCons == null) {
      getCons = this.getDb().prepareStatement(SELECT_CONSTRAINTS_QUERY_);
    }
    getCons.setString(1, table.getSchema().getName());
    getCons.setString(2, table.getName());

    ResultSet rs = null;

    try {
      rs = getCons.executeQuery();

      while (rs.next()) {
        final String consName = rs.getString("constraint_name");
        final String consType = rs.getString("constraint_type");

        ConstraintType type = ConstraintType.getType(consType);
        if (type == CHECK) {
          CheckConstraint cons = new CheckConstraint(table, consName);
          cons.setSchema(schema);
          getCheckClause(cons);
          table.addConstraint(cons);
        }
        else {
          ColumnedConstraint ccons = null;
          switch (type) {
          case PRIMARY_KEY:
            ccons = new PrimaryKeyConstraint(table, consName);
            break;

          case UNIQUE:
            ccons = new UniqueConstraint(table, consName);
            break;

          case FOREIGN_KEY:
            ccons = new ForeignKeyConstraint(table, consName);
            break;
          }
          ccons.setSchema(schema);
          getConstraintColumns(ccons);
          table.addConstraint(ccons);
        }
      }
    }
    finally {
      if (rs != null) {
        rs.close();
      }
    }
  }

  /**
   * @param driver
   * @param rs
   * @return
   * @throws SQLException
   */
  protected Column buildColumn(ResultSet rs)
    throws SQLException
  {
    Column result = new Column();

    result.setName(rs.getString("column_name"));

    final String typeName = rs.getString("data_type").toLowerCase();
    final TypeClass clazz = this.getDriver().string2Type(typeName);
    if (clazz == null) {
      throw new SQLException("Unknown data_type \""+typeName+"\"");
    }
    Type type = new Type(clazz);

    if (this.getDriver().getMaxedVars().contains(typeName)) {
      type.setLength(-1);
    }
    else {
      switch (clazz) {
      case CHAR:
      case NCHAR:
      case VARCHAR:
      case NVARCHAR:
        final Integer charLength = (Integer) rs
            .getObject("character_maximum_length");
        final Integer byteLength = (Integer) rs
            .getObject("character_octet_length");

        if (charLength != null) {
          type.setCountInChars(true);
          type.setLength(charLength);
        }
        else if (byteLength != null) {
          type.setCountInChars(false);
          type.setLength(byteLength);
        }

        break;
      case DECIMAL:
        type.setPrecision((Integer) rs.getObject("numeric_precision"));
        type.setScale((Integer) rs.getObject("numeric_scale"));
        break;

      case DATE:
      case TIME:
      case TIMESTAMP:
        type.setPrecision((Integer) rs.getObject("datetime_precision"));
        break;
      }
    }
    result.setType(type);
    result.setNullable(rs.getString("is_nullable").equalsIgnoreCase("yes"));
    result.setDefault(rs.getString("column_default"));

    //final BigDecimal seed = (BigDecimal) rs.getObject("SEED");
    //final BigDecimal incr = (BigDecimal) rs.getObject("INCR");

    //if ((seed != null) || (incr != null)) {
    //  Sequence seq = new Sequence();

    //  if (seed != null) {
    //    seq.setStart(seed.intValue());
    //  }
    //  if (incr != null) {
    //    seq.setIncrement(incr.intValue());
    //  }

    //  result.setSequence(seq);
    //}
    return result;
  }

  private PreparedStatement getCols = null;

  protected void loadColumns(Table table)
    throws SQLException
  {
    if (getCols == null) {
      getCols = this.getDb().prepareStatement(SELECT_TABCOLS_QUERY_);
    }

    getCols.setString(1, table.getSchema().getName());
    getCols.setString(2, table.getName());

    ResultSet rs = null;

    try {
      rs = getCols.executeQuery();

      while (rs.next()) {
        Column column = buildColumn(rs);

        table.addColumn(column);
      }
    }
    finally {
      if (rs != null) {
        rs.close();
      }
    }
  }

  protected abstract void loadIndices(Schema schema, Table table)
    throws SQLException;

  private PreparedStatement getTables = null;

  protected void loadTables(Schema schema)
    throws SQLException
  {
    if (getTables == null) {
      getTables = this.getDb().prepareStatement(SELECT_TABLES_QUERY_);
    }
    getTables.setString(1, schema.getName());

    ResultSet rs = null;

    try {
      rs = getTables.executeQuery();

      while (rs.next()) {
        final String tableName = rs.getString("table_name");

        Table table = new Table(tableName);
        schema.addTable(table);

        loadColumns(table);
        loadConstraints(schema, table);
        loadIndices(schema, table);
      }
    }
    finally {
      if (rs != null) {
        rs.close();
      }
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see nl.rakis.sql.iso.ISchemaLoader#load(java.lang.String)
   */
  public Schema load(String name)
    throws SQLException
  {
    Schema result = new Schema();

    result.setName(name);
    loadTables(result);
    fixReferences(result);

    return result;
  }
}
