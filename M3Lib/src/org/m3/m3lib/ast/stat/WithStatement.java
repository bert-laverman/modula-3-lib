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
public class WithStatement
  implements Statement
{

  public static class Binding
  {
    private String     id_;

    private Expression value_;

    /**
     * 
     */
    public Binding()
    {
      super();
    }

    /**
     * @param id
     * @param value
     */
    public Binding(String id, Expression value)
    {
      super();
      this.id_ = id;
      this.value_ = value;
    }

    /**
     * @return the id
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
     * @return the value
     */
    public Expression getValue()
    {
      return this.value_;
    }

    /**
     * @param value the value to set
     */
    public void setValue(Expression value)
    {
      this.value_ = value;
    }

  }

  /**
   * 
   */
  private static final long serialVersionUID = -3091846050866945016L;

  private List<Binding>     bindings_        = new ArrayList<Binding>();

  private List<Statement>   body_            = new ArrayList<Statement>();

  private SourceLocation    sourceLocation_  = null;

  /**
   * 
   */
  public WithStatement()
  {
    super();
  }

  /**
   * @return the bindings
   */
  public List<Binding> getBindings()
  {
    return this.bindings_;
  }

  public void addBinding(Binding b)
  {
    this.bindings_.add(b);
  }

  public void addBinding(String id, Expression value)
  {
    addBinding(new Binding(id, value));
  }

  /**
   * @return the body
   */
  public List<Statement> getBody()
  {
    return this.body_;
  }

  public void addBodyStat(Statement stat)
  {
    this.body_.add(stat);
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
    return getBody();
  }

}

/*
 * $Log$
 */
