package cs3500.music.view;

import java.util.Comparator;

/**
 * A Comparator for integers, in natural ordering.
 */
public class IntegerComparator implements Comparator<Integer> {
  @Override
  public int compare(Integer o1, Integer o2) {
    return o1 - o2;
  }
}
