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

import java.io.IOException;

import org.m3.m3lib.parser.MessageProducer;

/**
 * @author bertl
 *
 */
public interface Scanner<Token>
  extends MessageProducer
{

  /**
   * 
   */
  void close();

  /**
   * @return
   * @throws IOException
   */
  Token next()
    throws IOException;

  /**
   * @return
   */
  Token getCur();

  /**
   * @return
   */
  String getStrVal();

  /**
   * @return
   */
  long getIntVal();

  /**
   * @return
   */
  double getDblVal();

  /**
   * @return
   */
  Object getObjVal();

  /**
   * @return
   */
  SourceLocation getSourceLocation();

  /**
   * @return
   */
  long getLineNr();

  /**
   * @return
   */
  int getLinePos();

  /**
   * @return
   */
  int getFilePos();

  /**
   * @param id
   * @return
   */
  boolean isReservedId(String id);

  Token getErrorToken();

  Token getEofToken();

}

/*
 * $Log$
 */
