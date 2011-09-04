/**
 * Copyright (c) 2008 B. Laverman. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: bertl
 */

// $Id$
package org.m3.m3lib.ast.stat;

import java.util.List;

import org.m3.m3lib.ast.expr.Expression;
import org.m3.m3lib.ast.expr.NameExpression;
import org.m3.m3lib.scanner.SourceLocation;

/**
 * @author bertl
 * 
 */
public class RaiseStatement
  implements Statement
{

  /**
   * 
   */
  private static final long serialVersionUID = 1196616460127472822L;

  private NameExpression    name_            = null;

  private Expression        param_           = null;

  private SourceLocation    sourceLocation_  = null;

  /**
   * @return the name
   */
  public NameExpression getName()
  {
    return this.name_;
  }

  /**
   * @param name the name to set
   */
  public void setName(NameExpression name)
  {
    this.name_ = name;
  }

  /**
   * @return the param
   */
  public Expression getParam()
  {
    return this.param_;
  }

  /**
   * @param param the param to set
   */
  public void setParam(Expression param)
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
   * @param sourceLocation the sourceLocation to set
   */
  public void setSourceLocation(SourceLocation sourceLocation)
  {
    this.sourceLocation_ = sourceLocation;
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
