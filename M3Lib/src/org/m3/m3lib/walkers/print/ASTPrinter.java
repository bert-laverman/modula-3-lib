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

import java.io.PrintWriter;

import org.m3.m3lib.M3Token;
import org.m3.m3lib.ast.Binding;
import org.m3.m3lib.ast.Interface;
import org.m3.m3lib.ast.Module;
import org.m3.m3lib.ast.decl.ConstantDeclaration;
import org.m3.m3lib.ast.decl.Declaration;
import org.m3.m3lib.ast.decl.ExceptionDeclaration;
import org.m3.m3lib.ast.decl.ProcedureDeclaration;
import org.m3.m3lib.ast.decl.Revelation;
import org.m3.m3lib.ast.decl.TypeDeclaration;
import org.m3.m3lib.ast.decl.VariableDeclaration;
import org.m3.m3lib.ast.expr.Expression;
import org.m3.m3lib.ast.stat.BlockStatement;
import org.m3.m3lib.ast.type.Type;
import org.m3.m3lib.reflect.visitor.Visitor;
import org.m3.m3lib.reflect.visitor.Walker;

/**
 * 
 * @author LavermB
 * 
 */
public class ASTPrinter
  implements Visitor
{

  private PrintWriter       w_;

  private int               margin_    = 0;

  private boolean           needSem_   = false;

  private boolean           needNl_    = false;

  private boolean           needSpace_ = false;

  private TypePrinter       typePrinter_;

  private ExpressionPrinter expressionPrinter_;

  private StatementPrinter  statementPrinter_;

  /**
   * 
   */
  public ASTPrinter(PrintWriter w)
  {
    super();

    w_ = w;
    typePrinter_ = new TypePrinter(this);
    expressionPrinter_ = new ExpressionPrinter(this);
    statementPrinter_ = new StatementPrinter(this);

    w.println("(* Created ASTPrinter *)");
  }

  public void setNeedSem(boolean needSem)
  {
    this.needSem_ = needSem;
  }

  public void setNeedSem()
  {
    setNeedSem(true);
  }

  public boolean needSem()
  {
    return needSem_;
  }

  /**
   * @return the needSpace
   */
  public boolean needSpace()
  {
    return this.needSpace_;
  }

  /**
   * @param needSpace the needSpace to set
   */
  public void setNeedSpace(boolean needSpace)
  {
    this.needSpace_ = needSpace;
  }

  public void requestNewLine()
  {
    needNl_ = true;
  }

  public void noNewLine()
  {
    needNl_ = false;
  }

  public boolean needNl()
  {
    return needNl_;
  }

  public void indent()
  {
    margin_ += 2;
  }

  public void outDent()
  {
    if (margin_ > 0) {
      margin_ -= 2;
    }
  }

  public void print(M3Token token)
  {
    checkForSpace();
    checkForNl();
    w_.print(token.toString());
  }

  public void print(String s)
  {
    checkForSpace();
    checkForNl();
    w_.print(s);
  }

  public void printSpace()
  {
    if (needSem()) {
      w_.print(M3Token.SEMICOLON.toString());
      setNeedSem(false);
    }
    checkForNl();
    w_.print(' ');
    setNeedSpace(false);
  }

  public void newLine()
  {
    if (needSem()) {
      w_.print(M3Token.SEMICOLON.toString());
      setNeedSem(false);
    }
    w_.println();
    for (int i = margin_; i > 0; i--) {
      w_.print(' ');
    }
    noNewLine();
  }

  public void checkForNl()
  {
    if (needNl()) {
      newLine();
    }
  }

  public void checkForSpace()
  {
    if (needSpace()) {
      printSpace();
    }
  }

  public void startVisit(Binding b)
  {
    print(b.getName());
    printSpace();
    print(M3Token.BECOMES);
    printSpace();
    Walker.walk(b.getValue(), this);
    setNeedSem();
  }

  public void startVisit(ConstantDeclaration d)
  {
    print(d.getName());
    Type t = d.getType();
    if (t != null) {
      print(M3Token.COLON);
      printSpace();
      Walker.walk(t, getTypePrinter());
    }
    printSpace();
    print(M3Token.EQ);
    printSpace();
    Walker.walk(d.getValue(), getExpressionPrinter());
    setNeedSem();
    requestNewLine();
  }

  public void startVisit(TypeDeclaration t)
  {
    print(t.getName());
    printSpace();
    print(t.getOper());
    printSpace();
    Walker.walk(t.getType(), getTypePrinter());
    setNeedSem();
    requestNewLine();
  }

  public void startVisit(ExceptionDeclaration d)
  {
    print(d.getName());
    if (d.getParam() != null) {
      print(M3Token.LPAREN);
      Walker.walk(d.getParam(), getTypePrinter());
      print(M3Token.RPAREN);
    }
    setNeedSem();
    requestNewLine();
  }

  public void startVisit(VariableDeclaration d)
  {
    print(d.getName());
    Type t = d.getType();
    if (t != null) {
      print(M3Token.COLON);
      printSpace();
      Walker.walk(t, getTypePrinter());
    }
    Expression expr = d.getInitializer();
    if (expr != null) {
      printSpace();
      print(M3Token.BECOMES);
      printSpace();
      Walker.walk(expr, getExpressionPrinter());
    }
    setNeedSem();
    requestNewLine();
  }

  public void startVisit(ProcedureDeclaration d)
  {
    print(M3Token.PROCEDURE);
    printSpace();
    print(d.getName());
    getTypePrinter().printProcType(d.getType(), true);
    BlockStatement body = d.getBody();
    if (body != null) {
      printSpace();
      print(M3Token.EQ);
      requestNewLine();
      indent();
      Walker.walk(body, getStatementPrinter());
      setNeedSem(false);
      printSpace();
      print(d.getName());
      outDent();
    }
    setNeedSem();
    newLine();
    newLine();
  }

  public static String getSectionLeader(Declaration d)
  {
    if (d instanceof ConstantDeclaration) {
      return M3Token.CONST.toString();
    }
    else if (d instanceof ExceptionDeclaration) {
      return M3Token.EXCEPTION.toString();
    }
    else if (d instanceof ProcedureDeclaration) {
      return null; // Not a block of declarations!
    }
    else if (d instanceof Revelation) {
      return M3Token.REVEAL.toString();
    }
    else if (d instanceof TypeDeclaration) {
      return M3Token.TYPE.toString();
    }
    else if (d instanceof VariableDeclaration) {
      return M3Token.VAR.toString();
    }
    else {
      return null;
    }
  }
  public void startVisit(Interface intf)
  {
    print(M3Token.INTERFACE);
    printSpace();
    print(intf.getName());
    setNeedSem();
    requestNewLine();

    String leader = null;
    for (Declaration d : intf.getDecls()) {
      String thisLeader = getSectionLeader(d);
      if ((leader == null) || !leader.equals(thisLeader)) {
        if (leader != null) {
          outDent();
          newLine();
          newLine();
        }
        leader = thisLeader;
        if (leader != null) {
          print(thisLeader);
          requestNewLine();
          indent();
        }
      }
      Walker.walk(d, this);
    }
    outDent();
    print(M3Token.END);
    printSpace();
    print(intf.getName());
    print(M3Token.DOT);
    newLine();
  }

  public void startVisit(Module mod)
  {
    print(M3Token.MODULE);
    printSpace();
    print(mod.getName());
    requestNewLine();
    indent();
    print(M3Token.EXPORTS);
    printSpace();
    boolean needComma = false;
    for (String s : mod.getExports()) {
      if (needComma) {
        print(M3Token.COMMA);
        printSpace();
      }
      else {
        needComma = true;
      }
      print(s);
    }
    outDent();
    setNeedSem();
    requestNewLine();

    for (String s : mod.getImports()) {
      int slash = s.indexOf('/');
      if (slash != -1) {
        print(M3Token.FROM);
        printSpace();
        print(s.substring(0, slash));
        printSpace();
        print(M3Token.IMPORT);
        print(s.substring(slash + 1));
        setNeedSem();
        requestNewLine();
      }
      else {
        print(M3Token.IMPORT);
        printSpace();
        print(s);
        setNeedSem();
        requestNewLine();
      }
    }
    Walker.walk(mod.getBody(), getStatementPrinter());

    setNeedSem(false);
    printSpace();
    print(mod.getName());
    print(M3Token.DOT);
    newLine();
  }

  /**
   * @return the typePrinter
   */
  public TypePrinter getTypePrinter()
  {
    return this.typePrinter_;
  }

  /**
   * @return the expressionPrinter
   */
  public ExpressionPrinter getExpressionPrinter()
  {
    return this.expressionPrinter_;
  }

  /**
   * @return the statementPrinter
   */
  public StatementPrinter getStatementPrinter()
  {
    return this.statementPrinter_;
  }
}

/*
 * $Log$
 */
