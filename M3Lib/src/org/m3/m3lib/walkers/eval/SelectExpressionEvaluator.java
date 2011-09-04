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

import java.lang.reflect.Field;

import org.apache.log4j.Logger;

import org.m3.m3lib.ast.Binding;
import org.m3.m3lib.ast.Context;
import org.m3.m3lib.ast.M3Runtime;
import org.m3.m3lib.ast.M3RuntimeException;
import org.m3.m3lib.ast.Module;
import org.m3.m3lib.ast.expr.IdExpression;
import org.m3.m3lib.ast.expr.SelectExpression;
import org.m3.m3lib.ast.type.InterfaceType;
import org.m3.m3lib.ast.type.JavaClassType;
import org.m3.m3lib.ast.type.JavaPackageType;
import org.m3.m3lib.ast.value.FieldValue;
import org.m3.m3lib.ast.value.ImportedValue;
import org.m3.m3lib.ast.value.InterfaceValue;
import org.m3.m3lib.ast.value.JavaProcValue;
import org.m3.m3lib.ast.value.ObjectValue;
import org.m3.m3lib.ast.value.PropertyValue;
import org.m3.m3lib.ast.value.Value;
import org.m3.m3lib.reflect.Reflections;
import org.m3.m3lib.reflect.visitor.Visitor;

/**
 * 
 * @author LavermB
 *
 */
public class SelectExpressionEvaluator
    extends ExpressionEvaluator
    implements Visitor
{

  private static final Logger log_ = Logger.getLogger("com.syllogic.m3.ast.expr.eval.ExpressionEvaluator");

  /**
   * 
   */
  public SelectExpressionEvaluator()
  {
    super();
  }

  public Value getReturnState(SelectExpression expr, Context c)
      throws M3RuntimeException
  {
    Value lhs = evalExpression(expr.getOpnd1(), c).getValue();
    String id = ((IdExpression)expr.getOpnd2()).getId();

    if (lhs.getType() instanceof JavaClassType) {
      Object o = lhs.toObject();
      if (o instanceof Class) {
        Class<?> clazz = (Class<?>)o;

        // Going static?
        try {
          Field f = clazz.getDeclaredField(id);

          if (f != null) {
            return new FieldValue(o, id, f);
          }
        }
        catch (NoSuchFieldException e) {
          //Ignore
        }

        if (Reflections.hasStaticMethodWithName(clazz, id)) {
          return new JavaProcValue(clazz, id);
        }
      }
      log_.debug("Selecting from an object");
      if (Reflections.hasNonStaticMethodWithName(o.getClass(), id)) {
        return new JavaProcValue(o, id);
      }
      return new PropertyValue(((ObjectValue)lhs.getValue()).toObject(), id);
    }
    else if (lhs.getType() instanceof JavaPackageType) {
      log_.debug("Selecting from a package");
      final String name = lhs.toString()+"."+id;
      try {
        Class<?> clazz = Class.forName(name);
        if (clazz != null) {
          return new ObjectValue(clazz, new JavaClassType(Class.class));
        }
      }
      catch (ClassNotFoundException e) {
      }
      return new ObjectValue(name, JavaPackageType.getInstance());
    }
    else if (lhs.getType() instanceof InterfaceType) {
      InterfaceValue intv = (InterfaceValue) lhs.getValue();
      log_.debug("Selecting from Interface \""+intv.getInterface().getName()+"\"");
      M3Runtime m3 = M3Runtime.getInstance();
      Module mod = m3.getModuleForInterface(intv.getInterface().getName());
      log_.debug("Calling lookup of \""+id+"\" in module \""+mod.getName()+"\"");
      Binding b = mod.getContext().lookup(id);
      log_.debug("Lookup returned binding: "+b);
      return new ImportedValue(mod, b);
    }
    log_.error("What am I selecting from? (target="+lhs+" ["+lhs.getType()+"], id=\""+id+"\")");
    return ObjectValue.UNDEFINED;
  }
}


/*
 * $Log$
 */
