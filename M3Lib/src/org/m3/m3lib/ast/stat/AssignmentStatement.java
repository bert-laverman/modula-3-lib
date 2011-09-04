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
public class AssignmentStatement
    implements Statement, Serializable
{

  /**
   * 
   */
  private static final long serialVersionUID = 2649764070478595154L;

  Expression                lhs_;

  Expression                rhs_;

  private SourceLocation    sourceLocation_;

  /** Creates a new instance of AssignmentStatement */
  public AssignmentStatement(Expression lhs, Expression rhs)
  {
    lhs_ = lhs;
    rhs_ = rhs;
  }

  /**
   * @return Returns the lhs.
   */
  public Expression getLhs()
  {
    return this.lhs_;
  }

  /**
   * @return Returns the rhs.
   */
  public Expression getRhs()
  {
    return this.rhs_;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.Node#getChildren()
   */
  public List<?> getChildren()
  {
    return null;
  }

  /**
   * @return Returns the loc.
   */
  public SourceLocation getSourceLocation()
  {
    return this.sourceLocation_;
  }

  /**
   * @param loc
   *          The loc to set.
   */
  public void setSourceLocation(SourceLocation loc)
  {
    this.sourceLocation_ = loc;
  }

}

/*
 * $Log$
 */
