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

import org.m3.m3lib.ast.Binding;
import org.m3.m3lib.ast.Context;
import org.m3.m3lib.ast.IncompatibleTypeException;
import org.m3.m3lib.ast.expr.Expression;
import org.m3.m3lib.ast.expr.NewExpression;
import org.m3.m3lib.ast.type.IdType;
import org.m3.m3lib.ast.type.Type;
import org.m3.m3lib.ast.value.ObjectValue;
import org.m3.m3lib.ast.value.Value;
import org.m3.m3lib.reflect.Reflections;
import org.m3.m3lib.reflect.visitor.Visitor;

/**
 * 
 * @author LavermB
 * 
 */
public class NewExpressionEvaluator
    extends ExpressionEvaluator
    implements Visitor
{

  public static final Logger log_ = Logger
                                      .getLogger(NewExpressionEvaluator.class);

  /**
   * 
   */
  public NewExpressionEvaluator()
  {
    super();
  }

  public Value getReturnState(NewExpression exp, Context ctx)
      throws IncompatibleTypeException
  {
    log_.debug("NewExpression: type="+exp.getType()+", "+exp.getArgs().size()+" actuals");
    try {
      Type clazzType = exp.getType();
      Class<?> clazz;
      if (clazzType instanceof IdType) {
        final String clazzName = ((IdType) clazzType).getId();
        Binding b = ctx.lookup(clazzName);
        if (b == null) { throw new IncompatibleTypeException(
            "Cannot resulve typename \"" + clazzName + "\""); }
        clazz = (Class<?>) (b.getValue().toObject());
      }
      // else if (clazzType instanceof JavaClassType) {
      // clazz = (Class) (b.getValue().toObject());
      // }
      else {
        throw new IncompatibleTypeException("Can only construct Java Objects");
      }
      log_.debug("  clazz = "+clazz.getName());
      List<Expression> actuals = exp.getArgs();
      Object[] args = new Object[actuals.size()];
      for (int i = 0; i < actuals.size(); i++) {
        args[i] = evalExpression(actuals.get(i), ctx).toObject();
        log_.debug("  arg ["+i+"] = "+args [i]);
      }
      return ObjectValue.fromJavaObject(Reflections.construct(clazz, args));
    }
    catch (NoSuchMethodException e) {
      throw new IncompatibleTypeException(
          "No constructor found with these parameters", exp);
    }
    catch (Exception e) {

    }
    return ObjectValue.UNDEFINED;
  }
}

/*
 * $Log$
 */
