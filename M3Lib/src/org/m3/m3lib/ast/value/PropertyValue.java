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

//import org.apache.log4j.Logger;

import org.m3.m3lib.ast.IncompatibleTypeException;
import org.m3.m3lib.ast.type.JavaClassType;
import org.m3.m3lib.ast.type.Type;
import org.m3.m3lib.reflect.Property;

/**
 * 
 * @author LavermB
 * 
 */
public class PropertyValue
    implements LValue
{

//  private static final Logger log_ = Logger.getLogger(PropertyValue.class);

  private Object              object_;

  private String              name_;

  private Property            prop_;

  /**
   * 
   */
  public PropertyValue()
  {
    super();
  }

  /**
   * @param object
   * @param name
   */
  public PropertyValue(Object object, String name)
  {
    this.object_ = object;
    this.name_ = name;
    this.prop_ = new Property(object, name);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.value.LValue#setValue(com.syllogic.m3.ast.value.Value)
   */
  public void setValue(Value value)
      throws IncompatibleTypeException
  {
    prop_.setProperty(value.toObject());
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.value.Value#getType()
   */
  public Type getType()
  {
    return JavaClassType.fromClass(prop_.getPropertyClass());
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.value.Value#getValue()
   */
  public Value getValue()
  {
    return ObjectValue.fromJavaObject(toObject());
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.value.Value#toNumber()
   */
  public Number toNumber()
      throws IncompatibleTypeException
  {
    return getValue().toNumber();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.value.Value#toLong()
   */
  public long toLong()
      throws IncompatibleTypeException
  {
    return getValue().toLong();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.value.Value#toLongOrdinal()
   */
  public long toLongOrdinal()
      throws IncompatibleTypeException
  {
    return getValue().toLongOrdinal();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.value.Value#toDouble()
   */
  public double toDouble()
      throws IncompatibleTypeException
  {
    return getValue().toDouble();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.value.Value#toBoolean()
   */
  public boolean toBoolean()
      throws IncompatibleTypeException
  {
    return getValue().toBoolean();
  }

  /**
   * @return Returns the name.
   */
  public String getName()
  {
    return this.name_;
  }

  /**
   * @param name
   *          The name to set.
   */
  public void setName(String name)
  {
    this.name_ = name;
  }

  /**
   * @return Returns the object.
   */
  public Object getObject()
  {
    return this.object_;
  }

  /**
   * @param object
   *          The object to set.
   */
  public void setObject(Object object)
  {
    this.object_ = object;
  }

  /* (non-Javadoc)
   * @see com.syllogic.m3.ast.value.Value#toObject()
   */
  public Object toObject()
  {
    return prop_.getProperty();
  }

}

/*
 * $Log$
 */
