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

import org.apache.log4j.Logger;
import org.m3.m3lib.M3Token;
import org.m3.m3lib.ast.Context;
import org.m3.m3lib.ast.IncompatibleTypeException;
import org.m3.m3lib.ast.expr.MathExpression;
import org.m3.m3lib.ast.type.ExtendedType;
import org.m3.m3lib.ast.type.IntegerType;
import org.m3.m3lib.ast.type.TextType;
import org.m3.m3lib.ast.type.Type;
import org.m3.m3lib.ast.value.IntegerValue;
import org.m3.m3lib.ast.value.ObjectValue;
import org.m3.m3lib.ast.value.RealValue;
import org.m3.m3lib.ast.value.TextValue;
import org.m3.m3lib.ast.value.Value;
import org.m3.m3lib.reflect.visitor.Visitor;

/**
 * 
 * @author LavermB
 * 
 */
public class MathExpressionEvaluator
    extends ExpressionEvaluator
    implements Visitor
{

  private static final Logger log_ = Logger
                                       .getLogger(MathExpressionEvaluator.class);

  /**
   * 
   */
  public MathExpressionEvaluator()
  {
    super();
  }

  private Value floatExpression(M3Token oper, Value lhs, Value rhs)
      throws IncompatibleTypeException
  {
    log_.debug("floatExpression(" + oper + ", " + lhs + ", " + rhs + ")");

    double dLhs = lhs.toNumber().doubleValue();
    double dRhs = rhs.toNumber().doubleValue();

    switch (oper) {
    case PLUS:
      return new RealValue(dLhs + dRhs);
    case MINUS:
      return new RealValue(dLhs - dRhs);
    case TIMES:
      return new RealValue(dLhs * dRhs);
    case DIVIDE:
      return new RealValue(dLhs / dRhs);
    case DIV:
    case MOD:
      throw new IncompatibleTypeException("DIV and MOD require INTEGERs");
    }
    log_.warn("Unknown MathExpression operator "+oper);

    return ObjectValue.UNDEFINED;
  }

  private Value longExpression(M3Token oper, Value lhs, Value rhs)
      throws IncompatibleTypeException
  {
    log_.debug("longExpression(" + oper + ", " + lhs + ", " + rhs + ")");

    long dLhs = lhs.toNumber().longValue();
    long dRhs = rhs.toNumber().longValue();

    switch (oper) {
    case PLUS:
      return new IntegerValue(dLhs + dRhs);
    case MINUS:
      return new IntegerValue(dLhs - dRhs);
    case TIMES:
      return new IntegerValue(dLhs * dRhs);
    case DIVIDE:
      return new RealValue(((double)dLhs) / dRhs);
    case DIV:
      return new IntegerValue(dLhs / dRhs);
    case MOD:
      return new IntegerValue(dLhs % dRhs);
    }
    log_.warn("Unknown MathExpression operator "+oper);

    return ObjectValue.UNDEFINED;
  }

  private Value textExpression(M3Token oper, Value lhs, Value rhs)
      throws IncompatibleTypeException
  {
    String dLhs = lhs.getValue().toString();
    String dRhs = rhs.getValue().toString();

    switch (oper) {
    case AMP:
      return new TextValue(dLhs + dRhs);
    case PLUS:
    case MINUS:
    case TIMES:
    case DIVIDE:
    case DIV:
    case MOD:
      throw new IncompatibleTypeException("TEXT values can only be concatenated", lhs);
    }
    log_.warn("Unknown MathExpression operator "+oper);

    return ObjectValue.UNDEFINED;
  }

  public Value getReturnState(MathExpression e, Context c)
      throws IncompatibleTypeException
  {
    Value lhs = evalExpression(e.getOpnd1(), c).getValue();
    Type lhsType = lhs.getType();
    Value rhs = evalExpression(e.getOpnd2(), c).getValue();

    if (ExtendedType.getInstance().isAssignableFrom(lhsType)) {
      return floatExpression(e.getOper(), lhs, rhs);
    }
    if (IntegerType.getInstance().isAssignableFrom(lhsType)) {
      return longExpression(e.getOper(), lhs, rhs);
    }
    if (TextType.getInstance().isAssignableFrom(lhsType)) {
      return textExpression(e.getOper(), lhs, rhs);
    }
    log_.error("Don't know how to deal with MathExpression on "+lhs+" ("+lhs.getType()+") and "+rhs+" ("+rhs.getType()+")");
    throw new IncompatibleTypeException("Cannot compare values", e);
  }

}

/*
 * $Log$
 */
