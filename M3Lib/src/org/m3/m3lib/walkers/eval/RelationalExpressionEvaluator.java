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
import org.m3.m3lib.ast.expr.RelationalExpression;
import org.m3.m3lib.ast.type.ExtendedType;
import org.m3.m3lib.ast.type.OrdinalType;
import org.m3.m3lib.ast.type.RefType;
import org.m3.m3lib.ast.type.Type;
import org.m3.m3lib.ast.value.BooleanValue;
import org.m3.m3lib.ast.value.ObjectValue;
import org.m3.m3lib.ast.value.Value;
import org.m3.m3lib.reflect.visitor.Visitor;

/**
 * 
 * @author LavermB
 * 
 */
public class RelationalExpressionEvaluator
  extends ExpressionEvaluator
  implements Visitor
{

  private static final Logger log_ = Logger
                                       .getLogger(RelationalExpressionEvaluator.class);

  /**
   * 
   */
  public RelationalExpressionEvaluator()
  {
    super();
  }

  private Value floatExpression(M3Token oper, Value lhs, Value rhs)
    throws IncompatibleTypeException
  {
    log_.debug("floatExpression(" + oper + ", " + lhs + ", " + rhs + ")");

    double dLhs = lhs.toDouble();
    double dRhs = rhs.toDouble();

    switch (oper) {
    case LT:
      return new BooleanValue(dLhs < dRhs);
    case LE:
      return new BooleanValue(dLhs <= dRhs);
    case EQ:
      return new BooleanValue(dLhs == dRhs);
    case NE:
      return new BooleanValue(dLhs != dRhs);
    case GE:
      return new BooleanValue(dLhs >= dRhs);
    case GT:
      return new BooleanValue(dLhs > dRhs);
    }
    return ObjectValue.UNDEFINED;
  }

  private Value longExpression(M3Token oper, Value lhs, Value rhs)
    throws IncompatibleTypeException
  {
    log_.debug("longExpression(" + oper + ", " + lhs + ", " + rhs + ")");

    long dLhs = lhs.toLong();
    long dRhs = rhs.toLong();

    switch (oper) {
    case LT:
      return new BooleanValue(dLhs < dRhs);
    case LE:
      return new BooleanValue(dLhs <= dRhs);
    case EQ:
      return new BooleanValue(dLhs == dRhs);
    case NE:
      return new BooleanValue(dLhs != dRhs);
    case GE:
      return new BooleanValue(dLhs >= dRhs);
    case GT:
      return new BooleanValue(dLhs > dRhs);
    }
    return ObjectValue.UNDEFINED;
  }

  private Value ordinalExpression(M3Token oper, Value lhs, Value rhs)
    throws IncompatibleTypeException
  {
    if (!lhs.getType().getBaseType().equals(rhs.getType().getBaseType())) {
      throw new IncompatibleTypeException(
                                          "Cannot compare values from different types.");
    }
    log_.debug("ordinalExpression(" + oper + ", " + lhs + ", " + rhs + ")");

    long dLhs = lhs.toLongOrdinal();
    long dRhs = rhs.toLongOrdinal();

    switch (oper) {
    case LT:
      return new BooleanValue(dLhs < dRhs);
    case LE:
      return new BooleanValue(dLhs <= dRhs);
    case EQ:
      return new BooleanValue(dLhs == dRhs);
    case NE:
      return new BooleanValue(dLhs != dRhs);
    case GE:
      return new BooleanValue(dLhs >= dRhs);
    case GT:
      return new BooleanValue(dLhs > dRhs);
    }
    return ObjectValue.UNDEFINED;
  }

  public Value textExpression(M3Token oper, Value lhs, Value rhs)
    throws IncompatibleTypeException
  {
    String sLhs = lhs.toString();
    String sRhs = rhs.toString();

    if (oper == M3Token.EQ) {
      return new BooleanValue(sLhs.equals(sRhs));
    }
    else if (oper == M3Token.NE) {
      return new BooleanValue(!sLhs.equals(sRhs));
    }
    throw new IncompatibleTypeException(
                                        "Can only test (in)equality for strings");
  }

  public Value refExpression(M3Token oper, Value lhs, Value rhs)
    throws IncompatibleTypeException
  {
    Object oLhs = lhs.toObject();
    Object oRhs = rhs.toObject();

    if (oper == M3Token.EQ) {
      return new BooleanValue(oLhs == oRhs);
    }
    else if (oper == M3Token.NE) {
      return new BooleanValue(oLhs != oRhs);
    }

    throw new IncompatibleTypeException("Can only test (in)equality for REFs");
  }

  public Value getReturnState(RelationalExpression e, Context c)
    throws IncompatibleTypeException
  {
    Value lhs = evalExpression(e.getOpnd1(), c).getValue();
    Type lhsType = lhs.getType();
    Value rhs = evalExpression(e.getOpnd2(), c).getValue();

    if (ExtendedType.getInstance().isAssignableFrom(lhsType)) {
      return floatExpression(e.getOper(), lhs, rhs);
    }
    if (ExtendedType.getInstance().isAssignableFrom(lhsType)) {
      return longExpression(e.getOper(), lhs, rhs);
    }
    if (lhsType instanceof OrdinalType) {
      return ordinalExpression(e.getOper(), lhs, rhs);
    }
    if (ExtendedType.getInstance().isAssignableFrom(lhsType)) {
      return textExpression(e.getOper(), lhs, rhs);
    }
    if (lhsType instanceof RefType) {
      return refExpression(e.getOper(), lhs, rhs);
    }
    throw new IncompatibleTypeException("Cannot compare values", e);
  }

}

/*
 * $Log$
 */
