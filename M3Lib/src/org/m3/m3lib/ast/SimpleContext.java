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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.m3.m3lib.ast.value.BooleanValue;
import org.m3.m3lib.ast.value.IntegerValue;
import org.m3.m3lib.ast.value.RealValue;
import org.m3.m3lib.ast.value.Value;

/**
 * 
 * @author LavermB
 * 
 */
public class SimpleContext
    implements Context
{
  private Context              parent_ = null;

  private Map<String, Binding> ctx_    = new HashMap<String, Binding>();

  /** Creates a new instance of SimpleContext */
  public SimpleContext()
  {
  }

  /** Creates a new instance of SimpleContext */
  public SimpleContext(Context parent)
  {
    parent_ = parent;
  }

  public Binding lookup(String name)
  {
    Binding b = (Binding) ctx_.get(name);

    if ((b == null) && (parent_ != null)) {
      b = parent_.lookup(name);
    }

    return b;
  }

  public void add(Binding b)
  {
    ctx_.put(b.getName(), b);
  }

  public void remove(String name)
  {
    if (ctx_.containsKey(name)) {
      ctx_.remove(name);
    }
  }

  public void bind(String name, Value val)
  {
    ctx_.put(name, new Binding(name, val));
  }

  public void bind(String name, boolean val)
  {
    bind(name, new BooleanValue(val));
  }

  public void bind(String name, long val)
  {
    bind(name, new IntegerValue(val));
  }

  public void bind(String name, double val)
  {
    bind(name, new RealValue(val));
  }

  public Iterator<?> iterator()
  {
    return ctx_.keySet().iterator();
  }

  public Iterator<?> bindingsIterator()
  {
    return ctx_.values().iterator();
  }

  public List<Binding> getChildren()
  {
    return new ArrayList<Binding>(ctx_.values());
  }

  /**
   * @return Returns the parent.
   */
  public Context getParent()
  {
    return this.parent_;
  }

  /**
   * @param parent The parent to set.
   */
  public void setParent(Context parent)
  {
    this.parent_ = parent;
  }

}

/*
 * $Log$
 */
