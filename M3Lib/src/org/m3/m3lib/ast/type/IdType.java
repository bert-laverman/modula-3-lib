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
import java.util.List;

import org.apache.log4j.Logger;
import org.m3.m3lib.scanner.SourceLocation;

/**
 * 
 * @author LavermB
 * 
 */
public class IdType
  extends Type
{

  /**
   * 
   */
  private static final long   serialVersionUID = 8034490501852102681L;

  private static final Logger log_             = Logger.getLogger(IdType.class);

  private List<String>        ids_             = new ArrayList<String>();

  private SourceLocation      sourceLocation_;

  /**
   * 
   */
  public IdType(String id)
  {
    super();

    this.ids_.add(id);
  }

  public IdType(List<String> ids)
  {
    super();

    this.ids_.addAll(ids);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.type.Type#getBaseType()
   */
  public Type getBaseType()
  {
    return this;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.type.Type#isAssignableFrom(com.syllogic.m3.ast.type.Type)
   */
  public boolean isAssignableFrom(Type type)
  {
    log_.warn("Trying to check assignability on an unresolved type id");
    return false;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.type.Type#setSourceLocation(com.syllogic.m3.scanner.SourceLocation)
   */
  public void setSourceLocation(SourceLocation loc)
  {
    this.sourceLocation_ = loc;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.Node#getSourceLocation()
   */
  public SourceLocation getSourceLocation()
  {
    return this.sourceLocation_;
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

  /**
   * @return Returns the id.
   */
  public String getId()
  {
    StringBuffer result = new StringBuffer();
    boolean addDot = false;

    for (String id : ids_) {
      if (addDot) {
        result.append('.');
      }
      else {
        addDot = true;
      }
      result.append(id);
    }
    return result.toString();
  }

  /**
   * @return Returns the ids.
   */
  public List<String> getIds()
  {
    return ids_;
  }

}

/*
 * $Log$
 */
