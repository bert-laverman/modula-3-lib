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

import org.apache.log4j.Logger;
import org.m3.m3lib.ast.Context;
import org.m3.m3lib.ast.M3RuntimeException;
import org.m3.m3lib.ast.SimpleContext;
import org.m3.m3lib.ast.stat.BlockStatement;
import org.m3.m3lib.ast.stat.Statement;
import org.m3.m3lib.reflect.visitor.Visitor;

/**
 * 
 * @author LavermB
 * 
 */
public class BlockStatementRunner
  extends StatementRunner
  implements Visitor
{

  private static final Logger log_ = Logger
                                       .getLogger(BlockStatementRunner.class);

  /**
   * 
   */
  public BlockStatementRunner()
  {
    super();
  }

  public void startVisit(BlockStatement block, Context ctx)
    throws M3RuntimeException
  {
    log_.debug("Started running block statement (" + block.getDecls().size() +
               " declarations, " + block.getStats().size() + " statements)");
    SimpleContext inner = new SimpleContext(ctx);
    StatementRunner.fillContext(inner, block.getDecls());

    for (Statement stat : block.getStats()) {
      run(stat, inner);
    }
  }

}

/*
 * $Log$
 */
