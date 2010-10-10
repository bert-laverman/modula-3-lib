/**
 * 
 */
package nl.rakis.sql.sqlserver;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import nl.rakis.sql.ddl.model.Column;
import nl.rakis.sql.ddl.model.Index;
import nl.rakis.sql.ddl.model.Schema;
import nl.rakis.sql.ddl.model.Sequence;
import nl.rakis.sql.ddl.model.Table;
import nl.rakis.sql.ddl.model.Type;
import nl.rakis.sql.ddl.model.TypeClass;
import nl.rakis.sql.iso.ISOSchemaLoader;

/**
 * @author bertl
 * 
 */
public class SQLServerSchemaLoader
  extends ISOSchemaLoader
{

  private static final String SELECT_TABCOLS_QUERY_ = ""
                                                      + "SELECT c.*,CASE COLUMNPROPERTY(object_id(?), c.COLUMN_NAME, 'IsIdentity') WHEN 1 THEN IDENT_SEED(?) ELSE NULL END AS SEED,CASE COLUMNPROPERTY(object_id(?), c.COLUMN_NAME, 'IsIdentity') WHEN 1 THEN IDENT_INCR(?) ELSE NULL END AS INCR "
                                                      + "FROM information_schema.columns c "
                                                      + "WHERE table_schema=? AND table_name=? "
                                                      + "ORDER BY ordinal_position";
  private static final String SELECT_INDICES_QUERY_ = ""
                                                      + "SELECT i.name,i.object_id,i.index_id,i.is_unique "
                                                      + "FROM sys.objects AS o INNER JOIN sys.indexes AS i ON o.object_id = i.object_id WHERE (o.schema_id = SCHEMA_ID(?)) AND (o.name=?) AND (i.name IS NOT NULL)";
  private static final String SELECT_INDCOLS_QUERY_ = ""
                                                      + "SELECT c.name "
                                                      + "FROM sys.index_columns AS ic "
                                                      + "INNER JOIN sys.columns AS c ON ic.object_id = c.object_id AND c.column_id = ic.column_id "
                                                      + "WHERE (ic.object_id=?) AND (ic.index_id=?) ORDER BY ic.key_ordinal";

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

    final BigDecimal seed = (BigDecimal) rs.getObject("SEED");
    final BigDecimal incr = (BigDecimal) rs.getObject("INCR");

    if ((seed != null) || (incr != null)) {
      Sequence seq = new Sequence();

      if (seed != null) {
        seq.setStart(seed.intValue());
      }
      if (incr != null) {
        seq.setIncrement(incr.intValue());
      }

      result.setSequence(seq);
    }
    return result;
  }

  private PreparedStatement getCols = null;

  protected void loadColumns(Table table)
    throws SQLException
  {
    if (getCols == null) {
      getCols = this.getDb().prepareStatement(SELECT_TABCOLS_QUERY_);
    }
    final String schemaName = table.getSchema().getName();
    final String tableName = table.getName();
    final String fullTableName = schemaName + "." + tableName;

    getCols.setString(1, fullTableName);
    getCols.setString(2, tableName);
    getCols.setString(3, fullTableName);
    getCols.setString(4, tableName);
    getCols.setString(5, schemaName);
    getCols.setString(6, tableName);

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

  private PreparedStatement getIndCols = null;

  protected void loadIndexColumns(Index index, Long tableId, Long indexId)
    throws SQLException
  {
    if (getIndCols == null) {
      getIndCols = this.getDb().prepareStatement(SELECT_INDCOLS_QUERY_);
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

  protected void loadIndices(Schema schema, Table table)
    throws SQLException
  {
    if (getIndx == null) {
      getIndx = this.getDb().prepareStatement(SELECT_INDICES_QUERY_);
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
      }
    }
    finally {
      if (rs != null) {
        rs.close();
      }
    }
  }

}
