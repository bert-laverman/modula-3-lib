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

import java.util.ArrayList;
import java.util.List;

import org.m3.m3lib.ast.IncompatibleTypeException;
import org.m3.m3lib.ast.expr.Expression;
import org.m3.m3lib.ast.stat.BlockStatement;
import org.m3.m3lib.ast.type.ProcedureType;
import org.m3.m3lib.scanner.SourceLocation;

/**
 * 
 * @author LavermB
 * 
 */
public class ProcedureDeclaration
  implements Declaration
{

  public static class ProcVariant
  {

    private ProcedureType  type_;

    private BlockStatement body_;

    /**
     * 
     */
    public ProcVariant()
    {
      super();
    }

    /**
     * @param type
     * @param body
     */
    public ProcVariant(ProcedureType type, BlockStatement body)
    {
      super();
      this.type_ = type;
      this.body_ = body;
    }

    /**
     * @return the type
     */
    public ProcedureType getType()
    {
      return this.type_;
    }

    /**
     * @param type the type to set
     */
    public void setType(ProcedureType type)
    {
      this.type_ = type;
    }

    /**
     * @return the body
     */
    public BlockStatement getBody()
    {
      return this.body_;
    }

    /**
     * @param body the body to set
     */
    public void setBody(BlockStatement body)
    {
      this.body_ = body;
    }

  }

  /**
   * 
   */
  private static final long serialVersionUID = -7982856113486197018L;

  private String            name_;

  private List<ProcVariant> variants_        = new ArrayList<ProcVariant>();

  private SourceLocation    sourceLocation_;

  /**
   * @param name
   * @param type
   * @param body
   */
  public ProcedureDeclaration(String name, ProcedureType type,
                              BlockStatement body)
  {
    super();

    this.name_ = name;
    this.variants_.add(new ProcVariant(type, body));
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.decl.Declaration#getName()
   */
  public String getName()
  {
    return this.name_;
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
    return this.variants_.get(0).getBody();
  }

  /**
   * @return Returns the type.
   */
  public ProcedureType getType()
  {
    return this.variants_.get(0).getType();
  }

  public void addVariant(ProcedureType type, BlockStatement body)
    throws IncompatibleTypeException
  {
    if (this.variants_.size() != 0) {
      ProcedureType baseType = getType();

      if (((type.getReturnType() == null) && (baseType.getReturnType() != null)) ||
          ((type.getReturnType() != null) && !type.getReturnType()
              .equals(baseType.getReturnType())))
      {
        throw new IncompatibleTypeException(
                                            "New signature differs in return type",
                                            baseType, type);
      }
    }
    this.variants_.add(new ProcVariant(type, body));
  }

  public ProcVariant findVariant(List<Expression> actuals)
  {
//    for (ProcVariant variant: this.variants_) {
//      if ()
      //TODO
//    }
    return null;
  }

  /**
   * @param sourceLocation The sourceLocation to set.
   */
  public void setSourceLocation(SourceLocation sourceLocation)
  {
    this.sourceLocation_ = sourceLocation;
  }

}

/*
 * $Log$
 */
