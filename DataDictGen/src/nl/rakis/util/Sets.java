/**
 * 
 */
package nl.rakis.util;

import java.util.Set;

/**
 * @author bertl
 *
 */
public class Sets
{
  /**
   * Compares to sets and fills the two others with elements in only one of the original two.
   * 
   * @param <T>
   * @param a
   * @param b
   * @param inAnotB
   * @param inBnotA
   * @return
   */
  public static <T>boolean intersect(Set<T> a, Set<T> b, Set<T> inAnotB, Set<T> inBnotA) {
    inAnotB.clear();
    inBnotA.clear();
    inBnotA.addAll(b);

    for (T t: a) {
      if (b.contains(t)) {
        inBnotA.remove(t);
      }
      else {
        inAnotB.add(t);
      }
    }
    return (inAnotB.size() > 0) || (inBnotA.size() > 0);
  }
}
