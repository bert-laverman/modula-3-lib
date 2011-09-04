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
public class IfStatement
  implements Statement, Serializable
{

  public static class IfThen
  {

    private Expression      cond_     = null;

    private List<Statement> thenPart_ = new ArrayList<Statement>();

    /**
     * 
     */
    public IfThen()
    {
      super();
    }

    /**
     * @param cond
     */
    public IfThen(Expression cond)
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
     * @return the thenPart
     */
    public List<Statement> getThenPart()
    {
      return this.thenPart_;
    }
  }

  /**
   * 
   */
  private static final long serialVersionUID = 706422008575789189L;

  private IfThen            ifThen_          = new IfThen();

  private List<IfThen>      elsifs_          = new ArrayList<IfThen>();

  private List<Statement>   elsePart_        = new ArrayList<Statement>();

  private SourceLocation    sourceLocation_;

  /** Creates a new instance of IfStatement */
  public IfStatement()
  {
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
   * @return the ifThen
   */
  public IfThen getIfThen()
  {
    return this.ifThen_;
  }

  /**
   * @return the elsifs
   */
  public List<IfThen> getElsifs()
  {
    return this.elsifs_;
  }

  public void addElsif(IfThen elsif)
  {
    this.elsifs_.add(elsif);
  }

  /**
   * @return the elsePart
   */
  public List<Statement> getElsePart()
  {
    return this.elsePart_;
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
