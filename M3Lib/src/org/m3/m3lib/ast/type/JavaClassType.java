/**
 * Copyright (c) 2008 B. Laverman. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: bertl
 */

// $Id$
package org.m3.m3lib.ast.type;

/**
 * 
 * @author LavermB
 * 
 */
public class JavaClassType
  extends RefAnyType
{

  /**
   * 
   */
  private static final long serialVersionUID = -415235083906474340L;

  private Class<?>          clazz_;

  /**
   * 
   */
  public JavaClassType(Class<?> clazz)
  {
    super();

    this.clazz_ = clazz;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.type.Type#isAssignable(com.syllogic.m3.ast.Value)
   */
  public boolean isAssignableFrom(Type type)
  {
    return (type instanceof JavaClassType) &&
           getClazz().isAssignableFrom(((JavaClassType) type).getClazz());
  }

  /**
   * @return Returns the clazz.
   */
  public Class<?> getClazz()
  {
    return this.clazz_;
  }

  public static Type fromClass(Class<?> clazz)
  {
    if (clazz.isArray()) {
      return ArrayType.fromClass(clazz);
    }
    if (byte.class.equals(clazz) || Byte.class.equals(clazz) ||
        short.class.equals(clazz) || Short.class.equals(clazz) ||
        int.class.equals(clazz) || Integer.class.equals(clazz) ||
        long.class.equals(clazz) || Long.class.equals(clazz))
    {
      return IntegerType.getInstance();
    }
    if (float.class.equals(clazz) || Float.class.equals(clazz) ||
        double.class.equals(clazz) || Double.class.equals(clazz))
    {
      return RealType.getInstance();
    }
    if (boolean.class.equals(clazz) || Boolean.class.equals(clazz)) {
      return BooleanType.getInstance();
    }
    if (String.class.equals(clazz)) {
      return TextType.getInstance();
    }
    return new JavaClassType(clazz);
  }

}

/*
 * $Log$
 */
