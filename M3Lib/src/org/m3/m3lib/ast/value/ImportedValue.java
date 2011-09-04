/**
 * Copyright (c) 2008 B. Laverman. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: bertl
 */

// $Id$


package org.m3.m3lib.ast.value;

import org.m3.m3lib.ast.Binding;
import org.m3.m3lib.ast.Module;

/**
 * 
 * @author LavermB
 *
 */
public class ImportedValue
    extends BoundValue
{

  private Module module_;
  
  /**
   * @param binding
   */
  public ImportedValue(Module mod, Binding binding)
  {
    super(binding);

    this.module_ = mod;
  }

  /**
   * @return Returns the module.
   */
  public Module getModule()
  {
    return this.module_;
  }

}


/*
 * $Log$
 */
