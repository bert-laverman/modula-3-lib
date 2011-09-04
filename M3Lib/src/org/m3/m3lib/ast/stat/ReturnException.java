/**
 * Copyright (c) 2008 B. Laverman. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: bertl
 */

// $Id$


package org.m3.m3lib.ast.stat;

import org.m3.m3lib.ast.M3RuntimeException;
import org.m3.m3lib.ast.value.Value;

/**
 * 
 * @author LavermB
 *
 */
public class ReturnException
    extends M3RuntimeException
{

  /**
   * 
   */
  private static final long serialVersionUID = 8946098975571389952L;

  private Value result_;

  /**
   * @param result
   */
  public ReturnException(Value result)
  {
    super();

    this.result_ = result;
  }

  /**
   * @return Returns the result.
   */
  public Value getResult()
  {
    return this.result_;
  }
  
}


/*
 * $Log$
 */
