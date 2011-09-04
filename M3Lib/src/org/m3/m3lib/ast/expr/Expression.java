/**
 * Copyright (c) 2008 B. Laverman. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: bertl
 */

// $Id$

package org.m3.m3lib.ast.expr;

import org.m3.m3lib.ast.Node;
import org.m3.m3lib.ast.type.Type;
import org.m3.m3lib.scanner.SourceLocation;

/**
 * 
 * @author LavermB
 * 
 */
public interface Expression
    extends Node
{
  /**
   * Return the (sub)expression's precedence, which is a value from 0 to 8.
   * 
   * @return The (sub)expression's precedence.
   */
  int getPrecedence();

  /**
   * Indicate if this expression may be used as the target for an assignment,
   * commonly known as an "L-Value", indicating it may occur on the left side of
   * an assignment operator.
   * 
   * @return <code>true</code> if this expression refers to an L-Value.
   */
  boolean isLValue();

  /**
   * Indicate if this expression is constant.
   * 
   * Note that expressions with identifiers in them default to
   * <code>false</code>, which could change if it is resolved to
   * a constant.
   * 
   * @return <code>true</code> if the expression is constant.
   */
  boolean isConstant();

  /**
   * Return this expression's type. Returns an instance of
   * UndefinedType if (as yet) unknown.
   * @return The type of this expression.
   * @see com.syllogic.m3.ast.type.UnknownType
   */
  Type getType();

  void setSourceLocation(SourceLocation loc);

}

/*
 * $Log$
 */
