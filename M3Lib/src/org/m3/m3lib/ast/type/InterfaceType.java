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

import java.util.List;

import org.m3.m3lib.scanner.SourceLocation;

/**
 * 
 * @author LavermB
 * 
 */
public class InterfaceType
    extends Type
{

  /**
   * 
   */
  private static final long    serialVersionUID = 7560098011578681664L;

  private static InterfaceType instance_        = null;

  private SourceLocation       sourceLocation_;

  /**
   * 
   */
  public InterfaceType()
  {
    super();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.type.Type#getBaseType()
   */
  public Type getBaseType()
  {
    return this;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.type.Type#isAssignableFrom(com.syllogic.m3.ast.type.Type)
   */
  public boolean isAssignableFrom(Type type)
  {
    return false;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.type.Type#setSourceLocation(com.syllogic.m3.scanner.SourceLocation)
   */
  public void setSourceLocation(SourceLocation loc)
  {
    this.sourceLocation_ = loc;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.Node#getSourceLocation()
   */
  public SourceLocation getSourceLocation()
  {
    return this.sourceLocation_;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.visitor.ParentNode#getChildren()
   */
  public List<?> getChildren()
  {
    return null;
  }

  /**
   * @return Returns the instance.
   */
  public static InterfaceType getInstance()
  {
    if (instance_ == null) {
      instance_ = new InterfaceType();
    }
    return instance_;
  }

}

/*
 * $Log$
 */
