/**
 * Copyright (c) 2008 B. Laverman. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: bertl
 */

// $Id$
package org.m3.m3lib.ast.expr;

import java.util.List;

import org.m3.m3lib.ast.type.Type;
import org.m3.m3lib.scanner.SourceLocation;

/**
 * @author bertl
 * 
 */
public class TypeExpression
  implements Expression
{

  /**
   * 
   */
  private static final long serialVersionUID = 4755387628991899208L;

  private Type              type_;

  private SourceLocation    sourceLocation_  = null;

  /**
   * @param type
   */
  public TypeExpression(Type type)
  {
    super();
    this.type_ = type;
  }

  /* (non-Javadoc)
   * @see org.m3.m3lib.ast.expr.Expression#getPrecedence()
   */
  @Override
  public int getPrecedence()
  {
    return 8;
  }

  /* (non-Javadoc)
   * @see org.m3.m3lib.ast.expr.Expression#getType()
   */
  @Override
  public Type getType()
  {
    return this.type_;
  }

  /**
   * @param type the type to set
   */
  public void setType(Type type)
  {
    this.type_ = type;
  }

  /* (non-Javadoc)
   * @see org.m3.m3lib.ast.expr.Expression#isConstant()
   */
  @Override
  public boolean isConstant()
  {
    return true;
  }

  /* (non-Javadoc)
   * @see org.m3.m3lib.ast.expr.Expression#isLValue()
   */
  @Override
  public boolean isLValue()
  {
    return false;
  }

  /* (non-Javadoc)
   * @see org.m3.m3lib.ast.Node#getSourceLocation()
   */
  @Override
  public SourceLocation getSourceLocation()
  {
    return this.sourceLocation_;
  }

  /* (non-Javadoc)
   * @see org.m3.m3lib.ast.expr.Expression#setSourceLocation(org.m3.m3lib.scanner.SourceLocation)
   */
  @Override
  public void setSourceLocation(SourceLocation loc)
  {
    this.sourceLocation_ = loc;
  }

  /* (non-Javadoc)
   * @see org.m3.m3lib.reflect.visitor.ParentNode#getChildren()
   */
  @Override
  public List<?> getChildren()
  {
    return null;
  }

}

/*
 * $Log$
 */
