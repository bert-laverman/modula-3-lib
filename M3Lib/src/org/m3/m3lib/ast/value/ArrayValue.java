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

import org.m3.m3lib.M3Token;
import org.m3.m3lib.ast.IncompatibleTypeException;
import org.m3.m3lib.ast.type.ArrayType;
import org.m3.m3lib.ast.type.TextType;
import org.m3.m3lib.ast.type.Type;

/**
 * 
 * @author LavermB
 * 
 */
public class ArrayValue
    extends ObjectValue
{

  /**
   * 
   */
  private static final long serialVersionUID = 8072154452737655665L;

  /**
   * @param type
   */
  public ArrayValue(ArrayType type, Object[] value)
  {
    super(value, type);

    value_ = value;
  }

  public Object[] getArrayValue()
      throws IncompatibleTypeException
  {
    return (Object[]) toObject();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.value.Value#toString()
   */
  @Override
  public String toString()
  {
    StringBuffer b = new StringBuffer();
    ArrayType at = (ArrayType) getType();
    Type et = at.getElement();
    Object[] av = null;
    try {
      av = getArrayValue();
    }
    catch (IncompatibleTypeException e) {
      // This should not happen
      av = new Object[0];
    }

    b.append(M3Token.ARRAY.toString()).append(' ');
    if (at.getIndex() != null) {
      b.append(at.getIndex().toString()).append(' ');
    }
    b.append(M3Token.OF.toString()).append(' ').append(
        et.toString()).append(M3Token.LBRACE.toString());
    for (int i = 0; i < av.length; i++) {
      if (i > 0) {
        b.append(M3Token.COMMA.toString());
      }
      if (et.equals(TextType.getInstance())) {
        b.append(TextValue.quote(av[i].toString()));
      }
      else {
        b.append(av[i].toString());
      }
    }
    b.append(M3Token.RBRACE.toString());
    return b.toString();
  }

}

/*
 * $Log$
 */
