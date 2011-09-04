/**
 * Copyright (c) 2008 B. Laverman. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: bertl
 */

// $Id$


package org.m3.m3lib.ast;

import org.m3.m3lib.ast.type.OrdinalType;
import org.m3.m3lib.ast.value.OrdinalValue;
import org.m3.m3lib.ast.value.Value;

/**
 * 
 * @author LavermB
 *
 */
public class ValueOutOfRangeException
    extends M3RuntimeException
{

  /**
   * 
   */
  private static final long serialVersionUID = -3099151444623381426L;

  private OrdinalType type_;
  private Value       value_;

  /**
   * 
   */
  public ValueOutOfRangeException(OrdinalType t, Value v)
  {
    super("Value "+v+" is out of range for type "+t);

    this.type_ = t;
    this.value_ = v;
  }

  public ValueOutOfRangeException(OrdinalType t, long v)
  {
    super("Ordinal value "+v+" is out of range for type "+t);

    this.type_ = t;
    this.value_ = new OrdinalValue(t, v);
  }

  /**
   * @return Returns the type.
   */
  public OrdinalType getType()
  {
    return this.type_;
  }

  /**
   * @param type The type to set.
   */
  public void setType(OrdinalType type)
  {
    this.type_ = type;
  }

  /**
   * @return Returns the value.
   */
  public Value getValue()
  {
    return this.value_;
  }

  /**
   * @param value The value to set.
   */
  public void setValue(Value value)
  {
    this.value_ = value;
  }

}


/*
 * $Log$
 */
