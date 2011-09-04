/**
 * Copyright (c) 2008 B. Laverman. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: bertl
 */

// $Id$

package org.m3.m3lib.walkers.eval;

import org.m3.m3lib.ast.Context;
import org.m3.m3lib.ast.IncompatibleTypeException;
import org.m3.m3lib.ast.expr.AndExpression;
import org.m3.m3lib.ast.value.BooleanValue;
import org.m3.m3lib.ast.value.Value;
import org.m3.m3lib.reflect.visitor.Visitor;

/**
 * 
 * @author LavermB
 * 
 */
public class AndExpressionEvaluator
    extends ExpressionEvaluator
    implements Visitor
{

  /**
   * 
   */
  public AndExpressionEvaluator()
  {
    super();
  }

  public Value getReturnState(AndExpression e, Context c)
      throws IncompatibleTypeException
  {
    return new BooleanValue(evalBooleanExpression(e.getOpnd1(), c)
                  && evalBooleanExpression(e.getOpnd2(), c));
  }

}

/*
 * $Log$
 */
