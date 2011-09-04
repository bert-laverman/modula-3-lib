/**
 * Copyright (c) 2008 B. Laverman. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: bertl
 */

// $Id$


package org.m3.m3lib.walkers.run;

import org.m3.m3lib.reflect.visitor.NamedVisitorFactory;
import org.m3.m3lib.reflect.visitor.VisitorFactory;


/**
 * 
 * @author LavermB
 *
 */
public class StatementRunnerFactory
    extends NamedVisitorFactory
{

  private static VisitorFactory instance_ = null;

  /**
   * 
   */
  public StatementRunnerFactory()
  {
    super(new StatementRunner(),
//          StatementRunnerFactory.class.getPackage().getName(),
          "com.syllogic.m3.ast.stat.run",
          null,
          "Runner");
  }

  /**
   * @return Returns the instance.
   */
  public static VisitorFactory getInstance()
  {
    if (instance_ == null) {
      instance_ = new StatementRunnerFactory();
    }
    return instance_;
  }

}


/*
 * $Log$
 */
