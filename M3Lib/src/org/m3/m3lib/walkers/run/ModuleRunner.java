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

import org.m3.m3lib.ast.Context;
import org.m3.m3lib.ast.M3RuntimeException;
import org.m3.m3lib.ast.Module;
import org.m3.m3lib.reflect.visitor.Visitor;

/**
 * 
 * @author LavermB
 * 
 */
public class ModuleRunner
    extends StatementRunner
    implements Visitor
{

  /**
   * 
   */
  public ModuleRunner()
  {
    super();
  }

  public void startVisit(Module mod, Context ctx)
      throws M3RuntimeException
  {
    // MODULES ignore external contexts!
    run(mod.getBody(), mod.getContext());
  }
}

/*
 * $Log$
 */
