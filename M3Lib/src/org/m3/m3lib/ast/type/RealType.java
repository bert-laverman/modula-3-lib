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
public class RealType
    extends Type
{

  /**
   * 
   */
  private static final long serialVersionUID = -8079350222813619078L;

  private static RealType   instance_        = null;

  private SourceLocation    sourceLocation_;

  /**
   * 
   */
  public RealType()
  {
    super();
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
   * @return Returns the loc.
   */
  public SourceLocation getSourceLocation()
  {
    return this.sourceLocation_;
  }

  /**
   * @param loc
   *          The loc to set.
   */
  public void setSourceLocation(SourceLocation loc)
  {
    this.sourceLocation_ = loc;
  }

  /**
   * @return Returns the instance.
   */
  public static RealType getInstance()
  {
    if (instance_ == null) {
      instance_ = new RealType();
    }
    return instance_;
  }

  /* (non-Javadoc)
   * @see com.syllogic.m3.ast.type.Type#getBaseType()
   */
  public Type getBaseType()
  {
    return this;
  }

  /* (non-Javadoc)
   * @see com.syllogic.m3.ast.type.Type#isAssignable(com.syllogic.m3.ast.Value)
   */
  public boolean isAssignableFrom(Type type)
  {
    return (type != null) &&
    ((type instanceof ExtendedType) || (type instanceof LongRealType) || (type instanceof RealType));
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
    return "REAL";
  }

}

/*
 * $Log$
 */
