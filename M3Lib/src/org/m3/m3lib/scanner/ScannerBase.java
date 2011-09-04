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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.m3.m3lib.parser.Message;
import org.m3.m3lib.parser.MessageCollector;

/**
 * @author bertl
 * 
 */
abstract public class ScannerBase<Token>
  implements Scanner<Token>
{

  protected Set<String>          reservedSet_ = new HashSet<String>();
  protected Map<String, Token>   keyLookupMap = new TreeMap<String, Token>();
  protected Map<Token, String>   keyMap       = new TreeMap<Token, String>();
  protected String               file_;
  protected BufferedReader       fileRdr_;
  protected String               line_;
  protected int                  linePos_;
  protected int                  filePos_;
  protected int                  lastLinePos_;
  protected int                  ch_;
  protected Token                cur_;
  protected long                 lineNr_;
  protected StringBuffer         strBuf_      = new StringBuffer();
  protected String               strVal_;
  private long                   intVal_;
  private double                 dblVal_;
  protected Object               objVal_;
  private List<MessageCollector> collectors_  = new ArrayList<MessageCollector>();

  /**
   * 
   */
  public ScannerBase()
  {
    super();
  }

  /**
   * @param file
   * @throws FileNotFoundException
   */
  public ScannerBase(String file) throws FileNotFoundException
  {
    this(new BufferedReader(new FileReader(file)));
  }

  /**
   * @param rdr
   */
  public ScannerBase(Reader rdr)
  {
    super();

    initKeys();

    if (!rdr.markSupported()) {
      System.err.println("Scanner(): Reader doesn't support mark()! Aborting");
      System.exit(-1);
    }

    file_ = "<Unknown>";
    if (rdr instanceof BufferedReader) {
      fileRdr_ = (BufferedReader) rdr;
    }
    else {
      fileRdr_ = new BufferedReader(rdr);
    }

    cur_ = null;
    filePos_ = 0;
    line_ = "";
    lineNr_ = 0;
    linePos_ = 1;
    lastLinePos_ = 0;

    try {
      nextch();
    }
    catch (IOException e) {
      cur_ = getErrorToken();
      strVal_ = e.getMessage();
      objVal_ = e;
    }
  }

  protected abstract void initKeys();

  public void close()
  {
    try {
      fileRdr_.close();
      cur_ = getEofToken();
      line_ = null;
    }
    catch (IOException e) {
      cur_ = getErrorToken();
      strVal_ = e.getMessage();
      objVal_ = e;
    }
  }

  private Token setToken(Token token, String s, long i, double d, Object o)
  {
    cur_ = token;
    strVal_ = s;
    intVal_ = i;
    dblVal_ = d;
    objVal_ = o;

    return token;
  }

  protected Token setToken(Token token)
  {
    return setToken(token, null, 0, 0.0, null);
  }

  protected Token setToken(Token token, String s)
  {
    return setToken(token, s, 0, 0.0, s);
  }

  protected Token setToken(Token token, long i)
  {
    return setToken(token, null, i, i, new Long(i));
  }

  protected Token setToken(Token token, double d)
  {
    return setToken(token, null, 0, d, new Double(d));
  }

  protected void nextch()
    throws IOException
  {
    if (line_ == null) {
      line_ = "";
      linePos_ = 1;
    }

    if (linePos_ > line_.length()) {
      line_ = fileRdr_.readLine();
      if (line_ == null) {
        ch_ = -1;
        return;
      }
      linePos_ = 0;
      lineNr_ += 1;
    }
    if (linePos_ == line_.length()) {
      ch_ = '\n';
    }
    else {
      ch_ = line_.charAt(linePos_);
    }
    linePos_ += 1;
    filePos_ += 1;
  }

  protected boolean match(char ch)
    throws IOException
  {
    boolean r = (ch_ == ch);
    if (r) {
      nextch();
    }
    return r;
  }

  protected Token skipAndSetToken(Token token)
    throws IOException
  {
    nextch();
    return setToken(token);
  }

  public Token getCur()
  {
    return cur_;
  }

  public String getStrVal()
  {
    return strVal_;
  }

  public long getIntVal()
  {
    return intVal_;
  }

  public double getDblVal()
  {
    return dblVal_;
  }

  public Object getObjVal()
  {
    return objVal_;
  }

  public long getLineNr()
  {
    return lineNr_;
  }

  public int getLinePos()
  {
    return this.linePos_;
  }

  public int getFilePos()
  {
    return this.filePos_;
  }

  public void print(PrintWriter w)
    throws IOException
  {
    w.print(cur_.toString());
  }

  public SourceLocation getSourceLocation()
  {
    return new SourceLocation(file_, line_, lineNr_, lastLinePos_);
  }

  @Override
  public String toString()
  {
    return cur_.toString();
  }

  @Override
  public void addMessageCollector(MessageCollector c)
  {
    if (!this.collectors_.contains(c)) {
      this.collectors_.add(c);
    }
  }

  @Override
  public void removeMessageCollector(MessageCollector c)
  {
    if (this.collectors_.contains(c)) {
      this.collectors_.remove(c);
    }
  }

  @Override
  public boolean isReservedId(String id)
  {
    return reservedSet_.contains(id);
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

  protected void scanError(String msg)
  {
    addMessage(new ScanError<Token>(this, msg));
  }

}

/*
 * $Log$
 */
