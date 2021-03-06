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

import java.util.ArrayList;
import java.util.List;

import org.m3.m3lib.ast.expr.Expression;
import org.m3.m3lib.scanner.SourceLocation;

/**
 * @author bertl
 *
 */
public class LockStatement
  implements Statement
{

  /**
   * 
   */
  private static final long serialVersionUID = -4342707470043376069L;

  private Expression cond_;

  private List<Statement> doPart_ = new ArrayList<Statement>();

  private SourceLocation sourceLocation_ = null;

  /**
   * @param cond
   */
  public LockStatement(Expression cond)
  {
    super();
    this.cond_ = cond;
  }

  /**
   * @return the cond
   */
  public Expression getCond()
  {
    return this.cond_;
  }

  /**
   * @param cond the cond to set
   */
  public void setCond(Expression cond)
  {
    this.cond_ = cond;
  }

  /**
   * @return the doPart
   */
  public List<Statement> getDoPart()
  {
    return this.doPart_;
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
    return this.doPart_;
  }

}


/*
 * $Log$
 */
