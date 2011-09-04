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

import org.m3.m3lib.ast.Binding;
import org.m3.m3lib.ast.IncompatibleTypeException;
import org.m3.m3lib.ast.type.Type;

/**
 * 
 * @author LavermB
 * 
 */
public class BoundValue
    implements LValue
{

  private Binding binding_;

  /**
   * 
   */
  public BoundValue(Binding binding)
  {
    super();

    this.binding_ = binding;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.value.LValue#setValue(java.lang.Object)
   */
  public void setValue(Value value)
      throws IncompatibleTypeException
  {
    binding_.setValue(value);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.value.Value#getType()
   */
  public Type getType()
  {
    return binding_.getType();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.value.Value#getValue()
   */
  public Value getValue()
  {
    return binding_.getValue();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.value.Value#toNumber()
   */
  public Number toNumber()
      throws IncompatibleTypeException
  {
    return getValue().toNumber();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.value.Value#toLong()
   */
  public long toLong()
      throws IncompatibleTypeException
  {
    return getValue().toLong();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.value.Value#toLongOrdinal()
   */
  public long toLongOrdinal()
      throws IncompatibleTypeException
  {
    return getValue().toLongOrdinal();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.value.Value#toDouble()
   */
  public double toDouble()
      throws IncompatibleTypeException
  {
    return getValue().toDouble();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.value.Value#toBoolean()
   */
  public boolean toBoolean()
      throws IncompatibleTypeException
  {
    return getValue().toBoolean();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.value.Value#toObject()
   */
  public Object toObject()
      throws IncompatibleTypeException
  {
    return binding_.getValue().toObject();
  }

}

/*
 * $Log$
 */
