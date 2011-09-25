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

import org.m3.m3lib.reflect.visitor.NamedVisitorFactory;
import org.m3.m3lib.reflect.visitor.VisitorFactory;


/**
 * 
 * @author LavermB
 *
 */
public class ExpressionEvaluatorFactory
    extends NamedVisitorFactory
{

  private static VisitorFactory instance_ = null;

  /**
   * 
   */
  public ExpressionEvaluatorFactory()
  {
    super(new ExpressionEvaluator(),
//          ExpressionEvaluatorFactory.class.getPackage().getName(),
          "org.m3.m3lib.walkers.eval",
          null,
          "Evaluator");
  }

  /**
   * @return Returns the instance.
   */
  public static VisitorFactory getInstance()
  {
    if (instance_ == null) {
      instance_ = new ExpressionEvaluatorFactory();
    }
    return instance_;
  }

}


/*
 * $Log$
 */
