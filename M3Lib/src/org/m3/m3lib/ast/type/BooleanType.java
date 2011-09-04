/**
 * Copyright (c) 2008 B. Laverman. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: bertl
 */

// $Id$

package org.m3.m3lib.ast.type;

import org.m3.m3lib.ast.IncompatibleTypeException;
import org.m3.m3lib.ast.value.BooleanValue;
import org.m3.m3lib.ast.value.Value;

/**
 * 
 * @author LavermB
 * 
 */
public class BooleanType
    extends EnumeratedType
{

  /**
   * 
   */
  private static final long  serialVersionUID = 6859261316878058561L;

  private static String[]    ids_             = { "FALSE", "TRUE" };

  private static BooleanType instance_        = new BooleanType();

  /**
   * 
   */
  public BooleanType()
  {
    super(ids_);
  }

  /**
   * @return Returns the instance.
   */
  public static BooleanType getInstance()
  {
    return instance_;
  }

  public static long booleanToLongOrdinal(boolean b)
  {
    return b ? 1 : 0;
  }

  public static boolean longOrdinalToBoolean(long l)
  {
    return (l == 0) ? false : true;
  }

  public static Value booleanToValue(boolean b)
  {
    return new BooleanValue(b);
  }

  public static boolean valueToBoolean(Value v)
      throws IncompatibleTypeException
  {
    return longOrdinalToBoolean(v.toLongOrdinal());
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return getName();
  }

  public static String getName()
  {
    return "BOOLEAN";
  }

}

/*
 * $Log$
 */
