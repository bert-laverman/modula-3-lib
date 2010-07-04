/**
 * 
 */
package nl.rakis.sql.ddl;

import static nl.rakis.sql.ddl.model.ConstraintType.CHECK;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import nl.rakis.sql.DbDriver;
import nl.rakis.sql.ddl.model.Column;
import nl.rakis.sql.ddl.model.ColumnedConstraint;
import nl.rakis.sql.ddl.model.Constraint;
import nl.rakis.sql.ddl.model.ConstraintType;
import nl.rakis.sql.ddl.model.ForeignKeyConstraint;
import nl.rakis.sql.ddl.model.Index;
import nl.rakis.sql.ddl.model.PrimaryKeyConstraint;
import nl.rakis.sql.ddl.model.Schema;
import nl.rakis.sql.ddl.model.Table;
import nl.rakis.sql.ddl.model.UniqueConstraint;

/**
 * @author bertl
 * 
 */
public class SchemaLoader
{
  private static final String SELECT_TABLES_QUERY_      = "SELECT * FROM information_schema.tables WHERE table_schema = ?";
  private static final String SELECT_TABCOLS_QUERY_     = "SELECT * FROM information_schema.columns WHERE table_schema=? AND table_name=? ORDER BY ordinal_position";
  private static final String SELECT_CONSTRAINTS_QUERY_ = "SELECT * FROM information_schema.table_constraints WHERE table_schema=? AND table_name=?";
  private static final String SELECT_CONSCOLS_QUERY_    = "SELECT column_name FROM information_schema.key_column_usage WHERE constraint_schema=? AND constraint_name=? ORDER BY ordinal_position";
  private static final String SELECT_REFCONS_QUERY_     = "SELECT unique_constraint_name FROM information_schema.referential_constraints WHERE constraint_schema=? AND constraint_name=?";
  private static final String SELECT_INDICES_QUERY_     = "SELECT i.name,i.object_id,i.index_id,i.is_unique FROM sys.objects AS o INNER JOIN sys.indexes AS i ON o.object_id = i.object_id WHERE (o.schema_id = SCHEMA_ID(?)) AND (o.name=?) AND (i.name IS NOT NULL)";
  private static final String SELECT_INDCOLS_QUERY_     = "SELECT c.name FROM sys.index_columns AS ic INNER JOIN sys.columns AS c ON ic.object_id = c.object_id AND c.column_id = ic.column_id WHERE (ic.object_id=?) AND (ic.index_id=?) ORDER BY ic.key_ordinal";

  private DbDriver            driver_;
  private Connection          db_;

  public SchemaLoader(DbDriver driver, Connection db)
  {
    this.driver_ = driver;
    this.db_ = db;
  }

  private PreparedStatement getCols = null;

  private void loadColumns(Table table)
    throws SQLException
  {
    if (getCols == null) {
      getCols = this.db_.prepareStatement(SELECT_TABCOLS_QUERY_);
    }
    getCols.setString(1, table.getSchema().getName());
    getCols.setString(2, table.getName());

    ResultSet rs = null;

    try {
      rs = getCols.executeQuery();

      while (rs.next()) {
        Column column = Column.fromInformationSchema(this.driver_, rs);

        table.addColumn(column);
      }
    }
    finally {
      if (rs != null) {
        rs.close();
      }
    }
  }

  private PreparedStatement getConsRef = null;

  private String getReferredConstraint(ForeignKeyConstraint constraint)
    throws SQLException
  {
    if (getConsRef == null) {
      getConsRef = this.db_.prepareStatement(SELECT_REFCONS_QUERY_);
    }
    getConsRef.setString(1, constraint.getSchema().getName());
    getConsRef.setString(2, constraint.getName());

    ResultSet rs = null;

    try {
      rs = getConsRef.executeQuery();

      if (rs.next()) {
        return rs.getString(1);
      }
      return null;
    }
    finally {
      if (rs != null) {
        rs.close();
      }
    }
  }

  private void fixReferences(Schema schema)
    throws SQLException
  {
    for (ForeignKeyConstraint fk : schema.getForeignKeys()) {
      Constraint cons = schema.getConstraint(getReferredConstraint(fk));

      fk.setReference((ColumnedConstraint) cons);
    }
  }

  private PreparedStatement getConsCols = null;

  private void getConstraintColumns(ColumnedConstraint constraint)
    throws SQLException
  {
    if (getConsCols == null) {
      getConsCols = this.db_.prepareStatement(SELECT_CONSCOLS_QUERY_);
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

  private PreparedStatement getCons = null;

  private void loadConstraints(Schema schema, Table table)
    throws SQLException
  {
    if (getCons == null) {
      getCons = this.db_.prepareStatement(SELECT_CONSTRAINTS_QUERY_);
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
          // TODO
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
          schema.addConstraint(ccons);
        }
      }
    }
    finally {
      if (rs != null) {
        rs.close();
      }
    }
  }

  private PreparedStatement getIndCols = null;

  private void loadIndexColumns(Index index, Long tableId, Long indexId)
    throws SQLException
  {
    if (getIndCols == null) {
      getIndCols = this.db_.prepareStatement(SELECT_INDCOLS_QUERY_);
    }
    getIndCols.setLong(1, tableId);
    getIndCols.setLong(2, indexId);

    ResultSet rs = null;

    try {
      rs = getIndCols.executeQuery();

      while (rs.next()) {
        index.addColumn(rs.getString(1));
      }
    }
    finally {
      if (rs != null) {
        rs.close();
      }
    }
  }

  private PreparedStatement getIndx = null;

  private void loadIndices(Schema schema, Table table)
    throws SQLException
  {
    if (getIndx == null) {
      getIndx = this.db_.prepareStatement(SELECT_INDICES_QUERY_);
    }
    getIndx.setString(1, table.getSchema().getName());
    getIndx.setString(2, table.getName());

    ResultSet rs = null;

    try {
      rs = getIndx.executeQuery();

      while (rs.next()) {
        final String indexName = rs.getString(1);
        final Long tableId = rs.getLong(2);
        final Long indexId = rs.getLong(3);
        final boolean unique = rs.getInt(4) == 1;

        Index index = new Index(table, schema, indexName);
        index.setUnique(unique);
        loadIndexColumns(index, tableId, indexId);

        table.addIndex(index);
        schema.addIndex(index);
      }
    }
    finally {
      if (rs != null) {
        rs.close();
      }
    }
  }

  private PreparedStatement getTables = null;

  private void loadTables(Schema schema)
    throws SQLException
  {
    if (getTables == null) {
      getTables = this.db_.prepareStatement(SELECT_TABLES_QUERY_);
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
