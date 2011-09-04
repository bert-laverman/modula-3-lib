/**
 * Copyright (c) 2008 B. Laverman. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: bertl
 */

// $Id$

package org.m3.m3lib.scanner;

import org.m3.m3lib.ast.M3RuntimeException;


/**
 * 
 * @author LavermB
 * 
 */
public class ScannerException
    extends M3RuntimeException
{

  /**
   * 
   */
  private static final long serialVersionUID = -1235312363360436507L;

  /** Creates a new instance of ScannerException */
  public ScannerException(String msg)
  {
    super(msg);
  }

  public ScannerException(Scanner<?> s)
  {
    this("Exception while scanning line " + s.getLineNr());
  }
}

/*
 * $Log$
 */
