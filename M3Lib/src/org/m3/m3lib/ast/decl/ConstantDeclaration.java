/**
 * Copyright (c) 2008 B. Laverman. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: bertl
 */

// $Id$

package org.m3.m3lib.ast.decl;

import java.util.List;

import org.m3.m3lib.ast.expr.Expression;
import org.m3.m3lib.ast.type.Type;
import org.m3.m3lib.scanner.SourceLocation;

/**
 * 
 * @author LavermB
 * 
 */
public class ConstantDeclaration
    implements Declaration
{

  /**
   * 
   */
  private static final long serialVersionUID = -656324449647279813L;

  private String            name_;

  private Type              type_;

  private Expression        value_;

  private SourceLocation    sourceLocation_;

  /**
   * 
   */
  public ConstantDeclaration(String name)
  {
    super();

    this.name_ = name;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.Node#getSourceLocation()
   */
  public SourceLocation getSourceLocation()
  {
    return sourceLocation_;
  }

  /**
   * @param sourceLocation
   *          The sourceLocation to set.
   */
  public void setSourceLocation(SourceLocation sourceLocation)
  {
    this.sourceLocation_ = sourceLocation;
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
   * @return Returns the initializer.
   */
  public Expression getValue()
  {
    return this.value_;
  }

  /**
   * @param initializer
   *          The initializer to set.
   */
  public void setValue(Expression initializer)
  {
    this.value_ = initializer;
  }

  /**
   * @return Returns the type.
   */
  public Type getType()
  {
    return this.type_;
  }

  /**
   * @param type
   *          The type to set.
   */
  public void setType(Type type)
  {
    this.type_ = type;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.decl.Declaration#getName()
   */
  public String getName()
  {
    return name_;
  }

  /**
   * @param name
   *          The name to set.
   */
  public void setName(String name)
  {
    this.name_ = name;
  }

}

/*
 * $Log$
 */
