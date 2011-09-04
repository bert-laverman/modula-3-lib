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

import org.m3.m3lib.ast.IncompatibleTypeException;
import org.m3.m3lib.ast.ValueOutOfRangeException;
import org.m3.m3lib.ast.expr.Expression;
import org.m3.m3lib.ast.value.Value;

/**
 * 
 * @author LavermB
 * 
 */
public class SubrangeType
    extends OrdinalType
{

  /**
   * 
   */
  private static final long serialVersionUID = -2253133630467738229L;

  private OrdinalType       baseType_;
  private Expression        uncheckedLwb_;
  private Expression        uncheckedUpb_;

  /**
   * 
   */
  public SubrangeType()
  {
    super();
  }

  public SubrangeType(OrdinalType baseType)
  {
    super();

    this.baseType_ = (OrdinalType)baseType.getBaseType();
  }

  public SubrangeType(OrdinalType baseType, long lwb, long upb)
  {
    super(lwb, upb);

    this.baseType_ = (OrdinalType)baseType.getBaseType();
  }

  /**
   * @param lwb
   * @param upb
   */
  public SubrangeType(Expression lwb, Expression upb)
  {
    super();

    uncheckedLwb_ = lwb;
    uncheckedUpb_ = upb;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.type.OrdinalType#toString(com.syllogic.m3.ast.Value)
   */
  @Override
  public String toString(Value v)
      throws ValueOutOfRangeException, IncompatibleTypeException
  {
    return this.baseType_.toString(v);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.type.OrdinalType#toString(long)
   */
  @Override
  public String toString(long v)
      throws ValueOutOfRangeException
  {
    return this.baseType_.toString(v);
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
   * @return Returns the parent.
   */
  public OrdinalType getBaseType()
  {
    return this.baseType_;
  }

  /**
   * @param parent The parent to set.
   */
  public void setBaseType(OrdinalType parent)
  {
    this.baseType_ = parent;
  }

  /**
   * @return Returns the uncheckedLwb.
   */
  public Expression getUncheckedLwb()
  {
    return this.uncheckedLwb_;
  }

  /**
   * @return Returns the uncheckedUpb.
   */
  public Expression getUncheckedUpb()
  {
    return this.uncheckedUpb_;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    StringBuffer b = new StringBuffer();
    try {
      b.append('[')
       .append(getBaseType().toString(getLwb()))
       .append("..")
       .append(getBaseType().toString(getUpb()))
       .append(']');
    }
    catch (ValueOutOfRangeException e) { }
    return b.toString();
  }

}

/*
 * $Log$
 */
