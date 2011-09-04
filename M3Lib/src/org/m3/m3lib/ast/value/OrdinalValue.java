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

import org.apache.log4j.Logger;

import org.m3.m3lib.ast.IncompatibleTypeException;
import org.m3.m3lib.ast.ValueOutOfRangeException;
import org.m3.m3lib.ast.type.OrdinalType;

/**
 * 
 * @author LavermB
 * 
 */
public class OrdinalValue
    extends ValueBase
{

  /**
   * 
   */
  private static final long   serialVersionUID = 191736820561800397L;

  private static final Logger log_             = Logger
                                                   .getLogger(OrdinalValue.class);

  private long                value_;

  /**
   * @param type
   */
  public OrdinalValue(OrdinalType type, long value)
  {
    super(type);

    value_ = value;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.Value#getValue()
   */
  @Override
  public Value getValue()
  {
    return this;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.Value#toLongOrdinal()
   */
  @Override
  public long toLongOrdinal()
      throws IncompatibleTypeException
  {
    return value_;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.Value#toNumber()
   */
  @Override
  public Number toNumber()
      throws IncompatibleTypeException
  {
    throw new IncompatibleTypeException("Cannot cast an ordinal to a number",
        this);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.Value#toString()
   */
  @Override
  public String toString()
  {
    try {
      return ((OrdinalType) getType()).toString(value_);
    }
    catch (ValueOutOfRangeException e) {
      log_.error("Value out of range for its own type", e);
    }
    return "<some OrdinalType>" + Long.toString(value_);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.value.Value#toObject()
   */
  public Object toObject()
  {
    return new Long(value_);
  }

}

/*
 * $Log$
 */
