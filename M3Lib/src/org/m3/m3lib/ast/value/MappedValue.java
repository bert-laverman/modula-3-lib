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

import org.apache.log4j.Logger;

/**
 * 
 * @author LavermB
 *
 */
public class MappedValue
    extends ObjectValue
{

  /**
   * 
   */
  private static final long serialVersionUID = 989219445728923755L;
  private static final Logger log_ = Logger.getLogger(MappedValue.class);
  private Map<?,?> map_;
  private Object key_;

  /**
   * 
   */
  public MappedValue(Map<?,?> map, Object key)
  {
    super();
    // TODO Auto-generated constructor stub
    log_.warn("You're using an unimplemented MappedValue!");
  }

  /**
   * @return Returns the key.
   */
  public Object getKey()
  {
    return this.key_;
  }

  /**
   * @return Returns the map.
   */
  public Map<?,?> getMap()
  {
    return this.map_;
  }

}


/*
 * $Log$
 */
