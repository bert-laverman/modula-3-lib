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
import org.m3.m3lib.ast.type.IntegerType;

/**
 * 
 * @author LavermB
 *
 */
public class IntegerValue
    extends OrdinalValue
{

  /**
   * 
   */
  private static final long serialVersionUID = -3701935646177717210L;

  /**
   * @param type
   * @param value
   */
  public IntegerValue(long value)
  {
    super(IntegerType.getInstance(), value);
  }

  /* (non-Javadoc)
   * @see com.syllogic.m3.ast.value.OrdinalValue#toNumber()
   */
  @Override
  public Number toNumber()
      throws IncompatibleTypeException
  {
    return new Long(toLongOrdinal());
  }

}


/*
 * $Log$
 */
