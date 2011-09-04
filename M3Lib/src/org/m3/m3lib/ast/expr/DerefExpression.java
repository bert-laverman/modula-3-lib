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
import org.m3.m3lib.ast.type.RefType;
import org.m3.m3lib.ast.type.Type;

/**
 * @author bertl
 *
 */
public class DerefExpression
  extends MonadicExpression
{

  /**
   * 
   */
  private static final long serialVersionUID = 1393397614606938823L;

  /**
   * 
   */
  public DerefExpression()
  {
    super();

    setOper(M3Token.UP);
  }

  /**
   * 
   */
  public DerefExpression(Expression opnd)
  {
    this();

    setOpnd(opnd);
  }

  /* (non-Javadoc)
   * @see org.m3.m3lib.ast.expr.Expression#getPrecedence()
   */
  @Override
  public int getPrecedence()
  {
    return 7;
  }

  /* (non-Javadoc)
   * @see org.m3.m3lib.ast.expr.MonadicExpression#getType()
   */
  @Override
  public Type getType()
  {
    return ((RefType)getOpnd().getType()).getElement();
  }

}


/*
 * $Log$
 */
