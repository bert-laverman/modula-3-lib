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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.m3.m3lib.scanner.SourceLocation;

/**
 * @author bertl
 * 
 */
public class RecordType
  extends Type
{

  /**
   * 
   */
  private static final long serialVersionUID = 487961230314250882L;

  private Map<String, Field> fields_ = new HashMap<String, Field>();

  private SourceLocation sourceLocation_ = null;

  /* (non-Javadoc)
   * @see org.m3.m3lib.ast.type.Type#getBaseType()
   */
  @Override
  public Type getBaseType()
  {
    return this;
  }

  /* (non-Javadoc)
   * @see org.m3.m3lib.ast.type.Type#isAssignableFrom(org.m3.m3lib.ast.type.Type)
   */
  @Override
  public boolean isAssignableFrom(Type type)
  {
    // TODO Auto-generated method stub
    return false;
  }

  /* (non-Javadoc)
   * @see org.m3.m3lib.ast.Node#getSourceLocation()
   */
  @Override
  public SourceLocation getSourceLocation()
  {
    return this.sourceLocation_;
  }

  /* (non-Javadoc)
   * @see org.m3.m3lib.ast.type.Type#setSourceLocation(org.m3.m3lib.scanner.SourceLocation)
   */
  @Override
  public void setSourceLocation(SourceLocation loc)
  {
    this.sourceLocation_ = loc;
  }

  /* (non-Javadoc)
   * @see org.m3.m3lib.reflect.visitor.ParentNode#getChildren()
   */
  @Override
  public List<?> getChildren()
  {
    return new ArrayList<Field>(fields_.values());
  }

  /**
   * @return the fields
   */
  public Map<String, Field> getFields()
  {
    return this.fields_;
  }

  public Field getField(String name)
  {
    return fields_.get(name);
  }

  public void addField(Field field)
  {
    fields_.put(field.getName(), field);
  }

}

/*
 * $Log$
 */
