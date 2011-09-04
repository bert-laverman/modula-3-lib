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


/**
 * 
 * @author LavermB
 * 
 */
public class TextType
    extends RefAnyType
{

  /**
   * 
   */
  private static final long serialVersionUID = 2553446836299541801L;

  private static TextType   instance_        = new TextType();

  /**
   * 
   */
  public TextType()
  {
    super();
  }

  /**
   * @return Returns the instance.
   */
  public static TextType getInstance()
  {
    return instance_;
  }

  /* (non-Javadoc)
   * @see com.syllogic.m3.ast.type.Type#isAssignable(com.syllogic.m3.ast.Value)
   */
  public boolean isAssignableFrom(Type type)
  {
    return (type instanceof TextType);
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
    return "TEXT";
  }
}

/*
 * $Log$
 */
