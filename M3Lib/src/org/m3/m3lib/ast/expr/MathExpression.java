/**
 * Copyright (c) 2008 B. Laverman. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: bertl
 */

// $Id$

package org.m3.m3lib.ast.expr;

import org.m3.m3lib.M3Token;

/**
 * 
 * @author LavermB
 * 
 */
public class MathExpression
    extends DyadicExpression
{

  /**
   * 
   */
  private static final long serialVersionUID = -7325255928351116847L;

  /**
   * 
   */
  public MathExpression(Expression lhs, M3Token oper, Expression rhs)
  {
    super();

    setOper(oper);
    setOpnd1(lhs);
    setOpnd2(rhs);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.expr.Expression#getPrecedence()
   */
  public int getPrecedence()
  {
    switch (getOper()) {
    case PLUS:
    case MINUS:
    case AMP:
      return 4;
    case TIMES:
    case DIVIDE:
    case DIV:
    case MOD:
      return 5;
    default:
      return 0;
    }
  }

}

/*
 * $Log$
 */
