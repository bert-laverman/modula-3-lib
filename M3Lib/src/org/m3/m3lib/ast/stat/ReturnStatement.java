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

import java.io.Serializable;
import java.util.List;

import org.m3.m3lib.ast.expr.Expression;
import org.m3.m3lib.scanner.SourceLocation;

/**
 * 
 * @author LavermB
 *
 */
public class ReturnStatement
    implements Statement, Serializable
{

  /**
   * 
   */
  private static final long serialVersionUID = -4151779665239015258L;

  private Expression result_;
  
  private SourceLocation sourceLocation_;

  /**
   * 
   */
  public ReturnStatement(Expression result)
  {
    super();

    result_ = result;
  }

  /* (non-Javadoc)
   * @see com.syllogic.m3.ast.Node#getSourceLocation()
   */
  public SourceLocation getSourceLocation()
  {
    return this.sourceLocation_;
  }

  /* (non-Javadoc)
   * @see com.syllogic.visitor.ParentNode#getChildren()
   */
  public List<?> getChildren()
  {
    return null;
  }

  /**
   * @return Returns the result.
   */
  public Expression getResult()
  {
    return this.result_;
  }

  /**
   * @param sourceLocation The sourceLocation to set.
   */
  public void setSourceLocation(SourceLocation sourceLocation)
  {
    this.sourceLocation_ = sourceLocation;
  }

}


/*
 * $Log$
 */
