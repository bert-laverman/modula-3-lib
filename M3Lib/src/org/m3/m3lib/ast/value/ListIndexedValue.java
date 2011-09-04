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

import java.util.List;

import org.m3.m3lib.ast.IncompatibleTypeException;

/**
 * 
 * @author LavermB
 * 
 */
public class ListIndexedValue
  extends IndexedValue
{

  /**
   * 
   */
  private static final long serialVersionUID = -885078014213640828L;

  private List<Object>           list_;

  /**
   * @param index
   */
  public ListIndexedValue(List<Object> list, Value index)
  {
    super(index);

    this.list_ = list;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.value.ObjectValue#toObject()
   */
  @Override
  public Object toObject()
    throws IncompatibleTypeException
  {
    return list_.get((int) index_.toLongOrdinal());
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.value.LValue#setValue(com.syllogic.m3.ast.value.Value)
   */
  public void setValue(Value value)
    throws IncompatibleTypeException
  {
    list_.set((int) index_.toLongOrdinal(), value.toObject());
  }

}

/*
 * $Log$
 */
