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

import java.util.List;

import org.m3.m3lib.M3Token;
import org.m3.m3lib.ast.expr.NameExpression;
import org.m3.m3lib.ast.type.ArrayType;
import org.m3.m3lib.ast.type.EnumeratedType;
import org.m3.m3lib.ast.type.Field;
import org.m3.m3lib.ast.type.IdType;
import org.m3.m3lib.ast.type.ObjectType;
import org.m3.m3lib.ast.type.PackedType;
import org.m3.m3lib.ast.type.ProcedureType;
import org.m3.m3lib.ast.type.RecordType;
import org.m3.m3lib.ast.type.RefType;
import org.m3.m3lib.ast.type.SetType;
import org.m3.m3lib.ast.type.SubrangeType;
import org.m3.m3lib.ast.type.Type;
import org.m3.m3lib.reflect.visitor.Visitor;
import org.m3.m3lib.reflect.visitor.Walker;

/**
 * @author bertl
 *
 */
public class TypePrinter
  implements Visitor
{

  private ASTPrinter base_;

  /**
   * @param base
   */
  public TypePrinter(ASTPrinter base)
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

  public void startVisit(IdType type)
  {
    getBase().print(type.getId());
  }

  public void startVisit(SubrangeType t)
  {
    getBase().print(M3Token.LBRACK);
    Walker.walk(t.getUncheckedLwb(), getBase().getExpressionPrinter());
    getBase().print(M3Token.UPTO);
    Walker.walk(t.getUncheckedUpb(), getBase().getExpressionPrinter());
    getBase().print(M3Token.RBRACK);
  }

  public void startVisit(ArrayType t)
  {
    getBase().print(M3Token.ARRAY);
    getBase().printSpace();
    if (t.getUncheckedIndex() != null) {
      Walker.walk(t.getUncheckedIndex(), this);
      getBase().printSpace();
    }
    getBase().print(M3Token.OF);
    getBase().printSpace();
    Walker.walk(t.getElement(), this);
  }

  public void startVisit(PackedType t)
  {
    getBase().print(M3Token.BITS);
    getBase().printSpace();
    Walker.walk(t.getUncheckedBits(), getBase().getExpressionPrinter());
    getBase().printSpace();
    getBase().print(M3Token.FOR);
    getBase().printSpace();
    Walker.walk(t.getBaseType(), this);
  }

  public void startVisit(EnumeratedType t)
  {
    getBase().print(M3Token.LBRACE);
    boolean needComma = false;
    for (String id: t.getIds()) {
      if (needComma) {
        getBase().print(M3Token.COMMA);
        getBase().printSpace();
      }
      else {
        needComma = true;
      }
      getBase().print(id);
    }
    getBase().print(M3Token.RBRACE);
  }

  public void startVisit(SetType t)
  {
    getBase().print(M3Token.SET);
    getBase().printSpace();
    getBase().print(M3Token.OF);
    getBase().printSpace();
    Walker.walk(t.getElement(), this);
  }

  public void startVisit(RecordType t)
  {
    getBase().print(M3Token.RECORD);
    getBase().setNeedSpace(true);
  }
  public void betweenChildVisits(RecordType t)
  {
    getBase().setNeedSem();
    getBase().setNeedSpace(true);
  }
  public void endVisit(RecordType t)
  {
    getBase().print(M3Token.END);
  }

  public void startVisit(Field fld)
  {
    getBase().print(fld.getName());
    if (fld.getType() != null) {
      if (fld.isMethod() && (fld.getType() instanceof ProcedureType)) {
        printProcType((ProcedureType) fld.getType(), true);
      }
      else {
        getBase().print(M3Token.COLON);
        getBase().setNeedSpace(true);
        Walker.walk(fld.getType(), this);
      }
    }
    if (fld.getDefault() != null) {
      getBase().setNeedSpace(true);
      getBase().print(M3Token.BECOMES);
      getBase().setNeedSpace(true);
      Walker.walk(fld.getDefault(), getBase().getExpressionPrinter());
      getBase().setNeedSpace(true);
    }
  }

  public void startVisit(RefType t)
  {
    if (t.isUntraced()) {
      getBase().print(M3Token.UNTRACED);
      getBase().setNeedSpace(true);
    }
    if (t.getBrand() != null) {
      getBase().print(M3Token.BRANDED);
      getBase().setNeedSpace(true);
      getBase().print("\""+t.getBrand()+"\"");
      getBase().setNeedSpace(true);
    }
    getBase().print(M3Token.REF);
    getBase().setNeedSpace(true);
    Walker.walk(t.getElement(), this);
  }

  public void printProcType(ProcedureType t, boolean isMethod)
  {
    if (!isMethod) {
      getBase().print(M3Token.PROCEDURE);
      getBase().printSpace();
    }
    getBase().print(M3Token.LPAREN);
    boolean notFirst = false;
    for (ProcedureType.FormalArgument formal : t.getArgs()) {
      if (notFirst) {
        getBase().print(M3Token.SEMICOLON);
        getBase().printSpace();
      }
      else {
        notFirst = true;
      }
      switch (formal.getKind()) {
      case AK_CONST:
        getBase().print(M3Token.READONLY);
        getBase().printSpace();
        break;
      case AK_VAR:
        getBase().print(M3Token.VAR);
        getBase().printSpace();
        break;
      case AK_VALUE:
        getBase().print(M3Token.VALUE);
        getBase().printSpace();
        break;
      default:
        break;
      }
      getBase().print(formal.getName());
      if (formal.getType() != null) {
        getBase().print(M3Token.COLON);
        getBase().printSpace();
        Walker.walk(formal.getType(), this);
      }
      if (formal.getDefault() != null) {
        getBase().printSpace();
        getBase().print(M3Token.BECOMES);
        Walker.walk(formal.getDefault(), getBase().getExpressionPrinter());
      }
    }
    getBase().print(M3Token.RPAREN);
    Type result = t.getReturnType();
    if (result != null) {
      getBase().print(M3Token.COLON);
      getBase().printSpace();
      Walker.walk(result, this);
    }
    List<NameExpression> raisesList = t.getRaiseList();
    if (t.isRaisingAny() || (raisesList != null)) {
      getBase().setNeedSpace(true);
      getBase().print(M3Token.RAISES);
      getBase().setNeedSpace(true);
      if (t.isRaisingAny()) {
        getBase().print(M3Token.ANY);
      }
      else {
        getBase().print(M3Token.LBRACE);
        boolean needComma = false;
        for (NameExpression name: raisesList) {
          if (needComma) {
            getBase().print(M3Token.COMMA);
            getBase().setNeedSpace(true);
          }
          else {
            needComma = true;
          }
          Walker.walk(name, getBase().getExpressionPrinter());
        }
        getBase().print(M3Token.RBRACE);
      }
    }
  }

  public void startVisit(ProcedureType t)
  {
    printProcType(t, false);
  }

  public void startVisit(ObjectType t)
  {
    if (t.isRoot()) {
      if (t.isUntraced()) {
        getBase().print(M3Token.UNTRACED);
        getBase().setNeedSpace(true);
      }
      getBase().print(M3Token.ROOT);
      getBase().setNeedSpace(true);

      return;
    }
    Walker.walk(t.getBaseType(), this);
    getBase().setNeedSpace(true);
    if (t.getBrand() != null) {
      getBase().print(M3Token.BRANDED);
      getBase().setNeedSpace(true);
      getBase().print("\""+t.getBrand()+"\"");
      getBase().setNeedSpace(true);
    }
    getBase().print(M3Token.OBJECT);
    getBase().setNeedSpace(true);
  }
  public void betweenChildVisits(ObjectType t)
  {
    if (!t.isRoot()) {
      getBase().setNeedSpace(true);
    }
  }
  public void endVisit(ObjectType t)
  {
    if (!t.isRoot()) {
      getBase().print(M3Token.END);
    }
  }

  public void startVisit(ObjectType.Section s)
  {
    if ((s.getFields().size() > 0) && (s.getName() != null)) {
      getBase().print(s.getName());
      getBase().setNeedSpace(true);
    }
  }
  public void betweenChildVisits(ObjectType.Section s)
  {
    getBase().setNeedSem();
    getBase().setNeedSpace(true);
  }
}


/*
 * $Log$
 */
