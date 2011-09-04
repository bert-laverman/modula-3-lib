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

/**
 * 
 * @author LavermB
 *
 */
public class ArrayIndexedValue
    extends IndexedValue
{

  /**
   * 
   */
  private static final long serialVersionUID = -9144097127938908211L;
  private ArrayValue array_;

  /**
   * @param array
   * @param index
   */
  public ArrayIndexedValue(ArrayValue array, OrdinalValue index)
  {
    super(index);

    this.array_ = array;
  }

  /**
   * @return Returns the array.
   */
  public ArrayValue getArray()
  {
    return this.array_;
  }

  public Object toObject()
  {
    try {
      return array_.getArrayValue()[(int) (index_.toLongOrdinal())];
    }
    catch (IncompatibleTypeException e) {
      log_
          .error(
              "IncompatibleTypeException caught from conversion of index to ordinal value",
              e);
    }
    return UNDEFINED;
  }

  /* (non-Javadoc)
   * @see com.syllogic.m3.ast.value.LValue#setValue(com.syllogic.m3.ast.value.Value)
   */
  public void setValue(Value value)
      throws IncompatibleTypeException
  {
    // TODO Auto-generated method stub
    
  }

}


/*
 * $Log$
 */
