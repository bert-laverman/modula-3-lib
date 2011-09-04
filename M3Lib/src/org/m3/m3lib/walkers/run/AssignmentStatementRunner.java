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

import org.apache.log4j.Logger;

import org.m3.m3lib.ast.Context;
import org.m3.m3lib.ast.IncompatibleTypeException;
import org.m3.m3lib.ast.stat.AssignmentStatement;
import org.m3.m3lib.ast.value.LValue;
import org.m3.m3lib.ast.value.Value;
import org.m3.m3lib.reflect.visitor.Visitor;
import org.m3.m3lib.walkers.eval.ExpressionEvaluator;

/**
 * 
 * @author LavermB
 *
 */
public class AssignmentStatementRunner
    extends StatementRunner
    implements Visitor
{

  private static final Logger log_ = Logger.getLogger(AssignmentStatementRunner.class);

  /**
   * 
   */
  public AssignmentStatementRunner()
  {
    super();
  }

  public void startVisit(AssignmentStatement stat, Context ctx)
      throws IncompatibleTypeException
  {
    Value lhs = ExpressionEvaluator.evalExpression(stat.getLhs(), ctx);
    if (stat.getRhs() != null) {
      if (lhs instanceof LValue) {
        LValue var = (LValue) lhs;
        Value rhs = ExpressionEvaluator.evalExpression(stat.getRhs(), ctx);
        var.setValue(rhs.getValue());
      }
      else {
        log_.error("LHS is not an LValue");
        throw new IncompatibleTypeException("LHS in assignment not an LValue", lhs);
      }
    }
  }

}


/*
 * $Log$
 */
