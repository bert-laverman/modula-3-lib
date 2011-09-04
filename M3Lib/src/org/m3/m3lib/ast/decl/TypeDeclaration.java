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
import org.m3.m3lib.ast.type.Type;
import org.m3.m3lib.scanner.SourceLocation;

/**
 * @author bertl
 * 
 */
public class TypeDeclaration
  implements Declaration
{

  /**
   * 
   */
  private static final long serialVersionUID = 673289936469384015L;

  private String            name_;

  private M3Token           oper_;

  private Type              type_;

  private SourceLocation    sourceLocation_;

  /* (non-Javadoc)
   * @see org.m3.m3lib.ast.decl.Declaration#getName()
   */
  @Override
  public String getName()
  {
    return this.name_;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name)
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
