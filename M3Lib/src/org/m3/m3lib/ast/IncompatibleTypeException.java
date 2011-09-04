/**
 * Copyright (c) 2008 B. Laverman. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: bertl
 */

// $Id$
package org.m3.m3lib.ast;

import org.m3.m3lib.ast.expr.Expression;
import org.m3.m3lib.ast.type.Type;
import org.m3.m3lib.ast.value.Value;

/**
 * 
 * @author LavermB
 * 
 */
public class IncompatibleTypeException
  extends M3RuntimeException
{

  /**
   * 
   */
  private static final long serialVersionUID = -1252143051831213170L;

  private Value             value_           = null;

  private Type              type1_           = null;

  private Type              type2_           = null;

  private Expression        expr_            = null;

  public IncompatibleTypeException(String msg)
  {
    super("Incompatible type: " + msg);
  }

  /** Creates a new instance of IncompatibleTypeException */
  public IncompatibleTypeException(String msg, Value value)
  {
    super("Incompatible type in value: " + msg + "\n" + "Value = " +
          value.toString());
    this.value_ = value;
  }

  public IncompatibleTypeException(String msg, Type type, Value value)
  {
    super("Value incompatible with type: " + msg + "\nValue = " + value +
          "\nType = " + type);
    this.type1_ = type;
    this.value_ = value;
  }

  public IncompatibleTypeException(String msg, Expression expr)
  {
    super("Incompatible types in expression: " + msg + "\nLocation = " +
          expr.getSourceLocation() + "\nExpression = " + expr);
    this.expr_ = expr;
  }

  /**
   * @param string
   * @param baseType
   * @param type
   */
  public IncompatibleTypeException(String msg, Type baseType, Type newType)
  {
    super("Incompatible types: "+msg+"\nBase type = "+baseType+"\nNew  type = "+newType);

    this.type1_ = baseType;
    this.type2_ = newType;
  }

  /**
   * @return Returns the val.
   */
  public Value getValue()
  {
    return this.value_;
  }

  /**
   * @return Returns the type.
   */
  public Type getType()
  {
    return this.type1_;
  }

  public Type getOtherType()
  {
    return this.type2_;
  }

  /**
   * @return Returns the expr.
   */
  public Expression getExpr()
  {
    return this.expr_;
  }
}

/*
 * $Log$
 */
