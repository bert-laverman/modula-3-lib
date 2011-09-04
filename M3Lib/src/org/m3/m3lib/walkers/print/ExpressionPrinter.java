/**
 * Copyright (c) 2008 B. Laverman. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: bertl
 */

// $Id$

package org.m3.m3lib.walkers.print;

import org.m3.m3lib.M3Token;
import org.m3.m3lib.ast.expr.ApplicationExpression;
import org.m3.m3lib.ast.expr.ConstructorExpression;
import org.m3.m3lib.ast.expr.DerefExpression;
import org.m3.m3lib.ast.expr.DyadicExpression;
import org.m3.m3lib.ast.expr.Expression;
import org.m3.m3lib.ast.expr.IdExpression;
import org.m3.m3lib.ast.expr.IndexExpression;
import org.m3.m3lib.ast.expr.MonadicExpression;
import org.m3.m3lib.ast.expr.NameExpression;
import org.m3.m3lib.ast.expr.SelectExpression;
import org.m3.m3lib.ast.expr.TypeExpression;
import org.m3.m3lib.ast.expr.ValueExpression;
import org.m3.m3lib.ast.type.CharType;
import org.m3.m3lib.ast.type.TextType;
import org.m3.m3lib.ast.value.CharValue;
import org.m3.m3lib.ast.value.TextValue;
import org.m3.m3lib.reflect.visitor.Visitor;
import org.m3.m3lib.reflect.visitor.Walker;

/**
 * @author bertl
 *
 */
public class ExpressionPrinter
  implements Visitor
{

  private ASTPrinter base_;

  /**
   * @param base
   */
  public ExpressionPrinter(ASTPrinter base)
  {
    super();
    this.base_ = base;
  }

  /**
   * @return the base
   */
  public ASTPrinter getBase()
  {
    return this.base_;
  }

  public void startVisit(ApplicationExpression e)
  {
    Walker.walk(e.getFun(), this);
    getBase().print(M3Token.LPAREN);
  }
  public void betweenChildVisits(ApplicationExpression e)
  {
    getBase().print(M3Token.COMMA);
    getBase().printSpace();
  }
  public void endVisit(ApplicationExpression e)
  {
    getBase().print(M3Token.RPAREN);
  }
  public void startVisit(ApplicationExpression.Actual a)
  {
    if (a.getName() != null) {
      getBase().print(a.getName());
      getBase().print(M3Token.BECOMES);
    }
    Walker.walk(a.getValue(), this);
  }

  public void beforeChildVisit(DyadicExpression e, Expression child)
  {
    if (child.getPrecedence() < e.getPrecedence()) {
      getBase().print(M3Token.LPAREN);
    }
  }

  public void afterChildVisit(DyadicExpression e, Expression child)
  {
    if (child.getPrecedence() < e.getPrecedence()) {
      getBase().print(M3Token.RPAREN);
    }
  }

  public void betweenChildVisits(DyadicExpression e)
  {
    getBase().printSpace();
    getBase().print(e.getOper());
    getBase().printSpace();
  }

  public void betweenChildVisits(SelectExpression e)
  {
    getBase().print(e.getOper());
  }

  public void startVisit(DerefExpression e)
  {
  }
  public void endVisit(DerefExpression e)
  {
    getBase().print(e.getOper());
  }
  public void endVisit(IndexExpression e)
  {
    getBase().print(M3Token.RBRACK);
  }
  public void startVisit(MonadicExpression e)
  {
    getBase().print(e.getOper());
    if (e.getOper().isKeyword()) {
      getBase().printSpace();
    }
  }

  public void beforeChildVisit(MonadicExpression e, Expression child)
  {
    if (child.getPrecedence() < e.getPrecedence()) {
      getBase().print(M3Token.LPAREN);
    }
  }

  public void afterChildVisit(MonadicExpression e, Expression child)
  {
    if (child.getPrecedence() < e.getPrecedence()) {
      getBase().print(M3Token.RPAREN);
    }
  }

  public void startVisit(IdExpression e)
  {
    getBase().print(e.getId());
  }

  public void startVisit(ValueExpression e)
  {
    if (e.getValue().getType() instanceof TextType) {
      getBase().print(TextValue.quote(e.getValue().toString()));
    }
    else if (e.getValue().getType() instanceof CharType) {
      getBase().print(CharValue.quote(e.getValue().toString().charAt(0)));
    }
    else {
      getBase().print(e.getValue().toString());
    }
  }

  public void betweenChildVisits(NameExpression e)
  {
    getBase().print(M3Token.DOT);
  }

  public void startVisit(TypeExpression e)
  {
    Walker.walk(e.getType(), getBase().getTypePrinter());
  }

  public void startVisit(ConstructorExpression e)
  {
    Walker.walk(e.getType(), getBase().getTypePrinter());
    getBase().print(M3Token.LBRACE);
  }
  public void betweenChildVisits(ConstructorExpression e)
  {
    getBase().print(M3Token.COMMA);
    getBase().setNeedSpace(true);
  }
  public void endVisit(ConstructorExpression e)
  {
    if (e.isFilledOut()) {
      getBase().print(M3Token.COMMA);
      getBase().setNeedSpace(true);
      getBase().print(M3Token.UPTO);
    }
    getBase().print(M3Token.RBRACE);
  }
  public void startVisit(ConstructorExpression.RecordElt e)
  {
    getBase().print(e.getField());
    getBase().print(M3Token.BECOMES);
    Walker.walk(e.getValue(), this);
  }
  public void startVisit(ConstructorExpression.SetElt e)
  {
    Walker.walk(e.getValue(), this);
    getBase().print(M3Token.UPTO);
    Walker.walk(e.getUpto(), this);
  }
  public void startVisit(ConstructorExpression.ConsElt e)
  {
    Walker.walk(e.getValue(), this);
  }
}


/*
 * $Log$
 */
