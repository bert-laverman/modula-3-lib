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
public class SymbolNotFoundException
    extends M3RuntimeException
{

  /**
   * 
   */
  private static final long serialVersionUID = 839705866138600919L;

  /**
   * 
   */
  public SymbolNotFoundException()
  {
    super();
  }

  /**
   * @param arg0
   * @param arg1
   */
  public SymbolNotFoundException(String arg0, Throwable arg1)
  {
    super(arg0, arg1);
    // TODO Auto-generated constructor stub
  }

  /**
   * @param arg0
   */
  public SymbolNotFoundException(String arg0)
  {
    super(arg0);
  }

  /**
   * @param arg0
   */
  public SymbolNotFoundException(Throwable arg0)
  {
    super(arg0);
  }

}


/*
 * $Log$
 */
