/**
 * Copyright (c) 2008 B. Laverman. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: bertl
 */

// $Id$
package org.m3.m3lib.ast.decl;

import java.util.List;

import org.m3.m3lib.ast.type.Type;
import org.m3.m3lib.scanner.SourceLocation;

/**
 * @author bertl
 * 
 */
public class ExceptionDeclaration
  implements Declaration
{

  /**
   * 
   */
  private static final long serialVersionUID = -673148769888885543L;

  /**
   * 
   */
  private String            name_            = null;

  /**
   * 
   */
  private Type              param_           = null;

  /**
   * 
   */
  private SourceLocation    sourceLocation_  = null;

  /* (non-Javadoc)
   * @see org.m3.m3lib.ast.decl.Declaration#getName()
   */
  @Override
  public String getName()
  {
    return name_;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name)
  {
    this.name_ = name;
  }

  /**
   * @return the param
   */
  public Type getParam()
  {
    return this.param_;
  }

  /**
   * @param param the param to set
   */
  public void setParam(Type param)
  {
    this.param_ = param;
  }

  /* (non-Javadoc)
   * @see org.m3.m3lib.ast.Node#getSourceLocation()
   */
  @Override
  public SourceLocation getSourceLocation()
  {
    return this.sourceLocation_;
  }

  /**
   * @param location the location to set
   */
  public void setSourceLocation(SourceLocation location)
  {
    this.sourceLocation_ = location;
  }

  /* (non-Javadoc)
   * @see org.m3.m3lib.reflect.visitor.ParentNode#getChildren()
   */
  @Override
  public List<?> getChildren()
  {
    return null;
  }

}

/*
 * $Log$
 */
