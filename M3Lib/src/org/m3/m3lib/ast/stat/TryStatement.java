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

import org.m3.m3lib.ast.expr.NameExpression;
import org.m3.m3lib.reflect.visitor.ParentNode;
import org.m3.m3lib.scanner.SourceLocation;

/**
 * @author bertl
 * 
 */
public class TryStatement
  implements Statement
{

  public static class Label
  {
    private NameExpression name_;

    /**
     * @param name
     */
    public Label(NameExpression name)
    {
      super();
      this.name_ = name;
    }

    /**
     * @return the value
     */
    public NameExpression getName()
    {
      return this.name_;
    }

    /**
     * @param name the value to set
     */
    public void setName(NameExpression name)
    {
      this.name_ = name;
    }

  }

  public static class Alternative
    implements ParentNode
  {
    private List<Label>     labels_ = new ArrayList<Label>();

    private List<Statement> stats_  = new ArrayList<Statement>();

    private String          id_;

    /**
     * @return the labels
     */
    public List<Label> getLabels()
    {
      return this.labels_;
    }

    public void addLabel(Label label)
    {
      this.labels_.add(label);
    }

    public void addLabel(NameExpression name)
    {
      this.labels_.add(new Label(name));
    }

    /**
     * @return the stats
     */
    public List<Statement> getStats()
    {
      return this.stats_;
    }

    public void addStatement(Statement stat)
    {
      this.stats_.add(stat);
    }

    /* (non-Javadoc)
     * @see org.m3.m3lib.reflect.visitor.ParentNode#getChildren()
     */
    @Override
    public List<?> getChildren()
    {
      return this.stats_;
    }

    /**
     * @return the upto
     */
    public String getId()
    {
      return this.id_;
    }

    /**
     * @param id the upto to set
     */
    public void setId(String id)
    {
      this.id_ = id;
    }

  }

  /**
   * 
   */
  private static final long serialVersionUID = -3936444723214759480L;

  private List<Statement>   body_            = new ArrayList<Statement>();

  private List<Alternative> alternatives_    = new ArrayList<Alternative>();

  private List<Statement>   elsePart_        = new ArrayList<Statement>();

  private List<Statement>   finallyPart_     = new ArrayList<Statement>();

  private SourceLocation    sourceLocation_;

  /**
   * @param value
   */
  public TryStatement()
  {
    super();
  }

  /**
   * @return the body
   */
  public List<Statement> getBody()
  {
    return this.body_;
  }

  /**
   * @return the alternatives
   */
  public List<Alternative> getAlternatives()
  {
    return this.alternatives_;
  }

  public void addAlternative(Alternative alt)
  {
    this.alternatives_.add(alt);
  }

  /**
   * @return the elsePart
   */
  public List<Statement> getElsePart()
  {
    return this.elsePart_;
  }

  public void addElseStat(Statement stat)
  {
    this.elsePart_.add(stat);
  }

  /**
   * @return the finallyPart
   */
  public List<Statement> getFinallyPart()
  {
    return this.finallyPart_;
  }

  public void addFinallyStat(Statement stat)
  {
    this.finallyPart_.add(stat);
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
    return this.alternatives_;
  }

}

/*
 * $Log$
 */
