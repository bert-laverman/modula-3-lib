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

import java.util.Arrays;
import java.util.List;

import org.m3.m3lib.M3Token;
import org.m3.m3lib.ast.type.Type;
import org.m3.m3lib.ast.type.UndefinedType;
import org.m3.m3lib.ast.value.ObjectValue;
import org.m3.m3lib.scanner.SourceLocation;

/**
 * 
 * @author LavermB
 * 
 */
public abstract class DyadicExpression
  implements Expression
{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private SourceLocation sourceLocation_;

  private Expression     opnd1_;

  private Expression     opnd2_;

  private M3Token        oper_;

  private ObjectValue    value_;

  private Type           type_ = UndefinedType.getInstance();

  /**
   * 
   */
  public DyadicExpression()
  {
    super();
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
    return false;
  }

  /**
   * @return Returns the oper.
   */
  public M3Token getOper()
  {
    return this.oper_;
  }

  /**
   * @param oper The oper to set.
   */
  public void setOper(M3Token oper)
  {
    this.oper_ = oper;
  }

  /**
   * @return Returns the opnd1.
   */
  public Expression getOpnd1()
  {
    return this.opnd1_;
  }

  /**
   * @param opnd1 The opnd1 to set.
   */
  public void setOpnd1(Expression opnd1)
  {
    this.opnd1_ = opnd1;
  }

  /**
   * @return Returns the opnd2.
   */
  public Expression getOpnd2()
  {
    return this.opnd2_;
  }

  /**
   * @param opnd2 The opnd2 to set.
   */
  public void setOpnd2(Expression opnd2)
  {
    this.opnd2_ = opnd2;
  }

  /**
   * @return Returns the value.
   */
  public ObjectValue getValue()
  {
    return this.value_;
  }

  /**
   * @param value The value to set.
   */
  public void setValue(ObjectValue value)
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
    return Arrays.asList(new Object[] { opnd1_, opnd2_ });
  }

  /**
   * @return Returns the type.
   */
  public Type getType()
  {
    return this.type_;
  }

  /**
   * @param type The type to set.
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
