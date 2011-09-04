/**
 * Copyright (c) 2008 B. Laverman. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: bertl
 */

// $Id$


package org.m3.m3lib.walkers.eval;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.m3.m3lib.ast.Context;
import org.m3.m3lib.ast.IncompatibleTypeException;
import org.m3.m3lib.ast.ValueOutOfRangeException;
import org.m3.m3lib.ast.expr.IndexExpression;
import org.m3.m3lib.ast.type.ArrayType;
import org.m3.m3lib.ast.type.IntegerType;
import org.m3.m3lib.ast.type.OrdinalType;
import org.m3.m3lib.ast.type.SubrangeType;
import org.m3.m3lib.ast.value.ArrayIndexedValue;
import org.m3.m3lib.ast.value.ArrayValue;
import org.m3.m3lib.ast.value.ListIndexedValue;
import org.m3.m3lib.ast.value.ListValue;
import org.m3.m3lib.ast.value.MapIndexedValue;
import org.m3.m3lib.ast.value.MapValue;
import org.m3.m3lib.ast.value.ObjectValue;
import org.m3.m3lib.ast.value.OrdinalValue;
import org.m3.m3lib.ast.value.Value;
import org.m3.m3lib.reflect.visitor.Visitor;

/**
 * 
 * @author LavermB
 *
 */
public class IndexExpressionEvaluator
    extends ExpressionEvaluator
    implements Visitor
{

  private static final Logger log_ = Logger.getLogger(IndexExpressionEvaluator.class);

  /**
   * 
   */
  public IndexExpressionEvaluator()
  {
    super();
  }

  private Value doIndex(ArrayValue lhs, OrdinalValue rhs)
      throws IncompatibleTypeException, ValueOutOfRangeException
  {
    ArrayType arrType = (ArrayType)lhs.getType();
    OrdinalType indexType = arrType.getIndex();

    if (((indexType == null) && !IntegerType.getInstance().isAssignableFrom(rhs.getType()))
        || ((indexType != null) && !indexType.isAssignableFrom(rhs.getType()))) {
      throw new IncompatibleTypeException("Bad index type", indexType, rhs);
    }

    long index = rhs.toLongOrdinal();
    Object[] arr = lhs.getArrayValue();

    if (indexType != null) {
      indexType.rangeCheck(index);
      index -= indexType.getLwb();
    }
    else {
      if ((index < 0) || (index >= arr.length)) {
        throw new ValueOutOfRangeException(new SubrangeType(IntegerType.getInstance(), 0, arr.length-1), index);
      }
    }

    return new ArrayIndexedValue(lhs, rhs);
  }

  @SuppressWarnings("unchecked")
  private Value doIndex(Map<?,?> map, Value key)
  {
    return new MapIndexedValue((Map<Object, Object>) map, key);
  }

  @SuppressWarnings("unchecked")
  private Value doIndex(List<?> list, Value index)
  {
    return new ListIndexedValue((List<Object>) list, index);
  }

//  private Value doIndex(Object[] lhs, OrdinalValue rhs)
//      throws IncompatibleTypeException, ValueOutOfRangeException
//  {
//    if (!IntegerType.getInstance().isAssignableFrom(rhs.getType())) {
//      throw new IncompatibleTypeException("Cannot index Java array with non-Integer", rhs);
//    }
//    long index = rhs.toLongOrdinal();
//    if ((index < 0) || (index >= lhs.length)) {
//      throw new ValueOutOfRangeException(new SubrangeType(IntegerType.getInstance(), 0, lhs.length-1), index);
//    }
//
//    return ObjectValue.fromJavaObject(lhs [(int)rhs.toLongOrdinal()]);
//  }

  public Value getReturnState(IndexExpression e, Context c)
      throws IncompatibleTypeException, ValueOutOfRangeException
  {
    Value lhs = evalExpression(e.getOpnd1(), c).getValue();
    Value rhs = evalExpression(e.getOpnd2(), c).getValue();

    if (lhs.getType() instanceof ArrayType) {
      if (!(rhs.getType() instanceof OrdinalType)) {
        throw new IncompatibleTypeException("Index must be ordinal", rhs);
      }
      return doIndex((ArrayValue)lhs, (OrdinalValue)rhs);
    }
    if (lhs instanceof MapValue) {
      log_.debug("Indexing from Map with "+rhs.toObject());
      return doIndex(((MapValue)lhs).toMap(), rhs.getValue());
    }
    if (lhs instanceof ListValue) {
      log_.debug("Indexing from List with "+rhs.toString());
      return doIndex(((ListValue)lhs).toList(), rhs.getValue());
    }
    log_.error("Don't know how to index this object: "+lhs.toObject());
    log_.error("Type="+lhs.getType()+", Class="+lhs.getClass().getName());
//    else if (lhs.getType().getTypeClass().equals(T_CLASS)) {
//      return doIndex((Object[])(((ObjectValue)lhs).toObject()), (OrdinalValue)rhs);
//    }
    return ObjectValue.UNDEFINED;
  }
}


/*
 * $Log$
 */
