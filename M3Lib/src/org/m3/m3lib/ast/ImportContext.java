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

import org.m3.m3lib.ast.type.InterfaceType;
import org.m3.m3lib.ast.type.JavaClassType;
import org.m3.m3lib.ast.value.ImportedValue;
import org.m3.m3lib.ast.value.InterfaceValue;
import org.m3.m3lib.ast.value.ObjectValue;

/**
 * 
 * @author LavermB
 * 
 */
public class ImportContext
    extends SimpleContext
{

  private List<String> packages_ = new ArrayList<String>();

  /**
   * 
   */
  public ImportContext()
  {
    super();
  }

  /**
   * @param parent
   */
  public ImportContext(Context parent)
  {
    super(parent);
  }

  /**
   * @param s
   * @throws M3RuntimeException
   */
  public void addImport(String name)
      throws M3RuntimeException
  {
    int slash = name.indexOf('/');

    if (slash == -1) {
      if (JavaContext.isJavaPackage(name)) {
        // Shorthand for Java's "import some.package.*;"
        packages_.add(name);
        return;
      }
      if (JavaContext.isJavaClass(name)) {
        int lastDot = name.lastIndexOf('.');
        try {
          Binding b = new Binding(name.substring(lastDot+1), JavaClassType.getInstance(), ObjectValue.fromJavaObject(Class.forName(name)));
          b.setConst(true);
          add(b);
          return;
        }
        catch (ClassNotFoundException e) {
          // Actually, isJavaClass() ensures this should not happen
        }
      }
      M3CompilationUnit intf = M3Runtime.getInstance().getInterface(name);
      Binding b = new Binding(intf.getName(), InterfaceType.getInstance(),
          new InterfaceValue(intf));
      b.setConst(true);
      add(b);
    }
    else {
      String interfaceName = name.substring(0, slash);
      String id = name.substring(slash+1);

      Module mod = M3Runtime.getInstance().getModuleForInterface(interfaceName);

      Binding b = new Binding(id, new ImportedValue(mod, mod.getContext().lookup(id)));
      add(b);
    }
  }

  /* (non-Javadoc)
   * @see com.syllogic.m3.ast.SimpleContext#lookup(java.lang.String)
   */
  @Override
  public Binding lookup(String name)
  {
    Binding result = super.lookup(name);

    if (result == null) {
      for (String p: packages_) {
        final String className = p+"."+name;
        try {
          result = new Binding(name, JavaClassType.getInstance(), ObjectValue.fromJavaObject(Class.forName(className)));
          add(result);
          break;
        }
        catch (ClassNotFoundException e) {
          continue;
        }
      }
    }
    return result;
  }

}

/*
 * $Log$
 */
