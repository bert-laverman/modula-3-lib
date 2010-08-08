/**
 * 
 */
package nl.rakis.util;

/**
 * @author bertl
 *
 */
public class CompareUtil
{

  public static <T extends Comparable<T>>int compare(T a, T b) {
    return (a == null) ? ((b == null) ? 0 : -1) : ((b == null) ? -1 : a.compareTo(b));
  }
}
