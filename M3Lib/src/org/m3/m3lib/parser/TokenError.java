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
public class TokenError<Token>
  extends SyntaxError<Token>
{

  private Token token_;

  /**
   * @param message
   */
  public TokenError(String message, Token token)
  {
    super(message);

    this.token_ = token;
  }

  /**
   * @param scanner
   * @param message
   */
  public TokenError(Scanner<Token> scanner, String message, Token token)
  {
    super(scanner, message);

    this.token_ = token;
  }

  /**
   * @param message
   * @param sourceLocation
   */
  public TokenError(String message, SourceLocation sourceLocation, Token token)
  {
    super(message, sourceLocation);

    this.token_ = token;
  }

  /**
   * @return the token
   */
  public Token getToken()
  {
    return this.token_;
  }

  /**
   * @param token the token to set
   */
  public void setToken(Token token)
  {
    this.token_ = token;
  }

}

/*
 * $Log$
 */
