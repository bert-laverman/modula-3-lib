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

import org.m3.m3lib.ast.Context;
import org.m3.m3lib.ast.IncompatibleTypeException;
import org.m3.m3lib.ast.SymbolNotFoundException;
import org.m3.m3lib.ast.expr.IdExpression;
import org.m3.m3lib.ast.value.BoundValue;
import org.m3.m3lib.ast.value.Value;
import org.m3.m3lib.reflect.visitor.Visitor;

/**
 * 
 * @author LavermB
 *
 */
public class IdExpressionEvaluator
    extends ExpressionEvaluator
    implements Visitor
{

  /**
   * 
   */
  public IdExpressionEvaluator()
  {
    super();
  }

  public Value getReturnState(IdExpression e, Context c)
      throws IncompatibleTypeException, SymbolNotFoundException
  {
    if (c.lookup(e.getId()) == null) {
      throw new SymbolNotFoundException("Cannot resolve \""+e.getId()+"\"");
    }
    return new BoundValue(c.lookup(e.getId()));
  }
}


/*
 * $Log$
 */
