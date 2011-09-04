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
import java.util.List;

import org.m3.m3lib.ast.type.Type;
import org.m3.m3lib.ast.type.UndefinedType;
import org.m3.m3lib.scanner.SourceLocation;

/**
 * 
 * @author LavermB
 * 
 */
public class ApplicationExpression
  implements Expression
{

  public static class Actual
  {

    private String     name_;

    private Expression value_;

    /**
     * @param value
     */
    public Actual(Expression value)
    {
      super();
      this.value_ = value;
    }

    /**
     * @param field
     * @param value
     */
    public Actual(String name, Expression value)
    {
      super();
      this.name_ = name;
      this.value_ = value;
    }

    /**
     * @return the field
     */
    public String getName()
    {
      return this.name_;
    }

    /**
     * @param field the field to set
     */
    public void setName(String name)
    {
      this.name_ = name;
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
  private static final long serialVersionUID = 7243254746556091488L;

  private Expression        fun_;

  private List<Actual>      args_;

  private boolean           constant_        = false;

  private boolean           lvalue_          = false;

  private Type              type_            = UndefinedType.getInstance();

  private SourceLocation    sourceLocation_;

  /** Creates a new instance of ApplicationExpression */
  public ApplicationExpression(Expression fun)
  {
    fun_ = fun;
    args_ = new ArrayList<Actual>();
  }

  public ApplicationExpression(Expression fun, List<Actual> args)
  {
    fun_ = fun;
    args_ = new ArrayList<Actual>(args);
  }

  /**
   * @return Returns the args.
   */
  public List<Actual> getArgs()
  {
    return this.args_;
  }

  /**
   * @param args The args to set.
   */
  public void setArgs(List<Actual> args)
  {
    this.args_ = args;
  }

  public void addActual(Actual actual)
  {
    this.args_.add(actual);
  }

  public void addActual(Expression value)
  {
    this.args_.add(new Actual(value));
  }

  public void addActual(String name, Expression value)
  {
    this.args_.add(new Actual(name, value));
  }

  /**
   * @return Returns the fun.
   */
  public Expression getFun()
  {
    return this.fun_;
  }

  /**
   * @param fun The fun to set.
   */
  public void setFun(Expression fun)
  {
    this.fun_ = fun;
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
   * @param constant The constant to set.
   */
  public void setConstant(boolean constant)
  {
    this.constant_ = constant;
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
   * @param lvalue The lvalue to set.
   */
  public void setLValue(boolean lvalue)
  {
    this.lvalue_ = lvalue;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.Node#getChildren()
   */
  public List<?> getChildren()
  {
    List<?> l = new ArrayList<Actual>(args_);

    return l;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.expr.Expression#getPrecedence()
   */
  public int getPrecedence()
  {
    return 7;
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
