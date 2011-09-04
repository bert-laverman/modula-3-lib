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
import org.m3.m3lib.ast.value.IntegerValue;
import org.m3.m3lib.ast.value.Value;

/**
 * 
 * @author LavermB
 *
 */
public class IntegerType
    extends OrdinalType
{

  /**
   * 
   */
  private static final long serialVersionUID = 1407304984055943526L;

  private static IntegerType instance_ = null;

  /**
   * 
   */
  public IntegerType()
  {
    super();
  }

  /* (non-Javadoc)
   * @see com.syllogic.m3.ast.type.OrdinalType#pred(com.syllogic.m3.ast.Value)
   */
  @Override
  public Value pred(Value v)
      throws ValueOutOfRangeException, IncompatibleTypeException
  {
    return new IntegerValue(v.toLong()-1);
  }

  /* (non-Javadoc)
   * @see com.syllogic.m3.ast.type.OrdinalType#succ(com.syllogic.m3.ast.Value)
   */
  @Override
  public Value succ(Value v)
      throws ValueOutOfRangeException, IncompatibleTypeException
  {
    return new IntegerValue(v.toLong()+1);
  }

  /* (non-Javadoc)
   * @see com.syllogic.m3.ast.type.OrdinalType#toOrdinal(com.syllogic.m3.ast.Value)
   */
  @Override
  public long toOrdinal(Value v)
      throws ValueOutOfRangeException, IncompatibleTypeException
  {
    return v.toLong();
  }

  /* (non-Javadoc)
   * @see com.syllogic.m3.ast.type.OrdinalType#toString(com.syllogic.m3.ast.Value)
   */
  @Override
  public String toString(Value v)
      throws ValueOutOfRangeException, IncompatibleTypeException
  {
    return Long.toString(v.toLong());
  }

  /* (non-Javadoc)
   * @see com.syllogic.m3.ast.type.OrdinalType#toString(long)
   */
  @Override
  public String toString(long v)
      throws ValueOutOfRangeException
  {
    // TODO Auto-generated method stub
    return Long.toString(v);
  }

  /* (non-Javadoc)
   * @see com.syllogic.m3.ast.type.OrdinalType#toValue(long)
   */
  @Override
  public Value toValue(long v)
      throws ValueOutOfRangeException
  {
    return new IntegerValue(v);
  }

  /* (non-Javadoc)
   * @see com.syllogic.visitor.ParentNode#getChildren()
   */
  public List<?> getChildren()
  {
    return null;
  }

  /**
   * @return Returns the instance.
   */
  public static IntegerType getInstance()
  {
    if (instance_ == null) { instance_ = new IntegerType(); }
    return instance_;
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
    return "INTEGER";
  }

  /* (non-Javadoc)
   * @see com.syllogic.m3.ast.type.OrdinalType#isAssignableFrom(com.syllogic.m3.ast.type.Type)
   */
  @Override
  public boolean isAssignableFrom(Type type)
  {
    return type.getBaseType() instanceof IntegerType;
  }

  /* (non-Javadoc)
   * @see org.m3.m3lib.ast.type.OrdinalType#getBaseType()
   */
  @Override
  public Type getBaseType()
  {
    return this;
  }

}


/*
 * $Log$
 */
