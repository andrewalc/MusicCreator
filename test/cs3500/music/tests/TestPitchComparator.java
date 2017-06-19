package cs3500.music.tests;


import org.junit.Test;


import cs3500.music.model.Pitch;
import cs3500.music.model.PitchComparator;
import cs3500.music.model.Tones;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test class for PitchComparator.
 */
public class TestPitchComparator {

  PitchComparator comparator = new PitchComparator();

  // testing compare
  @Test
  public void testComparatorEqual1() {
    Pitch p1 = new Pitch(Tones.G, 1);
    assertEquals(comparator.compare(p1, p1), 0);
  }

  @Test
  public void testComparatorEqual2() {
    Pitch p1 = new Pitch(Tones.G, 1);
    Pitch p2 = new Pitch(p1);
    assertEquals(comparator.compare(p1, p2), 0);
  }

  @Test
  public void testComparatorEqual3() {
    Pitch p1 = new Pitch(Tones.G, 10);
    Pitch p2 = new Pitch(Tones.G, 10);
    assertEquals(comparator.compare(p1, p2), 0);
  }

  @Test
  public void testComparatorLessThan1() {
    Pitch p1 = new Pitch(Tones.D, 10);
    Pitch p2 = new Pitch(Tones.D_SHARP, 10);
    assertTrue(comparator.compare(p1, p2) < 0);
  }

  @Test
  public void testComparatorLessThan2() {
    Pitch p1 = new Pitch(Tones.E, 2);
    Pitch p2 = new Pitch(Tones.D_SHARP, 3);
    assertTrue(comparator.compare(p1, p2) < 0);
  }

  @Test
  public void testComparatorLessThan3() {
    Pitch p1 = new Pitch(Tones.F, 4);
    Pitch p2 = new Pitch(Tones.E, 8);
    assertTrue(comparator.compare(p1, p2) < 0);
  }

  @Test
  public void testComparatorLessThan4() {
    Pitch p1 = new Pitch(Tones.G, 4);
    Pitch p2 = new Pitch(Tones.D, 9);
    assertTrue(comparator.compare(p1, p2) < 0);
  }

  @Test
  public void testComparatorGreaterThan1() {
    Pitch p1 = new Pitch(Tones.G, 10);
    Pitch p2 = new Pitch(Tones.F_SHARP, 10);
    assertTrue(comparator.compare(p1, p2) > 0);
  }

  @Test
  public void testComparatorGreaterThan2() {
    Pitch p1 = new Pitch(Tones.G, 10);
    Pitch p2 = new Pitch(Tones.G, 9);
    assertTrue(comparator.compare(p1, p2) > 0);
  }

  @Test
  public void testComparatorGreaterThan3() {
    Pitch p1 = new Pitch(Tones.G, 10);
    Pitch p2 = new Pitch(p1.getPrevPitch());
    assertTrue(comparator.compare(p1, p2) > 0);
  }

  @Test
  public void testComparatorGreaterThan4() {
    Pitch p1 = new Pitch(Tones.C_SHARP, 1);
    Pitch p2 = new Pitch(Tones.C, 1);
    assertTrue(comparator.compare(p1, p2) > 0);
  }

  @Test
  public void testComparatorGreaterThan5() {
    Pitch p1 = new Pitch(Tones.E, 4);
    Pitch p2 = new Pitch(Tones.E, 1);
    assertTrue(comparator.compare(p1, p2) > 0);
  }
}
