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
import org.m3.m3lib.reflect.visitor.ParentNode;
import org.m3.m3lib.scanner.SourceLocation;

/**
 * @author bertl
 * 
 */
public class CaseStatement
  implements Statement
{

  public static class Label
  {
    private Expression value_;

    private Expression upto_;

    /**
     * @param value
     */
    public Label(Expression value)
    {
      super();
      this.value_ = value;
    }

    /**
     * @param value
     * @param upto
     */
    public Label(Expression value, Expression upto)
    {
      super();
      this.value_ = value;
      this.upto_ = upto;
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

    /**
     * @return the upto
     */
    public Expression getUpto()
    {
      return this.upto_;
    }

    /**
     * @param upto the upto to set
     */
    public void setUpto(Expression upto)
    {
      this.upto_ = upto;
    }

  }

  public static class Alternative
    implements ParentNode
  {
    private List<Label>     labels_ = new ArrayList<Label>();

    private List<Statement> stats_  = new ArrayList<Statement>();

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

    public void addLabel(Expression value)
    {
      this.labels_.add(new Label(value));
    }

    public void addLabel(Expression value, Expression upto)
    {
      this.labels_.add(new Label(value, upto));
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

  }

  /**
   * 
   */
  private static final long serialVersionUID = -3936444723214759480L;

  private Expression        value_;

  private List<Alternative> alternatives_    = new ArrayList<Alternative>();

  private List<Statement>   elsePart_        = new ArrayList<Statement>();

  private SourceLocation    sourceLocation_;

  /**
   * @param value
   */
  public CaseStatement(Expression value)
  {
    super();
    this.value_ = value;
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
