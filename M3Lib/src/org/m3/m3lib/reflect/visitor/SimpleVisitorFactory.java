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

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author bertl
 */
public class SimpleVisitorFactory
  implements VisitorFactory
{

  Visitor                defaultVisitor_ = null;
  Map<Class<?>, Visitor> visitorMap_     = new HashMap<Class<?>, Visitor>();

  /**
   * 
   */
  public SimpleVisitorFactory(Visitor defaultVisitor)
  {
    super();

    this.defaultVisitor_ = defaultVisitor;
  }

  public Visitor findVisitor(Class<?> targetClass)
  {
    Visitor r = visitorMap_.get(targetClass);

    return r;
  }

  /* (non-Javadoc)
   * @see com.syllogic.visitor.VisitorFactory#getVisitor(java.lang.Object)
   */
  public Visitor getVisitor(Object targetNode)
  {
    Visitor r = findVisitor(targetNode.getClass());

    if (r != null) {
      return r;
    }

    return defaultVisitor_;
  }

  /**
   * @return Returns the defaultVisitor.
   */
  public Visitor getDefaultVisitor()
  {
    return this.defaultVisitor_;
  }

  /**
   * @param defaultVisitor The defaultVisitor to set.
   */
  public void setDefaultVisitor(Visitor defaultVisitor)
  {
    this.defaultVisitor_ = defaultVisitor;
  }

  public void addVisitor(Class<?> targetClass, Visitor visitor)
  {
    visitorMap_.put(targetClass, visitor);
  }

  public void removeVisitor(Class<?> targetClass)
  {
    visitorMap_.remove(targetClass);
  }

}

/*
 * $Log$
 */
