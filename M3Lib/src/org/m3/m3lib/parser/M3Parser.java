/**
 * Copyright (c) 2008 B. Laverman. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: bertl
 */

// $Id$
package org.m3.m3lib.parser;

import static org.m3.m3lib.M3Token.AND;
import static org.m3.m3lib.M3Token.ANY;
import static org.m3.m3lib.M3Token.ARRAY;
import static org.m3.m3lib.M3Token.BAR;
import static org.m3.m3lib.M3Token.BECOMES;
import static org.m3.m3lib.M3Token.BEGIN;
import static org.m3.m3lib.M3Token.BITS;
import static org.m3.m3lib.M3Token.BRANDED;
import static org.m3.m3lib.M3Token.BY;
import static org.m3.m3lib.M3Token.CASE;
import static org.m3.m3lib.M3Token.COLON;
import static org.m3.m3lib.M3Token.COMMA;
import static org.m3.m3lib.M3Token.CONST;
import static org.m3.m3lib.M3Token.DO;
import static org.m3.m3lib.M3Token.DOT;
import static org.m3.m3lib.M3Token.ELSE;
import static org.m3.m3lib.M3Token.ELSIF;
import static org.m3.m3lib.M3Token.END;
import static org.m3.m3lib.M3Token.EQ;
import static org.m3.m3lib.M3Token.EVAL;
import static org.m3.m3lib.M3Token.EXCEPT;
import static org.m3.m3lib.M3Token.EXCEPTION;
import static org.m3.m3lib.M3Token.EXIT;
import static org.m3.m3lib.M3Token.EXPORTS;
import static org.m3.m3lib.M3Token.FINALLY;
import static org.m3.m3lib.M3Token.FOR;
import static org.m3.m3lib.M3Token.FROM;
import static org.m3.m3lib.M3Token.ID;
import static org.m3.m3lib.M3Token.IF;
import static org.m3.m3lib.M3Token.IMPLY;
import static org.m3.m3lib.M3Token.IMPORT;
import static org.m3.m3lib.M3Token.INTERFACE;
import static org.m3.m3lib.M3Token.LBRACE;
import static org.m3.m3lib.M3Token.LBRACK;
import static org.m3.m3lib.M3Token.LOCK;
import static org.m3.m3lib.M3Token.LOOP;
import static org.m3.m3lib.M3Token.LPAREN;
import static org.m3.m3lib.M3Token.METHODS;
import static org.m3.m3lib.M3Token.MINUS;
import static org.m3.m3lib.M3Token.MODULE;
import static org.m3.m3lib.M3Token.NOT;
import static org.m3.m3lib.M3Token.OBJECT;
import static org.m3.m3lib.M3Token.OF;
import static org.m3.m3lib.M3Token.OR;
import static org.m3.m3lib.M3Token.OVERRIDES;
import static org.m3.m3lib.M3Token.PLUS;
import static org.m3.m3lib.M3Token.PRAGMA;
import static org.m3.m3lib.M3Token.PROCEDURE;
import static org.m3.m3lib.M3Token.RAISE;
import static org.m3.m3lib.M3Token.RAISES;
import static org.m3.m3lib.M3Token.RBRACE;
import static org.m3.m3lib.M3Token.RBRACK;
import static org.m3.m3lib.M3Token.RECORD;
import static org.m3.m3lib.M3Token.REF;
import static org.m3.m3lib.M3Token.REPEAT;
import static org.m3.m3lib.M3Token.RETURN;
import static org.m3.m3lib.M3Token.REVEAL;
import static org.m3.m3lib.M3Token.ROOT;
import static org.m3.m3lib.M3Token.RPAREN;
import static org.m3.m3lib.M3Token.SEMICOLON;
import static org.m3.m3lib.M3Token.SET;
import static org.m3.m3lib.M3Token.SUBTYPE;
import static org.m3.m3lib.M3Token.THEN;
import static org.m3.m3lib.M3Token.TO;
import static org.m3.m3lib.M3Token.TRY;
import static org.m3.m3lib.M3Token.TYPE;
import static org.m3.m3lib.M3Token.TYPECASE;
import static org.m3.m3lib.M3Token.UNSAFE;
import static org.m3.m3lib.M3Token.UNTIL;
import static org.m3.m3lib.M3Token.UNTRACED;
import static org.m3.m3lib.M3Token.UP;
import static org.m3.m3lib.M3Token.UPTO;
import static org.m3.m3lib.M3Token.VAR;
import static org.m3.m3lib.M3Token.WHILE;
import static org.m3.m3lib.M3Token.WITH;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.m3.m3lib.M3Token;
import org.m3.m3lib.ast.Interface;
import org.m3.m3lib.ast.M3CompilationUnit;
import org.m3.m3lib.ast.Module;
import org.m3.m3lib.ast.decl.ConstantDeclaration;
import org.m3.m3lib.ast.decl.Declaration;
import org.m3.m3lib.ast.decl.ExceptionDeclaration;
import org.m3.m3lib.ast.decl.ProcedureDeclaration;
import org.m3.m3lib.ast.decl.Revelation;
import org.m3.m3lib.ast.decl.TypeDeclaration;
import org.m3.m3lib.ast.decl.VariableDeclaration;
import org.m3.m3lib.ast.expr.AndExpression;
import org.m3.m3lib.ast.expr.ApplicationExpression;
import org.m3.m3lib.ast.expr.ConstructorExpression;
import org.m3.m3lib.ast.expr.DerefExpression;
import org.m3.m3lib.ast.expr.Expression;
import org.m3.m3lib.ast.expr.IdExpression;
import org.m3.m3lib.ast.expr.IndexExpression;
import org.m3.m3lib.ast.expr.MathExpression;
import org.m3.m3lib.ast.expr.MinusExpression;
import org.m3.m3lib.ast.expr.NameExpression;
import org.m3.m3lib.ast.expr.NotExpression;
import org.m3.m3lib.ast.expr.OrExpression;
import org.m3.m3lib.ast.expr.PlusExpression;
import org.m3.m3lib.ast.expr.RelationalExpression;
import org.m3.m3lib.ast.expr.SelectExpression;
import org.m3.m3lib.ast.expr.TypeExpression;
import org.m3.m3lib.ast.expr.ValueExpression;
import org.m3.m3lib.ast.stat.AssignmentStatement;
import org.m3.m3lib.ast.stat.BlockStatement;
import org.m3.m3lib.ast.stat.CaseStatement;
import org.m3.m3lib.ast.stat.ExitStatement;
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
import org.m3.m3lib.ast.value.CharValue;
import org.m3.m3lib.ast.value.ExtendedValue;
import org.m3.m3lib.ast.value.IntegerValue;
import org.m3.m3lib.ast.value.LongRealValue;
import org.m3.m3lib.ast.value.RealValue;
import org.m3.m3lib.ast.value.TextValue;
import org.m3.m3lib.scanner.Scanner;
import org.m3.m3lib.scanner.ScannerException;
import org.m3.m3lib.scanner.SourceLocation;

/**
 * 
 * @author LavermB
 * 
 */
public class M3Parser
  extends ParserBase<M3Token>
{

  /** Creates a new instance of StatementParser */
  public M3Parser(Scanner<M3Token> scanner)
  {
    super(scanner);
  }

  public NameExpression parseName()
    throws M3ParserException
  {
    NameExpression name = new NameExpression();

    name.setSourceLocation(getSourceLocation());

    do {
      expect(M3Token.ID);
      IdExpression id = new IdExpression(getStrVal());
      id.setSourceLocation(getSourceLocation());

      name.addName(id);

      skip();
    } while (skipIf(M3Token.DOT));

    return name;
  }

  public List<Declaration> parseConstantDecl()
    throws M3ParserException, ScannerException
  {
    skip(M3Token.CONST);

    List<Declaration> decls = new ArrayList<Declaration>();

    while (match(M3Token.ID)) {
      String name = getStrVal();
      ConstantDeclaration decl = new ConstantDeclaration(name);
      skip();
      if (skipIf(M3Token.COLON)) {
        decl.setType(parseType());
      }
      skipIf(M3Token.EQ);
      decl.setValue(parseExpression());

      decls.add(decl);

      skip(M3Token.SEMICOLON);

      skipIf(PRAGMA);
    }
    return decls;
  }

  public List<Declaration> parseVariableDecl()
    throws M3ParserException, ScannerException
  {
    skip(M3Token.VAR);
    expect(M3Token.ID); // Enforce at least one declaration

    List<Declaration> decls = new ArrayList<Declaration>();

    while (match(M3Token.ID)) {
      List<String> ids = new ArrayList<String>();
      Type type = null;
      Expression init = null;

      do {
        expect(ID);
        ids.add(getStrVal());
        skip();
      } while (skipIf(COMMA));

      if (skipIf(M3Token.COLON)) {
        type = parseType();
      }
      if (skipIf(M3Token.BECOMES)) {
        init = parseExpression();
      }
      skip(M3Token.SEMICOLON);

      if ((type == null) && (init == null)) {
        syntaxError("A variable declaration needs at least one of type and initializer");
      }
      for (String id: ids) {
        VariableDeclaration decl = new VariableDeclaration(id);
        decl.setType(type);
        decl.setInitializer(init);

        decls.add(decl);
      }
    }
    return decls;
  }

  public void parseFormal(List<ProcedureType.FormalArgument> formals)
    throws M3ParserException, ScannerException
  {
    skipIf(PRAGMA);
    ProcedureType.ArgumentKind kind = ProcedureType.ArgumentKind.AK_VALUE;
    if (skipIf(M3Token.VAR)) {
      kind = ProcedureType.ArgumentKind.AK_VAR;
    }
    else if (skipIf(M3Token.READONLY)) {
      kind = ProcedureType.ArgumentKind.AK_CONST;
    }
    else {
      skipIf(M3Token.VALUE);
    }
    List<String> ids = new ArrayList<String>();
    do {
      expect(M3Token.ID);
      ids.add(getStrVal());
      skip();
    } while (skipIf(M3Token.COMMA));

    Type type = null;
    Expression value = null;

    if (skipIf(M3Token.COLON)) {
      type = parseType();
    }
    if (skipIf(M3Token.BECOMES)) {
      value = parseExpression();
    }
    if ((type == null) && (value == null)) {
      syntaxError("Expected type or default value for formal");
    }
    for (String id : ids) {
      formals.add(new ProcedureType.FormalArgument(id, kind, type, value));
    }
  }

  public ProcedureType parseSignature()
    throws M3ParserException, ScannerException
  {
    List<ProcedureType.FormalArgument> formals = new ArrayList<ProcedureType.FormalArgument>();

    skip(M3Token.LPAREN);
    while (!match(M3Token.RPAREN)) {
      parseFormal(formals);

      if (!match(RPAREN) && !match(SEMICOLON)) {
        syntaxError("Expected semicolon or end of formal list");
        skipTo(RPAREN);
      }
      skipIf(SEMICOLON);
    }
    while (skipIf(SEMICOLON))
      ;
    skip(RPAREN);

    Type result = null;
    if (skipIf(COLON)) {
      result = parseType();
    }

    ProcedureType procType = new ProcedureType();
    procType.setArgs(formals);
    procType.setReturnType(result);

    if (skipIf(RAISES)) {
      if (skipIf(LBRACE)) {
        if (match(ID)) {
          do {
            procType.addRaises(parseName());
          } while (skipIf(COMMA));
        }
        skip(RBRACE);
      }
      else {
        skip(ANY);
        procType.setRaisingAny(true);
      }
    }
    return procType;
  }

  public List<Declaration> parseProcedureDecl(boolean allowProcDef)
    throws M3ParserException, ScannerException
  {
    SourceLocation loc = getSourceLocation();

    List<Declaration> decls = new ArrayList<Declaration>();

    skip(PROCEDURE);
    expect(ID);
    final String name = getStrVal();
    skip();
    ProcedureType procType = parseSignature();
    procType.setSourceLocation(loc);

    BlockStatement body = null;
    if (allowProcDef) {
      skip(EQ);
      body = parseBlockStatement();
      expect(ID);
      if (!name.equals(getStrVal())) {
        syntaxError("Closing id doesn't match PROCEDURE's name. Expected \"" +
                    name + "\", found \"" + getStrVal() + "\".");
      }
      skip();
    }
    ProcedureDeclaration d = new ProcedureDeclaration(name, procType, body);
    decls.add(d);

    skip(SEMICOLON);

    skipIf(PRAGMA);

    return decls;
  }

  /**
   * @param decls
   * @throws M3ParserException
   * @throws ScannerException
   */
  public List<Declaration> parseRevelations()
    throws M3ParserException, ScannerException
  {
    skip(REVEAL);

    List<Declaration> decls = new ArrayList<Declaration>();

    while (match(ID)) {
      NameExpression name = parseName();
      M3Token oper = getCur();
      if (!match(EQ) && !match(SUBTYPE)) {
        syntaxError("Expected \"=\" or \"<:\"");
        tokenInserted(EQ);
        oper = EQ;
      }
      else {
        skip();
      }
      Type type = parseType();

      decls.add(new Revelation(name, oper, type));

      skip(SEMICOLON);

      skipIf(PRAGMA);
    }
    return decls;
  }

  /**
   * @param decls
   * @throws M3ParserException
   * @throws ScannerException
   */
  public List<Declaration> parseExceptionDecl()
    throws M3ParserException, ScannerException
  {
    skip(EXCEPTION);

    List<Declaration> decls = new ArrayList<Declaration>();

    while (match(ID)) {
      ExceptionDeclaration decl = new ExceptionDeclaration();
      decl.setName(getStrVal());
      decl.setSourceLocation(getSourceLocation());

      skip();
      if (skipIf(LPAREN)) {
        decl.setParam(parseType());
        skip(RPAREN);
      }
      skip(SEMICOLON);

      skipIf(PRAGMA);

      decls.add(decl);
    }
    return decls;
  }

  /**
   * @param decls
   */
  public List<Declaration> parseTypeDecl()
    throws M3ParserException, ScannerException
  {
    skip(TYPE);

    List<Declaration> decls = new ArrayList<Declaration>();

    while (match(ID)) {
      TypeDeclaration decl = new TypeDeclaration();
      decl.setName(getStrVal());
      decl.setSourceLocation(getSourceLocation());

      skip();
      M3Token oper = getCur();
      if (!match(EQ) && !match(SUBTYPE)) {
        syntaxError("Expected \"=\" or \"<:\"");
        tokenInserted(EQ);
        oper = EQ;
      }
      decl.setOper(oper);
      skip();
      decl.setType(parseType());

      decls.add(decl);

      skip(SEMICOLON);

      skipIf(PRAGMA);
    }
    return decls;
  }

  public static boolean isDeclarationLeader(M3Token token)
  {
    return (token == CONST) || (token == VAR) || (token == PROCEDURE) ||
           (token == TYPE) || (token == EXCEPTION) || (token == REVEAL);
  }

  public List<Declaration> parseDeclaration(boolean allowProcDef)
    throws ScannerException, M3ParserException
  {
    while (skipIf(PRAGMA));
    switch (getCur()) {
    case CONST:
      return parseConstantDecl();
    case TYPE:
      return parseTypeDecl();
    case EXCEPTION:
      return parseExceptionDecl();
    case VAR:
      return parseVariableDecl();
    case PROCEDURE:
      return parseProcedureDecl(allowProcDef);
    case REVEAL:
      return parseRevelations();
    }
    syntaxError("Declaration expected");
    return null;
  }

  public void parseDeclarations(List<Declaration> decls, boolean allowProcDef)
    throws ScannerException, M3ParserException
  {
    while (isDeclarationLeader(getCur())) {
      decls.addAll(parseDeclaration(allowProcDef));
    }
  }

  public void parseDeclarations(List<Declaration> decls)
    throws ScannerException, M3ParserException
  {
    parseDeclarations(decls, true);
  }

  public Statement parseEvalStatement()
    throws ScannerException, M3ParserException
  {
    Statement stat = null;

    skip(EVAL);
    stat = new AssignmentStatement(null, parseExpression());

    return stat;
  }

  public ForStatement parseForStatement()
    throws ScannerException, M3ParserException
  {
    ForStatement stat = new ForStatement();

    skip(FOR);
    expect(ID);
    stat.setId(getStrVal());
    skip();

    skip(BECOMES);
    stat.setLwb(parseExpression());
    skip(TO);
    stat.setUpb(parseExpression());
    if (skipIf(BY)) {
      stat.setBy(parseExpression());
    }
    skip(DO);
    parseStatements(stat.getDoPart());
    skip(END);

    return stat;
  }

  public IfStatement parseIfStatement()
    throws ScannerException, M3ParserException
  {
    skip(IF);

    IfStatement stat = new IfStatement();

    stat.getIfThen().setCond(parseExpression());
    skip(THEN);
    parseStatements(stat.getIfThen().getThenPart());

    while (skipIf(ELSIF)) {
      IfStatement.IfThen elsif = new IfStatement.IfThen(parseExpression());
      skip(THEN);
      parseStatements(elsif.getThenPart());
      stat.addElsif(elsif);
    }

    if (skipIf(ELSE)) {
      parseStatements(stat.getElsePart());
    }
    skip(END);

    return stat;
  }

  private Statement parseAssigmentStatement()
    throws ScannerException, M3ParserException
  {
    Statement stat = null;
    Expression lhs, rhs = null;

    lhs = parseExpression();
    if (lhs == null) {
      return null;
    }
    if (skipIf(BECOMES)) { // Assignment or just an expression?
      rhs = parseExpression();
    }
    stat = new AssignmentStatement(lhs, rhs);

    return stat;
  }

  private CaseStatement parseCaseStatement()
    throws ScannerException, M3ParserException
  {
    skip(CASE);
    CaseStatement stat = new CaseStatement(parseExpression());
    skip(OF);

    if ((getCur() != ELSE) && (getCur() != END)) {
      skipIf(BAR);
      do {
        CaseStatement.Alternative alt = new CaseStatement.Alternative();
        do {
          Expression value = parseExpression();
          if (skipIf(UPTO)) {
            alt.addLabel(value, parseExpression());
          }
          else {
            alt.addLabel(value);
          }
        } while (skipIf(COMMA));

        skip(IMPLY);
        parseStatements(alt.getStats());

        stat.addAlternative(alt);
      } while (skipIf(BAR));
    }
    if (skipIf(ELSE)) {
      parseStatements(stat.getElsePart());
    }
    skip(END);

    return stat;
  }

  private Statement parseReturnStatement()
    throws M3ParserException, ScannerException
  {
    skip(RETURN);
    Expression result = null;
    if (isExpressionLeader(getCur())) {
      result = parseExpression();
    }
    return new ReturnStatement(result);
  }

  /**
   * @return
   * @throws M3ParserException
   * @throws ScannerException
   */
  private Statement parseRaiseStatement()
    throws M3ParserException, ScannerException
  {
    RaiseStatement st = new RaiseStatement();

    st.setSourceLocation(getSourceLocation());

    skip(RAISE);
    st.setName(parseName());
    if (skipIf(LPAREN)) {
      st.setParam(parseExpression());
      skip(RPAREN);
    }
    return st;
  }

  private static Set<M3Token> statementLeaders_ = new HashSet<M3Token>();

  static {
    statementLeaders_.add(CASE);
    statementLeaders_.add(FOR);
    statementLeaders_.add(IF);
    statementLeaders_.add(EVAL);
    statementLeaders_.add(EXIT);
    statementLeaders_.add(LOCK);
    statementLeaders_.add(LOOP);
    statementLeaders_.add(RAISE);
    statementLeaders_.add(REPEAT);
    statementLeaders_.add(RETURN);
    statementLeaders_.add(TRY);
    statementLeaders_.add(TYPECASE);
    statementLeaders_.add(WHILE);
    statementLeaders_.add(WITH);
  }

  public boolean isStatementLeader(M3Token token)
  {
    return statementLeaders_.contains(token) || isExpressionLeader(token) ||
           isDeclarationLeader(token) || (getCur() == BEGIN);
  }

  public Statement parseStatement()
    throws ScannerException, M3ParserException
  {
    M3Token t = getCur();

    if (isDeclarationLeader(getCur()) || (getCur() == BEGIN)) {
      return parseBlockStatement();
    }
    switch (t) {
    case CASE:
      return parseCaseStatement();
    case FOR:
      return parseForStatement();
    case IF:
      return parseIfStatement();
    case EVAL:
      return parseEvalStatement();
    case EXIT:
      return parseExitStatement();
    case LOCK:
      return parseLockStatement();
    case LOOP:
      return parseLoopStatement();
    case RAISE:
      return parseRaiseStatement();
    case REPEAT:
      return parseRepeatStatement();
    case RETURN:
      return parseReturnStatement();
    case TRY:
      return parseTryStatement();
    case TYPECASE:
      return parseTypeCaseStatement();
    case WHILE:
      return parseWhileStatement();
    case WITH:
      return parseWithStatement();
    }
    // Anything else must be an assignment or no statement at all

    return parseAssigmentStatement();
  }

  /**
   * @return
   * @throws M3ParserException
   * @throws ScannerException
   */
  private WithStatement parseWithStatement()
    throws ScannerException, M3ParserException
  {
    skip(WITH);
    WithStatement stat = new WithStatement();
    do {
      expect(ID);
      String id = getStrVal();
      skip();
      skip(EQ);
      stat.addBinding(id, parseExpression());
    } while (skipIf(COMMA));
    skip(DO);
    parseStatements(stat.getBody());
    skip(END);

    return stat;
  }

  /**
   * @return
   * @throws M3ParserException
   * @throws ScannerException
   */
  private WhileStatement parseWhileStatement()
    throws ScannerException, M3ParserException
  {
    skip(WHILE);
    WhileStatement stat = new WhileStatement(parseExpression());
    skip(DO);
    parseStatements(stat.getBody());
    skip(END);

    return stat;
  }

  /**
   * @return
   * @throws M3ParserException
   * @throws ScannerException
   */
  private TryStatement parseTryStatement()
    throws ScannerException, M3ParserException
  {
    skip(TRY);
    TryStatement stat = new TryStatement();
    parseStatements(stat.getBody());
    if (skipIf(EXCEPT)) {
      if ((getCur() != ELSE) && (getCur() != END) && (getCur() != FINALLY)) {
        skipIf(BAR);
        do {
          TryStatement.Alternative alt = new TryStatement.Alternative();
          do {
            alt.addLabel(parseName());
          } while (skipIf(COMMA));

          if (skipIf(LPAREN)) {
            expect(ID);
            alt.setId(getStrVal());
            skip();
            skip(RPAREN);
          }
          skip(IMPLY);
          parseStatements(alt.getStats());

          stat.addAlternative(alt);
        } while (skipIf(BAR));
      }
      if (skipIf(ELSE)) {
        parseStatements(stat.getElsePart());
      }
    }
    if (skipIf(FINALLY)) {
      parseStatements(stat.getFinallyPart());
    }
    skip(END);

    return stat;
  }

  /**
   * @return
   * @throws M3ParserException
   * @throws ScannerException
   */
  private TypeCaseStatement parseTypeCaseStatement()
    throws ScannerException, M3ParserException
  {
    skip(TYPECASE);
    TypeCaseStatement stat = new TypeCaseStatement(parseExpression());
    skip(OF);

    if ((getCur() != ELSE) && (getCur() != END)) {
      skipIf(BAR);
      do {
        TypeCaseStatement.Alternative alt = new TypeCaseStatement.Alternative();
        do {
          alt.addLabel(parseType());
        } while (skipIf(COMMA));

        if (skipIf(LPAREN)) {
          expect(ID);
          alt.setId(getStrVal());
          skip();
          skip(RPAREN);
        }
        skip(IMPLY);
        parseStatements(alt.getStats());

        stat.addAlternative(alt);
      } while (skipIf(BAR));
    }
    if (skipIf(ELSE)) {
      parseStatements(stat.getElsePart());
    }
    skip(END);

    return stat;
  }

  /**
   * @return
   * @throws M3ParserException
   * @throws ScannerException
   */
  private RepeatStatement parseRepeatStatement()
    throws ScannerException, M3ParserException
  {
    skip(REPEAT);
    RepeatStatement stat = new RepeatStatement();
    parseStatements(stat.getBody());
    skip(UNTIL);
    stat.setCond(parseExpression());

    return stat;
  }

  /**
   * @return
   * @throws M3ParserException
   * @throws ScannerException
   */
  private LoopStatement parseLoopStatement()
    throws ScannerException, M3ParserException
  {
    skip(LOOP);
    LoopStatement stat = new LoopStatement();
    parseStatements(stat.getBody());
    skip(END);

    return stat;
  }

  /**
   * @return
   * @throws M3ParserException
   * @throws ScannerException
   */
  private LockStatement parseLockStatement()
    throws ScannerException, M3ParserException
  {
    skip(LOCK);
    LockStatement stat = new LockStatement(parseExpression());
    skip(DO);
    parseStatements(stat.getDoPart());
    skip(END);

    return stat;
  }

  /**
   * @return
   * @throws M3ParserException
   */
  private ExitStatement parseExitStatement()
    throws M3ParserException
  {
    skip(EXIT);

    return new ExitStatement();
  }

  public BlockStatement parseBlockStatement()
    throws ScannerException, M3ParserException
  {
    BlockStatement bs = new BlockStatement();

    parseDeclarations(bs.getDecls());

    skip(BEGIN);
    parseStatements(bs.getStats());
    skip(END);

    return bs;
  }

  /**
   * @param stats
   * @throws M3ParserException
   * @throws ScannerException
   */
  private void parseStatements(List<Statement> stats)
    throws ScannerException, M3ParserException
  {
    // S = [Stmt {";" Stmt} [";"] ].

    // Basically, this means no empty statements, i.e. no semicolons
    // with nothing in front. You may have no-statements-at-all and
    // the last semicolon is optional.

    if (isStatementLeader(getCur())) {
      stats.add(parseStatement());
      while (skipIf(SEMICOLON)) {
        if (!isStatementLeader(getCur())) {
          break;
        }
        stats.add(parseStatement());
      }
    }
  }

  public BlockStatement parseStatements()
    throws ScannerException, M3ParserException
  {
    BlockStatement bs = new BlockStatement();

    // No embedded M3 for the mo
    //    do {
    //      Statement st = parseStatement();
    //      if (st != null) {
    //        bs.add(st);
    //      }
    //    } while (skipIf(SEMICOLON) || match(EXPR_OPEN) ||
    //             match(SCRIPT_OPEN) || match(TEXTFRAGMENT));

    parseStatements(bs.getStats());

    return bs;
  }

  public boolean isExpressionLeader(M3Token token)
  {
    return token.isLiteral() || (token == ID) || (token == NOT) ||
           (token == PLUS) || (token == MINUS) || (token == LPAREN) ||
           isTypeLeader(token);
  }

  /**
   * @return
   * @throws M3ParserException
   * @throws ScannerException
   */
  private Expression parseConstructor(Type type)
    throws M3ParserException, ScannerException
  {
    ConstructorExpression expr = new ConstructorExpression(type);

    skip(LBRACE);
    if (!match(RBRACE)) {
      do {
        // Possible elements are:
        // 1) <expr>
        // 2) <id> ':=' <expr>
        // 3) <expr> '..' <expr>
        // 4) '..'

        if (skipIf(UPTO)) {
          if (expr.getValues().size() < 1) {
            syntaxError("Element repeating requires at least one element");
          }
          expr.setFilledOut(true);
          break;
        }
        Expression value = parseExpression();

        if (skipIf(BECOMES)) {
          if (value instanceof IdExpression) {
            expr.addValue(((IdExpression) value).getId(), parseExpression());
          }
          else {
            syntaxError("Record Constructors require an id before the ':='");
          }
        }
        else if (skipIf(UPTO)) {
          expr.addValue(value, parseExpression());
        }
        else {
          expr.addValue(value);
        }
      } while (skipIf(COMMA));
    }
    skip(RBRACE);

    return expr;
  }

  private Expression E8()
    throws IOException, ScannerException, M3ParserException
  {
    SourceLocation loc = getSourceLocation();
    Expression expr = null;

    if ((getCur() != LPAREN) && (isTypeLeader(getCur()) || (getCur() == ID))) {
      // Need to solve an LL() problem here
      Type type = parseType();

      // Now we either have a constructor and just successfully parsed
      // its type, or we have a type as actual parameter, or else we
      // mistakenly interpreted a <id> { '.' <id> } production as a type

      // Summary: two alternatives:
      //   1) <type> '{' <actuals> '}', i.e. a Constructor
      //   2) <type>, i.e. just a type, e.g. as parameter to NEW()
      if (getCur() == LBRACE) {
        expr = parseConstructor(type);
      }
      else {
        if (type instanceof IdType) {
          // Just names and dots.
          // Re-interpret this as having applied E7, which we can do
          // unpunished because of the high precedence of '.'.
          for (String id : ((IdType) type).getIds()) {
            if (expr == null) {
              expr = new IdExpression(id);
            }
            else {
              expr = new SelectExpression(expr, id);
            }
          }
          // Note: Semantic analysis may (at a later stage) undo this
          // if it turns out this was a qualified Type id.
        }
        else {
          // Not just names and dots, but a real type expression

          expr = new TypeExpression(type);
        }
      }
    }
    else {
      switch (getCur()) {
      case LIT_INT:
        expr = new ValueExpression(new IntegerValue(getIntVal()));
        skip();
        break;

      case LIT_REAL:
        expr = new ValueExpression(new RealValue(getDblVal()));
        skip();
        break;

      case LIT_LONGREAL:
        expr = new ValueExpression(new LongRealValue(getDblVal()));
        skip();
        break;

      case LIT_EXTENDED:
        expr = new ValueExpression(new ExtendedValue(getDblVal()));
        skip();
        break;

      case LIT_STRING:
        expr = new ValueExpression(new TextValue(getStrVal()));
        skip();
        break;

      case LIT_CHAR:
        expr = new ValueExpression(new CharValue(getStrVal()));
        skip();
        break;

      case LPAREN:
        skip();
        expr = parseExpression();
        skip(RPAREN);
        break;

      default:
        syntaxError("Expected an expression");
      }
    }
    if (expr != null) {
      expr.setSourceLocation(loc);
    }
    return expr;
  }

  private Expression E7()
    throws IOException, ScannerException, M3ParserException
  {
    SourceLocation loc = getSourceLocation();
    Expression expr = E8();
    M3Token t = getCur();

    while ((t == DOT) || (t == LBRACK) || (t == LPAREN) || (t == UP)) {
      if (skipIf(UP)) {
        expr = new DerefExpression(expr);
      }
      else if (skipIf(DOT)) {
        if (getCur() != ID) {
          syntaxError("Select expression requires an ID");
        }
        else {
          expr = new SelectExpression(expr, getStrVal());
          skip();
        }
      }
      else if (skipIf(LBRACK)) {
        do {
          expr = new IndexExpression(expr, parseExpression());
        } while (skipIf(COMMA));
        skip(RBRACK);
      }
      else if (skipIf(LPAREN)) {
        ApplicationExpression appExpr = new ApplicationExpression(expr);
        expr = appExpr;

        if (getCur() != RPAREN) {
          do {
            // Possible elements are:
            // 1) <expr>
            // 2) <id> ':=' <expr>

            Expression value = parseExpression();

            if (skipIf(BECOMES)) {
              if (value instanceof IdExpression) {
                appExpr.addActual(((IdExpression) value).getId(),
                                  parseExpression());
              }
              else {
                syntaxError("Named actuals require an id before the ':='");
              }
            }
            else {
              appExpr.addActual(value);
            }
          } while (skipIf(COMMA));
        }
        skip(RPAREN);
      }
      else {
        syntaxError("Internal Error");
      }
      t = getCur();
    }
    if (expr != null) {
      expr.setSourceLocation(loc);
    }
    return expr;
  }

  private Expression E6()
    throws IOException, ScannerException, M3ParserException
  {
    SourceLocation loc = getSourceLocation();
    Expression expr = null;

    switch (getCur()) {
    case PLUS:
      skip();
      expr = new PlusExpression(E6());
      expr.setSourceLocation(loc);
      break;

    case MINUS:
      skip();
      expr = new MinusExpression(E6());
      expr.setSourceLocation(loc);
      break;

    default:
      expr = E7();
      break;
    }
    return expr;
  }

  private Expression E5()
    throws IOException, ScannerException, M3ParserException
  {
    SourceLocation loc = getSourceLocation();
    Expression expr = E6();
    while (true) {
      M3Token t = getCur();
      switch (t) {
      case TIMES:
      case DIVIDE:
      case DIV:
      case MOD:
      case AMP:
        skip();
        expr = new MathExpression(expr, t, E6());
        expr.setSourceLocation(loc);
        break;

      default:
        return expr;
      }
    }
  }

  private Expression E4()
    throws IOException, ScannerException, M3ParserException
  {
    SourceLocation loc = getSourceLocation();
    Expression expr = E5();
    while (true) {
      M3Token t = getCur();
      switch (t) {
      case PLUS:
      case MINUS:
        skip();
        expr = new MathExpression(expr, t, E5());
        expr.setSourceLocation(loc);
        break;

      default:
        return expr;
      }
    }
  }

  private Expression E3()
    throws IOException, ScannerException, M3ParserException
  {
    SourceLocation loc = getSourceLocation();
    Expression expr = E4();
    M3Token t = getCur();
    switch (t) {
    case IN:
    case LE:
    case LT:
    case EQ:
    case NE:
    case GT:
    case GE:
      skip();
      expr = new RelationalExpression(expr, t, E4());
      expr.setSourceLocation(loc);
      break;

    default:
    }
    return expr;
  }

  private Expression E2()
    throws IOException, ScannerException, M3ParserException
  {
    SourceLocation loc = getSourceLocation();
    Expression expr = null;

    switch (getCur()) {
    case NOT:
      skip();
      expr = new NotExpression(E2());
      expr.setSourceLocation(loc);
      break;

    default:
      expr = E3();
      break;
    }
    return expr;
  }

  private Expression E1()
    throws IOException, ScannerException, M3ParserException
  {
    SourceLocation loc = getSourceLocation();
    Expression expr;
    expr = E2();
    while (skipIf(AND)) {
      Expression e = E2();

      expr = new AndExpression(expr, e);
      expr.setSourceLocation(loc);
    }
    return expr;
  }

  public Expression parseExpression()
    throws ScannerException, M3ParserException
  {
    SourceLocation loc = getSourceLocation();
    Expression expr;
    try {
      expr = E1();
      while (skipIf(OR)) {
        Expression e = E1();

        expr = new OrExpression(expr, e);
        expr.setSourceLocation(loc);
      }
    }
    catch (IOException e) {
      throw new ScannerException(e.getMessage());
    }
    return expr;
  }

  private ArrayType parseArrayType()
    throws IOException, ScannerException, M3ParserException
  {
    ArrayType outerMost = new ArrayType();
    ArrayType current = outerMost;

    skip(ARRAY);

    if (!match(OF)) {
      do {
        current.setUncheckedIndex(parseType());
        if (match(COMMA)) {
          ArrayType inner = new ArrayType();
          current.setElement(inner);
          current = inner;
        }
      } while (skipIf(COMMA));
    }
    skip(OF);
    current.setElement(parseType());

    return outerMost;
  }

  private Type parsePackedType()
    throws M3ParserException, ScannerException
  {
    skip(BITS);
    Expression bits = parseExpression();
    skip(FOR);
    Type base = parseType();

    return new PackedType(bits, base);
  }

  private EnumeratedType parseEnumeratedType()
    throws IOException, ScannerException, M3ParserException
  {
    List<String> ids = new ArrayList<String>();
    skip(LBRACE);

    if (!match(RBRACE)) {
      do {
        expect(ID);
        ids.add(getStrVal());
        skip();
      } while (skipIf(COMMA));
    }
    skip(RBRACE);

    return new EnumeratedType(ids);
  }

  private SubrangeType parseSubrangeType()
    throws IOException, ScannerException, M3ParserException
  {
    skip(LBRACK);
    Expression lwb = parseExpression();
    skip(UPTO);
    Expression upb = parseExpression();
    skip(RBRACK);

    return new SubrangeType(lwb, upb);
  }

  private boolean isObjectTypeLeader(M3Token token)
  {
    return (token == BRANDED) || (token == OBJECT);
  }

  private ObjectType parseObjectType()
    throws M3ParserException, ScannerException
  {
    ObjectType type = new ObjectType();
    type.setBase(ObjectType.getRootObj());
    ObjectType outer = type;

    do {
      if (skipIf(BRANDED)) {
        if (getCur() != OBJECT)
        outer.setBrand(parseExpression());
      }
      skip(OBJECT);
      parseFields(outer.getFields());
      if (skipIf(METHODS)) {
        do {
          if (!match(ID)) {
            break;
          }
          String name = getStrVal();
          if (outer.getMethods().containsKey(name)) {
            syntaxError("Duplicate method in Object Type");
          }
          Field method = new Field(name);
          skip();
          method.setType(parseSignature());
          if (skipIf(BECOMES)) {
            method.setDefault(parseExpression());
          }
          outer.addMethod(method);
        } while (skipIf(SEMICOLON));
      }
      if (skipIf(OVERRIDES)) {
        do {
          if (!match(ID)) {
            break;
          }
          String name = getStrVal();
          if (outer.getMethods().containsKey(name)) {
            syntaxError("Duplicate method in Object Type");
          }
          Field method = new Field(name);
          skip();
          skip(BECOMES);
          method.setDefault(parseExpression());

          outer.addOverride(method);
        } while (skipIf(SEMICOLON));
      }
      skip(END);
      if (isObjectTypeLeader(getCur())) {
        ObjectType newOuter = new ObjectType();
        newOuter.setBase(outer);
        outer = newOuter;
      }
    } while (isObjectTypeLeader(getCur()));

    return type;
  }

  /**
   * @return
   * @throws M3ParserException
   * @throws ScannerException
   */
  private Type parseRefTypeOrObjectType()
    throws M3ParserException, ScannerException
  {
    // Leader == BRANDED

    skip(BRANDED);
    Expression brand = null;

    if ((getCur() != REF) && (getCur() != OBJECT)) {
      brand = parseExpression();
    }
    if (match(REF)) {
      RefType refType = parseRefType();
      refType.setBrand(brand);

      return refType;
    }
    // Must be Object type, base type ROOT
    ObjectType objectType = parseObjectType();
    objectType.setBrand(brand);

    return objectType;
  }

  /**
   * @return
   * @throws M3ParserException
   * @throws ScannerException
   */
  private Type parseTypeNameOrObjectType()
    throws M3ParserException, ScannerException
  {
    // Leader == TypeName | ROOT
    IdType name = null;
    if (skipIf(ROOT)) {
      name = new IdType(ROOT.toString());
    }
    else {
      name = new IdType(parseName().getIds());
    }

    if (isObjectTypeLeader(getCur())) {
      ObjectType outer = parseObjectType();
      outer.setUncheckedBase(name);

      return outer;
    }
    return name;
  }

  /**
   * @return
   * @throws ScannerException
   * @throws M3ParserException
   */
  private Type parseProcedureType()
    throws M3ParserException, ScannerException
  {
    SourceLocation loc = getSourceLocation();
    skip(PROCEDURE);
    Type procType = parseSignature();
    procType.setSourceLocation(loc);

    return procType;
  }

  /**
   * @param fields
   * @throws M3ParserException
   * @throws ScannerException
   */
  private void parseFields(Map<String, Field> fields)
    throws M3ParserException, ScannerException
  {
    do {
      if (!match(ID)) {
        break;
      }

      List<String> ids = new ArrayList<String>();
      Type fieldType = null;
      Expression fieldDefault = null;

      do {
        expect(ID);
        ids.add(getStrVal());
        skip();
      } while (skipIf(COMMA));

      if (skipIf(COLON)) {
        fieldType = parseType();
      }
      if (skipIf(BECOMES)) {
        fieldDefault = parseExpression();
      }

      if ((fieldType == null) && (fieldDefault == null)) {
        syntaxError("Field declaration requires at least one of type and default value");
      }
      for (String name : ids) {
        if (fields.containsKey(name)) {
          syntaxError("Duplicate field declaration for \"" + name + "\"");
        }
        fields.put(name, new Field(name, fieldType, fieldDefault));
      }
    } while (skipIf(SEMICOLON));
  }

  /**
   * @return
   * @throws M3ParserException
   * @throws ScannerException
   */
  private Type parseRecordType()
    throws M3ParserException, ScannerException
  {
    RecordType type = new RecordType();

    type.setSourceLocation(getSourceLocation());
    skip(RECORD);
    parseFields(type.getFields());
    skip(END);

    return type;
  }

  /**
   * @return
   * @throws M3ParserException
   * @throws ScannerException
   */
  private Type parseSetType()
    throws M3ParserException, ScannerException
  {
    SourceLocation loc = getSourceLocation();

    skip(SET);
    skip(OF);
    Type type = new SetType(parseType());
    type.setSourceLocation(loc);

    return type;
  }

  /**
   * @return
   * @throws M3ParserException
   * @throws ScannerException
   */
  private RefType parseRefType()
    throws M3ParserException, ScannerException
  {
    SourceLocation loc = getSourceLocation();

    boolean untraced = skipIf(UNTRACED);
    Expression brand = null;
    if (skipIf(BRANDED)) {
      if (getCur() != REF) {
        brand = parseExpression();
      }
    }
    skip(REF);
    RefType refType = new RefType(parseType());
    refType.setUntraced(untraced);
    refType.setBrand(brand);
    refType.setSourceLocation(loc);

    return refType;
  }

  public Type parseType()
    throws ScannerException, M3ParserException
  {
    SourceLocation loc = getSourceLocation();
    Type type = null;

    try {
      if (getCur().isReservedId()) {
        type = new IdType(getCur().name());
        type.setSourceLocation(loc);
        skip();
        return type;
      }
      switch (getCur()) {
      case ARRAY:
        type = parseArrayType();
        break;

      case BITS:
        type = parsePackedType();
        break;

      case BRANDED:
        type = parseRefTypeOrObjectType();
        break;

      case ROOT:
        type = ObjectType.getRootObj();
        break;

      case ID:
        type = parseTypeNameOrObjectType();
        break;

      case LBRACE:
        type = parseEnumeratedType();
        break;

      case LBRACK:
        type = parseSubrangeType();
        break;

      case OBJECT:
        type = parseObjectType();
        break;

      case PROCEDURE:
        type = parseProcedureType();
        break;

      case RECORD:
        type = parseRecordType();
        break;

      case REF:
        type = parseRefType();
        break;

      case SET:
        type = parseSetType();
        break;

      case UNTRACED:
        skip();
        if (skipIf(ROOT)) {
          if (isObjectTypeLeader(getCur())) {
            ObjectType outer = parseObjectType();
            outer.setBase(ObjectType.getUntracedRootObj());
            type = outer;
          }
          else {
            type = ObjectType.getUntracedRootObj();
          }
        }
        else {
          type = parseRefType();
          ((RefType) type).setUntraced(true);
        }
        break;

      default:
        syntaxError("Type expected");
      }
    }
    catch (IOException e) {
      throw new ScannerException(e.getMessage());
    }

    if (type != null) {
      type.setSourceLocation(loc);
    }
    return type;
  }

  public static final boolean isTypeLeader(M3Token token)
  {
    return (token == ARRAY) || (token == BITS) || (token == BRANDED) ||
           (token == LBRACE) || (token == LBRACK) || (token == OBJECT) ||
           (token == PROCEDURE) || (token == RECORD) || (token == REF) ||
           (token == SET) || (token == UNTRACED) || (token == ID) ||
           (token == LPAREN);
  }

  public void parseImports(M3CompilationUnit unit)
    throws M3ParserException
  {
    while (match(IMPORT) || match(FROM)) {
      if (skipIf(IMPORT)) {
        do {
          StringBuffer fqin = new StringBuffer();
          boolean qualified = false;
          do {
            if (qualified) {
              fqin.append('.');
            }
            else {
              qualified = true;
            }
            expect(ID);
            fqin.append(getStrVal());
            skip();
          } while (skipIf(DOT));
          if (!qualified) {
            fqin.insert(0, unit.getPackage() + ".");
          }
          unit.addImport(fqin.toString());
        } while (skipIf(COMMA));
      }
      else { // FROM
        skip(FROM);
        StringBuffer fqin = new StringBuffer();
        boolean qualified = false;
        do {
          if (qualified) {
            fqin.append('.');
          }
          else {
            qualified = true;
          }
          expect(ID);
          fqin.append(getStrVal());
          skip();
        } while (skipIf(DOT));
        if (!qualified) {
          fqin.insert(0, unit.getPackage() + ".");
        }
        final String intface = fqin.toString();
        skip(IMPORT);
        do {
          expect(ID);
          unit.addImport(intface, getStrVal());
          skip();
        } while (skipIf(COMMA));
      }
      skip(SEMICOLON);
    }
  }

  public Interface parseInterface()
    throws ScannerException, M3ParserException
  {
    boolean unsafe = skipIf(UNSAFE);

    skip(INTERFACE);
    expect(ID);
    final String name = getStrVal();
    skip();
    Interface intf = new Interface(name);
    intf.setUnsafe(unsafe);
    skip(SEMICOLON);

    parseImports(intf);

    while (isDeclarationLeader(getCur())) {
      parseDeclarations(intf.getDecls(), false);
    }

    skip(END);
    expect(ID);
    if (!name.equals(getStrVal())) {
      syntaxError("Id at end of INTERFACE doesn't match Module's name. Found \"" +
                  getStrVal() + "\", expected \"" + name + "\"");
    }
    skip();
    skip(DOT);

    return intf;
  }

  public Module parseModule()
    throws ScannerException, M3ParserException
  {
    boolean unsafe = skipIf(UNSAFE);

    skip(MODULE);
    expect(ID);
    final String name = getStrVal();
    skip();
    Module mod = new Module(name);
    mod.setUnsafe(unsafe);

    if (skipIf(EXPORTS)) {
      do {
        expect(ID);
        mod.addExport(getStrVal());
        skip();
      } while (skipIf(COMMA));
    }
    else {
      mod.addExport(name);
    }
    skip(SEMICOLON);

    parseImports(mod);

    mod.setBody(parseBlockStatement());

    expect(ID);
    if (!name.equals(getStrVal())) {
      syntaxError("Id at end of MODULE doesn't match Module's name. Found \"" +
                  getStrVal() + "\", expected \"" + name + "\"");
    }
    skip();
    skip(DOT);

    return mod;
  }

  public M3CompilationUnit parseCompilationUnit()
    throws M3ParserException, ScannerException
  {
    boolean unsafe = skipIf(UNSAFE);
    M3CompilationUnit unit = null;

    if (match(INTERFACE)) {
      unit = parseInterface();
    }
    else if (match(MODULE)) {
      unit = parseModule();
    }
    else {
      syntaxError("Expected INTERFACE or MODULE");
    }
    if (unit != null) {
      unit.setUnsafe(unsafe);
    }

    return unit;
  }
}

/*
 * $Log$
 */
