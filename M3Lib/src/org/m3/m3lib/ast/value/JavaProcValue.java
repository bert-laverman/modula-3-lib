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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import org.m3.m3lib.ast.IncompatibleTypeException;
import org.m3.m3lib.ast.type.ProcedureType;
import org.m3.m3lib.ast.type.Type;
import org.m3.m3lib.reflect.Reflections;

/**
 * 
 * @author LavermB
 * 
 */
public class JavaProcValue
    implements Value
{

  private static final Logger log_         = Logger.getLogger(FieldValue.class);

  private Object              target_      = null;

  private Class<?>               targetClass_ = null;

  private String              name_;

  private List<Class<?>>      argTypes_    = null;

  private Method              method_;

  /**
   * @param type
   */
  public JavaProcValue(Class<?> targetClass, String name, List<Class<?>> argTypes)
  {
    super();

    this.targetClass_ = targetClass;
    this.name_ = name;
    this.argTypes_ = argTypes;
  }

  /**
   * @param type
   */
  public JavaProcValue(Object target, String name, List<Class<?>> argTypes)
  {
    this(target.getClass(), name, argTypes);

    this.target_ = target;
  }

  /**
   * @param type
   */
  public JavaProcValue(Class<?> targetClass, String name, Class<?>... argTypes)
  {
    super();

    this.targetClass_ = targetClass;
    this.name_ = name;
    this.argTypes_ = new ArrayList<Class<?>>();
    for (Class<?> clazz : argTypes) {
      argTypes_.add(clazz);
    }
  }

  /**
   * @param type
   */
  public JavaProcValue(Object target, String name, Class<?>... argTypes)
  {
    this(target.getClass(), name, argTypes);

    this.target_ = target;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.value.Value#getType()
   */
  public Type getType()
  {
    return ProcedureType.fromMethod(getMethod());
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.value.Value#getValue()
   */
  public Value getValue()
  {
    return this;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.value.Value#toBoolean()
   */
  public boolean toBoolean()
      throws IncompatibleTypeException
  {
    throw new IncompatibleTypeException(
        "Cannot convert a procedure to a boolean");
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.value.Value#toDouble()
   */
  public double toDouble()
      throws IncompatibleTypeException
  {
    throw new IncompatibleTypeException(
        "Cannot convert a procedure to a double");
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.value.Value#toLong()
   */
  public long toLong()
      throws IncompatibleTypeException
  {
    throw new IncompatibleTypeException("Cannot convert a procedure to a long");
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.value.Value#toLongOrdinal()
   */
  public long toLongOrdinal()
      throws IncompatibleTypeException
  {
    throw new IncompatibleTypeException(
        "Cannot convert a procedure to an ordinal");
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.value.Value#toNumber()
   */
  public Number toNumber()
      throws IncompatibleTypeException
  {
    throw new IncompatibleTypeException(
        "Cannot convert a procedure to a number");
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.value.Value#toObject()
   */
  public Object toObject()
  {
    return getMethod();
  }

  public void callProc(Object... parms)
      throws Throwable
  {
    log_.debug("Calling \""+name_+"\" as procedure");
    if ((argTypes_ == null) || (argTypes_.size() != parms.length)) {
      setArgTypesFromActuals(parms);
    }
    Reflections.callProc(target_, getMethod(), parms);
  }

  public Object callFunc(Object... parms)
      throws Throwable
  {
    log_.debug("Calling \""+name_+"\" as function");
    if ((argTypes_ == null) || (argTypes_.size() != parms.length)) {
      setArgTypesFromActuals(parms);
    }
    return Reflections.callFunc(target_, getMethod(), parms);
  }

  /**
   * @return Returns the method.
   */
  public Method getMethod()
  {
    if (method_ == null) {
      log_.debug("Searching for method");

      Class<?>[] argArr;
      if (argTypes_ == null) {
        log_.info("No argument types set!");
        argArr = new Class<?>[0];
      }
      else {
        log_.debug("Searching for method \""+name_+"\" in "+targetClass_.getName()+" with argument types:");
        argArr = new Class<?>[argTypes_.size()];
        for (int i = 0; i < argTypes_.size(); i++) {
          argArr[i] = argTypes_.get(i);
          log_.debug("  "+argArr [i].getName());
        }
      }
      if (target_ == null) {
        method_ = Reflections.findStaticMethod(targetClass_, name_, argArr);
      }
      else {
        method_ = Reflections.findNonStaticMethod(targetClass_, name_, argArr);
      }
    }
    return this.method_;
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

  /**
   * @return Returns the argTypes.
   */
  public List<Class<?>> getArgTypes()
  {
    return this.argTypes_;
  }

  public void setArgTypes(List<Class<?>> argTypes)
  {
    this.argTypes_ = new ArrayList<Class<?>>(argTypes);
  }

  public void setArgTypes(Class<?>... argTypes)
  {
    this.argTypes_ = Arrays.asList(argTypes);
  }

  public void setArgTypesFromActuals(List<Object> args)
  {
    log_.debug("Fixing argumenttypes from list with "+args.size()+" actuals");
    this.argTypes_ = new ArrayList<Class<?>>(args.size());
    for (Object obj : args) {
      if (obj == null) {
        argTypes_.add(Object.class);
      }
      else {
        argTypes_.add(obj.getClass());
      }
    }
  }

  public void setArgTypesFromActuals(Object... args)
  {
    log_.debug("Fixing argumenttypes from list with "+args.length+" actuals");
    this.argTypes_ = new ArrayList<Class<?>>(args.length);
    for (Object obj : args) {
      if (obj == null) {
        argTypes_.add(Object.class);
      }
      else {
        argTypes_.add(obj.getClass());
      }
    }
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return "method \""+name_+"\" on "+target_;
  }

}

/*
 * $Log$
 */
