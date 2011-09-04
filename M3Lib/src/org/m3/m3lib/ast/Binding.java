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

import org.apache.log4j.Logger;

import org.m3.m3lib.ast.type.Type;
import org.m3.m3lib.ast.type.UndefinedType;
import org.m3.m3lib.ast.value.Value;

/**
 * 
 * @author LavermB
 * 
 */
public class Binding
{
  private static final Logger log_   = Logger.getLogger(Binding.class);

  private String              name_;

  private Type                type_;

  private Value               value_;

  private boolean             const_ = false;

  /** Creates a new instance of Binding */
  public Binding(String name, Type type, Value value)
  {
    name_ = name;
    type_ = type;
    value_ = value;
  }

  public Binding(String name, Type type)
  {
    super();

    name_ = name;
    type_ = type;
    value_ = null;
  }

  public Binding(String name)
  {
    this(name, UndefinedType.getInstance());
  }

  public Binding(String name, Value value)
  {
    this(name, value.getType(), value);
  }

  public String getName()
  {
    return name_;
  }

  public Type getType()
  {
    return type_;
  }

  public Value getValue()
  {
    return value_;
  }

  public void setValue(Value value)
      throws IncompatibleTypeException
  {
    if (isConst()) { throw new IncompatibleTypeException(
        "Cannot rebind constant", value); }
    if (type_.getBaseType().isAssignableFrom(value.getType())) {
      this.value_ = value;
    }
    else {
      log_.error("Trying to assign value "+value.toString()+" with type "+value.getType()+" to variable with type "+type_.getBaseType());
      throw new IncompatibleTypeException("Cannot bind value to variable",
          type_, value);
    }
  }

  public String toString()
  {
    return name_ + ": " + type_ + " := " + value_;
  }

  /**
   * @return Returns the const.
   */
  public boolean isConst()
  {
    return this.const_;
  }

  /**
   * @param const1
   *          The const to set.
   */
  public void setConst(boolean const1)
  {
    this.const_ = const1;
  }
}

/*
 * $Log$
 */
