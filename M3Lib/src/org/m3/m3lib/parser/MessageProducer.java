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

/**
 * @author bertl
 *
 */
public interface MessageProducer
{

  /**
   * @param c
   */
  public void addMessageCollector(MessageCollector c);

  /**
   * @param c
   */
  public void removeMessageCollector(MessageCollector c);

}


/*
 * $Log$
 */
