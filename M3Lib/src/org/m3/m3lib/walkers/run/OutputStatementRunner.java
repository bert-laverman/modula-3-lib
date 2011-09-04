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

import java.io.PrintWriter;

import org.apache.log4j.Logger;

import org.m3.m3lib.ast.Binding;
import org.m3.m3lib.ast.Context;
import org.m3.m3lib.ast.IncompatibleTypeException;
import org.m3.m3lib.ast.M3Runtime;
import org.m3.m3lib.ast.expr.Expression;
import org.m3.m3lib.ast.stat.OutputStatement;
import org.m3.m3lib.ast.value.Value;
import org.m3.m3lib.reflect.visitor.Visitor;
import org.m3.m3lib.walkers.eval.ExpressionEvaluator;

/**
 * 
 * @author LavermB
 *
 */
public class OutputStatementRunner
    extends StatementRunner
    implements Visitor
{

  private static final Logger log_ = Logger.getLogger(OutputStatementRunner.class);

  /**
   * 
   */
  public OutputStatementRunner()
  {
    super();
  }

  private PrintWriter getOut(Context ctx)
  {
    try {
      Binding val = ctx.lookup("<<stdout>>");
      if (val != null) {
        Object wr = val.getValue().toObject();
        if (wr instanceof PrintWriter) {
          return (PrintWriter)wr;
        }
        log_.warn("Symbol '<<stdout>>' does not evaluate to a java.io.PrintWriter object. Reverting to M3Runtime default.");
      }
    }
    catch (IncompatibleTypeException e) {
      log_.error("Exception thrown trying to retrieve '<<stdout>>'", e);
    }
    return M3Runtime.getInstance().getStdout();
  }

  public void startVisit(OutputStatement stat, Context ctx)
    throws IncompatibleTypeException
  {
    String txt = stat.getText();
    if (txt != null) {
      getOut(ctx).print(txt);
      return;
    }
    Expression expr = stat.getExpr();
    if (expr != null) {
      Value val = ExpressionEvaluator.evalExpression(expr, ctx);
      getOut(ctx).print(val.getValue().toString());
    }
  }

}


/*
 * $Log$
 */
