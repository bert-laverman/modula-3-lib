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

import java.util.ArrayList;
import java.util.List;

/**
 * @author bertl
 *
 */
public class DefaultMessageCollector
  implements MessageCollector
{
  private List<Message> messages_ = new ArrayList<Message>();

  /* (non-Javadoc)
   * @see org.m3.m3lib.parser.MessageCollector#addMessage(org.m3.m3lib.parser.Message)
   */
  @Override
  public void addMessage(Message msg)
  {
    this.messages_.add(msg);
  }

  /* (non-Javadoc)
   * @see org.m3.m3lib.parser.MessageCollector#getMessages()
   */
  @Override
  public List<Message> getMessages()
  {
    return this.messages_;
  }

}


/*
 * $Log$
 */
