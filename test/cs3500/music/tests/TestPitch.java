package cs3500.music.tests;


import org.junit.Test;

import cs3500.music.model.Pitch;
import cs3500.music.model.Tones;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Test class for Pitch.
 */
public class TestPitch {

  // testing constructor

  @Test(expected = NullPointerException.class)
  public void testPitchConstructorNull() {
    Pitch test = new Pitch(null, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPitchConstructorBadOctaveNeg1() {
    Pitch test = new Pitch(Tones.A_SHARP, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPitchConstructorBadOctave0() {
    Pitch test = new Pitch(Tones.A_SHARP, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPitchConstructorBadOctaveOver10() {
    Pitch test = new Pitch(Tones.A_SHARP, 11);
  }

  @Test
  public void testPitchConstructorGood1() {
    Pitch test = new Pitch(Tones.B, 1);
    assertEquals(test.getTone(), Tones.B);
    assertEquals(test.getOctave(), 1);
  }

  @Test
  public void testPitchConstructorGood10() {
    Pitch test = new Pitch(Tones.B, 10);
    assertEquals(test.getTone(), Tones.B);
    assertEquals(test.getOctave(), 10);
  }

  @Test(expected = NullPointerException.class)
  public void testPitchConstructorCopyNull() {
    Pitch test = new Pitch(null);

  }

  @Test
  public void testPitchConstructorCopy() {
    Pitch test = new Pitch(Tones.B, 10);
    Pitch test2 = new Pitch(test);
    assertEquals(test.getTone(), test2.getTone());
    assertEquals(test.getOctave(), test2.getOctave());
    assertEquals(test, test2);

  }

  // testing getNextPitch

  @Test(expected = IllegalArgumentException.class)
  public void testGetNextPitchB1NextOctaveOver10Octaves() {
    new Pitch(Tones.B, 10).getNextPitch();
  }

  @Test
  public void testGetNextPitchC1() {
    assertEquals(new Pitch(Tones.C, 1).getNextPitch(), new Pitch(Tones.C_SHARP, 1));
  }

  @Test
  public void testGetNextPitchCSHARP1() {
    assertEquals(new Pitch(Tones.C_SHARP, 1).getNextPitch(), new Pitch(Tones.D, 1));
  }

  @Test
  public void testGetNextPitchB1NextOctave() {
    assertEquals(new Pitch(Tones.B, 1).getNextPitch(), new Pitch(Tones.C, 2));
  }

  @Test
  public void testGetNextPitchB1NextOctave2() {
    assertEquals(new Pitch(Tones.B, 9).getNextPitch(), new Pitch(Tones.C, 10));
  }

  @Test
  public void testGetNextPitchE() {
    assertEquals(new Pitch(Tones.E, 5).getNextPitch(), new Pitch(Tones.F, 5));
  }

  // testing getPrevPitch

  @Test(expected = IllegalArgumentException.class)
  public void testGetPrevPitchC1PrevOctaveUnder1Octaves() {
    new Pitch(Tones.C, 1).getPrevPitch();
  }

  @Test
  public void testGetPrevPitchCSHARP1() {
    assertEquals(new Pitch(Tones.C_SHARP, 1).getPrevPitch(), new Pitch(Tones.C, 1));
  }

  @Test
  public void testGetPrevPitchC2PrevOctave() {
    assertEquals(new Pitch(Tones.C, 2).getPrevPitch(), new Pitch(Tones.B, 1));
  }

  @Test
  public void testGetPrevPitchCPrevOctave10() {
    assertEquals(new Pitch(Tones.C, 10).getPrevPitch(), new Pitch(Tones.B, 9));
  }

  @Test
  public void testGetPrevPitchE5() {
    assertEquals(new Pitch(Tones.E, 5).getPrevPitch(), new Pitch(Tones.D_SHARP, 5));
  }

  // testing toString

  @Test
  public void testToStringC1() {
    assertEquals(new Pitch(Tones.C, 1).toString(), "C1");
  }

  @Test
  public void testToStringB6() {
    assertEquals(new Pitch(Tones.B, 6).toString(), "B6");
  }

  @Test
  public void testToStringFSHARP6() {
    assertEquals(new Pitch(Tones.F_SHARP, 6).toString(), "F#6");
  }

  @Test
  public void testToStringGSHARP10() {
    assertEquals(new Pitch(Tones.G_SHARP, 10).toString(), "G#10");
  }

  // testing equals

  @Test
  public void testEqualsSame() {
    Pitch p1 = new Pitch(Tones.C, 1);
    assertEquals(p1, p1);
  }

  @Test
  public void testNotEqualsDifferentInstanceOf1() {
    Pitch p1 = new Pitch(Tones.C, 1);
    assertNotEquals(p1, Tones.C);
  }

  @Test
  public void testNotEqualsDifferentInstanceOf2() {
    Pitch p1 = new Pitch(Tones.C, 1);
    assertNotEquals(p1, "C1");
  }

  @Test
  public void testEquals1() {
    Pitch p1 = new Pitch(Tones.F_SHARP, 1);
    Pitch p2 = new Pitch(Tones.F_SHARP, 1);
    assertEquals(p1, p2);
  }

  @Test
  public void testEquals2() {
    Pitch p1 = new Pitch(Tones.F_SHARP, 1);
    Pitch p2 = new Pitch(p1);
    assertEquals(p1, p2);
  }

  @Test
  public void testEquals3() {
    Pitch p1 = new Pitch(Tones.F_SHARP, 1);
    Pitch p2 = new Pitch(Tones.G, 1);
    p2 = p2.getPrevPitch();
    assertEquals(p1, p2);
  }

  // testing

  @Test
  public void testHashCodeC1() {
    Pitch p1 = new Pitch(Tones.C, 1);
    assertEquals(p1.hashCode(), 24);
  }

  @Test
  public void testHashCodeCSharp1() {
    Pitch p1 = new Pitch(Tones.C_SHARP, 1);
    assertEquals(p1.hashCode(), 25);
  }

  @Test
  public void testHashCodeB4() {
    Pitch p1 = new Pitch(Tones.B, 4);
    assertEquals(p1.hashCode(), 71);
  }

  @Test
  public void testHashCodeDSharp7() {
    Pitch p1 = new Pitch(Tones.D_SHARP, 7);
    assertEquals(p1.hashCode(), 99);
  }


  @Test
  public void testHashCodeSame() {
    Pitch p1 = new Pitch(Tones.C, 1);
    assertEquals(p1.hashCode(), p1.hashCode());
  }

  @Test
  public void testHashCodeNotEqual1() {
    Pitch p1 = new Pitch(Tones.F_SHARP, 1);
    Pitch p2 = new Pitch(Tones.F_SHARP, 2);
    assertNotEquals(p1.hashCode(), p2.hashCode());
  }

  @Test
  public void testHashCodeNotEqual2() {
    Pitch p1 = new Pitch(Tones.G, 2);
    Pitch p2 = new Pitch(Tones.F_SHARP, 2);
    assertNotEquals(p1.hashCode(), p2.hashCode());
  }

  @Test
  public void testHashCode1() {
    Pitch p1 = new Pitch(Tones.F_SHARP, 1);
    Pitch p2 = new Pitch(Tones.F_SHARP, 1);
    assertEquals(p1.hashCode(), p2.hashCode());
  }

  @Test
  public void testHashCode2() {
    Pitch p1 = new Pitch(Tones.F_SHARP, 1);
    Pitch p2 = new Pitch(p1);
    assertEquals(p1.hashCode(), p2.hashCode());
  }

  @Test
  public void testHashCode3() {
    Pitch p1 = new Pitch(Tones.F_SHARP, 1);
    Pitch p2 = new Pitch(Tones.G, 1);
    p2 = p2.getPrevPitch();
    assertEquals(p1.hashCode(), p2.hashCode());
  }
}
