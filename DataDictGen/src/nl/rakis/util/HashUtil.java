/**
 * 
 */
package nl.rakis.util;

/**
 * @author bertl
 * 
 */
public class HashUtil
{
  /**
   * @param obj
   * @return
   */
  public static int safeHash(Object obj) {
    return (obj == null) ? 0 : obj.hashCode();
  }
}
