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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.m3.m3lib.scanner.Scanner;
import org.m3.m3lib.scanner.SourceLocation;

/**
 * 
 * @author LavermB
 * 
 */
public class ParserBase<Token>
  implements MessageProducer, Scanner<Token>
{

  private Scanner<Token> scanner_;

  private List<MessageCollector> collectors_ = new ArrayList<MessageCollector>();

  /** Creates a new instance of ParserBase */
  public ParserBase(Scanner<Token> scanner)
  {
    scanner_ = scanner;
  }

  public Scanner<Token> getScanner()
  {
    return scanner_;
  }

  /* (non-Javadoc)
   * @see org.m3.m3lib.parser.MessageProducer#addMessageCollector(org.m3.m3lib.parser.MessageCollector)
   */
  @Override
  public void addMessageCollector(MessageCollector c)
  {
    if (!this.collectors_.contains(c)) {
      this.collectors_.add(c);
    }
    this.scanner_.addMessageCollector(c);
  }

  /* (non-Javadoc)
   * @see org.m3.m3lib.parser.MessageProducer#removeMessageCollector(org.m3.m3lib.parser.MessageCollector)
   */
  @Override
  public void removeMessageCollector(MessageCollector c)
  {
    if (this.collectors_.contains(c)) {
      this.collectors_.remove(c);
    }
    this.scanner_.removeMessageCollector(c);
  }

  protected void addMessage(Message msg)
  {
    for (MessageCollector c : this.collectors_) {
      c.addMessage(msg);
    }
  }

  protected List<Message> getMessages()
  {
    List<Message> allMessages = new ArrayList<Message>();

    for (MessageCollector c : this.collectors_) {
      allMessages.addAll(c.getMessages());
    }

    return allMessages;
  }

  protected void syntaxError(String msg)
  {
    addMessage(new SyntaxError<Token>(getScanner(), msg));
  }

  protected void tokenError(String msg, Token token)
  {
    addMessage(new TokenError<Token>(getScanner(), msg, token));
  }

  protected void tokenDeleted(Token token)
  {
    addMessage(new TokenDeletedError<Token>(getScanner(), token));
  }

  protected void tokenInserted(Token token)
  {
    addMessage(new TokenInsertedError<Token>(getScanner(),token));
  }
  protected boolean match(Token token)
  {
    return (scanner_.getCur() == token);
  }

  protected void skip()
    throws M3ParserException
  {
    try {
      scanner_.next();
    }
    catch (IOException e) {
      throw new M3ParserException(scanner_, "IOException caught: " +
                                          e.getMessage());
    }
  }

  protected void skipTo(Set<Token> tokens)
    throws M3ParserException
  {
    tokens.add(this.scanner_.getEofToken());
    tokens.add(this.scanner_.getErrorToken());

    while (!tokens.contains(getCur())) {
      tokenDeleted(getCur());
      skip();
    }
  }

  protected void skipTo(Token... tokens)
  {
    Set<Token> tokenSet = new HashSet<Token>(tokens.length);
    for (Token token : tokens) {
      tokenSet.add(token);
    }
    skipTo(tokens);
  }

  protected void skip(Token token)
    throws M3ParserException
  {
    if (!skipIf(token)) {
      tokenInserted(token);
    }
  }

  protected boolean skipIf(Token token)
    throws M3ParserException
  {
    boolean isMatch = match(token);
    if (isMatch) {
      skip();
    }
    return isMatch;
  }

  protected void expect(Token token)
    throws M3ParserException
  {
    if (!match(token)) {
      tokenInserted(token);
    }
  }

  /* (non-Javadoc)
   * @see org.m3.m3lib.scanner.Scanner#close()
   */
  @Override
  public void close()
  {
    this.scanner_.close();
  }

  /* (non-Javadoc)
   * @see org.m3.m3lib.scanner.Scanner#getCur()
   */
  @Override
  public Token getCur()
  {
    return this.scanner_.getCur();
  }

  /* (non-Javadoc)
   * @see org.m3.m3lib.scanner.Scanner#getDblVal()
   */
  @Override
  public double getDblVal()
  {
    return this.scanner_.getDblVal();
  }

  /* (non-Javadoc)
   * @see org.m3.m3lib.scanner.Scanner#getEofToken()
   */
  @Override
  public Token getEofToken()
  {
    return this.scanner_.getEofToken();
  }

  /* (non-Javadoc)
   * @see org.m3.m3lib.scanner.Scanner#getErrorToken()
   */
  @Override
  public Token getErrorToken()
  {
    return this.scanner_.getErrorToken();
  }

  /* (non-Javadoc)
   * @see org.m3.m3lib.scanner.Scanner#getFilePos()
   */
  @Override
  public int getFilePos()
  {
    return this.scanner_.getFilePos();
  }

  /* (non-Javadoc)
   * @see org.m3.m3lib.scanner.Scanner#getIntVal()
   */
  @Override
  public long getIntVal()
  {
    return this.scanner_.getIntVal();
  }

  /* (non-Javadoc)
   * @see org.m3.m3lib.scanner.Scanner#getLineNr()
   */
  @Override
  public long getLineNr()
  {
    return this.scanner_.getLineNr();
  }

  /* (non-Javadoc)
   * @see org.m3.m3lib.scanner.Scanner#getLinePos()
   */
  @Override
  public int getLinePos()
  {
    return this.scanner_.getLinePos();
  }

  /* (non-Javadoc)
   * @see org.m3.m3lib.scanner.Scanner#getObjVal()
   */
  @Override
  public Object getObjVal()
  {
    return this.scanner_.getObjVal();
  }

  /* (non-Javadoc)
   * @see org.m3.m3lib.scanner.Scanner#getSourceLocation()
   */
  @Override
  public SourceLocation getSourceLocation()
  {
    return this.scanner_.getSourceLocation();
  }

  /* (non-Javadoc)
   * @see org.m3.m3lib.scanner.Scanner#getStrVal()
   */
  @Override
  public String getStrVal()
  {
    return this.scanner_.getStrVal();
  }

  /* (non-Javadoc)
   * @see org.m3.m3lib.scanner.Scanner#isReservedId(java.lang.String)
   */
  @Override
  public boolean isReservedId(String id)
  {
    return this.scanner_.isReservedId(id);
  }

  /* (non-Javadoc)
   * @see org.m3.m3lib.scanner.Scanner#next()
   */
  @Override
  public Token next()
    throws IOException
  {
    return this.scanner_.next();
  }

}

/*
 * $Log$
 */
