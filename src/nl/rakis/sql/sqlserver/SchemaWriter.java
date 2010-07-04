/**
 * 
 */
package nl.rakis.sql.sqlserver;

import java.io.PrintWriter;

import nl.rakis.sql.ddl.SchemaWriterBase;
import nl.rakis.sql.ddl.model.Column;
import nl.rakis.sql.ddl.model.ColumnedConstraint;
import nl.rakis.sql.ddl.model.Constraint;
import nl.rakis.sql.ddl.model.ForeignKeyConstraint;
import nl.rakis.sql.ddl.model.Index;
import nl.rakis.sql.ddl.model.Schema;
import nl.rakis.sql.ddl.model.Table;

/**
 * @author bertl
 * 
 */
public class SchemaWriter
  extends SchemaWriterBase
{

  /**
   * 
   */
  public SchemaWriter()
  {
    super();
  }

  /**
   * @param writer
   */
  public SchemaWriter(PrintWriter writer)
  {
    super(writer);
  }

  /* (non-Javadoc)
   * @see nl.rakis.sql.ddl.SchemaGenerator#create(nl.rakis.sql.ddl.model.Schema)
   */
  @Override
  public void create(Schema schema)
  {
    
  }

  /* (non-Javadoc)
   * @see nl.rakis.sql.ddl.SchemaGenerator#create(java.io.PrintWriter, nl.rakis.sql.ddl.model.Table)
   */
  @Override
  public void create(Table table)
  {
    PrintWriter pw = getWriter();

    pw.print("CREATE TABLE [");
    pw.print(table.getSchema().getName());
    pw.print("].[");
    pw.print(table.getName());
    pw.println("] (");
    pw.println(")");
    pw.println("GO");
  }

  /* (non-Javadoc)
   * @see nl.rakis.sql.ddl.SchemaGenerator#create(java.io.PrintWriter, nl.rakis.sql.ddl.model.Column)
   */
  @Override
  public void create(Column column)
  {
    PrintWriter pw = getWriter();

    pw.print("ALTER TABLE [");
    pw.print(column.getTable().getSchema().getName());
    pw.print("].[");
    pw.print(column.getTable().getName());
    pw.println("] ADD (");
    pw.println(")");
    pw.println("GO");
  }

  /* (non-Javadoc)
   * @see nl.rakis.sql.ddl.SchemaGenerator#create(java.io.PrintWriter, nl.rakis.sql.ddl.model.Constraint)
   */
  @Override
  public void create(Constraint cons)
  {
    PrintWriter pw = getWriter();

    pw.print("ALTER TABLE [");
    pw.print(cons.getTable().getSchema().getName());
    pw.print("].[");
    pw.print(cons.getTable().getName());
    pw.println("] ADD");
    pw.print("  CONSTRAINT [");
    pw.print(cons.getName());
    pw.print("]");
    if (cons instanceof ColumnedConstraint) {
      ColumnedConstraint ccons = (ColumnedConstraint) cons;
      pw.print(" ");
      pw.print(cons.getType().getName());
      pw.print(" (");
      boolean doComma = false;
      for (Column col : ccons.getColumns()) {
        if (doComma) {
          pw.print(",");
        }
        else {
          doComma = true;
        }
        pw.print("[");
        pw.print(col.getName());
        pw.print("]");
      }
      pw.println(")");
    }
    if (cons instanceof ForeignKeyConstraint) {
      ForeignKeyConstraint fk = (ForeignKeyConstraint) cons;
      ColumnedConstraint refCon = fk.getReference();
      Table refTab = refCon.getTable();
      pw.print("  REFERENCES [");
      pw.print(refTab.getSchema().getName());
      pw.print("].[");
      pw.print(refTab.getName());
      pw.println("](");
      boolean doComma = false;
      for (Column col : refCon.getColumns()) {
        if (doComma) {
          pw.print(",");
        }
        else {
          doComma = true;
        }
        pw.print("[");
        pw.print(col.getName());
        pw.print("]");
      }
      pw.println(")");
    }
    pw.println("GO");
  }

  /* (non-Javadoc)
   * @see nl.rakis.sql.ddl.SchemaGenerator#drop(java.io.PrintWriter, nl.rakis.sql.ddl.model.Table)
   */
  @Override
  public void drop(Table table)
  {
    PrintWriter pw = getWriter();

    pw.print("DROP TABLE [");
    pw.print(table.getSchema().getName());
    pw.print("].[");
    pw.print(table.getName());
    pw.println("]");
    pw.println("GO");
  }

  /* (non-Javadoc)
   * @see nl.rakis.sql.ddl.SchemaGenerator#drop(java.io.PrintWriter, nl.rakis.sql.ddl.model.Column)
   */
  @Override
  public void drop(Column column)
  {
    PrintWriter pw = getWriter();

    pw.print("ALTER TABLE [");
    pw.print(column.getTable().getSchema().getName());
    pw.print("].[");
    pw.print(column.getTable().getName());
    pw.print("] DROP COLUMN [");
    pw.print(column.getName());
    pw.println("]");
    pw.println("GO");
  }

  /* (non-Javadoc)
   * @see nl.rakis.sql.ddl.SchemaGenerator#drop(java.io.PrintWriter, nl.rakis.sql.ddl.model.Constraint)
   */
  @Override
  public void drop(Constraint cons)
  {
    PrintWriter pw = getWriter();

    pw.print("ALTER TABLE [");
    pw.print(cons.getTable().getSchema().getName());
    pw.print("].[");
    pw.print(cons.getTable().getName());
    pw.print("] DROP CONSTRAINT [");
    pw.print(cons.getName());
    pw.println("]");
    pw.println("GO");
  }

  /* (non-Javadoc)
   * @see nl.rakis.sql.ddl.SchemaGenerator#create(nl.rakis.sql.ddl.model.Index)
   */
  @Override
  public void create(Index index)
  {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see nl.rakis.sql.ddl.SchemaGenerator#drop(nl.rakis.sql.ddl.model.Index)
   */
  @Override
  public void drop(Index index)
  {
    // TODO Auto-generated method stub

  }

}
