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

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.m3.m3lib.ast.Context;
import org.m3.m3lib.ast.IncompatibleTypeException;
import org.m3.m3lib.ast.M3RuntimeException;
import org.m3.m3lib.ast.expr.ApplicationExpression;
import org.m3.m3lib.ast.value.ImportedValue;
import org.m3.m3lib.ast.value.JavaProcValue;
import org.m3.m3lib.ast.value.ObjectValue;
import org.m3.m3lib.ast.value.ProcValue;
import org.m3.m3lib.ast.value.Value;
import org.m3.m3lib.reflect.visitor.Visitor;

/**
 * 
 * @author LavermB
 * 
 */
public class ApplicationExpressionEvaluator
    extends ExpressionEvaluator
    implements Visitor
{

  private static final Logger log_ = Logger
                                       .getLogger(ApplicationExpressionEvaluator.class);

  /**
   * 
   */
  public ApplicationExpressionEvaluator()
  {
    super();
  }

  /**
   * @param value
   * @param actuals
   * @throws M3RuntimeException
   */
  private Value callProc(ProcValue proc, List<Value> actuals, Context ctx)
      throws M3RuntimeException
  {
    log_.debug("Calling M3 PROCEDURE " + proc + " (" + actuals.size()
        + " actuals)");
    Value result = ProcValue.runProcedure(ctx, proc, actuals);

    log_.debug("  Return value: " + result);
    return result;
  }

  private Value callJavaProc(JavaProcValue proc, Object... argObs)
  {
    try {
      Object result = proc.callFunc(argObs);
      log_.debug("  Raw return value: " + result);
      return ObjectValue.fromJavaObject(result);
    }
    catch (Throwable e) {
      log_
          .error("Exception caught while calling \"" + proc.getName() + "\"", e);
    }
    return ObjectValue.UNDEFINED;
  }

  private Value callJavaProc(JavaProcValue proc, List<Value> actuals)
      throws IncompatibleTypeException
  {
    log_.debug("Calling Java method " + proc.getName() + " (" + actuals.size()
        + " actuals)");
    Object[] argObs = new Object[actuals.size()];
    for (int i = 0; i < actuals.size(); i++) {
      argObs[i] = actuals.get(i).toObject();
    }

    Value result = callJavaProc(proc, argObs);

    log_.debug("  Return value: " + result);
    return result;
  }

  public Value getReturnState(ApplicationExpression expr, Context ctx)
      throws M3RuntimeException
  {
    Value lhs = evalExpression(expr.getFun(), ctx);
    List<ApplicationExpression.Actual> argList = expr.getArgs();
    List<Value> actuals = new ArrayList<Value>();
    for (ApplicationExpression.Actual actual : argList) {
      actuals.add(evalExpression(actual.getValue(), ctx));
    }

    Context procContext = ctx;
    if (lhs instanceof ImportedValue) {
      procContext = ((ImportedValue) lhs).getModule().getContext();
    }
    lhs = lhs.getValue();

    log_.debug("ApplicationExpression: fun=" + lhs);
    if (lhs instanceof JavaProcValue) {
      return callJavaProc((JavaProcValue) lhs, actuals);
    }
    else if (lhs instanceof ProcValue) { return callProc((ProcValue) lhs,
        actuals, procContext); }

    return ObjectValue.UNDEFINED;
  }

}

/*
 * $Log$
 */
