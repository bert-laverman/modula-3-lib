/**
 * Copyright (c) 2008 B. Laverman. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: bertl
 */

// $Id$

package org.m3.m3lib.ast.value;

import org.apache.log4j.Logger;

import org.m3.m3lib.ast.IncompatibleTypeException;

/**
 * 
 * @author LavermB
 * 
 */
public abstract class IndexedValue
    extends ObjectValue
    implements LValue
{

  /**
   * 
   */
  private static final long     serialVersionUID = -4539716112597356934L;

  static protected final Logger log_             = Logger
                                                     .getLogger(IndexedValue.class);

  protected Value               index_;

  /**
   * 
   */
  public IndexedValue(Value index)
  {
    super();

    this.index_ = index;
  }

  /**
   * @return Returns the index.
   */
  public Value getIndex()
  {
    return this.index_;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.value.ObjectValue#getValue()
   */
  @Override
  public Value getValue()
  {
    try {
      return fromJavaObject(toObject(), getType());
    }
    catch (IncompatibleTypeException e) {
      log_.error("Internal error", e);
      return ObjectValue.UNDEFINED;
    }
  }

}

/*
 * $Log$
 */
