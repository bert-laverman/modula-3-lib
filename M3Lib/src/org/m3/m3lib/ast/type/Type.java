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

import org.m3.m3lib.ast.Node;
import org.m3.m3lib.scanner.SourceLocation;

/**
 * @author bertl
 *
 */
abstract public class Type
  implements Node
{
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * @return
   */
  abstract public Type getBaseType();

  /**
   * @param type
   * @return
   */
  abstract public boolean isAssignableFrom(Type type);

  /**
   * @param loc
   */
  abstract public void setSourceLocation(SourceLocation loc);

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj)
  {
    if ((obj == null) || !this.getClass().equals(obj.getClass())) {
      return false;
    }

    return equals(this, (Type)obj);
  }

  protected static boolean equals(Type type1, Type type2)
  {
    if (type1 == null) {
      return (type2 == null);
    }
    if ((type2 == null) || !type1.getClass().equals(type2.getClass())) {
      return false;
    }
    return type1.equals(type2);
  }

}

/*
 * $Log$
 */
