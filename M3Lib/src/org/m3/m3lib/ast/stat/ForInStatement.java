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
public class ForInStatement
    implements Statement, Serializable
{

  /**
   * 
   */
  private static final long serialVersionUID = 4171141264704303716L;

  private String            id_;

  private Expression        set_;

  private BlockStatement    body_;

  private SourceLocation    sourceLocation_;

  /** Creates a new instance of ForInStatement */
  public ForInStatement(String id, Expression set, BlockStatement body)
  {
    id_ = id;
    set_ = set;
    body_ = body;
  }

  /**
   * @return Returns the body.
   */
  public Statement getBody()
  {
    return this.body_;
  }

  /**
   * @return Returns the id.
   */
  public String getId()
  {
    return this.id_;
  }

  /**
   * @return Returns the set.
   */
  public Expression getSet()
  {
    return this.set_;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.Node#getChildren()
   */
  public List<?> getChildren()
  {
    return body_.getStats();
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
