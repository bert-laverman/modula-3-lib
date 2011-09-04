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

import org.apache.log4j.Logger;

import org.m3.m3lib.ast.IncompatibleTypeException;
import org.m3.m3lib.ast.ValueOutOfRangeException;
import org.m3.m3lib.ast.value.OrdinalValue;
import org.m3.m3lib.ast.value.Value;
import org.m3.m3lib.scanner.SourceLocation;

/**
 * 
 * @author LavermB
 * 
 */
public abstract class OrdinalType
    extends Type
{

  /**
   * 
   */
  private static final long serialVersionUID = -1167618053215664516L;

  private static final Logger log_ = Logger.getLogger(OrdinalType.class);

  private long              lwb_;

  private long              upb_;

  private SourceLocation    sourceLocation_;

  /**
   * 
   */
  public OrdinalType()
  {
    super();
  }

  /**
   * 
   */
  public OrdinalType(long lwb, long upb)
  {
    super();

    this.lwb_ = lwb;
    this.upb_ = upb;
  }

  public void rangeCheck(long v)
      throws ValueOutOfRangeException
  {
    if ((v < lwb_) || (v > upb_)) { throw new ValueOutOfRangeException(this, v); }
  }

  public Value pred(Value v)
      throws ValueOutOfRangeException, IncompatibleTypeException
  {
    return toValue(v.toLongOrdinal() - 1);
  }

  public Value succ(Value v)
      throws ValueOutOfRangeException, IncompatibleTypeException
  {
    return toValue(v.toLongOrdinal() + 1);
  }

  public long toOrdinal(Value v)
      throws ValueOutOfRangeException, IncompatibleTypeException
  {
    long r = v.toLongOrdinal();
    rangeCheck(r);
    return r;
  }

  public abstract String toString(Value v)
      throws ValueOutOfRangeException, IncompatibleTypeException;

  public abstract String toString(long v)
      throws ValueOutOfRangeException;

  public Value toValue(long v)
      throws ValueOutOfRangeException
  {
    rangeCheck(v);
    return new OrdinalValue(this, v);
  }

  public Type getBaseType()
  {
    return this;
  }

  /**
   * @return Returns the lwb.
   */
  public long getLwb()
  {
    return this.lwb_;
  }

  /**
   * @param lwb
   *          The lwb to set.
   */
  public void setLwb(long lwb)
  {
    this.lwb_ = lwb;
  }

  /**
   * @return Returns the upb.
   */
  public long getUpb()
  {
    return this.upb_;
  }

  /**
   * @param upb
   *          The upb to set.
   */
  public void setUpb(long upb)
  {
    this.upb_ = upb;
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

  /* (non-Javadoc)
   * @see com.syllogic.m3.ast.type.Type#isAssignable(com.syllogic.m3.ast.Value)
   */
  public boolean isAssignableFrom(Type type)
  {
    if (!getBaseType().equals(type.getBaseType())) {
      return false;
    }
    try {
      OrdinalType ot = (OrdinalType)type;
      return ((ot.getLwb() >= getLwb()) && (ot.getUpb() <= getUpb()));
    }
    catch (ClassCastException e) {
      log_.error("baseType() matches, but cast to OrdinalType failes!?", e);
    }
    return false;
  }

}

/*
 * $Log$
 */
