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

import org.m3.m3lib.ast.expr.Expression;

public class Field
{
  private String     name_;
  private Type       type_    = null;
  private Expression default_ = null;
  private boolean    method_  = false;

  /**
   * @param name
   */
  public Field(String name)
  {
    super();
    this.name_ = name;
  }

  /**
   * @param name
   * @param type
   */
  public Field(String name, Type type)
  {
    super();
    this.name_ = name;
    this.type_ = type;
  }

  /**
   * @param name
   * @param default1
   */
  public Field(String name, Expression default1)
  {
    super();
    this.name_ = name;
    this.default_ = default1;
  }

  /**
   * @param name
   * @param type
   * @param default1
   */
  public Field(String name, Type type, Expression default1)
  {
    super();
    this.name_ = name;
    this.type_ = type;
    this.default_ = default1;
  }

  /**
   * @return the name
   */
  public String getName()
  {
    return this.name_;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name)
  {
    this.name_ = name;
  }

  /**
   * @return the type
   */
  public Type getType()
  {
    return this.type_;
  }

  /**
   * @param type the type to set
   */
  public void setType(Type type)
  {
    this.type_ = type;
  }

  /**
   * @return the default
   */
  public Expression getDefault()
  {
    return this.default_;
  }

  /**
   * @param default1 the default to set
   */
  public void setDefault(Expression default1)
  {
    this.default_ = default1;
  }

  /**
   * @return the method
   */
  public boolean isMethod()
  {
    return this.method_;
  }

  /**
   * @param method the method to set
   */
  public void setMethod(boolean method)
  {
    this.method_ = method;
  }

}

/*
 * $Log$
 */
