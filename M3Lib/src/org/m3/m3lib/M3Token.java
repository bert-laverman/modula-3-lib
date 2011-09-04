/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.m3.m3lib;


/**
 * 
 * @author bertl
 */
public enum M3Token
{
  // Keywords
  AND,
  ANY,
  ARRAY,
  AS,
  BEGIN,
  BITS,
  BRANDED,
  BY,
  CASE,
  CONST,
  DIV,
  DO,
  ELSE,
  ELSIF,
  END,
  EVAL,
  EXCEPT,
  EXCEPTION,
  EXIT,
  EXPORTS,
  FINALLY,
  FOR,
  FROM,
  GENERIC,
  IF,
  IMPORT,
  IN,
  INTERFACE,
  LOCK,
  LOOP,
  METHODS,
  MOD,
  MODULE,
  NOT,
  OBJECT,
  OF,
  OR,
  OVERRIDES,
  PROCEDURE,
  RAISE,
  RAISES,
  READONLY,
  RECORD,
  REF,
  REPEAT,
  RETURN,
  REVEAL,
  ROOT,
  SET,
  THEN,
  TO,
  TRY,
  TYPE,
  TYPECASE,
  UNSAFE,
  UNTIL,
  UNTRACED,
  VALUE,
  VAR,
  WHILE,
  WITH,

  // Reserved Identifiers
  ABS(TokenType.RESERVED_ID),
  ADDRESS(TokenType.RESERVED_ID),
  ADR(TokenType.RESERVED_ID),
  ADRSIZE(TokenType.RESERVED_ID),
  BITSIZE(TokenType.RESERVED_ID),
  BOOLEAN(TokenType.RESERVED_ID),
  BYTESIZE(TokenType.RESERVED_ID),
  CARDINAL(TokenType.RESERVED_ID),
  CEILING(TokenType.RESERVED_ID),
  CHAR(TokenType.RESERVED_ID),
  DEC(TokenType.RESERVED_ID),
  DISPOSE(TokenType.RESERVED_ID),
  EXTENDED(TokenType.RESERVED_ID),
  FALSE(TokenType.RESERVED_ID),
  FIRST(TokenType.RESERVED_ID),
  FLOAT(TokenType.RESERVED_ID),
  FLOOR(TokenType.RESERVED_ID),
  INC(TokenType.RESERVED_ID),
  INTEGER(TokenType.RESERVED_ID),
  ISTYPE(TokenType.RESERVED_ID),
  LAST(TokenType.RESERVED_ID),
  LONGREAL(TokenType.RESERVED_ID),
  LOOPHOLE(TokenType.RESERVED_ID),
  MAX(TokenType.RESERVED_ID),
  MIN(TokenType.RESERVED_ID),
  MUTEX(TokenType.RESERVED_ID),
  NARROW(TokenType.RESERVED_ID),
  NEW(TokenType.RESERVED_ID),
  NIL(TokenType.RESERVED_ID),
  NULL(TokenType.RESERVED_ID),
  NUMBER(TokenType.RESERVED_ID),
  ORD(TokenType.RESERVED_ID),
  REAL(TokenType.RESERVED_ID),
  REFANY(TokenType.RESERVED_ID),
  ROUND(TokenType.RESERVED_ID),
  SUBARRAY(TokenType.RESERVED_ID),
  TEXT(TokenType.RESERVED_ID),
  TRUE(TokenType.RESERVED_ID),
  TRUNC(TokenType.RESERVED_ID),
  TYPECODE(TokenType.RESERVED_ID),
  VAL(TokenType.RESERVED_ID),

  // Operators
  PLUS(TokenType.SYMBOL, "+"),
  MINUS(TokenType.SYMBOL, "-"),
  TIMES(TokenType.SYMBOL, "*"),
  DIVIDE(TokenType.SYMBOL, "/"),
  LT(TokenType.SYMBOL, "<"),
  GT(TokenType.SYMBOL, ">"),
  LE(TokenType.SYMBOL, "<="),
  GE(TokenType.SYMBOL, ">="),
  NE(TokenType.SYMBOL, "#"),
  LBRACE(TokenType.SYMBOL, "{"),
  LPAREN(TokenType.SYMBOL, "("),
  LBRACK(TokenType.SYMBOL, "["),
  EQ(TokenType.SYMBOL, "="),
  RBRACE(TokenType.SYMBOL, "}"),
  RPAREN(TokenType.SYMBOL, ")"),
  RBRACK(TokenType.SYMBOL, "]"),
  SEMICOLON(TokenType.SYMBOL, ";"),
  BAR(TokenType.SYMBOL, "|"),
  UP(TokenType.SYMBOL, "^"),
  DOT(TokenType.SYMBOL, "."),
  UPTO(TokenType.SYMBOL, ".."),
  BECOMES(TokenType.SYMBOL, ":="),
  COMMA(TokenType.SYMBOL, ","),
  AMP(TokenType.SYMBOL, "&"),
  COLON(TokenType.SYMBOL, ":"),
  SUBTYPE(TokenType.SYMBOL, "<:"),
  IMPLY(TokenType.SYMBOL, "=>"),

  // Scanner tokens
  EOF(TokenType.SCANNER_TOKEN, "<eof>"),
  ERROR(TokenType.SCANNER_TOKEN, "<error>"),
  ID(TokenType.SCANNER_TOKEN, "<id>"),
  PRAGMA(TokenType.SCANNER_TOKEN, "<*PRAGMA*>"),
  LIT_CHAR(TokenType.LITERAL, "<char>"),
  LIT_STRING(TokenType.LITERAL, "<string>"),
  LIT_INT(TokenType.LITERAL, "<int>"),
  LIT_REAL(TokenType.LITERAL, "<real>"),
  LIT_LONGREAL(TokenType.LITERAL, "<longreal>"),
  LIT_EXTENDED(TokenType.LITERAL, "<extended>");

  private enum TokenType
  {
    KEYWORD,
    RESERVED_ID,
    SYMBOL,
    SCANNER_TOKEN,
    LITERAL;
  };

  private TokenType type_;
  private String    text_ = null;

  public boolean isKeyword()
  {
    return type_ == TokenType.KEYWORD;
  }

  public boolean isReservedId()
  {
    return type_ == TokenType.RESERVED_ID;
  }

  public boolean isSymbol()
  {
    return type_ == TokenType.SYMBOL;
  }

  public boolean isScannerToken()
  {
    return type_ == TokenType.SCANNER_TOKEN;
  }

  public boolean isLiteral()
  {
    return type_ == TokenType.LITERAL;
  }

  M3Token()
  {
    this.type_ = TokenType.KEYWORD;
  }

  M3Token(TokenType type)
  {
    this.type_ = type;
  }

  M3Token(TokenType type, String text)
  {
    this.type_ = type;
    this.text_ = text;
  }

  @Override
  public String toString()
  {
    if ((type_ == TokenType.KEYWORD) || (type_ == TokenType.RESERVED_ID)) {
      return super.name();
    }
    return this.text_;
  }
}
