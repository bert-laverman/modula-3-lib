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
import org.m3.m3lib.ast.type.Type;

/**
 * 
 * @author LavermB
 *
 */
public interface Value
{

  public Type getType();

  public Value getValue();

  public Object toObject()
      throws IncompatibleTypeException;

  public Number toNumber()
      throws IncompatibleTypeException;

  public long toLong()
      throws IncompatibleTypeException;

  public long toLongOrdinal()
      throws IncompatibleTypeException;

  public double toDouble()
      throws IncompatibleTypeException;

  public boolean toBoolean()
      throws IncompatibleTypeException;

  public String toString();

}

/*
 * $Log$
 */
