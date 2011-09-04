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

import org.m3.m3lib.ast.IncompatibleTypeException;
import org.m3.m3lib.ast.type.RealType;

/**
 * 
 * @author LavermB
 * 
 */
public class RealValue
    extends ValueBase
{

  /**
   * 
   */
  private static final long serialVersionUID = -8432217606018745139L;

  private double            value_;

  /**
   * @param type
   */
  public RealValue(double value)
  {
    super(RealType.getInstance());

    value_ = value;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.value.Value#getValue()
   */
  @Override
  public Value getValue()
  {
    return this;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.value.ValueBase#toNumber()
   */
  @Override
  public Number toNumber()
      throws IncompatibleTypeException
  {
    return new Double(this.value_);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.value.ValueBase#toString()
   */
  @Override
  public String toString()
  {
    return Double.toString(this.value_);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.value.Value#toObject()
   */
  public Object toObject()
  {
    return new Double(value_);
  }

}

/*
 * $Log$
 */
