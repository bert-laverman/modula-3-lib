/**
 * Copyright (c) 2008 B. Laverman. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: bertl
 */

// $Id$


package org.m3.m3lib.walkers.run;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import org.m3.m3lib.ast.Binding;
import org.m3.m3lib.ast.Context;
import org.m3.m3lib.ast.M3RuntimeException;
import org.m3.m3lib.ast.SimpleContext;
import org.m3.m3lib.ast.stat.ForInStatement;
import org.m3.m3lib.ast.stat.Statement;
import org.m3.m3lib.ast.value.ArrayValue;
import org.m3.m3lib.ast.value.CollectionValue;
import org.m3.m3lib.ast.value.MapValue;
import org.m3.m3lib.ast.value.ObjectValue;
import org.m3.m3lib.ast.value.Value;
import org.m3.m3lib.reflect.visitor.Visitor;
import org.m3.m3lib.walkers.eval.ExpressionEvaluator;

/**
 * 
 * @author LavermB
 *
 */
public class ForInStatementRunner
    extends StatementRunner
    implements Visitor
{

  private static final Logger log_ = Logger.getLogger(ForInStatementRunner.class);

  /**
   * 
   */
  public ForInStatementRunner()
  {
    super();
  }

  private void doLoop(String id, Iterator<?> vals, Statement body, Context ctx)
      throws M3RuntimeException
  {
    SimpleContext inner = new SimpleContext(ctx);

    while (vals.hasNext()) {
      Object obj = vals.next();
      Binding b;
      if (obj instanceof Value) {
        b = new Binding(id, (Value)obj);
      }
      else {
        b = new Binding(id, ObjectValue.fromJavaObject(obj));
      }
      inner.remove(id);
      b.setConst(true);
      inner.add(b);

      run(body, inner);
    }
  }
  public void startVisit(ForInStatement stat, Context ctx)
      throws M3RuntimeException
  {
    final String id = stat.getId();
    Value setVal = ExpressionEvaluator.evalExpression(stat.getSet(), ctx).getValue(); 
    if (setVal instanceof MapValue) {
      MapValue map = (MapValue) setVal;

      doLoop(id, map.toMap().keySet().iterator(), stat.getBody(), ctx);
    }
    else if (setVal instanceof CollectionValue) {
      CollectionValue coll = (CollectionValue) setVal;

      doLoop(id, coll.toCollection().iterator(), stat.getBody(), ctx);
    }
    else if (setVal instanceof ArrayValue) {
      List<?> list = Arrays.asList(((ArrayValue)setVal).getArrayValue());
      doLoop(id, list.iterator(), stat.getBody(), ctx);
    }
    else {
      log_.error("Don't know how to iterate over a "+setVal.getClass().getName());
    }
  }
}


/*
 * $Log$
 */
