/**
 * Copyright (c) 2008 B. Laverman. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: bertl
 */

// $Id$
package org.m3.m3lib.scanner;

import static org.m3.m3lib.M3Token.AMP;
import static org.m3.m3lib.M3Token.BAR;
import static org.m3.m3lib.M3Token.BECOMES;
import static org.m3.m3lib.M3Token.COLON;
import static org.m3.m3lib.M3Token.COMMA;
import static org.m3.m3lib.M3Token.DIVIDE;
import static org.m3.m3lib.M3Token.DOT;
import static org.m3.m3lib.M3Token.EOF;
import static org.m3.m3lib.M3Token.EQ;
import static org.m3.m3lib.M3Token.ERROR;
import static org.m3.m3lib.M3Token.GE;
import static org.m3.m3lib.M3Token.GT;
import static org.m3.m3lib.M3Token.IMPLY;
import static org.m3.m3lib.M3Token.LBRACE;
import static org.m3.m3lib.M3Token.LBRACK;
import static org.m3.m3lib.M3Token.LE;
import static org.m3.m3lib.M3Token.LIT_CHAR;
import static org.m3.m3lib.M3Token.LIT_EXTENDED;
import static org.m3.m3lib.M3Token.LIT_INT;
import static org.m3.m3lib.M3Token.LIT_LONGREAL;
import static org.m3.m3lib.M3Token.LIT_REAL;
import static org.m3.m3lib.M3Token.LIT_STRING;
import static org.m3.m3lib.M3Token.LPAREN;
import static org.m3.m3lib.M3Token.LT;
import static org.m3.m3lib.M3Token.MINUS;
import static org.m3.m3lib.M3Token.NE;
import static org.m3.m3lib.M3Token.PLUS;
import static org.m3.m3lib.M3Token.PRAGMA;
import static org.m3.m3lib.M3Token.RBRACE;
import static org.m3.m3lib.M3Token.RBRACK;
import static org.m3.m3lib.M3Token.RPAREN;
import static org.m3.m3lib.M3Token.SEMICOLON;
import static org.m3.m3lib.M3Token.SUBTYPE;
import static org.m3.m3lib.M3Token.TIMES;
import static org.m3.m3lib.M3Token.UP;
import static org.m3.m3lib.M3Token.UPTO;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;

import org.m3.m3lib.M3Token;

/**
 * 
 * @author LavermB
 * 
 */
public class M3Scanner
  extends ScannerBase<M3Token>
{

  private String pragma_ = null;

  /** Creates a new instance of Scanner */
  public M3Scanner(Reader rdr)
  {
    super(rdr);
  }

  public M3Scanner(String file) throws FileNotFoundException
  {
    super(file);
  }

  /**
   * @return the pragma
   */
  public String getPragma()
  {
    return this.pragma_;
  }

  /**
   * @param pragma the pragma to set
   */
  public void setPragma(String pragma)
  {
    this.pragma_ = pragma;
  }

  public void resetPragma()
  {
    this.pragma_ = null;
  }

  protected void initKeys()
  {
    for (M3Token token : M3Token.values()) {
      if (token.isKeyword() && !token.isReservedId()) {
        keyLookupMap.put(token.toString(), token);
        keyMap.put(token, token.toString());
      }
      if (token.isReservedId()) {
        reservedSet_.add(token.toString());
      }
    }
  }

  private M3Token checkKeys(final String word)
  {
    strVal_ = word;
    objVal_ = word;

    if (keyLookupMap.containsKey(word)) {
      cur_ = keyLookupMap.get(word);
    }
    else {
      cur_ = M3Token.ID;
    }

    return cur_;
  }

  private M3Token scanNumber()
    throws IOException
  {
    // Scan number
    strBuf_.setLength(0);

    while (Character.isDigit((char) ch_)) {
      strBuf_.append((char) ch_);
      nextch();
    }
    if (ch_ == '_') {
      // Non-decimal numbers

      nextch();
      boolean scanError = false;

      // We greedily collected digits, so the base may be too high.
      int base = Integer.parseInt(strBuf_.toString());
      if ((base < 2) || (base > 16)) {
        scanError("Bad base for a number literal");
        scanError = true;
      }
      strBuf_.setLength(0);
      while (Character.isDigit((char) ch_) || ((ch_ >= 'a') && (ch_ <= 'f')) ||
             ((ch_ >= 'A') && (ch_ <= 'F')))
      {
        strBuf_.append((char) ch_);
        nextch();
      }
      int value = 0;
      final String txt = strBuf_.toString();

      try {
        if (!scanError) {
          value = Integer.parseInt(txt, base);
        }
      }
      catch (NumberFormatException e) {
        scanError(e.getMessage() + ", base = " + base + ", value = \"" + txt);
      }
      return setToken(M3Token.LIT_INT, value);
    }
    else if (ch_ == '.') {
      // Look ahead to see if we have a '..' instead of a fraction
      int mark = linePos_;
      nextch();
      if (ch_ == '.') { // UPTO!!
        linePos_ = mark;
        return setIntLiteral();
      }

      strBuf_.append('.');

      while (Character.isDigit((char) ch_)) {
        strBuf_.append((char) ch_);
        nextch();
      }
      boolean haveExp = (ch_ == 'e') || (ch_ == 'E') || (ch_ == 'd') ||
                        (ch_ == 'D') || (ch_ == 'x') || (ch_ == 'X');
      char exp = (char) ch_;
      if (haveExp) {
        strBuf_.append('e');
        nextch();

        if ((ch_ == '-') || (ch_ == '+')) {
          strBuf_.append((char) ch_);
          nextch();
        }
        while (Character.isDigit((char) ch_)) {
          strBuf_.append((char) ch_);
          nextch();
        }
      }
      if (!haveExp || (exp == 'e') || (exp == 'E')) { // REAL
        return setRealLiteral();
      }
      else if (haveExp && ((exp == 'd') || (exp == 'D'))) { // LONGREAL
        return setLongRealLiteral();
      }
      else if (haveExp && ((exp == 'x') || (exp == 'X'))) { // EXTENDED
        return setExtendedLiteral();
      }
      else {
        scanError("Internal error: what number format?");
        return setToken(LIT_REAL, 0);
      }
    }
    else {
      return setIntLiteral();
    }
  }

  /**
   * @return
   */
  private M3Token setRealLiteral()
  {
    double value = 0;
    try {
      value = Double.parseDouble(strBuf_.toString());
    }
    catch (NumberFormatException e) {
      scanError("Bad REAL literal");
    }
    return setToken(LIT_REAL, value);
  }

  /**
   * @return
   */
  private M3Token setLongRealLiteral()
  {
    double value = 0;
    try {
      value = Double.parseDouble(strBuf_.toString());
    }
    catch (NumberFormatException e) {
      scanError("Bad LONGREAL literal");
    }
    return setToken(LIT_LONGREAL, value);
  }

  /**
   * @return
   */
  private M3Token setExtendedLiteral()
  {
    double value = 0;
    try {
      value = Double.parseDouble(strBuf_.toString());
    }
    catch (NumberFormatException e) {
      scanError("Bad EXTENDED literal");
    }
    return setToken(LIT_EXTENDED, value);
  }

  /**
   * @return
   */
  private M3Token setIntLiteral()
  {
    int value = 0;
    try {
      value = Integer.parseInt(strBuf_.toString());
    }
    catch (NumberFormatException e) {
      scanError("Bad number format");
    }
    return setToken(LIT_INT, value);
  }

  /* (non-Javadoc)
   * @see org.m3.m3lib.scanner.Scanner#next()
   */
  public M3Token next()
    throws IOException
  {
    if (cur_ == ERROR) {
      // Unreported IO/Scanner error
      cur_ = EOF;

      if (objVal_ instanceof IOException) {
        throw (IOException) objVal_;
      }
      throw new IOException(strVal_);
    }
    else if (cur_ == EOF) {
      // Read past EOF
      throw new EOFException(
                             "Scanner.next(): Attempt to scan past End-Of-File or Error");
    }
    while (Character.isWhitespace((char) ch_)) {
      nextch();
    }
    lastLinePos_ = linePos_;
    if (ch_ == -1) {
      return setToken(EOF);
    }
    if (Character.isLetter((char) ch_)) {
      // Scan identifier
      strBuf_.setLength(0);

      while (Character.isLetterOrDigit((char) ch_)) {
        strBuf_.append((char) ch_);
        nextch();
      }
      return checkKeys(strBuf_.toString());
    }
    else if (Character.isDigit((char) ch_)) {
      return scanNumber();
    }
    else if (ch_ == '\"') {
      // Scan string literal
      strBuf_.setLength(0);
      nextch();
      while (!match('\"')) {
        if (match('\\')) {
          if ((ch_ >= '0') && (ch_ <= '7')) { // permissive octal
            // value: up-to 3 digits
            int oVal = ch_ - '0';
            nextch();
            if ((ch_ >= '0') && (ch_ <= '7')) {
              oVal = oVal * 8 + ch_ - '0';
              nextch();
            }
            if ((ch_ >= '0') && (ch_ <= '7')) {
              oVal = oVal * 8 + ch_ - '0';
              nextch();
            }
            strBuf_.append((char) oVal);
            continue;
          }
          switch (ch_) {
          case 'f':
            strBuf_.append('\f');
            break;
          case 'n':
            strBuf_.append('\n');
            break;
          case 'r':
            strBuf_.append('\r');
            break;
          case 't':
            strBuf_.append('\t');
            break;
          default:
            strBuf_.append((char) ch_);
          }
          nextch();
          continue;
        }
        else if (Character.isISOControl((char) ch_)) { // Unterminated string!
          return setToken(ERROR, "Unterminated string literal");
        }
        strBuf_.append((char) ch_);
        nextch();
      }
      return setToken(LIT_STRING, strBuf_.toString());
    }
    else if (ch_ == '\'') {
      // Scan char literal
      strBuf_.setLength(0);
      nextch();
      while (!match('\'')) {
        if (match('\\')) {
          if ((ch_ >= '0') && (ch_ <= '7')) { // permissive octal
            // value: up-to 3 digits
            int oVal = ch_ - '0';
            nextch();
            if ((ch_ >= '0') && (ch_ <= '7')) {
              oVal = oVal * 8 + ch_ - '0';
              nextch();
            }
            if ((ch_ >= '0') && (ch_ <= '7')) {
              oVal = oVal * 8 + ch_ - '0';
              nextch();
            }
            strBuf_.append((char) oVal);
            continue;
          }
          switch (ch_) {
          case 'f':
            strBuf_.append('\f');
            break;
          case 'n':
            strBuf_.append('\n');
            break;
          case 'r':
            strBuf_.append('\r');
            break;
          case 't':
            strBuf_.append('\t');
            break;
          default:
            strBuf_.append((char) ch_);
          }
          nextch();
          continue;
        }
        else if (Character.isISOControl((char) ch_)) { // Unterminated string!
          return setToken(ERROR, "Unterminated string literal");
        }
        strBuf_.append((char) ch_);
        nextch();
      }
      if (strBuf_.length() != 1) {
        return setToken(ERROR, "Character literal of wrong size");
      }
      return setToken(LIT_CHAR, strBuf_.toString());
    }
    switch (ch_) {
    case '}':
      return skipAndSetToken(RBRACE);
    case ']':
      return skipAndSetToken(RBRACK);

    case '<':
      nextch();
      if (match('=')) {
        return setToken(LE);
      }
      else if (match('*')) {
        return scanPragma();
      }
      else if (match(':')) {
        return setToken(SUBTYPE);
      }
      return setToken(LT);

    case '>':
      nextch();
      if (match('=')) {
        return setToken(GE);
      }
      return setToken(GT);

    case ':':
      nextch();
      if (match('=')) {
        return setToken(BECOMES);
      }
      return setToken(COLON);

    case '=':
      nextch();
      if (match('>')) {
        return setToken(IMPLY);
      }
      return setToken(EQ);
    case '#':
      return skipAndSetToken(NE);
    case '-':
      return skipAndSetToken(MINUS);
    case '+':
      return skipAndSetToken(PLUS);
    case '*':
      return skipAndSetToken(TIMES);
    case '/':
      return skipAndSetToken(DIVIDE);
    case '.':
      nextch();
      if (match('.')) {
        return setToken(UPTO);
      }
      return setToken(DOT);
    case ',':
      return skipAndSetToken(COMMA);
    case ';':
      return skipAndSetToken(SEMICOLON);
    case '(':
      // Comment parsing!
      nextch();
      if (ch_ == '*') {
        scanComment();
        return next();
      }
      else {
        return setToken(LPAREN);
      }
    case ')':
      return skipAndSetToken(RPAREN);
    case '{':
      return skipAndSetToken(LBRACE);
    case '[':
      return skipAndSetToken(LBRACK);
    case '&':
      return skipAndSetToken(AMP);
    case '^':
      return skipAndSetToken(UP);
    case '|':
      return skipAndSetToken(BAR);
    }
    return setToken(ERROR, "Don't know how to match \'" + ch_ + "\'");
  }

  /**
   * @throws IOException
   * 
   */
  private void scanComment()
    throws IOException
  {
    // "(*" has been encountered, '*' not yet skipped
    nextch();
    while (true) {
      while ((ch_ != '*') && (ch_ != '(') && (ch_ != -1)) {
        nextch();
      }
      if (ch_ == -1) {
        scanError("Comment running to End-Of-File");
        return;
      }
      if (ch_ == '(') {
        nextch();
        if (ch_ == '*') {
          scanComment();
        }
        else {
          continue;
        }
      }
      if (ch_ == '*') {
        while ((ch_ == '*') && (ch_ != -1)) {
          nextch();
        }
        if (ch_ == -1) {
          scanError("Comment running to End-Of-File");
          return;
        }
        if (ch_ == ')') {
          nextch();
          return;
        }
      }
    }
  }

  private M3Token scanPragma()
    throws IOException
  {
    strBuf_.setLength(0);

    // "<*" has been encountered
    while (true) {
      while ((ch_ != '*') && (ch_ != -1)) {
        strBuf_.append((char) ch_);
        nextch();
      }
      if (ch_ == -1) {
        scanError("Pragma running to End-Of-File");
        return setToken(PRAGMA, "");
      }
      if (ch_ == '*') {
        while ((ch_ == '*') && (ch_ != -1)) {
          strBuf_.append((char) ch_);
          nextch();
        }
        if (ch_ == -1) {
          scanError("Pragma running to End-Of-File");
          return setToken(PRAGMA, "");
        }
        if (ch_ == '>') {
          // Last '*' is of the "*>"
          strBuf_.setLength(strBuf_.length() - 1);
          setPragma(strBuf_.toString());

          nextch();
          return next();
        }
      }
    }
  }

  /* (non-Javadoc)
   * @see org.m3.m3lib.scanner.Scanner#getEofToken()
   */
  @Override
  public M3Token getEofToken()
  {
    return EOF;
  }

  /* (non-Javadoc)
   * @see org.m3.m3lib.scanner.Scanner#getErrorToken()
   */
  @Override
  public M3Token getErrorToken()
  {
    return ERROR;
  }
}

/*
 * $Log$
 */
