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
import org.m3.m3lib.ast.decl.Declaration;
import org.m3.m3lib.ast.stat.AssignmentStatement;
import org.m3.m3lib.ast.stat.BlockStatement;
import org.m3.m3lib.ast.stat.CaseStatement;
import org.m3.m3lib.ast.stat.ExitStatement;
import org.m3.m3lib.ast.stat.ForInStatement;
import org.m3.m3lib.ast.stat.ForStatement;
import org.m3.m3lib.ast.stat.IfStatement;
import org.m3.m3lib.ast.stat.LockStatement;
import org.m3.m3lib.ast.stat.LoopStatement;
import org.m3.m3lib.ast.stat.RaiseStatement;
import org.m3.m3lib.ast.stat.RepeatStatement;
import org.m3.m3lib.ast.stat.ReturnStatement;
import org.m3.m3lib.ast.stat.Statement;
import org.m3.m3lib.ast.stat.TryStatement;
import org.m3.m3lib.ast.stat.TypeCaseStatement;
import org.m3.m3lib.ast.stat.WhileStatement;
import org.m3.m3lib.ast.stat.WithStatement;
import org.m3.m3lib.reflect.visitor.Visitor;
import org.m3.m3lib.reflect.visitor.Walker;

/**
 * @author bertl
 * 
 */
public class StatementPrinter
  implements Visitor
{

  private ASTPrinter base_;

  /**
   * @param base
   */
  public StatementPrinter(ASTPrinter base)
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

  public void startVisit(BlockStatement s)
  {
    getBase().requestNewLine();
    String leader = null;
    for (Declaration d : s.getDecls()) {
      String thisLeader = ASTPrinter.getSectionLeader(d);
      if ((leader == null) || !leader.equals(thisLeader)) {
        if (leader != null) {
          getBase().outDent();
          getBase().newLine();
          getBase().newLine();
        }
        leader = thisLeader;
        if (leader != null) {
          getBase().print(thisLeader);
          getBase().requestNewLine();
          getBase().indent();
        }
      }
      Walker.walk(d, getBase());
    }
    getBase().requestNewLine();
    getBase().outDent();
    getBase().print(M3Token.BEGIN);
    getBase().requestNewLine();
    getBase().indent();
  }

  public void betweenChildVisits(BlockStatement s)
  {
    getBase().setNeedSem();
    getBase().requestNewLine();
  }
  public void endVisit(BlockStatement s)
  {
    getBase().outDent();
    getBase().requestNewLine();
    getBase().setNeedSem(false);
    getBase().print(M3Token.END);
    getBase().setNeedSem();
  }

  public void startVisit(ForInStatement s)
  {
    getBase().requestNewLine();
    getBase().print(M3Token.FOR);
    getBase().printSpace();
    getBase().print(s.getId());
    getBase().printSpace();
    getBase().print(M3Token.IN);
    getBase().printSpace();
    Walker.walk(s.getSet(), this);
    getBase().printSpace();
    getBase().print(M3Token.DO);
    getBase().indent();
  }

  public void endVisit(ForInStatement s)
  {
    getBase().outDent();
    getBase().requestNewLine();
    getBase().setNeedSem(false);
    getBase().print(M3Token.END);
    getBase().setNeedSem();
  }

  public void startVisit(ReturnStatement s)
  {
    getBase().print(M3Token.RETURN);
    if (s.getResult() != null) {
      getBase().printSpace();
      Walker.walk(s.getResult(), getBase().getExpressionPrinter());
    }
  }

  public void startVisit(RaiseStatement s)
  {
    getBase().print(M3Token.RAISE);
    getBase().printSpace();
    Walker.walk(s.getName(), getBase().getExpressionPrinter());
    if (s.getParam() != null) {
      getBase().print(M3Token.LPAREN);
      Walker.walk(s.getParam(), getBase().getExpressionPrinter());
      getBase().print(M3Token.RPAREN);
    }
  }

  private void doStatements(List<Statement> stats)
  {
    for (Statement s: stats) {
      Walker.walk(s, this);
      getBase().setNeedSem();
      getBase().requestNewLine();
    }
  }

  public void startVisit(CaseStatement s)
  {
    getBase().print(M3Token.CASE);
    getBase().setNeedSpace(true);
    Walker.walk(s.getValue(), getBase().getExpressionPrinter());
    getBase().setNeedSpace(true);
    getBase().print(M3Token.OF);
    getBase().requestNewLine();
  }
  public void betweenChildVisits(CaseStatement s)
  {
    getBase().requestNewLine();
  }
  public void endVisit(CaseStatement s)
  {
    getBase().requestNewLine();
    if (s.getElsePart().size() > 0) {
      getBase().print(M3Token.ELSE);
      getBase().requestNewLine();
      getBase().indent();
      doStatements(s.getElsePart());
      getBase().outDent();
    }
    getBase().print(M3Token.END);
  }

  public void startVisit(CaseStatement.Alternative a)
  {
    getBase().print(M3Token.BAR);
    getBase().setNeedSpace(true);
    boolean needComma = false;
    for (CaseStatement.Label l: a.getLabels()) {
      if (needComma) {
        getBase().print(M3Token.COMMA);
      }
      else {
        needComma = true;
      }
      Walker.walk(l.getValue(), getBase().getExpressionPrinter());
      if (l.getUpto() != null) {
        getBase().print(M3Token.UPTO);
        Walker.walk(l.getUpto(), getBase().getExpressionPrinter());
      }
    }
    getBase().setNeedSpace(true);
    getBase().print(M3Token.IMPLY);
    getBase().requestNewLine();
    getBase().indent();
  }
  public void betweenChildVisits(CaseStatement.Alternative a)
  {
    getBase().requestNewLine();
  }
  public void endVisit(CaseStatement.Alternative a)
  {
    getBase().outDent();
  }

  public void startVisit(TypeCaseStatement s)
  {
    getBase().print(M3Token.TYPECASE);
    getBase().setNeedSpace(true);
    Walker.walk(s.getValue(), getBase().getExpressionPrinter());
    getBase().setNeedSpace(true);
    getBase().print(M3Token.OF);
    getBase().requestNewLine();
  }
  public void betweenChildVisits(TypeCaseStatement s)
  {
    getBase().requestNewLine();
  }
  public void endVisit(TypeCaseStatement s)
  {
    getBase().requestNewLine();
    if (s.getElsePart().size() > 0) {
      getBase().print(M3Token.ELSE);
      getBase().requestNewLine();
      getBase().indent();
      doStatements(s.getElsePart());
      getBase().outDent();
    }
    getBase().print(M3Token.END);
  }

  public void startVisit(TypeCaseStatement.Alternative a)
  {
    getBase().print(M3Token.BAR);
    getBase().setNeedSpace(true);
    boolean needComma = false;
    for (TypeCaseStatement.Label l: a.getLabels()) {
      if (needComma) {
        getBase().print(M3Token.COMMA);
      }
      else {
        needComma = true;
      }
      Walker.walk(l.getType(), getBase().getTypePrinter());
    }
    if (a.getId() != null) {
      getBase().print(M3Token.LPAREN);
      getBase().print(a.getId());
      getBase().print(M3Token.RPAREN);
    }
    getBase().setNeedSpace(true);
    getBase().print(M3Token.IMPLY);
    getBase().requestNewLine();
    getBase().indent();
  }
  public void betweenChildVisits(TypeCaseStatement.Alternative a)
  {
    getBase().requestNewLine();
  }
  public void endVisit(TypeCaseStatement.Alternative a)
  {
    getBase().outDent();
  }

  public void startVisit(TryStatement s)
  {
    getBase().print(M3Token.TRY);
    getBase().requestNewLine();
    getBase().indent();
    doStatements(s.getBody());
    getBase().outDent();
    if (s.getAlternatives().size() > 0) {
      getBase().print(M3Token.EXCEPT);
      getBase().requestNewLine();
    }
  }
  public void betweenChildVisits(TryStatement s)
  {
    getBase().requestNewLine();
  }
  public void endVisit(TryStatement s)
  {
    getBase().requestNewLine();
    if (s.getElsePart().size() > 0) {
      getBase().print(M3Token.ELSE);
      getBase().requestNewLine();
      getBase().indent();
      doStatements(s.getElsePart());
      getBase().outDent();
    }
    if (s.getFinallyPart().size() > 0) {
      getBase().print(M3Token.FINALLY);
      getBase().requestNewLine();
      getBase().indent();
      doStatements(s.getFinallyPart());
      getBase().outDent();
    }
    getBase().print(M3Token.END);
  }

  public void startVisit(TryStatement.Alternative a)
  {
    getBase().print(M3Token.BAR);
    getBase().setNeedSpace(true);
    boolean needComma = false;
    for (TryStatement.Label l: a.getLabels()) {
      if (needComma) {
        getBase().print(M3Token.COMMA);
      }
      else {
        needComma = true;
      }
      Walker.walk(l.getName(), getBase().getExpressionPrinter());
    }
    if (a.getId() != null) {
      getBase().print(M3Token.LPAREN);
      getBase().print(a.getId());
      getBase().print(M3Token.RPAREN);
    }
    getBase().setNeedSpace(true);
    getBase().print(M3Token.IMPLY);
    getBase().requestNewLine();
    getBase().indent();
  }
  public void betweenChildVisits(TryStatement.Alternative a)
  {
    getBase().requestNewLine();
  }
  public void endVisit(TryStatement.Alternative a)
  {
    getBase().outDent();
  }

  public void startVisit(AssignmentStatement s)
  {
    if (s.getLhs() == null) {
      getBase().print(M3Token.EVAL);
      getBase().printSpace();
      Walker.walk(s.getRhs(), getBase().getExpressionPrinter());
    }
    else {
      Walker.walk(s.getLhs(), getBase().getExpressionPrinter());
      if (s.getRhs() != null) {
        getBase().printSpace();
        getBase().print(M3Token.BECOMES);
        getBase().printSpace();
        Walker.walk(s.getRhs(), getBase().getExpressionPrinter());
      }
    }
  }
  public void startVisit(Statement s)
  {
    getBase().requestNewLine();
  }

  public void endVisit(Statement s)
  {
    getBase().setNeedSem();
  }

  public void startVisit(ExitStatement s)
  {
    getBase().print(M3Token.EXIT);
  }

  public void startVisit(ForStatement s)
  {
    getBase().print(M3Token.FOR);
    getBase().setNeedSpace(true);
    getBase().print(s.getId());
    getBase().print(M3Token.BECOMES);
    Walker.walk(s.getLwb(), getBase().getExpressionPrinter());
    getBase().setNeedSpace(true);
    getBase().print(M3Token.TO);
    getBase().setNeedSpace(true);
    Walker.walk(s.getUpb(), getBase().getExpressionPrinter());
    if (s.getBy() != null) {
      getBase().setNeedSpace(true);
      getBase().print(M3Token.BY);
      getBase().setNeedSpace(true);
      Walker.walk(s.getBy(), getBase().getExpressionPrinter());
    }
    getBase().setNeedSpace(true);
    getBase().print(M3Token.DO);
    getBase().requestNewLine();
    getBase().indent();
  }
  public void betweenChildVisits(ForStatement s)
  {
    getBase().setNeedSem();
    getBase().requestNewLine();
  }
  public void endVisit(ForStatement s)
  {
    getBase().requestNewLine();
    getBase().outDent();
    getBase().print(M3Token.END);
  }

  public void startVisit(LockStatement s)
  {
    getBase().print(M3Token.LOCK);
    getBase().setNeedSpace(true);
    Walker.walk(s.getCond(), getBase().getExpressionPrinter());
    getBase().setNeedSpace(true);
    getBase().print(M3Token.DO);
    getBase().requestNewLine();
    getBase().indent();
  }
  public void betweenChildVisits(LockStatement s)
  {
    getBase().setNeedSem();
    getBase().requestNewLine();
  }
  public void endVisit(LockStatement s)
  {
    getBase().requestNewLine();
    getBase().outDent();
    getBase().print(M3Token.END);
  }

  public void startVisit(LoopStatement s)
  {
    getBase().print(M3Token.LOOP);
    getBase().requestNewLine();
    getBase().indent();
  }
  public void betweenChildVisits(LoopStatement s)
  {
    getBase().setNeedSem();
    getBase().requestNewLine();
  }
  public void endVisit(LoopStatement s)
  {
    getBase().requestNewLine();
    getBase().outDent();
    getBase().print(M3Token.END);
  }

  public void startVisit(RepeatStatement s)
  {
    getBase().print(M3Token.REPEAT);
    getBase().requestNewLine();
    getBase().indent();
  }
  public void betweenChildVisits(RepeatStatement s)
  {
    getBase().setNeedSem();
    getBase().requestNewLine();
  }
  public void endVisit(RepeatStatement s)
  {
    getBase().requestNewLine();
    getBase().outDent();
    getBase().print(M3Token.UNTIL);
    getBase().setNeedSpace(true);
    Walker.walk(s.getCond(), getBase().getExpressionPrinter());
  }

  public void startVisit(WhileStatement s)
  {
    getBase().print(M3Token.WHILE);
    getBase().setNeedSpace(true);
    Walker.walk(s.getCond(), getBase().getExpressionPrinter());
    getBase().setNeedSpace(true);
    getBase().print(M3Token.DO);
    getBase().requestNewLine();
    getBase().indent();
  }
  public void betweenChildVisits(WhileStatement s)
  {
    getBase().setNeedSem();
    getBase().requestNewLine();
  }
  public void endVisit(WhileStatement s)
  {
    getBase().requestNewLine();
    getBase().outDent();
    getBase().print(M3Token.END);
  }

  public void startVisit(WithStatement s)
  {
    getBase().print(M3Token.WITH);
    getBase().requestNewLine();
    getBase().indent();
    boolean needComma = false;
    for (WithStatement.Binding binding: s.getBindings()) {
      if (needComma) {
        getBase().print(M3Token.COMMA);
        getBase().requestNewLine();
      }
      else {
        needComma = true;
      }
      getBase().print(binding.getId());
      getBase().setNeedSpace(true);
      getBase().print(M3Token.EQ);
      getBase().setNeedSpace(true);
      Walker.walk(binding.getValue(), getBase().getExpressionPrinter());
    }
    getBase().requestNewLine();
    getBase().outDent();
    getBase().print(M3Token.DO);
    getBase().requestNewLine();
    getBase().indent();
  }
  public void betweenChildVisits(WithStatement s)
  {
    getBase().setNeedSem();
    getBase().requestNewLine();
  }
  public void endVisit(WithStatement s)
  {
    getBase().requestNewLine();
    getBase().outDent();
    getBase().print(M3Token.END);
  }

  public void startVisit(IfStatement s)
  {
    getBase().print(M3Token.IF);
    getBase().setNeedSpace(true);
    Walker.walk(s.getIfThen().getCond(), getBase().getExpressionPrinter());
    getBase().setNeedSpace(true);
    getBase().print(M3Token.THEN);
    getBase().requestNewLine();
    getBase().indent();
    doStatements(s.getIfThen().getThenPart());
    getBase().requestNewLine();
    getBase().outDent();
    for (IfStatement.IfThen elsif: s.getElsifs()) {
      getBase().print(M3Token.ELSIF);
      getBase().setNeedSpace(true);
      Walker.walk(elsif.getCond(), getBase().getExpressionPrinter());
      getBase().setNeedSpace(true);
      getBase().print(M3Token.THEN);
      getBase().requestNewLine();
      getBase().indent();
      doStatements(elsif.getThenPart());
      getBase().requestNewLine();
      getBase().outDent();
    }
    if (s.getElsePart().size() > 0) {
      getBase().print(M3Token.ELSE);
      getBase().requestNewLine();
      getBase().indent();
      doStatements(s.getElsePart());
      getBase().requestNewLine();
      getBase().outDent();
    }
    getBase().print(M3Token.END);
  }

}

/*
 * $Log$
 */
