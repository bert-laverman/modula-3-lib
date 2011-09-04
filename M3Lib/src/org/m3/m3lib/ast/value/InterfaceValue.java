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
import org.m3.m3lib.ast.M3CompilationUnit;
import org.m3.m3lib.ast.type.InterfaceType;
import org.m3.m3lib.ast.type.Type;

/**
 * 
 * @author LavermB
 *
 */
public class InterfaceValue
    implements Value
{

  private M3CompilationUnit interface_;

  /**
   * 
   */
  public InterfaceValue(M3CompilationUnit intf)
  {
    super();

    this.interface_ = intf;
  }

  /* (non-Javadoc)
   * @see com.syllogic.m3.ast.value.Value#getType()
   */
  public Type getType()
  {
    return InterfaceType.getInstance();
  }

  /* (non-Javadoc)
   * @see com.syllogic.m3.ast.value.Value#getValue()
   */
  public Value getValue()
  {
    return this;
  }

  /* (non-Javadoc)
   * @see com.syllogic.m3.ast.value.Value#toObject()
   */
  public Object toObject()
  {
    return getInterface();
  }

  /* (non-Javadoc)
   * @see com.syllogic.m3.ast.value.Value#toNumber()
   */
  public Number toNumber()
      throws IncompatibleTypeException
  {
    throw new IncompatibleTypeException("An INTERFACE is not a number");
  }

  /* (non-Javadoc)
   * @see com.syllogic.m3.ast.value.Value#toLong()
   */
  public long toLong()
      throws IncompatibleTypeException
  {
    throw new IncompatibleTypeException("An INTERFACE is not a number");
  }

  /* (non-Javadoc)
   * @see com.syllogic.m3.ast.value.Value#toLongOrdinal()
   */
  public long toLongOrdinal()
      throws IncompatibleTypeException
  {
    throw new IncompatibleTypeException("An INTERFACE is not an Ordinal");
  }

  /* (non-Javadoc)
   * @see com.syllogic.m3.ast.value.Value#toDouble()
   */
  public double toDouble()
      throws IncompatibleTypeException
  {
    throw new IncompatibleTypeException("An INTERFACE is not a number");
  }

  /* (non-Javadoc)
   * @see com.syllogic.m3.ast.value.Value#toBoolean()
   */
  public boolean toBoolean()
      throws IncompatibleTypeException
  {
    throw new IncompatibleTypeException("An INTERFACE is not a boolean");
  }

  /**
   * @return Returns the interface.
   */
  public M3CompilationUnit getInterface()
  {
    return this.interface_;
  }

}


/*
 * $Log$
 */
