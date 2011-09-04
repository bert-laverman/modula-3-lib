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
public class PlusExpression
    extends MonadicExpression
{

  /**
   * 
   */
  private static final long serialVersionUID = 471503299871688763L;

  /**
   * 
   */
  public PlusExpression(Expression opnd)
  {
    super();

    setOper(M3Token.PLUS);
    setOpnd(opnd);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.expr.Expression#getPrecedence()
   */
  public int getPrecedence()
  {
    return 6;
  }

}

/*
 * $Log$
 */
