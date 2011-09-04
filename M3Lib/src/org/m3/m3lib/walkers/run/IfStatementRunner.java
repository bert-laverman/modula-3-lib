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

import org.m3.m3lib.ast.Context;
import org.m3.m3lib.ast.M3RuntimeException;
import org.m3.m3lib.ast.stat.IfStatement;
import org.m3.m3lib.reflect.visitor.Visitor;
import org.m3.m3lib.walkers.eval.ExpressionEvaluator;

/**
 * 
 * @author LavermB
 *
 */
public class IfStatementRunner
    extends StatementRunner
    implements Visitor
{

  /**
   * 
   */
  public IfStatementRunner()
  {
    super();
  }

  public void startVisit(IfStatement st, Context ctx)
    throws M3RuntimeException
  {
    IfStatement.IfThen ifthen = st.getIfThen();

    if (ExpressionEvaluator.evalBooleanExpression(ifthen.getCond(), ctx)) {
      run(ifthen.getThenPart(), ctx);
      return;
    }
    for (IfStatement.IfThen elsif: st.getElsifs()) {
      if (ExpressionEvaluator.evalBooleanExpression(elsif.getCond(), ctx)) {
        run(elsif.getThenPart(), ctx);
        return;
      }
    }
    if (st.getElsePart() != null) {
      run(st.getElsePart(), ctx);
    }
  }
}


/*
 * $Log$
 */
