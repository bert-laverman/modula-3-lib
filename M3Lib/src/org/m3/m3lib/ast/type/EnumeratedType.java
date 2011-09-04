/**
 * Copyright (c) 2008 B. Laverman. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: bertl
 */

// $Id$
package org.m3.m3lib.ast.type;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.m3.m3lib.ast.IncompatibleTypeException;
import org.m3.m3lib.ast.ValueOutOfRangeException;
import org.m3.m3lib.ast.value.Value;

/**
 * 
 * @author LavermB
 * 
 */
public class EnumeratedType
  extends OrdinalType
{

  //  private static final Logger log_ = Logger.getLogger(EnumeratedType.class);

  /**
   * 
   */
  private static final long serialVersionUID = 4922259961697367140L;

  private List<String>      ids_             = null;

  public EnumeratedType()
  {
    super(0, -1);

    ids_ = new ArrayList<String>();
  }
  /**
   * 
   */
  public EnumeratedType(String... ids)
  {
    super(0, ids.length - 1);

    ids_ = Arrays.asList(ids);
  }

  /**
   * 
   */
  public EnumeratedType(List<String> ids)
  {
    super(0, ids.size() - 1);

    ids_ = new ArrayList<String>(ids);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.type.OrdinalType#toString(com.syllogic.m3.ast.Value)
   */
  @Override
  public String toString(Value v)
    throws ValueOutOfRangeException, IncompatibleTypeException
  {
    long idx = v.toLongOrdinal();

    rangeCheck(idx);

    return ids_.get((int) idx);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.type.OrdinalType#toString(int)
   */
  @Override
  public String toString(long v)
    throws ValueOutOfRangeException
  {
    rangeCheck(v);

    return ids_.get((int) v);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.visitor.ParentNode#getChildren()
   */
  public List<?> getChildren()
  {
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    StringBuffer b = new StringBuffer();
    b.append('{');
    for (int i = 0; i < this.ids_.size(); i++) {
      if (i > 0) {
        b.append(',');
      }
      b.append(this.ids_.get(i).toString());
    }
    b.append('}');

    return b.toString();
  }

  public List<String> getIds()
  {
    return ids_;
  }

  public void addId(String id)
  {
    ids_.add(id);
    setUpb(ids_.size()-1);
  }
}

/*
 * $Log$
 */
