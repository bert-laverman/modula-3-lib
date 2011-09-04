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
public class ListValue
    extends CollectionValue
{

  /**
   * 
   */
  private static final long serialVersionUID = -51016154371561117L;

  /**
   * @param value
   */
  public ListValue(List<?> value)
  {
    super(value);
  }

  public List<?> toList()
      throws IncompatibleTypeException
  {
    return (List<?>) toObject();
  }

}

/*
 * $Log$
 */
