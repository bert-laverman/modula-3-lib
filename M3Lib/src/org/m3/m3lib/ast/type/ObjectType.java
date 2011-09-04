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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.m3.m3lib.M3Token;
import org.m3.m3lib.ast.expr.Expression;
import org.m3.m3lib.reflect.visitor.ParentNode;
import org.m3.m3lib.scanner.SourceLocation;

/**
 * @author bertl
 * 
 */
public class ObjectType
  extends Type
{

  public static class Section
    implements ParentNode
  {
    private String      name_;
    private List<Field> fields_;

    /**
     * @param name
     * @param fields
     */
    public Section(String name, Map<String, Field> fields)
    {
      super();
      this.name_ = name;
      this.fields_ = new ArrayList<Field>(fields.values());
    }

    /**
     * @return the name
     */
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
     * @return the fields
     */
    public List<Field> getFields()
    {
      return this.fields_;
    }

    /**
     * @param fields the fields to set
     */
    public void setFields(List<Field> fields)
    {
      this.fields_ = fields;
    }

    @Override
    public List<?> getChildren()
    {
      return this.fields_;
    }

  }

  /**
   * 
   */
  private static final long  serialVersionUID = 3438053210599216292L;

  private Expression         brand_           = null;
  private Type               uncheckedBase_   = null;
  private ObjectType         base_            = null;
  private Map<String, Field> fields_          = new HashMap<String, Field>();
  private Map<String, Field> methods_         = new HashMap<String, Field>();
  private Map<String, Field> overrides_       = new HashMap<String, Field>();

  private List<Section>      sections_        = new ArrayList<Section>(3);

  private SourceLocation     sourceLocation_;

  private boolean            untraced_        = false;
  private boolean            root_            = false;

  public ObjectType()
  {
    super();
  }

  /* (non-Javadoc)
   * @see org.m3.m3lib.ast.type.Type#getBaseType()
   */
  @Override
  public Type getBaseType()
  {
    return (this.base_ != null) ? this.base_ : this.uncheckedBase_;
  }

  /* (non-Javadoc)
   * @see org.m3.m3lib.ast.type.Type#isAssignableFrom(org.m3.m3lib.ast.type.Type)
   */
  @Override
  public boolean isAssignableFrom(Type type)
  {
    // TODO Auto-generated method stub
    return false;
  }

  /* (non-Javadoc)
   * @see org.m3.m3lib.ast.Node#getSourceLocation()
   */
  @Override
  public SourceLocation getSourceLocation()
  {
    return this.sourceLocation_;
  }

  /* (non-Javadoc)
   * @see org.m3.m3lib.ast.type.Type#setSourceLocation(org.m3.m3lib.scanner.SourceLocation)
   */
  @Override
  public void setSourceLocation(SourceLocation loc)
  {
    this.sourceLocation_ = loc;
  }

  /* (non-Javadoc)
   * @see org.m3.m3lib.reflect.visitor.ParentNode#getChildren()
   */
  @Override
  public List<?> getChildren()
  {
    this.sections_.clear();
    this.sections_.add(new Section(null, fields_));
    this.sections_.add(new Section(M3Token.METHODS.toString(), methods_));
    this.sections_.add(new Section(M3Token.OVERRIDES.toString(), overrides_));

    return this.sections_;
  }

  /**
   * @return the fields
   */
  public Map<String, Field> getFields()
  {
    return this.fields_;
  }

  public Field getField(String name)
  {
    return fields_.get(name);
  }

  public void addField(Field field)
  {
    fields_.put(field.getName(), field);
  }

  /**
   * @return the fields
   */
  public Map<String, Field> getMethods()
  {
    return this.methods_;
  }

  public Field getMethod(String name)
  {
    return methods_.get(name);
  }

  public void addMethod(Field field)
  {
    field.setMethod(true);
    methods_.put(field.getName(), field);
  }

  /**
   * @return the fields
   */
  public Map<String, Field> getOverrides()
  {
    return this.overrides_;
  }

  public Field getOverride(String name)
  {
    return overrides_.get(name);
  }

  public void addOverride(Field field)
  {
    field.setMethod(true);
    overrides_.put(field.getName(), field);
  }

  /**
   * @return the brand
   */
  public Expression getBrand()
  {
    return this.brand_;
  }

  /**
   * @param brand the brand to set
   */
  public void setBrand(Expression brand)
  {
    this.brand_ = brand;
  }

  /**
   * @return the uncheckedBase
   */
  public Type getUncheckedBase()
  {
    return this.uncheckedBase_;
  }

  /**
   * @param uncheckedBase the uncheckedBase to set
   */
  public void setUncheckedBase(Type uncheckedBase)
  {
    this.uncheckedBase_ = uncheckedBase;
  }

  /**
   * @return the base
   */
  public ObjectType getBase()
  {
    return this.base_;
  }

  /**
   * @param base the base to set
   */
  public void setBase(ObjectType base)
  {
    this.base_ = base;
  }

  /**
   * @return the untraced
   */
  public boolean isUntraced()
  {
    return this.untraced_;
  }

  /**
   * @param untraced the untraced to set
   */
  public void setUntraced(boolean untraced)
  {
    this.untraced_ = untraced;
  }

  /**
   * @return the untraced
   */
  public boolean isRoot()
  {
    return this.root_;
  }

  /**
   * @param root the root to set
   */
  public void setRoot(boolean root)
  {
    this.root_ = root;
  }

  private static ObjectType rootObj_ = null;

  public static ObjectType getRootObj()
  {
    if (rootObj_ == null) {
      rootObj_ = new ObjectType();
      rootObj_.setRoot(true);
    }
    return rootObj_;
  }

  private static ObjectType untracedRootObj_ = null;

  public static ObjectType getUntracedRootObj()
  {
    if (untracedRootObj_ == null) {
      untracedRootObj_ = new ObjectType();
      untracedRootObj_.setUntraced(true);
      untracedRootObj_.setRoot(true);
    }
    return untracedRootObj_;
  }

}

/*
 * $Log$
 */
