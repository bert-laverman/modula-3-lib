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
import java.util.ArrayList;
import java.util.List;

import org.m3.m3lib.ast.expr.Expression;
import org.m3.m3lib.scanner.SourceLocation;

/**
 * 
 * @author LavermB
 * 
 */
public class ForStatement
  implements Statement, Serializable
{

  /**
   * 
   */
  private static final long serialVersionUID = 1030801965664330095L;

  private String            id_;

  private Expression        lwb_;

  private Expression        upb_;

  private Expression        by_              = null;

  private List<Statement>   doPart_          = new ArrayList<Statement>();

  private SourceLocation    sourceLocation_;

  /** Creates a new instance of ForStatement */
  public ForStatement()
  {
  }

  /**
   * @return Returns the body.
   */
  public List<Statement> getDoPart()
  {
    return this.doPart_;
  }

  /**
   * @return Returns the id.
   */
  public String getId()
  {
    return this.id_;
  }

  /**
   * @param id the id to set
   */
  public void setId(String id)
  {
    this.id_ = id;
  }

  /**
   * @return Returns the lwb.
   */
  public Expression getLwb()
  {
    return this.lwb_;
  }

  /**
   * @param lwb the lwb to set
   */
  public void setLwb(Expression lwb)
  {
    this.lwb_ = lwb;
  }

  /**
   * @return Returns the upb.
   */
  public Expression getUpb()
  {
    return this.upb_;
  }

  /**
   * @param upb the upb to set
   */
  public void setUpb(Expression upb)
  {
    this.upb_ = upb;
  }

  /**
   * @return the by
   */
  public Expression getBy()
  {
    return this.by_;
  }

  /**
   * @param by the by to set
   */
  public void setBy(Expression by)
  {
    this.by_ = by;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.Node#getChildren()
   */
  public List<?> getChildren()
  {
    return doPart_;
  }

  /**
   * @return Returns the loc.
   */
  public SourceLocation getSourceLocation()
  {
    return this.sourceLocation_;
  }

  /**
   * @param loc The loc to set.
   */
  public void setSourceLocation(SourceLocation loc)
  {
    this.sourceLocation_ = loc;
  }

}

/*
 * $Log$
 */
