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

import org.m3.m3lib.parser.Error;

/**
 * @author bertl
 * 
 */
public class ScanError<Token>
  extends Error<Token>
{

  /**
   * @param message
   */
  public ScanError(String message)
  {
    super("Scanner error: " + message);
  }

  /**
   * @param message
   * @param sourceLocation
   */
  public ScanError(String message, SourceLocation sourceLocation)
  {
    super("Scanner error: " + message, sourceLocation);
  }

  /**
   * @param scanner
   * @param message
   */
  public ScanError(Scanner<Token> scanner, String message)
  {
    super(scanner, "Scanner error: " + message);
  }

}

/*
 * $Log$
 */
