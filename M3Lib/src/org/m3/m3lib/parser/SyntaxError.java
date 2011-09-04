/**
 * Copyright (c) 2008 B. Laverman. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: bertl
 */

// $Id$

package org.m3.m3lib.parser;

import org.m3.m3lib.scanner.Scanner;
import org.m3.m3lib.scanner.SourceLocation;

/**
 * @author bertl
 *
 */
public class SyntaxError<Token>
  extends Error<Token>
{

  /**
   * @param message
   */
  public SyntaxError(String message)
  {
    super(message);
  }

  /**
   * @param scanner
   * @param message
   */
  public SyntaxError(Scanner<Token> scanner, String message)
  {
    super(scanner, message);
  }

  /**
   * @param message
   * @param sourceLocation
   */
  public SyntaxError(String message, SourceLocation sourceLocation)
  {
    super(message, sourceLocation);
  }

}


/*
 * $Log$
 */
