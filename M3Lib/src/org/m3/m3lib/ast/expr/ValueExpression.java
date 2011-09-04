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

import java.util.Collections;
import java.util.List;

import org.m3.m3lib.ast.type.Type;
import org.m3.m3lib.ast.value.Value;
import org.m3.m3lib.scanner.SourceLocation;

/**
 * 
 * @author LavermB
 * 
 */
public class ValueExpression
    implements Expression
{

  /**
   * 
   */
  private static final long serialVersionUID = -1805028098948773856L;

  private Value             value_;

  private SourceLocation    sourceLocation_;

  /** Creates a new instance of ValueExpression */
  public ValueExpression(Value value)
  {
    value_ = value;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.expr.Expression#isLValue()
   */
  public boolean isLValue()
  {
    return false;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.expr.Expression#isConstant()
   */
  public boolean isConstant()
  {
    return true;
  }

  /**
   * @return Returns the value.
   */
  public Value getValue()
  {
    return this.value_;
  }

  /**
   * @param value
   *          The value to set.
   */
  public void setValue(Value value)
  {
    this.value_ = value;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.Node#getChildren()
   */
  public List<?> getChildren()
  {
    return Collections.singletonList(this.value_);
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
   * @see com.syllogic.m3.ast.expr.Expression#getType()
   */
  public Type getType()
  {
    return value_.getType();
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
