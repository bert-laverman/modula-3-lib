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

import java.util.Collection;

import org.m3.m3lib.ast.IncompatibleTypeException;

/**
 * 
 * @author LavermB
 * 
 */
public class CollectionValue
    extends ObjectValue
{

  /**
   * 
   */
  private static final long serialVersionUID = 3631202418397054870L;

  /**
   * @param value
   */
  public CollectionValue(Collection<?> value)
  {
    super(value);
  }

  public Collection<?> toCollection()
      throws IncompatibleTypeException
  {
    return (Collection<?>) toObject();
  }

}

/*
 * $Log$
 */
