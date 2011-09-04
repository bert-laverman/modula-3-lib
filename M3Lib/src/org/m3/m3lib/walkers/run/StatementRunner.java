/**
 * Copyright (c) 2008 B. Laverman. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: bertl
 */

// $Id$
package org.m3.m3lib.walkers.run;

import java.util.List;

import org.apache.log4j.Logger;
import org.m3.m3lib.ast.Binding;
import org.m3.m3lib.ast.Context;
import org.m3.m3lib.ast.IncompatibleTypeException;
import org.m3.m3lib.ast.M3RuntimeException;
import org.m3.m3lib.ast.Node;
import org.m3.m3lib.ast.SimpleContext;
import org.m3.m3lib.ast.decl.ConstantDeclaration;
import org.m3.m3lib.ast.decl.Declaration;
import org.m3.m3lib.ast.decl.ProcedureDeclaration;
import org.m3.m3lib.ast.decl.VariableDeclaration;
import org.m3.m3lib.ast.stat.Statement;
import org.m3.m3lib.ast.type.ProcedureType;
import org.m3.m3lib.ast.value.ObjectValue;
import org.m3.m3lib.ast.value.ProcValue;
import org.m3.m3lib.ast.value.Value;
import org.m3.m3lib.reflect.visitor.Visitor;
import org.m3.m3lib.reflect.visitor.Walker;
import org.m3.m3lib.walkers.eval.ExpressionEvaluator;

/**
 * 
 * @author LavermB
 * 
 */
public class StatementRunner
  implements Visitor
{

  public static class XClass
  {
    private String x_ = "Howdey";

    public String getX()
    {
      return x_;
    }
  }

  private static final Logger log_ = Logger.getLogger(StatementRunner.class);

  /**
   * 
   */
  public StatementRunner()
  {
    super();
  }

  public List<?> getChildren(Statement stat, Context ctx)
  {
    return null;
  }

  public void startVisit(Statement stat, Context ctx)
  {
    log_.warn("Unimplemented startVisit() for " + stat.getClass());
  }

  public static void fillContext(SimpleContext ctx, List<Declaration> decls)
    throws IncompatibleTypeException
  {

    for (Declaration decl : decls) {
      if (decl instanceof ConstantDeclaration) {
        ConstantDeclaration cd = (ConstantDeclaration) decl;
        Value val = ExpressionEvaluator.evalExpression(cd.getValue(), ctx)
            .getValue();
        if (cd.getType() == null) {
          cd.setType(val.getType());
        }
        Binding cb = new Binding(cd.getName(), cd.getType(), val);
        cb.setConst(true);
        ctx.add(cb);
        log_.debug("Added constant \"" + cd.getName() + "\"");
      }
      else if (decl instanceof VariableDeclaration) {
        VariableDeclaration vd = (VariableDeclaration) decl;
        Value val = ObjectValue.UNDEFINED;
        if (vd.getInitializer() != null) {
          val = ExpressionEvaluator.evalExpression(vd.getInitializer(), ctx)
              .getValue();
        }
        if (vd.getType() == null) {
          vd.setType(val.getType());
        }
        Binding cb = new Binding(vd.getName(), vd.getType(), val);
        ctx.add(cb);
        log_.debug("Added variable \"" + vd.getName() + "\"");
      }
      else if (decl instanceof ProcedureDeclaration) {
        ProcedureDeclaration pd = (ProcedureDeclaration) decl;

        Value val = new ProcValue((ProcedureType) pd.getType(), pd.getBody());
        Binding pb = new Binding(pd.getName(), pd.getType(), val);
        pb.setConst(true);
        ctx.add(pb);
        log_.debug("Added procedure \"" + pd.getName() + "\"");
      }
    }
  }

  public static void run(Node stat, Context ctx)
    throws M3RuntimeException
  {
    try {
      Walker.walkE(stat, StatementRunnerFactory.getInstance(), ctx);
    }
    catch (M3RuntimeException e) {
      throw e;
    }
    catch (Throwable t) {
      log_.error("Exception (non M3RuntimeException descendant) thrown", t);
    }
  }

  public static void run(List<Statement> stats, Context ctx)
    throws M3RuntimeException
  {
    for (Node node : stats) {
      run(node, ctx);
    }
  }

}

/*
 * $Log$
 */
