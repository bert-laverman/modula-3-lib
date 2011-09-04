/**
 * Copyright (c) 2008 B. Laverman. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: bertl
 */

// $Id$

package org.m3.m3lib.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.apache.log4j.Logger;


/**
 * 
 * @author LavermB
 * 
 */
public class Reflections
{

  public static final class MethodComparator
      implements Comparator<Method>
  {
    public int compare(Method o1, Method o2)
    {
      Method m1 = (Method) o1, m2 = (Method) o2;
      Class<?> p1[] = m1.getParameterTypes();
      Class<?> p2[] = m2.getParameterTypes();

      int i = 0;
      while (i < p1.length) {
        if (i >= p2.length) { // p1 is more detailed
          return -1;
        }

        Class<?> t1 = forceWrappedPrimitives(p1[i]);
        Class<?> t2 = forceWrappedPrimitives(p2[i]);
        boolean m1_from_m2 = t1.isAssignableFrom(t2);
        boolean m2_from_m1 = t2.isAssignableFrom(t1);

        if (!m1_from_m2) {
          if (!m2_from_m1) { // Incompatible classes! Plan B: compare names
            return t1.getName().compareTo(t2.getName());
          }
          // p1 [i] is subclass of p2 [i]
          return -1;
        }
        if (!m2_from_m1) { // p2 [i] is subclass of p1 [i]
          return 1;
        }
        // p1 [i] and p2 [i] are equal
        i += 1;
      }
      // i >= p1.length
      // * if i < p2.length, p2 is more detailed
      // otherwise both are exhausted, so they are equal
      return (i < p2.length) ? 1 : 0;
    }
  }

  private static final Logger                           log_       = Logger
                                                                       .getLogger(Reflections.class);

  private static Map<String, Map<String, List<Method>>> methodMap_ = new HashMap<String, Map<String, List<Method>>>();

  /**
   * 
   */
  private Reflections()
  {
    super();
  }

  public static Field getField(Class<?> target, String name)
  {
    Field r = null;

    try {
      r = target.getField(name);
    }
    catch (NoSuchFieldException e) {
      log_.debug("no field called \"" + name + "\" in class \""
          + target.getClass().getName() + "\", returning null", e);
    }

    return r;
  }

  public static Class<?> forceWrappedPrimitives(Class<?> c)
  {
    if (c.isPrimitive()) {
      if (c.equals(boolean.class)) {
        return Boolean.class;
      }
      else if (c.equals(char.class)) {
        return Character.class;
      }
      else if (c.equals(byte.class)) {
        return Byte.class;
      }
      else if (c.equals(short.class)) {
        return Short.class;
      }
      else if (c.equals(int.class)) {
        return Integer.class;
      }
      else if (c.equals(long.class)) {
        return Long.class;
      }
      else if (c.equals(float.class)) {
        return Float.class;
      }
      else if (c.equals(double.class)) {
        return Double.class;
      }
      else if (c.equals(void.class)) { return Void.class; }
    }
    return c;
  }

  private static void fillMethodMap(Map<String, List<Method>> m, Class<?> target)
  {
    Method[] allMethods = target.getMethods();

    for (int i = 0; i < allMethods.length; i++) {
      final String name = allMethods[i].getName();
      List<Method> l = m.get(name);
      if (l == null) {
        l = new ArrayList<Method>();
        m.put(name, l);
      }
      l.add(allMethods[i]);
    }
  }

  private static List<Method> getMethods(Class<?> target, String name)
  {
    final String cn = target.getName();

    Map<String, List<Method>> m = methodMap_.get(cn);
    if (m == null) {
      m = new HashMap<String, List<Method>>();
      methodMap_.put(cn, m);
      fillMethodMap(m, target);
    }

    return m.get(name);
  }

  private static boolean isAssignableFrom(Class<?>[] formalTypes,
      Class<?>[] actualTypes)
  {
    for (int i = 0; i < formalTypes.length; i++) {
      if (i >= actualTypes.length) { return false; }
      if (!forceWrappedPrimitives(formalTypes[i]).isAssignableFrom(
          forceWrappedPrimitives(actualTypes[i]))) { return false; }
    }
    return true;
  }

  public static Field getField(Object target, String name)
  {
    return getField(target.getClass(), name);
  }

  public static boolean hasMethodWithName(Class<?> target, String name)
  {
    List<Method> methods = getMethods(target, name);

    return methods != null;
  }

  public static boolean hasStaticMethodWithName(Class<?> target, String name)
  {
    List<Method> methods = getMethods(target, name);

    if (methods == null) { return false; }
    for (Method m : methods) {
      if (Modifier.isStatic(m.getModifiers())) { return true; }
    }
    return false;
  }

  public static boolean hasNonStaticMethodWithName(Class<?> target, String name)
  {
    List<Method> methods = getMethods(target, name);

    if (methods == null) { return false; }
    for (Method m : methods) {
      if (!Modifier.isStatic(m.getModifiers())) { return true; }
    }
    return false;
  }

  private static List<Method> findAllMethods(Class<?> target, String name,
      boolean isStatic, Class<?>... parmTypes)
  {
    Set<Method> r = new TreeSet<Method>(new Reflections.MethodComparator());

    List<?> methods = getMethods(target, name);
    if (methods == null) { return null; }
    for (int i = 0; i < methods.size(); i++) {
      Method m = (Method) methods.get(i);
      if ((Modifier.isStatic(m.getModifiers()) == isStatic)
          && isAssignableFrom(m.getParameterTypes(), parmTypes)) {
        r.add(m);
      }
    }
    return new ArrayList<Method>(r);
  }

  public static Method findStaticMethod(Class<?> target, String name,
      Class<?>... parms)
  {
    List<?> r = findAllMethods(target, name, true, parms);
    if ((r == null) || (r.size() == 0)) { return null; }

    return (Method) r.get(0);
  }

  public static Method findNonStaticMethod(Class<?> target, String name,
      Class<?>... parms)
  {
    List<?> r = findAllMethods(target, name, false, parms);
    if ((r == null) || (r.size() == 0)) { return null; }

    return (Method) r.get(0);
  }

  private static void fixPrimitiveArgs(Method m, Object[] args)
  {
    Class<?>[] argTypes = m.getParameterTypes();

    for (int i = 0; i < args.length; i++) {
      if (!argTypes [i].isPrimitive()) {
        continue;
      }

      if (args[i] == null) {
        if (argTypes[i].equals(boolean.class)) {
          args[i] = new Boolean(false);
        }
        else {
          args[i] = new Integer(0);
        }
        continue;
      }
      if (argTypes [i].equals(byte.class) || argTypes [i].equals(Byte.class)) {
        args [i] = new Byte(((Number)args [i]).byteValue());
      }
      else if (argTypes [i].equals(short.class) || argTypes [i].equals(Short.class)) {
        args [i] = new Short(((Number)args [i]).shortValue());
      }
      else if (argTypes [i].equals(int.class) || argTypes [i].equals(Integer.class)) {
        args [i] = new Integer(((Number)args [i]).intValue());
      }
      else if (argTypes [i].equals(long.class) || argTypes [i].equals(Long.class)) {
        args [i] = new Long(((Number)args [i]).longValue());
      }
      else if (argTypes [i].equals(float.class) || argTypes [i].equals(Float.class)) {
        args [i] = new Float(((Number)args [i]).floatValue());
      }
      else if (argTypes [i].equals(double.class) || argTypes [i].equals(Double.class)) {
        args [i] = new Double(((Number)args [i]).doubleValue());
      }
    }
  }

  public static void checkArgs(Method m, Object[] args)
  {
    Class<?>[] argTypes = m.getParameterTypes();

    for (int i = 0; i < args.length; i++) {
      Class<?> checkClass = forceWrappedPrimitives(argTypes[i]);
      if (args[i] == null) {
        continue;
      }
      if (!checkClass.isInstance(args[i])) {
        throw new ClassCastException(
          "Trying to pass " + args[i]+"["+args [i].getClass().getName()+"] as argument " + i + " to " + m);
      }
    }
  }

  public static void callProc(Object target, Method m, Object... args)
      throws Throwable
  {
    fixPrimitiveArgs(m, args);
    checkArgs(m, args);
    try {
      m.invoke(target, args);
    }
    catch (IllegalAccessException iae) {
      log_.warn("Exception caught while calling \"" + m.toString() + "\"", iae);
    }
    catch (InvocationTargetException ite) {
      log_.warn("Exception caught while calling \"" + m.toString() + "\"", ite);
      throw ite.getTargetException();
    }
    catch (IllegalArgumentException iae) {
      log_.warn("Exception caught while calling \"" + m.toString() + "\"", iae);
      throw new ClassCastException(iae.getMessage());
    }
  }

  public static Object callFunc(Object target, Method m, Object... args)
      throws Throwable
  {
    fixPrimitiveArgs(m, args);
    checkArgs(m, args);
    try {
      log_.debug("Going to call Method.invoke: target=" + target + ", args="
          + args + ", m=" + m);
      return m.invoke(target, args);
    }
    catch (IllegalAccessException iae) {
      log_.warn("Exception caught while calling \"" + m.toString() + "\"", iae);
    }
    catch (InvocationTargetException ite) {
      log_.warn("Exception caught while calling \"" + m.toString() + "\"", ite);
      throw ite.getTargetException();
    }
    catch (IllegalArgumentException iae) {
      log_.warn("Exception caught while calling \"" + m.toString() + "\"", iae);
      throw new ClassCastException(iae.getMessage());
    }
    return null;
  }

  public static boolean isProc(Method m)
  {
    Class<?> returnType = m.getReturnType();

    return (Void.class.equals(returnType) || void.class.equals(returnType));
  }

  public static Class<?> getClassOf(Object o)
  {
    return (o != null) ? o.getClass() : Object.class;
  }

  public static void doCallProc(Object target, Method m, Object... parms)
      throws Throwable
  {
    if (parms.length > 4) {
      log_.warn("Reflections.doCallProc(): Ignoring parameters in excess of 4");
    }
    Class<?>[] argTypes = m.getParameterTypes();

    switch (argTypes.length) {
    case 0:
      callProc(target, m);
      break;
    case 1:
      callProc(target, m, parms[0]);
      break;
    case 2:
      callProc(target, m, parms[0], parms[1]);
      break;
    case 3:
      callProc(target, m, parms[0], parms[1], parms[2]);
      break;
    default:
      callProc(target, m, parms[0], parms[1], parms[2], parms[3]);
      break;
    }
  }

  public static Class<?>[] getClasses(Object... objects)
  {
    Class<?>[] r = new Class<?>[objects.length];

    for (int i = 0; i < objects.length; i++) {
      r[i] = getClassOf(objects[i]);
    }
    return r;
  }

  public static void doCallProc(Object target, String methodName,
      Object... parms)
      throws Throwable
  {
    if (parms.length > 4) {
      log_.warn("Reflections.doCallProc(): Ignoring parameters in excess of 4");
    }
    Class<?>[] parmTypes = getClasses(parms);

    Method m = null;
    if (parmTypes.length >= 4) {
      m = findNonStaticMethod(target.getClass(), methodName, parmTypes[0],
          parmTypes[1], parmTypes[2], parmTypes[3]);
    }
    if ((m == null) && (parmTypes.length >= 3)) {
      m = findNonStaticMethod(target.getClass(), methodName, parmTypes[0],
          parmTypes[1], parmTypes[2]);
    }
    if ((m == null) && (parmTypes.length >= 2)) {
      m = findNonStaticMethod(target.getClass(), methodName, parmTypes[0],
          parmTypes[1]);
    }
    if ((m == null) && (parmTypes.length >= 1)) {
      m = findNonStaticMethod(target.getClass(), methodName, parmTypes[0]);
    }
    if (m == null) {
      m = findNonStaticMethod(target.getClass(), methodName);
    }
    if (m != null) {
      doCallProc(target, m, parms);
    }
  }

  public static Object doCallFunc(Object target, Method m, Object... parms)
      throws Throwable
  {
    if (parms.length > 4) {
      log_.warn("Reflections.doCallFunc(): Ignoring parameters in excess of 4");
    }
    Class<?>[] argTypes = m.getParameterTypes();

    switch (argTypes.length) {
    case 0:
      return callFunc(target, m);
    case 1:
      return callFunc(target, m, parms[0]);
    case 2:
      return callFunc(target, m, parms[0], parms[1]);
    case 3:
      return callFunc(target, m, parms[0], parms[1], parms[2]);
    default:
      return callFunc(target, m, parms[0], parms[1], parms[2], parms[3]);
    }
  }

  public static Object doCallFunc(Object target, String methodName,
      Object... parms)
      throws Throwable
  {
    if (parms.length > 4) {
      log_.warn("Reflections.doCallFunc(): Ignoring parameters in excess of 4");
    }
    Class<?>[] parmTypes = getClasses(parms);

    Method m = null;
    if (parmTypes.length >= 4) {
      m = findNonStaticMethod(target.getClass(), methodName, parmTypes[0],
          parmTypes[1], parmTypes[2], parmTypes[3]);
    }
    if ((m == null) && (parmTypes.length >= 3)) {
      m = findNonStaticMethod(target.getClass(), methodName, parmTypes[0],
          parmTypes[1], parmTypes[2]);
    }
    if ((m == null) && (parmTypes.length >= 2)) {
      m = findNonStaticMethod(target.getClass(), methodName, parmTypes[0],
          parmTypes[1]);
    }
    if ((m == null) && (parmTypes.length >= 1)) {
      m = findNonStaticMethod(target.getClass(), methodName, parmTypes[0]);
    }
    if (m == null) {
      m = findNonStaticMethod(target.getClass(), methodName);
    }
    if (m != null) { return doCallFunc(target, m, parms); }
    return null;
  }

  private static Constructor<?> findConstructor(Class<?> target, Class<?>... argTypes)
      throws NoSuchMethodException
  {
    log_.debug("Searching for matching constructor in " + target.getName());
    for (Constructor<?> c : target.getConstructors()) {
      log_.debug("  Checking " + c);
      Class<?>[] formalTypes = c.getParameterTypes();
      if (argTypes.length != formalTypes.length) {
        continue;
      }
      for (int i = 0; i < formalTypes.length; i++) {
        log_.debug("    " + i + ": " + formalTypes[i].getName() + " from "
            + argTypes[i].getName());
        if (!forceWrappedPrimitives(formalTypes[i]).isAssignableFrom(
            forceWrappedPrimitives(argTypes[i]))) {
          continue;
        }
      }
      return c;
    }

    log_.warn("Failed to find matching constructor in " + target.getName());
    throw new NoSuchMethodException(
        "Cannot find matching constructor in class \"" + target.getName()
            + "\"");
  }

  public static Object construct(Class<?> target, Object... args)
      throws Exception
  {
    log_.debug("Constructing a " + target.getName());
    Class<?>[] argTypes = getClasses(args);

    Constructor<?> cons = findConstructor(target, argTypes);

    log_.debug("Using constructor " + cons);
    Object result = cons.newInstance(args);

    log_.debug("Constructed " + result);
    return result;
  }

  public static List<Property> getProperties(Object target)
  {
    Field[] fields = target.getClass().getDeclaredFields();
    List<Property> r = new ArrayList<Property>();

    for (int i = 0; i < fields.length; i++) {
      String name = fields [i].getName();
      if (name.endsWith("_")) { name = name.substring(0, name.length()-1); }
      Property prop = new Property(target, name);
      try {
        if (prop.getPropertyClass() != null) {
          r.add(prop);
        }
      }
      catch (NullPointerException e) {
        // Means no getter method found.
        // TODO find a better way to detect this
        /*SKIP*/
      }
    }

    return r;
  }

}

/*
 * $Log$
 */
