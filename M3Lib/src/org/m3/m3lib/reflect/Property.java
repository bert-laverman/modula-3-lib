/**
 * Copyright (c) 2008 B. Laverman. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: bertl
 */

// $Id$

package org.m3.m3lib.reflect;

import java.lang.reflect.Method;
import org.apache.log4j.Logger;


/**
 * 
 * @author LavermB
 * 
 */
public class Property
{

  private static final Logger log_ = Logger.getLogger(Property.class);

  private Object              target_;

  private Class<?>               targetClass_;

  private String              name_;

  /**
   * @param target
   */
  public Property(Object target)
  {
    this.target_ = target;
    this.targetClass_ = target.getClass();
  }

  /**
   * @param target
   * @param name
   */
  public Property(Object target, String name)
  {
    this(target);
    this.name_ = name;
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

  private String getGetterName()
  {
    StringBuffer b = new StringBuffer();

    b.append("get").append(Character.toUpperCase(name_.charAt(0))).append(
        name_.substring(1));

    return b.toString();
  }

  private String getGetterNameB()
  {
    StringBuffer b = new StringBuffer();

    b.append("is").append(Character.toUpperCase(name_.charAt(0))).append(
        name_.substring(1));

    return b.toString();
  }

  private Method getGetter()
  {
    Method m = Reflections.findNonStaticMethod(targetClass_, getGetterName());
    if (m == null) { // Might be boolean "isXxx"
      m = Reflections.findNonStaticMethod(targetClass_, getGetterNameB());
    }

    return m;
  }

  private String getSetterName()
  {
    StringBuffer b = new StringBuffer();

    b.append("set").append(Character.toUpperCase(name_.charAt(0))).append(
        name_.substring(1));

    return b.toString();
  }

  private Method getSetter()
  {
    return Reflections.findNonStaticMethod(targetClass_, getSetterName(),
        getPropertyClass());
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

  public Object getProperty()
  {
    try {
      return Reflections.doCallFunc(target_, getGetter());
    }
    catch (Throwable e) {
      log_.error("Exception thrown during call to \""+getGetterName()+"\"", e);
    }
    return null;
  }

  public void setProperty(Object value)
  {
    try {
      Reflections.doCallProc(target_, getSetter(), value);
    }
    catch (Throwable e) {
      log_.error("Exception thrown during call to \""+getSetterName()+"\"", e);
    }
  }

  public Class<?> getPropertyClass()
  {
    return getGetter().getReturnType();
  }
}

/*
 * $Log$
 */
