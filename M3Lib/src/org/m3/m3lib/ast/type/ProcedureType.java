/**
 * Copyright (c) 2008 B. Laverman. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: bertl
 */

// $Id$
package org.m3.m3lib.ast.type;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.m3.m3lib.ast.expr.Expression;
import org.m3.m3lib.ast.expr.NameExpression;
import org.m3.m3lib.scanner.SourceLocation;

/**
 * 
 * @author LavermB
 * 
 */
public class ProcedureType
  extends Type
{

  public static enum ArgumentKind
  {
    AK_VALUE,
    AK_CONST,
    AK_VAR
  }

  public static class FormalArgument
    implements Serializable
  {
    /**
     * 
     */
    private static final long serialVersionUID = 380640768637572713L;

    private String            name_            = null;

    private ArgumentKind      kind_            = ArgumentKind.AK_VALUE;

    private Type              type_            = null;

    private Expression        default_         = null;

    private SourceLocation    sourceLocation_;

    /**
     * @param name
     * @param kind
     * @param type
     */
    public FormalArgument(String name, ArgumentKind kind, Type type)
    {
      super();
      this.name_ = name;
      this.kind_ = kind;
      this.type_ = type;
    }

    /**
     * @param name
     * @param type
     */
    public FormalArgument(String name, Type type)
    {
      super();
      this.name_ = name;
      this.type_ = type;
    }

    /**
     * @param name
     * @param kind
     * @param type
     * @param default1
     */
    public FormalArgument(String name, ArgumentKind kind, Type type,
                          Expression default1)
    {
      super();
      this.name_ = name;
      this.kind_ = kind;
      this.type_ = type;
      this.default_ = default1;
    }

    /**
     * @return Returns the kind.
     */
    public ArgumentKind getKind()
    {
      return this.kind_;
    }

    /**
     * @param kind The kind to set.
     */
    public void setKind(ArgumentKind kind)
    {
      this.kind_ = kind;
    }

    /**
     * @return Returns the name.
     */
    public String getName()
    {
      return this.name_;
    }

    /**
     * @param name The name to set.
     */
    public void setName(String name)
    {
      this.name_ = name;
    }

    /**
     * @return Returns the type.
     */
    public Type getType()
    {
      return this.type_;
    }

    /**
     * @param type The type to set.
     */
    public void setType(Type type)
    {
      this.type_ = type;
    }

    /**
     * @return the default
     */
    public Expression getDefault()
    {
      return this.default_;
    }

    /**
     * @param default1 the default to set
     */
    public void setDefault(Expression default1)
    {
      this.default_ = default1;
    }

    /**
     * @return Returns the loc.
     */
    public SourceLocation getSourceLocation()
    {
      return this.sourceLocation_;
    }

    /**
     * @param loc The loc to set.
     */
    public void setSourceLocation(SourceLocation loc)
    {
      this.sourceLocation_ = loc;
    }
  }

  /**
   * 
   */
  private static final long    serialVersionUID = 1666890749040257981L;

  private Type                 returnType_      = null;

  private List<FormalArgument> args_            = new ArrayList<FormalArgument>();

  private boolean              raisingAny_      = false;

  private List<NameExpression> raiseList_       = null;

  private SourceLocation       sourceLocation_;

  /**
   * 
   */
  public ProcedureType()
  {
    super();
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
   * @return Returns the args.
   */
  public List<FormalArgument> getArgs()
  {
    return this.args_;
  }

  /**
   * @param args The args to set.
   */
  public void setArgs(List<FormalArgument> args)
  {
    this.args_ = args;
  }

  /**
   * @return the raisingAny
   */
  public boolean isRaisingAny()
  {
    return this.raisingAny_;
  }

  /**
   * @param raisingAny the raisingAny to set
   */
  public void setRaisingAny(boolean raisingAny)
  {
    this.raisingAny_ = raisingAny;
  }

  /**
   * @return the raiseList
   */
  public List<NameExpression> getRaiseList()
  {
    return this.raiseList_;
  }

  /**
   * @param name
   */
  public void addRaises(NameExpression name)
  {
    if (this.raiseList_ == null) {
      this.raiseList_ = new ArrayList<NameExpression>();
    }
    getRaiseList().add(name);
  }

  /**
   * @return Returns the returnType.
   */
  public Type getReturnType()
  {
    return this.returnType_;
  }

  /**
   * @param returnType The returnType to set.
   */
  public void setReturnType(Type returnType)
  {
    this.returnType_ = returnType;
  }

  public boolean isFunction()
  {
    return this.returnType_ != null;
  }

  /**
   * @return Returns the loc.
   */
  public SourceLocation getSourceLocation()
  {
    return this.sourceLocation_;
  }

  /**
   * @param loc The loc to set.
   */
  public void setSourceLocation(SourceLocation loc)
  {
    this.sourceLocation_ = loc;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.type.Type#getBaseType()
   */
  public Type getBaseType()
  {
    return this;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.type.Type#isAssignable(com.syllogic.m3.ast.Value)
   */
  public boolean isAssignableFrom(Type type)
  {
    return type.equals(this);
  }

  public static ProcedureType fromMethod(Method meth)
  {
    return new ProcedureType();
  }
}

/*
 * $Log$
 */
