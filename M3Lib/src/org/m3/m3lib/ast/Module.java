/**
 * Copyright (c) 2008 B. Laverman. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: bertl
 */

// $Id$
package org.m3.m3lib.ast;

import java.util.ArrayList;
import java.util.List;

import org.m3.m3lib.ast.decl.Declaration;
import org.m3.m3lib.ast.stat.BlockStatement;
import org.m3.m3lib.scanner.SourceLocation;
import org.m3.m3lib.walkers.run.StatementRunner;

/**
 * 
 * @author LavermB
 * 
 */
public class Module
  implements Node, M3CompilationUnit
{

  /**
   * 
   */
  private static final long       serialVersionUID = -2836326737541366508L;

  private boolean                 unsafe_          = false;

  private String                  package_;

  private String                  name_;

  private List<String>            exports_;

  private List<String>            imports_;

  private BlockStatement          body_;

  private SourceLocation          sourceLocation_;

  private transient SimpleContext context_;

  /**
   * 
   */
  public Module(String name)
  {
    super();

    int lastDot = name.lastIndexOf('.');
    if (lastDot == -1) {
      this.package_ = "";
      this.name_ = name;
    }
    else {
      this.package_ = name.substring(0, lastDot);
      this.name_ = name.substring(lastDot + 1);
    }
  }

  /**
   * @return the unsafe
   */
  public boolean isUnsafe()
  {
    return this.unsafe_;
  }

  /**
   * @param unsafe the unsafe to set
   */
  @Override
  public void setUnsafe(boolean unsafe)
  {
    this.unsafe_ = unsafe;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.visitor.ParentNode#getChildren()
   */
  public List<?> getChildren()
  {
    return null;
  }

  /**
   * @return Returns the body.
   */
  public BlockStatement getBody()
  {
    return this.body_;
  }

  /**
   * @param body The body to set.
   */
  public void setBody(BlockStatement body)
  {
    this.body_ = body;
  }

  /**
   * @return Returns the package.
   */
  public String getPackage()
  {
    return this.package_;
  }

  /**
   * @param package1 The package to set.
   */
  public void setPackage(String package1)
  {
    this.package_ = package1;
  }

  /**
   * @return Returns the name.
   */
  public String getName()
  {
    return this.name_;
  }

  /**
   * @param name The name to set.
   */
  public void setName(String name)
  {
    this.name_ = name;
  }

  /**
   * @param sourceLocation The sourceLocation to set.
   */
  public void setSourceLocation(SourceLocation sourceLocation)
  {
    this.sourceLocation_ = sourceLocation;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.Node#getSourceLocation()
   */
  public SourceLocation getSourceLocation()
  {
    return this.sourceLocation_;
  }

  /**
   * @return Returns the exports.
   */
  public List<String> getExports()
  {
    if (this.exports_ == null) {
      this.exports_ = new ArrayList<String>();
    }
    return this.exports_;
  }

  /**
   * @param exports The exports to set.
   */
  public void setExports(List<String> exports)
  {
    this.exports_ = exports;
  }

  public void addExport(String name)
  {
    getExports().add(name);
  }

  /**
   * @return Returns the imports.
   */
  public List<String> getImports()
  {
    if (this.imports_ == null) {
      this.imports_ = new ArrayList<String>();
    }
    return this.imports_;
  }

  /**
   * @param imports The imports to set.
   */
  public void setImports(List<String> imports)
  {
    this.imports_ = imports;
  }

  public void addImport(String name)
  {
    getImports().add(name);
  }

  public void addImport(String module, String name)
  {
    addImport(module + "/" + name);
  }

  /**
   * @return Returns the context.
   */
  public Context getContext()
  {
    return this.context_;
  }

  public void buildContext()
    throws IncompatibleTypeException, M3RuntimeException
  {
    ImportContext iCtx = new ImportContext(new JavaContext());
    context_ = new SimpleContext(iCtx);

    for (String s : getExports()) {
      StatementRunner.fillContext(context_, M3Runtime.getInstance()
          .getInterface(s).getDecls());
    }
    for (String s : getImports()) {
      iCtx.addImport(s);
    }
    StatementRunner.fillContext(context_, getBody().getDecls());
  }

  /* (non-Javadoc)
   * @see com.syllogic.m3.ast.M3CompilationUnit#getDecls()
   */
  public List<Declaration> getDecls()
  {
    return getBody().getDecls();
  }
}

/*
 * $Log$
 */
