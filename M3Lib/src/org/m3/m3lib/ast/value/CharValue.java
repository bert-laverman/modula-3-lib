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

import org.m3.m3lib.ast.IncompatibleTypeException;
import org.m3.m3lib.ast.type.CharType;

/**
 * @author bertl
 * 
 */
public class CharValue
  extends OrdinalValue
{

  /**
   * 
   */
  private static final long serialVersionUID = 8692492595867949554L;

  public CharValue(char c)
  {
    super(CharType.getInstance(), c);
  }

  /**
   * @param strVal
   */
  public CharValue(String strVal)
  {
    this(strVal.charAt(0));
  }

  /* (non-Javadoc)
   * @see org.m3.m3lib.ast.value.OrdinalValue#toNumber()
   */
  @Override
  public Number toNumber()
    throws IncompatibleTypeException
  {
    return new Long(toLongOrdinal());
  }

  /**
   * @return
   * @throws IncompatibleTypeException
   */
  public char getChar()
    throws IncompatibleTypeException
  {
    return ((char) toLong());
  }

  public static String quote(char c)
  {
    StringBuffer b = new StringBuffer();

    b.append('\'');
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
    b.append('\'');

    return b.toString();
  }

}

/*
 * $Log$
 */
