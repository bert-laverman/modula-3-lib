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
import org.m3.m3lib.scanner.SourceLocation;

/**
 * 
 * @author LavermB
 * 
 */
public class ConstructorExpression
  implements Expression
{

  public static class ConsElt
  {
    private Expression value_;

    /**
     * @param value
     */
    public ConsElt(Expression value)
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

  }

  public static class SetElt
    extends ConsElt
  {

    private Expression upto_;

    /**
     * @param value
     * @param upto
     */
    public SetElt(Expression value, Expression upto)
    {
      super(value);
      this.upto_ = upto;
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

  public static class RecordElt
    extends ConsElt
  {

    private String field_;

    /**
     * @param value
     * @param field
     */
    public RecordElt(Expression value, String field)
    {
      super(value);
      this.field_ = field;
    }

    /**
     * @return the field
     */
    public String getField()
    {
      return this.field_;
    }

    /**
     * @param field the field to set
     */
    public void setField(String field)
    {
      this.field_ = field;
    }

  }

  /**
   * 
   */
  private static final long serialVersionUID = -1187517934068648000L;

  //  private static final Logger log_             = Logger
  //                                                   .getLogger(ConstructorExpression.class);

  private Type              type_;

  private List<ConsElt>     values_          = new ArrayList<ConsElt>();

  private boolean           filledOut_       = false;

  private SourceLocation    sourceLocation_  = null;

  /**
   * @param type
   */
  public ConstructorExpression(Type type)
  {
    super();
    this.type_ = type;
  }

  /**
   * 
   */
  public ConstructorExpression(Type type, List<ConsElt> values)
  {
    super();

    this.type_ = type;
    this.values_ = values;
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

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.expr.Expression#getType()
   */
  public Type getType()
  {
    return this.type_;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.expr.Expression#setSourceLocation(com.syllogic.m3.scanner.SourceLocation)
   */
  public void setSourceLocation(SourceLocation loc)
  {
    this.sourceLocation_ = loc;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.Node#getSourceLocation()
   */
  public SourceLocation getSourceLocation()
  {
    return this.sourceLocation_;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.visitor.ParentNode#getChildren()
   */
  public List<?> getChildren()
  {
    return this.values_;
  }

  /**
   * @return Returns the values.
   */
  public List<ConsElt> getValues()
  {
    return this.values_;
  }

  public void addValue(ConsElt elt)
  {
    this.values_.add(elt);
  }

  public void addValue(Expression e)
  {
    addValue(new ConsElt(e));
  }

  public void addValue(Expression from, Expression upto)
  {
    addValue(new SetElt(from, upto));
  }

  public void addValue(String name, Expression value)
  {
    addValue(new RecordElt(value, name));
  }

  /**
   * @return the filledOut
   */
  public boolean isFilledOut()
  {
    return this.filledOut_;
  }

  /**
   * @param filledOut the filledOut to set
   */
  public void setFilledOut(boolean filledOut)
  {
    this.filledOut_ = filledOut;
  }

}

/*
 * $Log$
 */
