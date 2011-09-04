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

/**
 * 
 * @author LavermB
 * 
 */
public class M3ParserException
  extends Exception
{

  /**
   * 
   */
  private static final long serialVersionUID = -2373662406781441112L;

  private Scanner<?>    s_;

  /** Creates a new instance of ParserException */
  public M3ParserException(String msg)
  {
    super(msg);
  }

  public M3ParserException(Scanner<?> scanner, String msg)
  {
    this(msg + "\nScanner info:\n  Current linenr: " + scanner.getLineNr() +
         "\n  Last scanned token: " + scanner.toString());

    s_ = scanner;
  }

  /**
   * Getter for property scanner.
   * 
   * @return Value of property scanner.
   */
  public Scanner<?> getScanner()
  {

    return this.s_;
  }

  /**
   * Setter for property scanner.
   * 
   * @param scanner New value of property scanner.
   */
  public void setScanner(Scanner<?> scanner)
  {

    this.s_ = scanner;
  }

}

/*
 * $Log$
 */
