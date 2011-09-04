/**
 * Copyright (c) 2008 B. Laverman. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: bertl
 */

// $Id$


package org.m3.m3lib.ast.value;

import org.m3.m3lib.ast.type.TextType;

/**
 * 
 * @author LavermB
 *
 */
public class TextValue
    extends ObjectValue
{

  /**
   * 
   */
  private static final long serialVersionUID = 5378102342546065846L;

  /**
   * 
   */
  public TextValue(String value)
  {
    super(value, TextType.getInstance());
  }

  public static String quote(String v)
  {
    StringBuffer b = new StringBuffer();
  
    b.append('"');
    for (int i = 0; i < v.length(); i++) {
      char c = v.charAt(i);
      if (c < ' ') {
        b.append('\\');
        switch (c) {
        case '\n':
          b.append('n');
          break;
        case '\t':
          b.append('t');
          break;
        case '\r':
          b.append('r');
          break;
        case '\f':
          b.append('f');
          break;
        default:
          b.append((char) (((int) c) >> 6));
          b.append((char) ((((int) c) >> 3) & 0x07));
          b.append((char) (((int) c) & 0x07));
          break;
        }
      }
      else {
        switch (c) {
        case '\\':
          b.append("\\\\");
          break;
        case '\'':
          b.append("\\'");
          break;
        case '\"':
          b.append("\\\"");
          break;
        default:
          b.append(c);
          break;
        }
      }
    }
    b.append('"');
  
    return b.toString();
  }

}


/*
 * $Log$
 */
