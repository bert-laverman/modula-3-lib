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
import org.m3.m3lib.ast.value.CharValue;
import org.m3.m3lib.ast.value.Value;

/**
 * @author bertl
 * 
 */
public class CharType
  extends OrdinalType
{

  /**
   * 
   */
  private static final long serialVersionUID = -2510517794902140012L;

  private static CharType   instance_        = null;

  public CharType()
  {
    super();
  }

  /* (non-Javadoc)
   * @see org.m3.m3lib.ast.type.OrdinalType#toString(org.m3.m3lib.ast.value.Value)
   */
  @Override
  public String toString(Value v)
    throws ValueOutOfRangeException, IncompatibleTypeException
  {
    if (v instanceof CharValue) {
      CharValue cv = (CharValue) v;
      return Character.toString(cv.getChar());
    }
    return v.toString();
  }

  /* (non-Javadoc)
   * @see org.m3.m3lib.ast.type.OrdinalType#toString(long)
   */
  @Override
  public String toString(long v)
    throws ValueOutOfRangeException
  {
    return Character.toString((char)v);
  }

  /* (non-Javadoc)
   * @see org.m3.m3lib.reflect.visitor.ParentNode#getChildren()
   */
  @Override
  public List<?> getChildren()
  {
    return null;
  }

  /* (non-Javadoc)
   * @see org.m3.m3lib.ast.type.OrdinalType#pred(org.m3.m3lib.ast.value.Value)
   */
  @Override
  public Value pred(Value v)
    throws ValueOutOfRangeException, IncompatibleTypeException
  {
    return new CharValue((char)(v.toLong()-1));
  }

  /* (non-Javadoc)
   * @see org.m3.m3lib.ast.type.OrdinalType#succ(org.m3.m3lib.ast.value.Value)
   */
  @Override
  public Value succ(Value v)
    throws ValueOutOfRangeException, IncompatibleTypeException
  {
    return new CharValue((char)(v.toLong()+1));
  }

  /* (non-Javadoc)
   * @see org.m3.m3lib.ast.type.OrdinalType#toOrdinal(org.m3.m3lib.ast.value.Value)
   */
  @Override
  public long toOrdinal(Value v)
    throws ValueOutOfRangeException, IncompatibleTypeException
  {
    return v.toLong();
  }

  /* (non-Javadoc)
   * @see org.m3.m3lib.ast.type.OrdinalType#toValue(long)
   */
  @Override
  public Value toValue(long v)
    throws ValueOutOfRangeException
  {
    // TODO Auto-generated method stub
    return super.toValue(v);
  }

  /**
   * @return
   */
  public static CharType getInstance()
  {
    if (instance_ == null) {
      instance_ = new CharType();
    }

    return instance_;
  }

}

/*
 * $Log$
 */
