/**
 * 
 */
package nl.rakis.util;

import java.util.List;

/**
 * @author bertl
 *
 */
public class EqualsUtil
{

  /**
   * @param <T>
   * @param a
   * @param b
   * @return
   */
  public static <T>boolean equals(List<T> a, List<T> b) {
    boolean result = (a == null) ? (b == null) : ((b != null) && (a.size() == b.size()));

    if ((a != null) && result) {
      // both non-null and equals length
      for (int i = 0; result && (i < a.size()); i++) {
        result = equals(a.get(i), b.get(i));
      }
    }

    return result;
  }

  /**
   * @param <T>
   * @param a
   * @param b
   * @return
   */
  public static <T>boolean equals(T a, T b) {
    return (a == null) ? (b == null) : ((b != null) && a.equals(b));
  }

}
