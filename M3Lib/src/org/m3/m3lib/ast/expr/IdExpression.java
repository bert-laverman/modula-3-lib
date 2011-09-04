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
import org.m3.m3lib.ast.type.UndefinedType;
import org.m3.m3lib.scanner.SourceLocation;

/**
 * 
 * @author LavermB
 * 
 */
public class IdExpression
    implements Expression
{

  /**
   * 
   */
  private static final long serialVersionUID = 5595839060629883493L;

  private String            id_;

  private boolean           lvalue_          = true;

  private boolean           constant_        = false;

  private Type              type_            = UndefinedType.getInstance();

  private SourceLocation    sourceLocation_;

  /** Creates a new instance of IdExpression */
  public IdExpression(String id)
  {
    id_ = id;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.expr.Expression#getPrecedence()
   */
  public int getPrecedence()
  {
    return 8;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.expr.Expression#isLValue()
   */
  public boolean isLValue()
  {
    return lvalue_;
  }

  /**
   * @param lvalue
   *          The lvalue to set.
   */
  public void setLValue(boolean lvalue)
  {
    this.lvalue_ = lvalue;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.expr.Expression#isConstant()
   */
  public boolean isConstant()
  {
    return constant_;
  }

  /**
   * @param constant
   *          The constant to set.
   */
  public void setConstant(boolean constant)
  {
    this.constant_ = constant;
  }

  /**
   * @return Returns the id.
   */
  public String getId()
  {
    return this.id_;
  }

  /**
   * @param id
   *          The id to set.
   */
  public void setId(String id)
  {
    this.id_ = id;
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
   * @return Returns the type.
   */
  public Type getType()
  {
    return this.type_;
  }

  /**
   * @param type
   *          The type to set.
   */
  public void setType(Type type)
  {
    this.type_ = type;
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
