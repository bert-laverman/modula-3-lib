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
import org.m3.m3lib.ast.type.BooleanType;
import org.m3.m3lib.ast.type.Type;

/**
 * 
 * @author LavermB
 * 
 */
public class AndExpression
    extends DyadicExpression
{

  /**
   * 
   */
  private static final long serialVersionUID = -3936310448853050021L;

  /**
   * 
   */
  public AndExpression(Expression lhs, Expression rhs)
  {
    super();

    setOper(M3Token.AND);
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
    return 1;
  }

  /* (non-Javadoc)
   * @see com.syllogic.m3.ast.expr.Expression#getType()
   */
  public Type getType()
  {
    return BooleanType.getInstance();
  }

}

/*
 * $Log$
 */
