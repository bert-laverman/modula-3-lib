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
public class OutputStatement
    implements Statement, Serializable
{

  /**
   * 
   */
  private static final long serialVersionUID = -8317716480882245691L;

  String                    text_            = null;

  Expression                expr_            = null;

  private SourceLocation    sourceLocation_;

  /** Creates a new instance of OutputStatement */
  public OutputStatement(String text)
  {
    text_ = text;
  }

  public OutputStatement(Expression expr)
  {
    expr_ = expr;
  }

  /**
   * @return Returns the expr.
   */
  public Expression getExpr()
  {
    return this.expr_;
  }

  /**
   * @return Returns the text.
   */
  public String getText()
  {
    return this.text_;
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
