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
public class M3RuntimeException
    extends Exception
{

  /**
   * 
   */
  private static final long serialVersionUID = 4608913213852306511L;

  /**
   * 
   */
  public M3RuntimeException()
  {
    super();
  }

  /**
   * @param arg0
   * @param arg1
   */
  public M3RuntimeException(String arg0, Throwable arg1)
  {
    super(arg0, arg1);
  }

  /**
   * @param arg0
   */
  public M3RuntimeException(String arg0)
  {
    super(arg0);
  }

  /**
   * @param arg0
   */
  public M3RuntimeException(Throwable arg0)
  {
    super(arg0);
  }

}


/*
 * $Log$
 */
