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
public class SelectExpression
    extends DyadicExpression
{

  /**
   * 
   */
  private static final long serialVersionUID = 3048914730216112841L;

  /**
   * 
   */
  public SelectExpression(Expression lhs, String id)
  {
    super();

    setOper(M3Token.DOT);
    setOpnd1(lhs);
    setOpnd2(new IdExpression(id));
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.expr.Expression#getPrecedence()
   */
  public int getPrecedence()
  {
    return 7;
  }

}

/*
 * $Log$
 */
