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

import java.io.Serializable;
import java.io.PrintWriter;

/**
 * 
 * @author LavermB
 * 
 */
public class SourceLocation
    implements Serializable
{

  /**
   * 
   */
  private static final long serialVersionUID = -5100244377666122533L;

  private String            fileName_;

  private String            lineText_;

  private long              lineNr_;

  private int               linePos_;

  /**
   * @param fileName
   * @param lineText
   * @param lineNr
   * @param linePos
   */
  public SourceLocation(String fileName, String lineText, long lineNr,
      int linePos)
  {
    // TODO Auto-generated constructor stub
    this.fileName_ = fileName;
    this.lineText_ = lineText;
    this.lineNr_ = lineNr;
    this.linePos_ = linePos;
  }

  /**
   * @return Returns the fileName.
   */
  public String getFileName()
  {
    return fileName_;
  }

  /**
   * @param fileName
   *          The fileName to set.
   */
  public void setFileName(String fileName)
  {
    this.fileName_ = fileName;
  }

  /**
   * @return Returns the lineNr.
   */
  public long getLineNr()
  {
    return lineNr_;
  }

  /**
   * @param lineNr
   *          The lineNr to set.
   */
  public void setLineNr(long lineNr)
  {
    this.lineNr_ = lineNr;
  }

  /**
   * @return Returns the linePos.
   */
  public int getLinePos()
  {
    return linePos_;
  }

  /**
   * @param linePos
   *          The linePos to set.
   */
  public void setLinePos(int linePos)
  {
    this.linePos_ = linePos;
  }

  /**
   * @return Returns the lineText.
   */
  public String getLineText()
  {
    return lineText_;
  }

  /**
   * @param lineText
   *          The lineText to set.
   */
  public void setLineText(String lineText)
  {
    this.lineText_ = lineText;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#clone()
   */
  protected Object clone()
      throws CloneNotSupportedException
  {
    return new SourceLocation(fileName_, lineText_, lineNr_, linePos_);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  public boolean equals(Object arg0)
  {
    return (arg0 != null) && arg0.getClass().equals(getClass())
        && arg0.toString().equals(toString());
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  public int hashCode()
  {
    return toString().hashCode();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  public String toString()
  {
    return fileName_ + ":" + lineNr_ + "(" + linePos_ + ")";
  }

  public void printLong(PrintWriter w)
  {
    w.print(fileName_); w.print(":");
    w.print(lineNr_); w.print(": ");
    w.println(lineText_);
    w.print(fileName_); w.print(":");
    w.print(lineNr_); w.print(": ");
    for (int i = 0; i < linePos_-1; i++) {
      w.print(' ');
    }
    w.println("^");
  }
}

/*
 * $Log$
 */
