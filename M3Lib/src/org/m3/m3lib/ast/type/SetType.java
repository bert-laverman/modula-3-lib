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
 * @author bertl
 *
 */
public class SetType
  extends Type
{

  /**
   * 
   */
  private static final long serialVersionUID = -8474877492238400421L;

  private Type element_;

  private SourceLocation sourceLocation_ = null;

  /**
   * @param element
   */
  public SetType(Type element)
  {
    super();
    this.element_ = element;
  }

  /* (non-Javadoc)
   * @see org.m3.m3lib.ast.type.Type#getBaseType()
   */
  @Override
  public Type getBaseType()
  {
    return this;
  }

  /* (non-Javadoc)
   * @see org.m3.m3lib.ast.type.Type#isAssignableFrom(org.m3.m3lib.ast.type.Type)
   */
  @Override
  public boolean isAssignableFrom(Type type)
  {
    return false;
  }

  /* (non-Javadoc)
   * @see org.m3.m3lib.ast.Node#getSourceLocation()
   */
  @Override
  public SourceLocation getSourceLocation()
  {
    return this.sourceLocation_;
  }

  /* (non-Javadoc)
   * @see org.m3.m3lib.ast.type.Type#setSourceLocation(org.m3.m3lib.scanner.SourceLocation)
   */
  @Override
  public void setSourceLocation(SourceLocation loc)
  {
    this.sourceLocation_ = loc;
  }

  /* (non-Javadoc)
   * @see org.m3.m3lib.reflect.visitor.ParentNode#getChildren()
   */
  @Override
  public List<?> getChildren()
  {
    return null;
  }

  /**
   * @return the element
   */
  public Type getElement()
  {
    return this.element_;
  }

  /**
   * @param element the element to set
   */
  public void setElement(Type element)
  {
    this.element_ = element;
  }

}


/*
 * $Log$
 */
