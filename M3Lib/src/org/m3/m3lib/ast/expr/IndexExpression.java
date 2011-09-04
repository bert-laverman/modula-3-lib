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
public class IndexExpression
    extends DyadicExpression
{

  /**
   * 
   */
  private static final long serialVersionUID = -4638958455920623831L;

  /** Creates a new instance of NotExpression */
  public IndexExpression(Expression lhs, Expression rhs)
  {
    setOper(M3Token.LBRACK);
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
    return 7;
  }

}

/*
 * $Log$
 */
