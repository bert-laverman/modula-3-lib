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

import java.util.List;

import org.m3.m3lib.ast.decl.Declaration;
import org.m3.m3lib.scanner.SourceLocation;

/**
 * 
 * @author LavermB
 *
 */
public interface M3CompilationUnit
{

  /**
   * @return Returns the package.
   */
  String getPackage();

  /**
   * @param package1 The package to set.
   */
  void setPackage(String package1);

  /**
   * @return Returns the name.
   */
  String getName();

  /**
   * @param name The name to set.
   */
  void setName(String name);

  /**
   * @param sourceLocation
   *          The sourceLocation to set.
   */
  void setSourceLocation(SourceLocation sourceLocation);

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.Node#getSourceLocation()
   */
  SourceLocation getSourceLocation();

  /**
   * @return Returns the imports.
   */
  List<String> getImports();

  /**
   * @param imports
   *          The imports to set.
   */
  void setImports(List<String> imports);

  void addImport(String name);

  void addImport(String module, String name);

  List<Declaration> getDecls();

  /**
   * @param unsafe
   */
  void setUnsafe(boolean unsafe);

}

/*
 * $Log$
 */
