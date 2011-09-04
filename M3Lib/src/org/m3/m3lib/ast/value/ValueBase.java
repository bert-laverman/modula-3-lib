/**
 * Copyright (c) 2008 B. Laverman. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: bertl
 */

// $Id$

package org.m3.m3lib.ast.value;

import java.io.Serializable;

import org.m3.m3lib.ast.IncompatibleTypeException;
import org.m3.m3lib.ast.type.BooleanType;
import org.m3.m3lib.ast.type.NullType;
import org.m3.m3lib.ast.type.OrdinalType;
import org.m3.m3lib.ast.type.Type;
import org.m3.m3lib.ast.type.UndefinedType;

/**
 * 
 * @author LavermB
 * 
 */
public abstract class ValueBase
    implements Value, Serializable
{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private Type                    type_;

  /**
   * 
   */
  public ValueBase(Type type)
  {
    super();

    type_ = type;
  }

  /* (non-Javadoc)
   * @see com.syllogic.m3.ast.value.Value#getType()
   */
  public Type getType()
  {
    return type_;
  }

  /**
   * @param type The type to set.
   */
  public void setType(Type type)
  {
    this.type_ = type;
  }

  /* (non-Javadoc)
   * @see com.syllogic.m3.ast.value.Value#getValue()
   */
  public abstract Value getValue();

  /* (non-Javadoc)
   * @see com.syllogic.m3.ast.value.Value#toNumber()
   */
  public Number toNumber()
      throws IncompatibleTypeException
  {
    Object v = getValue();

    if (!(v instanceof Number)) {
      throw new IncompatibleTypeException(
        "Cannot convert a " + v.getClass().getName()
            + " to a java.lang.Number", this);
    }
    return (Number) v;
  }

  /* (non-Javadoc)
   * @see com.syllogic.m3.ast.value.Value#toLong()
   */
  public long toLong()
      throws IncompatibleTypeException
  {
    return toNumber().longValue();
  }

  /* (non-Javadoc)
   * @see com.syllogic.m3.ast.value.Value#toLongOrdinal()
   */
  public long toLongOrdinal()
      throws IncompatibleTypeException
  {
    if (!(type_ instanceof OrdinalType)) {
      throw new IncompatibleTypeException("Not of an ordinal type", this);
    }
    return toLong();
  }

  /* (non-Javadoc)
   * @see com.syllogic.m3.ast.value.Value#toDouble()
   */
  public double toDouble()
      throws IncompatibleTypeException
  {
    return toNumber().doubleValue();
  }

  /* (non-Javadoc)
   * @see com.syllogic.m3.ast.value.Value#toBoolean()
   */
  public boolean toBoolean()
      throws IncompatibleTypeException
  {
    return BooleanType.valueToBoolean(this);
  }

  /* (non-Javadoc)
   * @see com.syllogic.m3.ast.value.Value#toString()
   */
  public String toString()
  {
    if (type_.equals(UndefinedType.getInstance())) {
      return "UNDEFINED";
    }
    else if (type_.equals(NullType.getInstance())) {
      return "NIL";
    }
    return getValue().toString();
  }

}

/*
 * $Log$
 */
