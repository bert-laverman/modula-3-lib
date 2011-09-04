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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.m3.m3lib.ast.type.Type;
import org.m3.m3lib.ast.type.UndefinedType;
import org.m3.m3lib.scanner.SourceLocation;

/**
 * @author bertl
 * 
 */
public class NameExpression
  implements Expression
{

  /**
   * 
   */
  private static final long  serialVersionUID = 5455459531714533793L;

  private List<IdExpression> names_;

  private boolean           lvalue_          = true;

  private boolean           constant_        = false;

  private Type              type_            = UndefinedType.getInstance();

  private SourceLocation    sourceLocation_;

  public NameExpression()
  {
    names_ = new ArrayList<IdExpression>();
  }

  public NameExpression(IdExpression... names)
  {
    names_ = Arrays.asList(names);
  }

  /* (non-Javadoc)
   * @see org.m3.m3lib.ast.expr.Expression#getPrecedence()
   */

  @Override
  public int getPrecedence()
  {
    return 8;
  }

  /**
   * @return the names
   */
  public List<IdExpression> getNames()
  {
    return this.names_;
  }

  /**
   * @param name The name to add
   */
  public void addName(IdExpression name)
  {
    this.names_.add(name);
  }

  /**
   * @param names the names to set
   */
  public void setNames(List<IdExpression> names)
  {
    this.names_ = names;
  }

  /**
   * @return the lvalue
   */
  public boolean isLvalue()
  {
    return this.lvalue_;
  }

  /**
   * @param lvalue the lvalue to set
   */
  public void setLvalue(boolean lvalue)
  {
    this.lvalue_ = lvalue;
  }

  /* (non-Javadoc)
   * @see org.m3.m3lib.ast.expr.Expression#isConstant()
   */
  @Override
  public boolean isConstant()
  {
    return this.constant_;
  }

  /**
   * @param constant the constant to set
   */
  public void setConstant(boolean constant)
  {
    this.constant_ = constant;
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
   * @see org.m3.m3lib.ast.expr.Expression#isLValue()
   */
  @Override
  public boolean isLValue()
  {
    return this.lvalue_;
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
   * @see org.m3.m3lib.ast.Node#getSourceLocation()
   */
  @Override
  public SourceLocation getSourceLocation()
  {
    return this.sourceLocation_;
  }

  /* (non-Javadoc)
   * @see org.m3.m3lib.reflect.visitor.ParentNode#getChildren()
   */
  @Override
  public List<?> getChildren()
  {
    return this.names_;
  }

  /**
   * @return
   */
  public List<String> getIds()
  {
    List<String> ids = new ArrayList<String>();
    for (IdExpression id: this.names_) {
      ids.add(id.getId());
    }
    return ids;
  }

}

/*
 * $Log$
 */
