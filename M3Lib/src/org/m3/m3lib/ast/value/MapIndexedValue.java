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

import java.util.Map;

import org.m3.m3lib.ast.IncompatibleTypeException;

/**
 * 
 * @author LavermB
 * 
 */
public class MapIndexedValue
    extends IndexedValue
{

  /**
   * 
   */
  private static final long serialVersionUID = 148120085436320774L;

  private Map<Object,Object>               map_;

  /**
   * @param index
   */
  public MapIndexedValue(Map<Object,Object> map, Value index)
  {
    super(index);

    this.map_ = map;
  }

  /* (non-Javadoc)
   * @see com.syllogic.m3.ast.value.ObjectValue#toObject()
   */
  @Override
  public Object toObject()
      throws IncompatibleTypeException
  {
    return map_.get(index_.toObject());
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.value.LValue#setValue(com.syllogic.m3.ast.value.Value)
   */
  public void setValue(Value value)
      throws IncompatibleTypeException
  {
    map_.put(index_.toObject(), value.toObject());
  }

}

/*
 * $Log$
 */
