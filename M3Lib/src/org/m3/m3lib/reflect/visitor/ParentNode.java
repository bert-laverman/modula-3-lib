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

import java.util.List;

/**
 * 
 * @author LavermB
 * 
 */
public interface ParentNode
    extends Node
{

  /**
   * Return the children of this node.
   * 
   * @return A <code>List</code> containing this node's children.
   */
  public List<?> getChildren();

}

/*
 * $Log$
 */
