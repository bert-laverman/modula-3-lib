/**
 * Copyright (c) 2008 B. Laverman. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: bertl
 */

// $Id$
package org.m3.m3lib.ast.stat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.m3.m3lib.ast.decl.Declaration;
import org.m3.m3lib.scanner.SourceLocation;

/**
 * 
 * @author LavermB
 * 
 */
public class BlockStatement
  implements Statement, Serializable
{

  /**
   * 
   */
  private static final long serialVersionUID = -3349920119451802455L;

  private List<Declaration> decls_           = new ArrayList<Declaration>();

  private List<Statement>   stats_           = new ArrayList<Statement>();

  private SourceLocation    sourceLocation_;

  /** Creates a new instance of BlockStatement */
  public BlockStatement()
  {
  }

  public void add(Declaration decl)
  {
    decls_.add(decl);
  }

  public void add(Statement stat)
  {
    stats_.add(stat);
  }

  /**
   * @return Returns the decls.
   */
  public List<Declaration> getDecls()
  {
    return this.decls_;
  }

  /**
   * @return Returns the stats.
   */
  public List<Statement> getStats()
  {
    return this.stats_;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.Node#getChildren()
   */
  public List<?> getChildren()
  {
    return new ArrayList<Statement>(stats_);
  }

  /**
   * @return Returns the loc.
   */
  public SourceLocation getSourceLocation()
  {
    return this.sourceLocation_;
  }

  /**
   * @param loc The loc to set.
   */
  public void setSourceLocation(SourceLocation loc)
  {
    this.sourceLocation_ = loc;
  }

}

/*
 * $Log$
 */
