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
import org.m3.m3lib.scanner.SourceLocation;

/**
 * 
 * @author LavermB
 * 
 */
public class Interface
  implements Node, M3CompilationUnit
{

  /**
   * 
   */
  private static final long serialVersionUID = -7613370226919662266L;

  private boolean           unsafe_          = false;

  private String            package_;

  private String            name_;

  private List<String>      imports_;

  private List<Declaration> decls_           = new ArrayList<Declaration>();

  private SourceLocation    sourceLocation_;

  /**
   * 
   */
  public Interface(String name)
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
   * @return Returns the decls.
   */
  public List<Declaration> getDecls()
  {
    return this.decls_;
  }

  public void addDeclaration(Declaration decl)
  {
    getDecls().add(decl);
  }

  /* (non-Javadoc)
   * @see com.syllogic.m3.ast.M3CompilationUnit#getPackage()
   */
  public String getPackage()
  {
    return this.package_;
  }

  /* (non-Javadoc)
   * @see com.syllogic.m3.ast.M3CompilationUnit#setPackage(java.lang.String)
   */
  public void setPackage(String package1)
  {
    this.package_ = package1;
  }

  /* (non-Javadoc)
   * @see com.syllogic.m3.ast.M3CompilationUnit#getName()
   */
  public String getName()
  {
    return this.name_;
  }

  /* (non-Javadoc)
   * @see com.syllogic.m3.ast.M3CompilationUnit#setName(java.lang.String)
   */
  public void setName(String name)
  {
    this.name_ = name;
  }

  /* (non-Javadoc)
   * @see com.syllogic.m3.ast.M3CompilationUnit#setSourceLocation(com.syllogic.m3.scanner.SourceLocation)
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
  /* (non-Javadoc)
   * @see com.syllogic.m3.ast.M3CompilationUnit#getSourceLocation()
   */
  public SourceLocation getSourceLocation()
  {
    return this.sourceLocation_;
  }

  /* (non-Javadoc)
   * @see com.syllogic.m3.ast.M3CompilationUnit#getImports()
   */
  public List<String> getImports()
  {
    if (this.imports_ == null) {
      this.imports_ = new ArrayList<String>();
    }
    return this.imports_;
  }

  /* (non-Javadoc)
   * @see com.syllogic.m3.ast.M3CompilationUnit#setImports(java.util.List)
   */
  public void setImports(List<String> imports)
  {
    this.imports_ = imports;
  }

  /* (non-Javadoc)
   * @see com.syllogic.m3.ast.M3CompilationUnit#addImport(java.lang.String)
   */
  public void addImport(String name)
  {
    getImports().add(name);
  }

  /* (non-Javadoc)
   * @see com.syllogic.m3.ast.M3CompilationUnit#addImport(java.lang.String, java.lang.String)
   */
  public void addImport(String module, String name)
  {
    addImport(module + "/" + name);
  }

}

/*
 * $Log$
 */
