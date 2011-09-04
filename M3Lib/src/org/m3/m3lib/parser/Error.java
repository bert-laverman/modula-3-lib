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
public class Error<Token>
  implements Message
{
  private String         message_;

  private SourceLocation sourceLocation_ = null;

  /**
   * @param message
   */
  public Error(String message)
  {
    super();
    this.message_ = message;
  }

  /**
   * @param message
   * @param sourceLocation
   */
  public Error(String message, SourceLocation sourceLocation)
  {
    super();
    this.message_ = message;
    this.sourceLocation_ = sourceLocation;
  }

  /**
   * @param scanner
   * @param message
   */
  public Error(Scanner<Token> scanner, String message)
  {
    super();
    this.message_ = message;
    this.sourceLocation_ = scanner.getSourceLocation();
  }

  /* (non-Javadoc)
   * @see org.m3.m3lib.parser.Message#getMessage()
   */
  @Override
  public String getMessage()
  {
    return this.message_;
  }

  /**
   * @param message the message to set
   */
  public void setMessage(String message)
  {
    this.message_ = message;
  }

  /* (non-Javadoc)
   * @see org.m3.m3lib.parser.Message#getSourceLocation()
   */
  @Override
  public SourceLocation getSourceLocation()
  {
    return this.sourceLocation_;
  }

  /**
   * @param sourceLocation the sourceLocation to set
   */
  public void setSourceLocation(SourceLocation sourceLocation)
  {
    this.sourceLocation_ = sourceLocation;
  }

}

/*
 * $Log$
 */
