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

import org.m3.m3lib.M3Token;
import org.m3.m3lib.ast.expr.NameExpression;
import org.m3.m3lib.ast.type.Type;
import org.m3.m3lib.scanner.SourceLocation;

/**
 * @author bertl
 * 
 */
public class Revelation
  implements Declaration
{

  /**
   * 
   */
  private static final long serialVersionUID = -4664476179106348270L;

  private NameExpression    name_;
  private M3Token           oper_;
  private Type              type_;
  private SourceLocation    sourceLocation_;

  /**
   * @param name
   * @param oper
   * @param type
   */
  public Revelation(NameExpression name, M3Token oper, Type type)
  {
    super();
    this.name_ = name;
    this.oper_ = oper;
    this.type_ = type;
  }

  /* (non-Javadoc)
   * @see org.m3.m3lib.ast.decl.Declaration#getName()
   */
  @Override
  public String getName()
  {
    return this.name_.toString();
  }

  /**
   * @param name the name to set
   */
  public void setName(NameExpression name)
  {
    this.name_ = name;
  }

  /**
   * @return the oper
   */
  public M3Token getOper()
  {
    return this.oper_;
  }

  /**
   * @param oper the oper to set
   */
  public void setOper(M3Token oper)
  {
    this.oper_ = oper;
  }

  /**
   * @return the type
   */
  public Type getType()
  {
    return this.type_;
  }

  /**
   * @param type the type to set
   */
  public void setType(Type type)
  {
    this.type_ = type;
  }

  /* (non-Javadoc)
   * @see org.m3.m3lib.ast.Node#getSourceLocation()
   */
  @Override
  public SourceLocation getSourceLocation()
  {
    return this.sourceLocation_;
  }

  /**
   * @param sourceLocation the sourceLocation to set
   */
  public void setSourceLocation(SourceLocation sourceLocation)
  {
    this.sourceLocation_ = sourceLocation;
  }

  /* (non-Javadoc)
   * @see org.m3.m3lib.reflect.visitor.ParentNode#getChildren()
   */
  @Override
  public List<?> getChildren()
  {
    return null;
  }

}

/*
 * $Log$
 */
