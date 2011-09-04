/**
 * Copyright (c) 2008 B. Laverman. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: bertl
 */

// $Id$

package org.m3.m3lib.ast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;

import org.m3.m3lib.walkers.run.StatementRunner;

/**
 * 
 * @author LavermB
 * 
 */
public class M3Runtime
{

  private static final String    miName_    = "META-INF";

  private static final String    indexName_ = "m3Index.xml";

  private static M3Runtime       instance_  = null;

  private static final Logger    log_       = Logger.getLogger(M3Runtime.class);

  private File                   baseDir_   = new File(".");

  private String                 buildName_ = "build";

  private String                 srcName_   = "src";

  private Properties             index_     = new Properties();

  private Map<String, Interface> m3ICache   = new HashMap<String, Interface>();

  private Map<String, Module>    m3MCache   = new HashMap<String, Module>();

  private Set<String>            m3MBusy    = new HashSet<String>();

  private BufferedReader         stdin_     = new BufferedReader(
                                                new InputStreamReader(System.in));

  private PrintWriter            stdout_    = new PrintWriter(System.out);

  private PrintWriter            stderr_    = new PrintWriter(System.err);

  /**
   * 
   */
  public M3Runtime()
  {
    super();

    log_.info("Created M3Runtime instance");
  }

  private static File subDir(File base, String file)
      throws M3RuntimeException
  {
    File result = new File(base + File.separator + file);

    if (!result.exists()) {
      if (!result.mkdir()) { throw new M3RuntimeException(
          "Failed to create directory \"" + result + "\""); }
    }
    return result;
  }

  private static File subFile(File base, String file)
      throws M3RuntimeException
  {
    File result = new File(base + File.separator + file);

    return result;
  }

  private File getIndexFile()
      throws M3RuntimeException
  {
    File mi = subDir(baseDir_, miName_);
    if (!mi.isDirectory()) { throw new M3RuntimeException(mi
        + " is not a directory"); }
    File result = subFile(mi, indexName_);
    if (!result.exists()) {
      try {
        new Properties().storeToXML(new FileOutputStream(result),
            "M3Runtime index");
      }
      catch (Exception e) {
        throw new M3RuntimeException(
            "Exception while trying to create initial index");
      }
    }
    if (!result.isFile()) { throw new M3RuntimeException(result
        + " is not a file"); }
    return result;
  }

  private void loadIndex()
      throws M3RuntimeException
  {
    if (index_.size() != 0) { return; }
    try {
      log_.info("Loading index from " + getIndexFile());
      index_.loadFromXML(new FileInputStream(getIndexFile()));
    }
    catch (InvalidPropertiesFormatException e) {
      throw new M3RuntimeException("Corrupt index");
    }
    catch (FileNotFoundException e) {
      throw new M3RuntimeException("Nonexisting index (strange)");
    }
    catch (IOException e) {
      throw new M3RuntimeException("IO error while reading index: "
          + e.getMessage());
    }
  }

  private File getBuildDir(String group, String name)
      throws M3RuntimeException
  {
    File build = subDir(subDir(baseDir_, buildName_), group);

    String[] path = name.split(".");

    for (int i = 0; i < path.length - 1; i++) {
      build = subDir(build, path[i]);
    }
    return build;
  }

  private File getInterfaceBuildDir(String name)
      throws M3RuntimeException
  {
    return getBuildDir("interfaces", name);
  }

  private File getModuleBuildDir(String name)
      throws M3RuntimeException
  {
    return getBuildDir("modules", name);
  }

  private File getBuildDir(M3CompilationUnit unit)
      throws M3RuntimeException
  {
    if (unit instanceof Interface) { return getInterfaceBuildDir(unit.getName()); }
    return getModuleBuildDir(unit.getName());
  }

  private void saveIndex()
      throws M3RuntimeException
  {
    try {
      log_.info("Saving index to " + getIndexFile());
      index_
          .storeToXML(new FileOutputStream(getIndexFile()), "M3Runtime index");
    }
    catch (InvalidPropertiesFormatException e) {
      throw new M3RuntimeException("Corrupt index");
    }
    catch (FileNotFoundException e) {
      throw new M3RuntimeException("Nonexisting index (strange)");
    }
    catch (IOException e) {
      throw new M3RuntimeException("IO error while reading index: "
          + e.getMessage());
    }
  }

  public Module getModule(String name)
      throws M3RuntimeException
  {
    if (name == null) { return null; }
    if (m3MCache.containsKey(name)) { return m3MCache.get(name); }

    if (m3MBusy.contains(name)) {
      log_.error("Recursive MODULE dependency detected!");
      throw new M3RuntimeException("Failed to load MODULE \"" + name
          + "\": Recursive dependency");
    }

    log_.info("Loading module \"" + name + "\"");
    m3MBusy.add(name);
    Module result = null;
    try {
      File buildDir = getModuleBuildDir(name);
      FileInputStream fis = new FileInputStream(subFile(buildDir, name + ".mo"));
      ObjectInputStream ois = new ObjectInputStream(fis);

      result = (Module) ois.readObject();

      ois.close();

      // Now get all needed interfaces
      for (String s : result.getExports()) {
        getInterface(s, false);
      }
      for (String s : result.getImports()) {
        if (!JavaContext.isJavaClass(s) && !JavaContext.isJavaPackage(s)) {
          getInterface(s);
        }
      }
      result.buildContext();
      StatementRunner.run(result, JavaContext.getInstance());

      m3MCache.put(name, result);
      return result;
    }
    catch (IOException e) {
      log_.error("IOException caught during load of module \"" + name + "\": "
          + e.getMessage());
      throw new M3RuntimeException("Load of module \"" + name + "\" failed");
    }
    catch (ClassNotFoundException e) {
      log_.error("ClassNotFoundException caught during load of module \""
          + name + "\": " + e.getMessage());
      throw new M3RuntimeException("Load of module \"" + name + "\" failed");
    }
    finally {
      m3MBusy.remove(name);
    }
  }

  public Module getModuleForInterface(String name)
      throws M3RuntimeException
  {
    return getModule(index_.getProperty(name));
  }

  public Interface getInterface(String name, boolean forceModuleLoad)
      throws M3RuntimeException
  {
    if (name == null) { return null; }
    if (m3ICache.containsKey(name)) { return m3ICache.get(name); }
    log_.info("Loading interface \"" + name + "\"");
    Interface result = null;
    try {
      File buildDir = getInterfaceBuildDir(name);
      FileInputStream fis = new FileInputStream(subFile(buildDir, name + ".io"));
      ObjectInputStream ois = new ObjectInputStream(fis);

      result = (Interface) ois.readObject();

      ois.close();
    }
    catch (IOException e) {
      log_.error("IOException caught during load of interface \"" + name
          + "\": " + e.getMessage());
      throw new M3RuntimeException("Load of interface \"" + name + "\" failed");
    }
    catch (ClassNotFoundException e) {
      log_.error("ClassNotFoundException caught during load of interface \""
          + name + "\": " + e.getMessage());
      throw new M3RuntimeException("Load of interface \"" + name + "\" failed");
    }
    m3ICache.put(name, result);

    // Now that loaded the interface, make sure the imports and Module gets
    // loaded and initialized as well.
    for (String s : result.getImports()) {
      getInterface(s);
    }
    if (forceModuleLoad) {
      getModule(index_.getProperty(name));
    }

    return result;
  }

  public Interface getInterface(String name)
      throws M3RuntimeException
  {
    return getInterface(name, true);
  }

  public void storeModule(Module mod)
      throws M3RuntimeException
  {
    final String name = mod.getName();
    log_.info("Storing module \"" + name + "\"");

    try {
      File buildDir = getBuildDir(mod);
      FileOutputStream fos = new FileOutputStream(subFile(buildDir, name
          + ".mo"));
      ObjectOutputStream oos = new ObjectOutputStream(fos);

      oos.writeObject(mod);

      oos.close();
    }
    catch (IOException e) {
      log_.error("IOException caught during save of interface \"" + name);
    }

    loadIndex();

    for (String intf : mod.getExports()) {
      index_.put(intf, mod.getName());
    }

    saveIndex();
  }

  public void storeInterface(Interface intf)
      throws M3RuntimeException
  {
    final String name = intf.getName();
    log_.info("Storing interface \"" + name + "\"");

    try {
      File buildDir = getBuildDir(intf);
      FileOutputStream fos = new FileOutputStream(subFile(buildDir, name
          + ".io"));
      ObjectOutputStream oos = new ObjectOutputStream(fos);

      oos.writeObject(intf);

      oos.close();
    }
    catch (IOException e) {
      log_.error("IOException caught during save of interface \"" + name);
    }
    loadIndex();
    if (!index_.containsKey(name)) {
      index_.put(name, "<Unknown>");
      saveIndex();
    }
  }

  /**
   * @return Returns the instance.
   */
  public static M3Runtime getInstance()
  {
    if (instance_ == null) {
      instance_ = new M3Runtime();
    }
    return instance_;
  }

  /**
   * @return Returns the indexName.
   */
  public static String getIndexName()
  {
    return indexName_;
  }

  /**
   * @return Returns the baseDir.
   */
  public File getBaseDir()
  {
    return this.baseDir_;
  }

  /**
   * @param baseDir
   *          The baseDir to set.
   */
  public void setBaseDir(String baseDir)
  {
    this.baseDir_ = new File(baseDir);
  }

  /**
   * @return Returns the buildName.
   */
  public String getBuildName()
  {
    return this.buildName_;
  }

  /**
   * @param buildName
   *          The buildName to set.
   */
  public void setBuildName(String buildName)
  {
    this.buildName_ = buildName;
  }

  /**
   * @return Returns the srcName.
   */
  public String getSrcName()
  {
    return this.srcName_;
  }

  /**
   * @param srcName
   *          The srcName to set.
   */
  public void setSrcName(String srcName)
  {
    this.srcName_ = srcName;
  }

  /**
   * @return Returns the stderr.
   */
  public PrintWriter getStderr()
  {
    return this.stderr_;
  }

  /**
   * @param stderr The stderr to set.
   */
  public void setStderr(PrintWriter stderr)
  {
    this.stderr_ = stderr;
  }

  /**
   * @return Returns the stdin.
   */
  public BufferedReader getStdin()
  {
    return this.stdin_;
  }

  /**
   * @param stdin The stdin to set.
   */
  public void setStdin(BufferedReader stdin)
  {
    this.stdin_ = stdin;
  }

  /**
   * @return Returns the stdout.
   */
  public PrintWriter getStdout()
  {
    return this.stdout_;
  }

  /**
   * @param stdout The stdout to set.
   */
  public void setStdout(PrintWriter stdout)
  {
    this.stdout_ = stdout;
  }

}

/*
 * $Log$
 */
