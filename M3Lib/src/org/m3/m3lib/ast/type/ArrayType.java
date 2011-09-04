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
public class ArrayType
    extends Type
{

  /**
   * 
   */
  private static final long serialVersionUID = -7569580751428645804L;

  private Type              element_;

  private Type              uncheckedIndex_;

  private OrdinalType       index_;

  private SourceLocation    sourceLocation_;

  /**
   * 
   */
  public ArrayType(Type element)
  {
    super();

    this.element_ = element;
  }

  /**
   * 
   */
  public ArrayType(OrdinalType index, Type element)
  {
    super();

    this.index_ = index;
    this.element_ = element;
  }

  /**
   * 
   */
  public ArrayType(Type index, Type element)
  {
    super();

    this.uncheckedIndex_ = index;
    this.element_ = element;
  }

  /**
   * 
   */
  public ArrayType()
  {
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.visitor.ParentNode#getChildren()
   */
  public List<Type> getChildren()
  {
    return null;
  }

  /**
   * @return Returns the element.
   */
  public Type getElement()
  {
    return this.element_;
  }

  /**
   * @param element
   *          The element to set.
   */
  public void setElement(Type element)
  {
    this.element_ = element;
  }

  /**
   * @return Returns the uncheckedIndex.
   */
  public Type getUncheckedIndex()
  {
    return (this.uncheckedIndex_ == null) ? getIndex() : this.uncheckedIndex_;
  }

  /**
   * @param uncheckedIndex the uncheckedIndex to set
   */
  public void setUncheckedIndex(Type uncheckedIndex)
  {
    this.uncheckedIndex_ = uncheckedIndex;
  }

  /**
   * @return Returns the index.
   */
  public OrdinalType getIndex()
  {
    return this.index_;
  }

  /**
   * @param index
   *          The index to set.
   */
  public void setIndex(OrdinalType index)
  {
    this.index_ = index;
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
   * @see com.syllogic.m3.ast.type.Type#isAssignable(com.syllogic.m3.ast.Value)
   */
  public boolean isAssignableFrom(Type type)
  {
    if (!(type instanceof ArrayType)) { return false; }
    ArrayType at = (ArrayType) (type);
    OrdinalType it = at.getIndex();

    if ((getIndex() != null) && !getIndex().equals(it)) { return false; }

    return this.element_.isAssignableFrom(at.getElement());
  }

  /**
   * @param clazz
   * @return
   */
  public static Type fromClass(Class<?> clazz)
  {
    return new ArrayType(JavaClassType.fromClass(clazz.getComponentType()));
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj)
  {
    if ((obj == null) || !(obj instanceof ArrayType)) {
      return false;
    }
    if (obj == this) {
      return true;
    }
    return super.equals(obj);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode()
  {
    // TODO Auto-generated method stub
    return super.hashCode();
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    // TODO Auto-generated method stub
    return super.toString();
  }

}

/*
 * $Log$
 */
