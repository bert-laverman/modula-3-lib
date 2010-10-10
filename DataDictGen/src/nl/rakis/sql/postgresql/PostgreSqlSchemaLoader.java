/**
 * 
 */
package nl.rakis.sql.postgresql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import nl.rakis.sql.ddl.model.Index;
import nl.rakis.sql.ddl.model.Schema;
import nl.rakis.sql.ddl.model.Table;
import nl.rakis.sql.iso.ISOSchemaLoader;

/**
 * @author bertl
 * 
 */
public class PostgreSqlSchemaLoader
  extends ISOSchemaLoader
{
  private static final String SELECT_INDCOLS_QUERY_ = "" + "SELECT attname "
                                                      + "FROM pg_attribute "
                                                      + "WHERE attrelid=? "
                                                      + "ORDER BY attnum";
  private static final String SELECT_INDICES_QUERY_ = ""
                                                      + "SELECT cl.oid,cl.relname,ix,indisunique "
                                                      + "FROM pg_index ix,pg_class tb,pg_class cl,pg_namespace ns "
                                                      + "WHERE (ix.indexrelid=cl.oid) AND (ix.indrelid=tb.oid) AND (tb.relname=?) AND (tb.relnamespace=ns.oid) AND (ns.nspname=?)";

  private PreparedStatement   getIndCols            = null;

  protected void loadIndexColumns(Index index, Object indexOid)
    throws SQLException
  {
    if (getIndCols == null) {
      getIndCols = this.getDb().prepareStatement(SELECT_INDCOLS_QUERY_);
    }
    getIndCols.setObject(1, indexOid);

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
    getIndx.setString(1, table.getName());
    getIndx.setString(2, table.getSchema().getName());

    ResultSet rs = null;

    try {
      rs = getIndx.executeQuery();

      while (rs.next()) {
        final Object indexOid = rs.getLong(1);
        final String indexName = rs.getString(2);
        final boolean unique = rs.getBoolean(3);

        Index index = new Index(table, schema, indexName);
        index.setUnique(unique);
        loadIndexColumns(index, indexOid);

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
