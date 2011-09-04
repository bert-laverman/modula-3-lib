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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.apache.log4j.Logger;

import org.m3.m3lib.ast.type.JavaPackageType;
import org.m3.m3lib.ast.value.ObjectValue;

/**
 * 
 * @author LavermB
 * 
 */
public class JavaContext
    implements Context
{

  private static final String[] packages_ = { "java", "javax", "com", "org",
      "net"                              };

//  private static final Logger   log_      = Logger
//                                              .getLogger("com.syllogic.m3.ast.expr.eval.ExpressionEvaluator");

  private static JavaContext    instance_ = null;

  private Map<String, Binding>  ctx_      = new HashMap<String, Binding>();

  /**
   * 
   */
  public JavaContext()
  {
    super();

    for (String p : packages_) {
      Binding b = new Binding(p, new ObjectValue(p, JavaPackageType
          .getInstance()));
      ctx_.put(p, b);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.Context#lookup(java.lang.String)
   */
  public Binding lookup(String name)
  {
    Binding b = ctx_.get(name);

    if (b == null) {
      Package p = Package.getPackage(name);

      if (p != null) {
        b = new Binding(name, new ObjectValue(name, JavaPackageType
            .getInstance()));
        b.setConst(true);
        ctx_.put(name, b);
      }
//      else {
//        log_.error("Could not find package \"" + name + "\"");
//      }
    }

    return b;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.Context#add(com.syllogic.m3.ast.Binding)
   */
  public void add(Binding b)
  {
    // TODO Auto-generated method stub

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
   * @return Returns the instance.
   */
  public static JavaContext getInstance()
  {
    if (instance_ == null) {
      instance_ = new JavaContext();
    }
    return instance_;
  }

  public static boolean isJavaPackage(String name)
  {
    String[] path = name.split("\\.");
    if (getInstance().lookup(path [0]) == null) {
      return false;
    }
    try {
      Class.forName(name);
      // If we get past the Class.forName() call, it was a class.
      return false;
    }
    catch (ClassNotFoundException e) {
      // Ignore
    }
    // Actually this sucks, but we don't have a real clue about
    // what packages might be available, due to the demand-loading
    // nature of the ClassLoader. Not until you attempt to load
    // a class will the runtime find out if the package is available.
    
    // As a best guess, we'll just assume that anything under the
    // toplevel packages given above (in the packages_ array) must
    // be a class, or else a package. You won't find out for sure
    // until you try to select a class.
    
    // Note that the ClassLoader.getPackage() method (accessible
    // through the Package class) won't help either. It only returns
    // something for packages for which a class has been loaded.

    return true;
  }

  public static boolean isJavaClass(String name)
  {
    try {
      Class.forName(name);
      // If we get past the Class.forName() call, it was a class.
      return true;
    }
    catch (ClassNotFoundException e) {
      // Ignore
    }

    return false;
  }

}

/*
 * $Log$
 */
