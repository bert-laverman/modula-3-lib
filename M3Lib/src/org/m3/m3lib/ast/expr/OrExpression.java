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
public class OrExpression
    extends DyadicExpression
{

  /**
   * 
   */
  private static final long serialVersionUID = 1498462733569960283L;

  /** Creates a new instance of AndExpression */
  public OrExpression(Expression opnd1, Expression opnd2)
  {
    setOper(M3Token.OR);
    setOpnd1(opnd1);
    setOpnd2(opnd2);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.expr.Expression#getPrecedence()
   */
  public int getPrecedence()
  {
    return 0;
  }

  /* (non-Javadoc)
   * @see com.syllogic.m3.ast.expr.DyadicExpression#getType()
   */
  @Override
  public Type getType()
  {
    return BooleanType.getInstance();
  }

}

/*
 * $Log$
 */
