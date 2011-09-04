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
import org.m3.m3lib.ast.type.BooleanType;

/**
 * 
 * @author LavermB
 * 
 */
public class BooleanValue
    extends OrdinalValue
{

  /**
   * 
   */
  private static final long serialVersionUID = -4668266840938477309L;

  /**
   * @param type
   */
  public BooleanValue(boolean value)
  {
    super(BooleanType.getInstance(), BooleanType.booleanToLongOrdinal(value));
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.value.OrdinalValue#toObject()
   */
  @Override
  public Object toObject()
  {
    try {
      return BooleanType.valueToBoolean(this);
    }
    catch (IncompatibleTypeException e) {
      e.printStackTrace();
    }
    return super.toObject();
  }

}

/*
 * $Log$
 */
