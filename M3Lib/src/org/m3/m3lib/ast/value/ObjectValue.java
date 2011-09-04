/**
 * Copyright (c) 2008 B. Laverman. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: bertl
 */

// $Id$

package org.m3.m3lib.ast.value;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.m3.m3lib.ast.IncompatibleTypeException;
import org.m3.m3lib.ast.type.ArrayType;
import org.m3.m3lib.ast.type.BooleanType;
import org.m3.m3lib.ast.type.IntegerType;
import org.m3.m3lib.ast.type.JavaClassType;
import org.m3.m3lib.ast.type.NullType;
import org.m3.m3lib.ast.type.RealType;
import org.m3.m3lib.ast.type.TextType;
import org.m3.m3lib.ast.type.Type;
import org.m3.m3lib.ast.type.UndefinedType;

/**
 * 
 * @author LavermB
 * 
 */
public class ObjectValue
    extends ValueBase
    implements Serializable
{

  /**
   * 
   */
  private static final long       serialVersionUID = -8514800494973199792L;

  public static final ObjectValue UNDEFINED        = new ObjectValue(null,
                                                       UndefinedType
                                                           .getInstance());

  public static final ObjectValue NIL              = new ObjectValue(null,
                                                       NullType.getInstance());

  Object                          value_;

  public ObjectValue()
  {
    super(UndefinedType.getInstance());
    value_ = null;
  }

  public ObjectValue(Object value)
  {
    super((value == null) ? NullType.getInstance() : new JavaClassType(value
        .getClass()));

    value_ = value;

  }

  public ObjectValue(Object value, Type type)
  {
    super(type);

    value_ = value;
  }

  public ObjectValue(long ordinalValue, Type type)
  {
    super(type);

    value_ = new Long(ordinalValue);
  }

  public ObjectValue(long value)
  {
    this(new Long(value), IntegerType.getInstance());
  }

  public ObjectValue(double value)
  {
    this(new Double(value), RealType.getInstance());
  }

  public Value getValue()
  {
    return this;
  }

  public Object toObject()
      throws IncompatibleTypeException
  {
    return value_;
  }

  public List<?> toList()
      throws IncompatibleTypeException
  {
    if (!(value_ instanceof List)) { throw new IncompatibleTypeException(
        "Cannot convert a " + value_.getClass().getName()
            + " to a java.util.List", this); }
    return (List<?>) value_;
  }

  public Map<?,?> toMap()
      throws IncompatibleTypeException
  {
    if (!(value_ instanceof Map)) { throw new IncompatibleTypeException(
        "Cannot convert a " + value_.getClass().getName()
            + " to a java.util.Map", this); }
    return (Map<?,?>) value_;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.value.ValueBase#toBoolean()
   */
  @Override
  public boolean toBoolean()
      throws IncompatibleTypeException
  {
    if (!(this.value_ instanceof Boolean)) { throw new IncompatibleTypeException(
        "Cannot convert object to Boolean", this); }
    return ((Boolean) this.value_).booleanValue();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.value.ValueBase#toDouble()
   */
  @Override
  public double toDouble()
      throws IncompatibleTypeException
  {
    if (!(this.value_ instanceof Number)) { throw new IncompatibleTypeException(
        "Cannot convert object to double", this); }
    return ((Number) this.value_).doubleValue();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.value.ValueBase#toLong()
   */
  @Override
  public long toLong()
      throws IncompatibleTypeException
  {
    if (!(this.value_ instanceof Number)) { throw new IncompatibleTypeException(
        "Cannot convert object to long", this); }
    return ((Number) this.value_).longValue();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.value.ValueBase#toLongOrdinal()
   */
  @Override
  public long toLongOrdinal()
      throws IncompatibleTypeException
  {
    return this.toLong();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.value.ValueBase#toNumber()
   */
  @Override
  public Number toNumber()
      throws IncompatibleTypeException
  {
    if (!(this.value_ instanceof Number)) { throw new IncompatibleTypeException(
        "Cannot convert object to Number", this); }
    return (Number) this.value_;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.value.ValueBase#toString()
   */
  @Override
  public String toString()
  {
    if (value_ == null) {
      if (getType().equals(UndefinedType.getInstance())) {
        return "UNDEFINED";
      }
      else {
        return "NIL";
      }
    }
    return this.value_.toString();
  }

  public static Value fromJavaObject(Object obj, Type type)
  {
    if (obj == null) { return new ObjectValue(obj); }
    if (type == null) {
      type = JavaClassType.fromClass(obj.getClass());
    }

    if (type instanceof IntegerType) { return new IntegerValue(((Number) obj)
        .longValue()); }
    if (type instanceof RealType) { return new RealValue(((Number) obj)
        .doubleValue()); }
    if (type instanceof BooleanType) { return new BooleanValue(((Boolean) obj)
        .booleanValue()); }
    if (type instanceof TextType) { return new TextValue((String) obj); }
    if (type instanceof ArrayType) { return new ArrayValue((ArrayType) type,
        (Object[]) obj); }
    if (obj instanceof Map) { return new MapValue((Map<?,?>) obj); }
    if (obj instanceof List) { return new ListValue((List<?>) obj); }
    if (obj instanceof Collection) { return new CollectionValue(
        (Collection<?>) obj); }
    return new ObjectValue(obj, type);
  }

  public static Value fromJavaObject(Object obj)
  {
    if (obj == null) { return new ObjectValue(obj); }

    return fromJavaObject(obj, JavaClassType.fromClass(obj.getClass()));
  }
}

/*
 * $Log$
 */
