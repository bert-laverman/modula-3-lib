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

import java.util.Set;

import org.m3.m3lib.ast.IncompatibleTypeException;

/**
 * 
 * @author LavermB
 * 
 */
public class SetValue
    extends CollectionValue
{

  /**
   * 
   */
  private static final long serialVersionUID = 8732717363065409327L;

  /**
   * @param value
   */
  public SetValue(Set<?> value)
  {
    super(value);
  }

  public Set<?> toSet()
      throws IncompatibleTypeException
  {
    return (Set<?>) toObject();
  }

}

/*
 * $Log$
 */
