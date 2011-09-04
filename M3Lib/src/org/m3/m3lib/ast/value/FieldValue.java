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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.apache.log4j.Logger;

import org.m3.m3lib.ast.IncompatibleTypeException;
import org.m3.m3lib.ast.type.JavaClassType;
import org.m3.m3lib.ast.type.Type;
import org.m3.m3lib.reflect.Reflections;

/**
 * 
 * @author LavermB
 * 
 */
public class FieldValue
  implements LValue
{

  private static final Logger log_         = Logger.getLogger(FieldValue.class);

  private Object              target_      = null;

  private Class<?>            targetClass_ = null;

  private String              name_;

  private Field               field_;

  /**
   * 
   */
  public FieldValue(Class<?> targetClass, String name)
  {
    super();

    this.targetClass_ = targetClass;
    this.name_ = name;
    this.field_ = Reflections.getField(targetClass, name);
  }

  /**
   * 
   */
  public FieldValue(Object target, String name)
  {
    this(target.getClass(), name);

    this.target_ = target;
  }

  /**
   * 
   */
  public FieldValue(Class<?> targetClass, String name, Field field)
  {
    super();

    this.targetClass_ = targetClass;
    this.name_ = name;
    this.field_ = field;
  }

  /**
   * 
   */
  public FieldValue(Object target, String name, Field field)
  {
    this(target.getClass(), name, field);

    if (!Modifier.isStatic(field.getModifiers())) {
      this.target_ = target;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.value.LValue#setValue(com.syllogic.m3.ast.value.Value)
   */
  public void setValue(Value value)
    throws IncompatibleTypeException
  {
    try {
      field_.set(getTarget(), value.toObject());
    }
    catch (IllegalAccessException e) {
      log_.error("IllegalAccessException caught trying to set field \"" +
                 name_ + "\"");
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.value.Value#getType()
   */
  public Type getType()
  {
    return new JavaClassType(field_.getType());
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.value.Value#getValue()
   */
  public Value getValue()
  {
    return new ObjectValue(toObject());
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.value.Value#toObject()
   */
  public Object toObject()
  {
    try {
      return field_.get(getTarget());
    }
    catch (IllegalAccessException e) {
      log_.error("IllegalAccessException caught trying to get field \"" +
                 name_ + "\"");
    }
    return null;
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
    try {
      return field_.getLong(getTarget());
    }
    catch (IllegalArgumentException e) {
      log_.error("IllegalArgumentException caught in getLong()", e);
      throw new IncompatibleTypeException(
                                          "IllegalArgumentException caught in getLong() on Java field");
    }
    catch (IllegalAccessException e) {
      log_.error("IllegalAccessException caught in getLong()", e);
      throw new IncompatibleTypeException(
                                          "IllegalAccessException caught in getLong() on Java field");
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.value.Value#toLongOrdinal()
   */
  public long toLongOrdinal()
    throws IncompatibleTypeException
  {
    try {
      return field_.getLong(getTarget());
    }
    catch (IllegalArgumentException e) {
      log_.error("IllegalArgumentException caught in getLong()", e);
      throw new IncompatibleTypeException(
                                          "IllegalArgumentException caught in getLong() on Java field");
    }
    catch (IllegalAccessException e) {
      log_.error("IllegalAccessException caught in getLong()", e);
      throw new IncompatibleTypeException(
                                          "IllegalAccessException caught in getLong() on Java field");
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.value.Value#toDouble()
   */
  public double toDouble()
    throws IncompatibleTypeException
  {
    try {
      return field_.getDouble(getTarget());
    }
    catch (IllegalArgumentException e) {
      log_.error("IllegalArgumentException caught in getDouble()", e);
      throw new IncompatibleTypeException(
                                          "IllegalArgumentException caught in getDouble() on Java field");
    }
    catch (IllegalAccessException e) {
      log_.error("IllegalAccessException caught in getDouble()", e);
      throw new IncompatibleTypeException(
                                          "IllegalAccessException caught in getDouble() on Java field");
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.value.Value#toBoolean()
   */
  public boolean toBoolean()
    throws IncompatibleTypeException
  {
    try {
      return field_.getBoolean(getTarget());
    }
    catch (IllegalArgumentException e) {
      log_.error("IllegalArgumentException caught in getBoolean()", e);
      throw new IncompatibleTypeException(
                                          "IllegalArgumentException caught in getBoolean() on Java field");
    }
    catch (IllegalAccessException e) {
      log_.error("IllegalAccessException caught in getBoolean()", e);
      throw new IncompatibleTypeException(
                                          "IllegalAccessException caught in getBoolean() on Java field");
    }
  }

  /**
   * @return Returns the name.
   */
  public String getName()
  {
    return this.name_;
  }

  /**
   * @return Returns the target.
   */
  public Object getTarget()
  {
    return this.target_;
  }

  /**
   * @return Returns the targetClass.
   */
  public Class<?> getTargetClass()
  {
    return this.targetClass_;
  }

  public boolean isStatic()
  {
    return Modifier.isStatic(field_.getModifiers());
  }
}

/*
 * $Log$
 */
