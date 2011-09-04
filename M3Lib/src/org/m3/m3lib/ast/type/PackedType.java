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

import org.m3.m3lib.ast.expr.Expression;
import org.m3.m3lib.scanner.SourceLocation;

/**
 * @author bertl
 * 
 */
public class PackedType
  extends Type
{

  /**
   * 
   */
  private static final long serialVersionUID = -1481837944180099605L;

  private int               bits_;

  private Expression        uncheckedBits_;

  private Type              baseType_;

  private SourceLocation    sourceLocation_;

  /**
   * @param bits
   * @param base
   */
  public PackedType(Expression bits, Type base)
  {
    super();

    this.uncheckedBits_ = bits;
    this.baseType_ = base;
  }

  /* (non-Javadoc)
   * @see org.m3.m3lib.ast.type.Type#getBaseType()
   */
  @Override
  public Type getBaseType()
  {
    return this.baseType_;
  }

  /**
   * @param baseType the baseType to set
   */
  public void setBaseType(Type baseType)
  {
    this.baseType_ = baseType;
  }

  /**
   * @return the bits
   */
  public int getBits()
  {
    return this.bits_;
  }

  /**
   * @param bits the bits to set
   */
  public void setBits(int bits)
  {
    this.bits_ = bits;
  }

  /**
   * @return the uncheckedBits
   */
  public Expression getUncheckedBits()
  {
    return this.uncheckedBits_;
  }

  /**
   * @param uncheckedBits the uncheckedBits to set
   */
  public void setUncheckedBits(Expression uncheckedBits)
  {
    this.uncheckedBits_ = uncheckedBits;
  }

  /* (non-Javadoc)
   * @see org.m3.m3lib.ast.type.Type#isAssignableFrom(org.m3.m3lib.ast.type.Type)
   */
  @Override
  public boolean isAssignableFrom(Type type)
  {
    return baseType_.isAssignableFrom(type);
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
   * @see org.m3.m3lib.ast.Node#getSourceLocation()
   */
  @Override
  public SourceLocation getSourceLocation()
  {
    return this.sourceLocation_;
  }

  /* (non-Javadoc)
   * @see org.m3.m3lib.reflect.visitor.ParentNode#getChildren()
   */
  @Override
  public List<?> getChildren()
  {
    return null;
  }

}

/*
 * $Log$
 */
