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

import java.util.List;

import org.apache.log4j.Logger;
import org.m3.m3lib.ast.Context;
import org.m3.m3lib.ast.IncompatibleTypeException;
import org.m3.m3lib.ast.expr.Expression;
import org.m3.m3lib.ast.expr.MinusExpression;
import org.m3.m3lib.ast.expr.PlusExpression;
import org.m3.m3lib.ast.expr.ValueExpression;
import org.m3.m3lib.ast.type.IntegerType;
import org.m3.m3lib.ast.value.IntegerValue;
import org.m3.m3lib.ast.value.ObjectValue;
import org.m3.m3lib.ast.value.RealValue;
import org.m3.m3lib.ast.value.Value;
import org.m3.m3lib.reflect.visitor.Visitor;
import org.m3.m3lib.reflect.visitor.Walker;

/**
 * 
 * @author LavermB
 * 
 */
public class ExpressionEvaluator
  implements Visitor
{

  private static final Logger log_ = Logger.getLogger("com.syllogic.m3.ast.expr.eval.ExpressionEvaluator");

  /**
   * 
   */
  public ExpressionEvaluator()
  {
    super();
  }

  public Context getInitialState(Expression e, Context c) {
    return c;
  }
  public List<?> getChildren(Expression e) {
    return null;
  }

  public Value getReturnState(Value v)
  {
    return v;
  }

  public Value getReturnState(ValueExpression e, Context c)
  {
    return e.getValue();
  }

  public Value getReturnState(PlusExpression e, Context c)
      throws IncompatibleTypeException
  {
    Value r = evalExpression(e.getOpnd(), c).getValue();
    r.toNumber();
    return r;
  }

  public Value getReturnState(MinusExpression e, Context c)
      throws IncompatibleTypeException
  {
    Value r = evalExpression(e.getOpnd(), c).getValue();
    r.toNumber();
    if (r.getType().getBaseType() instanceof IntegerType) {
      r = new IntegerValue(-r.toLong());
    }
    else {
      r = new RealValue(-r.toDouble());
    }
    return r;
  }

  public static Value evalExpression(Expression expr, Context c)
      throws IncompatibleTypeException
  {
    try {
      return (Value)Walker.walkE(expr, ExpressionEvaluatorFactory.getInstance(), c);
    }
    catch (IncompatibleTypeException e) {
      throw e;
    }
    catch (Throwable e) {
      log_.error("Unexpected exception caught during evaluation", e);
    }
    return ObjectValue.UNDEFINED;
  }

  public static boolean evalBooleanExpression(Expression e, Context c)
      throws IncompatibleTypeException
  {
    return evalExpression(e, c).toBoolean();
  }
}

/*
 * $Log$
 */
