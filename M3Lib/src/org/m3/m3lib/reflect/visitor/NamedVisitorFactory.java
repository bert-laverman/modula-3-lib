/**
 * Copyright (c) 2008 B. Laverman. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: bertl
 */

// $Id$


package org.m3.m3lib.reflect.visitor;

/**
 * 
 * @author LavermB
 *
 */
public class NamedVisitorFactory
    extends SimpleVisitorFactory
{

  private String package_ = null;
  private String prefix_ = null;
  private String suffix_ = null;

  /**
   * @param defaultVisitor
   */
  public NamedVisitorFactory(Visitor defaultVisitor)
  {
    super(defaultVisitor);
  }

  public NamedVisitorFactory(Visitor defaultVisitor, String packag, String prefix, String suffix)
  {
    super(defaultVisitor);
    this.package_ = packag;
    this.prefix_ = prefix;
    this.suffix_ = suffix;
  }

  /* (non-Javadoc)
   * @see com.syllogic.visitor.SimpleVisitorFactory#findVisitor(java.lang.Class)
   */
  @Override
  public Visitor findVisitor(Class<?> targetClass)
  {
    Visitor r = super.findVisitor(targetClass);

    if (r != null) { return r; }

    try {
      final String packageName = (package_ == null)
                               ? targetClass.getPackage().getName()
                               : package_;
      final String className   = ((prefix_ == null) ? "" : prefix_)
                                +targetClass.getSimpleName()
                                +((suffix_ == null) ? "" : suffix_);

      Class<?> visitorClass = Class.forName(packageName+"."+className);

      return (Visitor)(visitorClass.newInstance());
    }
    catch (ClassNotFoundException e) {
      // Try for the parent
      if (!targetClass.equals(Object.class)) {
        return findVisitor(targetClass.getSuperclass());
      }
    }
    catch (ClassCastException e) {
      
    }
    catch (IllegalAccessException e) {
      
    }
    catch (InstantiationException e) {
      
    }
    return null;
  }

  /**
   * @return Returns the prefix.
   */
  public String getPrefix()
  {
    return this.prefix_;
  }

  /**
   * @param prefix The prefix to set.
   */
  public void setPrefix(String prefix)
  {
    this.prefix_ = prefix;
  }

  /**
   * @return Returns the suffix.
   */
  public String getSuffix()
  {
    return this.suffix_;
  }

  /**
   * @param suffix The suffix to set.
   */
  public void setSuffix(String suffix)
  {
    this.suffix_ = suffix;
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

  
}


/*
 * $Log$
 */
