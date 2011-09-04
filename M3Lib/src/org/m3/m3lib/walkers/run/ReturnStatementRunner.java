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
import org.m3.m3lib.ast.IncompatibleTypeException;
import org.m3.m3lib.ast.stat.ReturnException;
import org.m3.m3lib.ast.stat.ReturnStatement;
import org.m3.m3lib.reflect.visitor.Visitor;
import org.m3.m3lib.walkers.eval.ExpressionEvaluator;

/**
 * 
 * @author LavermB
 * 
 */
public class ReturnStatementRunner
    extends StatementRunner
    implements Visitor
{

  /**
   * 
   */
  public ReturnStatementRunner()
  {
    super();
  }

  public void startVisit(ReturnStatement stat, Context ctx)
      throws ReturnException, IncompatibleTypeException
  {
    throw new ReturnException(ExpressionEvaluator.evalExpression(
        stat.getResult(), ctx).getValue());
  }
}

/*
 * $Log$
 */
