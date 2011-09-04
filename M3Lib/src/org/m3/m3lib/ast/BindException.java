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

/**
 * 
 * @author LavermB
 *
 */
public class BindException
    extends Exception
{

  /**
   * 
   */
  private static final long serialVersionUID = -840810431283456487L;

  /**
   * 
   */
  public BindException()
  {
    super();
    // TODO Auto-generated constructor stub
  }

  /**
   * @param arg0
   */
  public BindException(String arg0)
  {
    super(arg0);
    // TODO Auto-generated constructor stub
  }

  /**
   * @param arg0
   * @param arg1
   */
  public BindException(String arg0, Throwable arg1)
  {
    super(arg0, arg1);
    // TODO Auto-generated constructor stub
  }

  /**
   * @param arg0
   */
  public BindException(Throwable arg0)
  {
    super(arg0);
    // TODO Auto-generated constructor stub
  }

}


/*
 * $Log$
 */
