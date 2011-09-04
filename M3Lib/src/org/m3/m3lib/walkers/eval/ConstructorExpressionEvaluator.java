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

import java.util.List;

import org.apache.log4j.Logger;
import org.m3.m3lib.ast.Context;
import org.m3.m3lib.ast.IncompatibleTypeException;
import org.m3.m3lib.ast.expr.ConstructorExpression;
import org.m3.m3lib.ast.type.ArrayType;
import org.m3.m3lib.ast.value.ArrayValue;
import org.m3.m3lib.ast.value.Value;
import org.m3.m3lib.reflect.visitor.Visitor;

/**
 * 
 * @author LavermB
 * 
 */
public class ConstructorExpressionEvaluator
    extends ExpressionEvaluator
    implements Visitor
{

  private static final Logger log_ = Logger
                                       .getLogger(ConstructorExpressionEvaluator.class);

  /**
   * 
   */
  public ConstructorExpressionEvaluator()
  {
    super();
  }

  public Value getReturnState(ConstructorExpression e, Context ctx)
      throws IncompatibleTypeException
  {
//    ArrayType type = (ArrayType)(e.getType());
    List<ConstructorExpression.ConsElt> vals = e.getValues();
    Object[] arr = new Object [vals.size()];

    for (int i = 0; i < vals.size(); i++) {
      arr [i] = evalExpression(vals.get(i).getValue(), ctx).toObject();
    }

    log_.debug("Created array constant");
    return new ArrayValue((ArrayType)(e.getType()), arr);
  }
}

/*
 * $Log$
 */
