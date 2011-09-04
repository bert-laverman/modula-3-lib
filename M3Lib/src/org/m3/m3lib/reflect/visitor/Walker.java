/**
 * Copyright (c) 2008 B. Laverman. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: bertl
 */

// $Id$

package org.m3.m3lib.reflect.visitor;

import java.lang.reflect.Method;
import java.util.List;
import org.m3.m3lib.reflect.Reflections;


/**
 * 
 * @author LavermB
 * 
 */
public class Walker
{

  public static final String M_STARTVISIT    = "startVisit";

  public static final String M_BEFOREVISIT   = "beforeChildVisit";

  public static final String M_AFTERVISIT    = "afterChildVisit";

  public static final String M_BETWEENVISITS = "betweenChildVisits";

  public static final String M_ENDVISIT      = "endVisit";

  public static final String M_INITIALSTATE  = "getInitialState";

  public static final String M_STATEFORCHILD = "getStateForChild";

  public static final String M_PROCESSSTATE  = "processStateFromChild";

  public static final String M_RETURNSTATE   = "getReturnState";

  public static final String M_GETCHILDREN   = "getChildren";

  public static List<?> getChildren(Object node, Visitor v)
  {
    Method m = Reflections.findNonStaticMethod(v.getClass(), M_GETCHILDREN, node.getClass());
    if (m != null) { // Visitor provides children
      try {
        return (List<?>) Reflections.callFunc(v, m, node);
      }
      catch (Throwable e) {
        // The getChildren() method threw an Exception.
        // This is a bit too much. Just ignore and return null.
      }
    }
    if (node instanceof ParentNode) { return ((ParentNode) node).getChildren(); }
    return null;
  }

  public static Object walkE(Object node, VisitorFactory f, Object parentState)
      throws Throwable
  {
    if (node == null) {
      throw new Exception("Cannot walk NULL");
    }
    final Visitor v = f.getVisitor(node);
    if (v == null) { return parentState; }

    Reflections.doCallProc(v, M_STARTVISIT, node, parentState);

    final Class<?> visitorClass = v.getClass();
    final Class<?> nodeClass = node.getClass();

    Method mGetInitialState = Reflections.findNonStaticMethod(visitorClass, M_INITIALSTATE,
        nodeClass, Reflections.getClassOf(parentState));
    if (mGetInitialState == null) {
      mGetInitialState = Reflections.findNonStaticMethod(visitorClass, M_INITIALSTATE, nodeClass);
    }
    Object currentState = null;
    if (mGetInitialState != null) {
      if (mGetInitialState.getParameterTypes().length > 1) {
        currentState = Reflections.doCallFunc(v, mGetInitialState, node, parentState, null,
            null);
      }
    }

    List<?> children = getChildren(node, v);
    if (children != null) {
      for (int i = 0; i < children.size(); i++) {
        Object child = children.get(i);
if (child == null) {
  System.err.println("Node "+node+" has null child in list "+children);
  continue;
}
        Reflections.doCallProc(v, M_BEFOREVISIT, node, child, currentState);

        Object stateForChild = Reflections.doCallFunc(v, M_STATEFORCHILD, node, child,
            currentState);
        Object stateFromChild = walk(child, f, stateForChild);
        Object processedState = Reflections.doCallFunc(v, M_PROCESSSTATE, node, child,
            currentState, stateFromChild);
        if (processedState != null) {
          currentState = processedState;
        }

        Reflections.doCallProc(v, M_AFTERVISIT, node, child, currentState);

        if (i < children.size() - 1) {
          Reflections.doCallProc(v, M_BETWEENVISITS, node, currentState);
        }
      }
    }

    Reflections.doCallProc(v, M_ENDVISIT, node, currentState);

    Method mGetReturnState = Reflections.findNonStaticMethod(visitorClass, M_RETURNSTATE, nodeClass,
        Reflections.getClassOf(currentState));
    if (mGetReturnState == null) {
      mGetReturnState = Reflections.findNonStaticMethod(visitorClass, M_RETURNSTATE, nodeClass);
    }
    if (mGetReturnState != null) { return Reflections.doCallFunc(v, mGetReturnState, node,
        currentState, null, null); }
    return (mGetInitialState != null) ? currentState : parentState;
  }

  public static Object walkE(Object node, Visitor v, Object parentState)
      throws Throwable
  {
    return walk(node, new SimpleVisitorFactory(v), parentState);
  }

  public static Object walkE(Object node, Visitor v)
      throws Throwable
  {
    return walk(node, new SimpleVisitorFactory(v), null);
  }

  public static Object walkE(Object node, VisitorFactory f)
      throws Throwable
  {
    return walk(node, f, null);
  }

  public static Object walk(Object node, VisitorFactory f, Object parentState)
  {
    try {
      return walkE(node, f, parentState);
    }
    catch (Throwable e) {
      e.printStackTrace();
    }
    return null;
  }

  public static Object walk(Object node, Visitor v, Object parentState)
  {
    return walk(node, new SimpleVisitorFactory(v), parentState);
  }

  public static Object walk(Object node, Visitor v)
  {
    return walk(node, new SimpleVisitorFactory(v), null);
  }

  public static Object walk(Object node, VisitorFactory f)
  {
    return walk(node, f, null);
  }

}

/*
 * $Log$
 */
