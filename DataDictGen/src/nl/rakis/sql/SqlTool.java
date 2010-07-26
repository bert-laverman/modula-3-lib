/**
 * 
 */
package nl.rakis.sql;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import nl.rakis.sql.ddl.SchemaGenerator;
import nl.rakis.sql.ddl.SchemaLoader;

/**
 * @author bertl
 * 
 */
public class SqlTool
{
  /**
   * 
   */
  public static final String     INPUT_FILE                 = "input.file";

  /**
   * 
   */
  public static final String     INPUT_FILE_NAME            = "input.file.name";

  /**
   * 
   */
  public static final String     INPUT_DB                   = "input.db";

  /**
   * 
   */
  public static final String     INPUT_DB_DRIVER_CLASS      = "input.db.driverClass";

  /**
   * 
   */
  public static final String     INPUT_DB_SERVER            = "input.db.server";

  /**
   * 
   */
  public static final String     INPUT_DB_PORT              = "input.db.port";

  /**
   * 
   */
  public static final String     INPUT_DB_NAME              = "input.db.name";

  /**
   * 
   */
  public static final String     INPUT_DB_USER              = "input.db.user";

  /**
   * 
   */
  public static final String     INPUT_DB_PWD               = "input.db.pwd";

  /**
   * 
   */
  public static final String     INPUT_DB_SCHEMA            = "input.db.schema";

  /**
   * 
   */
  public static final String     OUTPUT_FILE                = "output.file";

  /**
   * 
   */
  public static final String     OUTPUT_FILE_NAME           = "output.file.name";

  /**
   * 
   */
  public static final String     OUTPUT_DB                  = "output.db";

  /**
   * 
   */
  public static final String     OUTPUT_DB_DRIVER_CLASS     = "output.db.driverClass";

  /**
   * 
   */
  public static final String     OUTPUT_DB_SERVER           = "output.db.server";

  /**
   * 
   */
  public static final String     OUTPUT_DB_PORT             = "output.db.port";

  /**
   * 
   */
  public static final String     OUTPUT_DB_NAME             = "output.db.name";

  /**
   * 
   */
  public static final String     OUTPUT_DB_USER             = "output.db.user";

  /**
   * 
   */
  public static final String     OUTPUT_DB_PWD              = "output.db.pwd";

  /**
   * 
   */
  public static final String     OUTPUT_DB_SCHEMA           = "output.db.schema";

  /**
   * 
   */
  public static final String     SQL_FORMATTED              = "sql.formatted";

  public static final String     PROPFILE                   = "sql.properties";

  private static String          propFile_                  = PROPFILE;
  private static String          inputFileName_             = null;
  private static String          outputFileName_            = null;

  private static Properties      props_                     = new Properties();
  private static DbDriver        inputDriver_               = null;
  private static String          inputSchemaName_           = null;
  private static DbDriver        outputDriver_              = null;
  private static String          outputSchemaName_          = null;

  private static SchemaLoader    schemaLoader_              = null;
  private static Reader          schemaReader_              = null;
  private static Connection      schemaLoaderConnection_    = null;
  private static SchemaGenerator schemaGenerator_           = null;
  private static PrintWriter     schemaWriter_              = null;
  private static Connection      schemaGeneratorConnection_ = null;

  public static void error(String msg) {
    System.err.println(msg);
  }

  public static void warning(String msg) {
    System.err.println(msg + " (Ignored)");
  }

  public static String getProp(String name) {
    String result = null;

    if (props_ != null) {
      result = props_.getProperty(name);
    }
    return (result == null) ? "" : result;
  }

  public static boolean haveProp(String name) {
    return props_.containsKey(name);
  }

  public static Integer getIntProp(String name) {
    try {
      return new Integer(getProp(name));
    }
    catch (NumberFormatException e) {
      // IGNORE bad or missing value
    }
    return null;
  }

  public static Boolean getBoolProp(String name) {
    try {
      return getProp(name).equalsIgnoreCase("true");
    }
    catch (NullPointerException e) {
      // IGNORE
    }
    return false;
  }

  private static void loadProps() {
    final File propFile = new File(propFile_);
    if (propFile.exists() && (propFile.canRead())) {
      try {
        props_.load(new FileReader(propFile));
      }
      catch (FileNotFoundException e) {
        // Should not happen
        e.printStackTrace();
      }
      catch (IOException e) {
        error(e.getMessage());
      }
    }
    if (haveProp(INPUT_DB_SCHEMA)) {
      inputSchemaName_ = getProp(INPUT_DB_SCHEMA);
    }
    if ((inputFileName_ == null) && haveProp(INPUT_FILE_NAME)) {
      inputFileName_ = getProp(INPUT_FILE_NAME);
    }
    if (haveProp(OUTPUT_DB_SCHEMA)) {
      outputSchemaName_ = getProp(OUTPUT_DB_SCHEMA);
    }
    if ((outputFileName_ == null) && haveProp(OUTPUT_FILE_NAME)) {
      outputFileName_ = getProp(OUTPUT_FILE_NAME);
    }
  }

  public static void init(String... args) {
    for (int i = 0; i < args.length; i++) {
      if (args[i].equals("-p")) {
        if (++i == args.length) {
          error("Expected property filename after '-p'");
          System.exit(1);
        }
        setPropFile(args[i]);
      }
      else if (args[i].equals("-i")) {
        if (++i == args.length) {
          error("Expected input filename after '-i'");
          System.exit(1);
        }
        setInputFileName(args[i]);
      }
      else if (args[i].equals("-o")) {
        if (++i == args.length) {
          error("Expected output filename after '-o'");
          System.exit(1);
        }
        setInputFileName(args[i]);
      }
    }
    loadProps();
  }

  public static void cleanup() {
    try {
      if (schemaReader_ != null) {
        schemaReader_.close();
      }
      if (schemaLoaderConnection_ != null) {
        schemaLoaderConnection_.close();
      }
      if (schemaWriter_ != null) {
        schemaWriter_.close();
      }
      if (schemaGeneratorConnection_ != null) {
        schemaGeneratorConnection_.close();
      }
    }
    catch (Exception e) {
      warning(e.getMessage());
    }
  }

  /**
   * @param propFile
   *          the propFile to set
   */
  public static void setPropFile(String propFile) {
    propFile_ = propFile;
  }

  /**
   * @return the propFile
   */
  public static String getPropFile() {
    return propFile_;
  }

  /**
   * @param inputFileName
   *          the inputFileName to set
   */
  public static void setInputFileName(String inputFileName) {
    inputFileName_ = inputFileName;
  }

  /**
   * @return the inputFileName
   */
  public static String getInputFilename() {
    return inputFileName_;
  }

  /**
   * @return the outputFileName
   */
  public static String getOutputFileName() {
    return outputFileName_;
  }

  /**
   * @param outputFileName
   *          the outputFileName to set
   */
  public static void setOutputFileName(String outputFileName) {
    outputFileName_ = outputFileName;
  }

  public static DbDriver getInputDriver() {
    if (inputDriver_ == null) {
      final String className = props_.getProperty(INPUT_DB_DRIVER_CLASS);
      try {
        inputDriver_ = (DbDriver) Class.forName(className).newInstance();
        inputDriver_.init();
        inputDriver_.setProperties(props_);
      }
      catch (Exception e) {
        error("Failed to instantiate driver " + className);
        System.exit(1);
      }
    }
    return inputDriver_;
  }

  /**
   * @param driver
   *          the driver to set
   */
  public static void setInputDriver(DbDriver driver) {
    inputDriver_ = driver;
  }

  /**
   * @param schemaName
   *          the schemaName to set
   */
  public static void setInputSchemaName(String schemaName) {
    inputSchemaName_ = schemaName;
  }

  /**
   * @return the schemaName
   */
  public static String getInputSchemaName() {
    return inputSchemaName_;
  }

  /**
   * @param outputDriver
   *          the outputDriver to set
   */
  public static void setOutputDriver(DbDriver outputDriver) {
    outputDriver_ = outputDriver;
  }

  /**
   * @return the outputDriver
   */
  public static DbDriver getOutputDriver() {
    return outputDriver_;
  }

  /**
   * @param outputSchemaName the outputSchemaName to set
   */
  public static void setOutputSchemaName(String outputSchemaName) {
    outputSchemaName_ = outputSchemaName;
  }

  /**
   * @return the outputSchemaName
   */
  public static String getOutputSchemaName() {
    return outputSchemaName_;
  }

  /**
   * @param schemaLoader
   *          the schemaLoader to set
   */
  public static void setSchemaLoader(SchemaLoader schemaLoader) {
    schemaLoader_ = schemaLoader;
  }

  /**
   * @return the schemaLoader
   * @throws FileNotFoundException
   * @throws SQLException
   */
  public static SchemaLoader getSchemaLoader()
    throws FileNotFoundException,
      SQLException
  {
    if (schemaLoader_ == null) {
      if (haveProp(INPUT_FILE) && getBoolProp(INPUT_FILE)) {
        schemaReader_ = new BufferedReader(new FileReader(inputFileName_));
        schemaLoader_ = getInputDriver().getSchemaXmlReader(schemaReader_);
      }
      else if (haveProp(INPUT_DB) && getBoolProp(INPUT_DB)) {
        final String url = haveProp(INPUT_DB_PORT) ? getInputDriver()
            .buildUrl(getProp(INPUT_DB_SERVER), getIntProp(INPUT_DB_PORT),
                      getProp(INPUT_DB_NAME)) : getInputDriver()
            .buildUrl(getProp(INPUT_DB_SERVER), getProp(INPUT_DB_NAME));
        schemaLoaderConnection_ = getInputDriver()
            .getDb(url, getProp(INPUT_DB_USER), getProp(INPUT_DB_PWD));
        schemaLoader_ = getInputDriver()
            .getSchemaLoader(schemaLoaderConnection_);
      }
    }
    return schemaLoader_;
  }

  /**
   * @param schemaGenerator
   *          the schemaWriter to set
   */
  public static void setSchemaGenerator(SchemaGenerator schemaGenerator) {
    schemaGenerator_ = schemaGenerator;
  }

  /**
   * @return the schemaGenerator
   * @throws FileNotFoundException
   * @throws SQLException
   */
  public static SchemaGenerator getSchemaGenerator()
    throws FileNotFoundException,
      SQLException
  {
    if (schemaGenerator_ == null) {
      if (haveProp(OUTPUT_FILE) && getBoolProp(OUTPUT_FILE)) {
        schemaWriter_ = new PrintWriter(outputFileName_);
        schemaGenerator_ = getInputDriver().getSchemaWriter(schemaWriter_);
      }
      else if (haveProp(OUTPUT_DB) && getBoolProp(OUTPUT_DB)) {
        final String url = haveProp(OUTPUT_DB_PORT) ? getInputDriver()
            .buildUrl(getProp(OUTPUT_DB_SERVER), getIntProp(OUTPUT_DB_PORT),
                      getProp(OUTPUT_DB_NAME)) : getInputDriver()
            .buildUrl(getProp(OUTPUT_DB_SERVER), getProp(OUTPUT_DB_NAME));
        schemaGeneratorConnection_ = getInputDriver()
            .getDb(url, getProp(OUTPUT_DB_USER), getProp(OUTPUT_DB_PWD));
        schemaGenerator_ = getInputDriver()
            .getSchemaGenerator(schemaGeneratorConnection_);
      }
    }
    return schemaGenerator_;
  }
}
