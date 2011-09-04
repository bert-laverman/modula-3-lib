/**
 * Copyright (c) 2008 B. Laverman. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: bertl
 */

// $Id$

package org.m3.m3lib;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.m3.m3lib.ast.Context;
import org.m3.m3lib.ast.M3CompilationUnit;
import org.m3.m3lib.ast.SimpleContext;
import org.m3.m3lib.ast.stat.Statement;
import org.m3.m3lib.parser.DefaultMessageCollector;
import org.m3.m3lib.parser.M3Parser;
import org.m3.m3lib.parser.Message;
import org.m3.m3lib.reflect.visitor.Walker;
import org.m3.m3lib.scanner.M3Scanner;
import org.m3.m3lib.scanner.SourceLocation;
import org.m3.m3lib.walkers.print.ASTPrinter;

/**
 * 
 * @author LavermB
 * 
 */
public class TestParser
{

  public static class MyBean
  {
    private String name = "Jonatan";

    public String getName()
    {
      return name;
    }
  }

  public static class Gateway
  {
    private List<Object> args = new ArrayList<Object>();

    public void resetArgs()
    {
      args.clear();
    }

    public void addArg(Object o)
    {
      args.add(o);
    }

    public Object call(String clazz, String methodName)
    {
      // JavaBeans type work

      Object[] vals = args.toArray();
      Class<?>[] types = new Class[vals.length];
      for (int i = 0; i < vals.length; i++) {
        types[i] = vals[i].getClass();
      }
      try {
        Method method = Class.forName(clazz).getMethod(methodName, types);
        Object result = method.invoke(null, vals);
        return result;
      }
      catch (Exception e) {
        e.printStackTrace();
      }
      return null;
    }

    public Object get(String clazz, String fieldName)
    {
      // JavaBeans type work

      try {
        Field field = Class.forName(clazz).getField(fieldName);
        return field.get(null);
      }
      catch (Exception e) {
        e.printStackTrace();
      }
      return null;
    }

  }

  private static Context makeContext()
  {
    SimpleContext sc = new SimpleContext();

    sc.bind("a", 1);
    sc.bind("b", true);
    sc.bind("c", 3.14159265);
    Map<String, Object> m = new HashMap<String, Object>();
    m.put("field1", new Long(123));
    m.put("field2", new Boolean(false));
    m.put("field3", new Double(3.14159265));
    //    sc.bind("map", m);
    List<String> l = new ArrayList<String>();
    l.add("aap");
    l.add("noot");
    l.add("mies");
    //    sc.bind("array", l);

    //    sc.bind("person", new MyBean());
    sc.bind("int1", new Integer(1));
    //    sc.bind("gw", new Gateway());

    return sc;
  }

  private static void interactiveMain(Context ctx)
  {
    StringBuffer line = new StringBuffer();

    System.err.print("=> ");
    while (true) {
      try {
        int c = System.in.read();
        // System.err.println("[Read character \'"+((char)c)+"\']");
        // System.err.flush();
        if (c == '\r') {
          continue;
        }
        else if (c == '\n') {
          if (line.toString().equals("quit")) {
            break;
          }
          PrintWriter stderr = new PrintWriter(System.err);
          PrintWriter stdout = new PrintWriter(System.out);
          M3Scanner s = new M3Scanner(new StringReader(line.toString()));
          s.next();
          M3Parser prs = new M3Parser(s);
          Statement st;
          while ((st = prs.parseStatement()) != null) {
            if (st != null) {
              Walker.walk(st, new ASTPrinter(stderr));
            }
          }

          stdout.flush();
          stderr.flush();

          line.setLength(0);
          System.err.print("=> ");
        }
        else {
          line.append((char) c);
        }
      }
      catch (Exception e) {
        e.printStackTrace();
        line.setLength(0);
        continue;
      }
    }
  }

  /**
   * @param args
   */
  public static void main(String[] args)
  {
    Context ctx = makeContext();

    if (args.length == 0) {
      interactiveMain(ctx);
      System.exit(0);
    }
    PrintWriter stderr = new PrintWriter(System.err);
    PrintWriter stdout = new PrintWriter(System.out);
    M3Scanner s = null;
    M3Parser prs = null;
    DefaultMessageCollector coll = new DefaultMessageCollector();

    try {
      if (args.length > 0) {
        s = new M3Scanner(args[0]);
      }
      else {
        s = new M3Scanner(new BufferedReader(new InputStreamReader(System.in)));
      }

      s.next();

      prs = new M3Parser(s);
      prs.addMessageCollector(coll);

      M3CompilationUnit unit = prs.parseCompilationUnit();

      if (unit != null) {
        Walker.walk(unit, new ASTPrinter(stderr));
      }

    }
    catch (Exception e) {
      stderr.flush();
      e.printStackTrace();
    }
    catch (Throwable e) {
      stderr.flush();
      e.printStackTrace();
    }
    finally {
      for (Message msg: coll.getMessages()) {
        SourceLocation loc = msg.getSourceLocation();
        if (loc != null) {
          stderr.println(loc.toString()+": "+msg.getMessage());
          msg.getSourceLocation().printLong(stderr);
        }
        else {
          stderr.println(msg);
        }
        stderr.println();
      }

      stderr.close();
      stdout.close();
      if (s != null) {
        s.close();
      }
    }
    System.exit(0);
  }

//  public static String ConvertString(String input, Context ctx)
//    throws ScannerException, M3ParserException, M3RuntimeException
//  {
//    StringReader rdr = new StringReader(input);
//    M3Scanner scn = new M3Scanner(rdr);
//    scn.setTextMode(true);
//    StringWriter swr = new StringWriter(input.length());
//    PrintWriter wr = new PrintWriter(swr);
//    SimpleContext inner = new SimpleContext(ctx);
//    inner.bind("<<stdout>>", ObjectValue.fromJavaObject(wr));
//    M3Parser prs = new M3Parser(scn);
//  
//    try {
//      while (scn.getCur() != M3Token.EOF) {
//        Statement st = prs.parseStatement();
//        StatementRunner.run(st, inner);
//        if (scn.getCur() == M3Token.SEMICOLON) {
//          scn.next();
//        }
//      }
//    }
//    catch (IOException e) {
//      log_
//          .error(
//                 "IOException thrown while scanning from String. Rethrowing as ScannerException.",
//                 e);
//      throw new ScannerException(scn);
//    }
//    wr.flush();
//    return swr.toString();
//  }

//  private static final Logger log_ = Logger.getLogger(TestParser.class);

}

/*
 * $Log$
 */
