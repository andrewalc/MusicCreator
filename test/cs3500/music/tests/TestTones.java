package cs3500.music.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import cs3500.music.model.Tones;


/**
 * Test class for Tones.
 */
public class TestTones {

  //testing toString method

  @Test
  public void testToStringC() {
    assertEquals(Tones.C.toString(), "C");
  }

  public void testToStringCSHARP() {
    assertEquals(Tones.C_SHARP.toString(), "C#");
  }

  @Test
  public void testToStringB() {
    assertEquals(Tones.B.toString(), "B");
  }

  @Test
  public void testToStringE() {
    assertEquals(Tones.E.toString(), "E");
  }

  @Test
  public void testToStringDSHARP() {
    assertEquals(Tones.D_SHARP.toString(), "D#");
  }

  // testing getToneVal

  @Test
  public void testGetToneValC() {
    assertEquals(Tones.C.getToneVal(), 0);
  }

  @Test
  public void testGetToneValCSharp() {
    assertEquals(Tones.C_SHARP.getToneVal(), 1);
  }

  @Test
  public void testGetToneValD() {
    assertEquals(Tones.D.getToneVal(), 2);
  }

  @Test
  public void testGetToneValG() {
    assertEquals(Tones.G.getToneVal(), 7);
  }

  @Test
  public void testGetToneValA() {
    assertEquals(Tones.A.getToneVal(), 9);
  }

  @Test
  public void testGetToneValASharp() {
    assertEquals(Tones.A_SHARP.getToneVal(), 10);
  }

  @Test
  public void testGetToneValB() {
    assertEquals(Tones.B.getToneVal(), 11);
  }

  // testing getToneAtToneVal

  @Test(expected = IllegalArgumentException.class)
  public void testToneAtToneValInvalidUnder0() {
    Tones.getToneAtToneVal(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testToneAtToneValInvalidOver11() {
    Tones.getToneAtToneVal(12);
  }

  @Test
  public void testToneAtToneVals() {
    assertEquals(Tones.getToneAtToneVal(0), Tones.C);
    assertEquals(Tones.getToneAtToneVal(1), Tones.C_SHARP);
    assertEquals(Tones.getToneAtToneVal(2), Tones.D);
    assertEquals(Tones.getToneAtToneVal(3), Tones.D_SHARP);
    assertEquals(Tones.getToneAtToneVal(4), Tones.E);
    assertEquals(Tones.getToneAtToneVal(5), Tones.F);
    assertEquals(Tones.getToneAtToneVal(6), Tones.F_SHARP);
    assertEquals(Tones.getToneAtToneVal(7), Tones.G);
    assertEquals(Tones.getToneAtToneVal(8), Tones.G_SHARP);
    assertEquals(Tones.getToneAtToneVal(9), Tones.A);
    assertEquals(Tones.getToneAtToneVal(10), Tones.A_SHARP);
    assertEquals(Tones.getToneAtToneVal(11), Tones.B);
  }
  //  testing getNextTone

  @Test
  public void testGetNextToneC() {
    assertEquals(Tones.C.getNextTone(), Tones.C_SHARP);
  }

  @Test
  public void testGetNextToneCSHARP() {
    assertEquals(Tones.C_SHARP.getNextTone(), Tones.D);
  }

  @Test
  public void testGetNextToneB() {
    assertEquals(Tones.B.getNextTone(), Tones.C);
  }

  @Test
  public void testGetNextToneE() {
    assertEquals(Tones.E.getNextTone(), Tones.F);
  }

  @Test
  public void testGetNextToneDSHARP() {
    assertEquals(Tones.D_SHARP.getNextTone(), Tones.E);
  }

  @Test
  public void testGetNextToneGSHARP() {
    assertEquals(Tones.G_SHARP.getNextTone(), Tones.A);
  }

  // testing getPrevTone
  @Test
  public void testGetPrevToneC() {
    assertEquals(Tones.C.getPrevTone(), Tones.B);
  }

  @Test
  public void testGetPrevToneCSHARP() {
    assertEquals(Tones.C_SHARP.getPrevTone(), Tones.C);
  }

  @Test
  public void testGetPrevToneB() {
    assertEquals(Tones.B.getPrevTone(), Tones.A_SHARP);
  }

  @Test
  public void testGetPrevToneE() {
    assertEquals(Tones.E.getPrevTone(), Tones.D_SHARP);
  }

  @Test
  public void testGetPrevToneDSHARP() {
    assertEquals(Tones.D_SHARP.getPrevTone(), Tones.D);
  }

  @Test
  public void testGetPrevToneGSHARP() {
    assertEquals(Tones.G_SHARP.getPrevTone(), Tones.G);
  }

  // testing getPitchShiftedTone

  @Test
  public void testGetPitchShiftedToneBelow() {
    assertEquals(Tones.C.getPitchShiftedTone(-1), Tones.B);
  }

  @Test
  public void testGetPitchShiftedToneBelow2() {
    assertEquals(Tones.C.getPitchShiftedTone(-9999), Tones.A);
  }

  @Test
  public void testGetPitchShiftedToneOver() {
    assertEquals(Tones.C.getPitchShiftedTone(9999), Tones.D_SHARP);
  }

  @Test
  public void testGetPitchShiftedToneCAdd3() {
    assertEquals(Tones.C.getPitchShiftedTone(3), Tones.D_SHARP);
  }

  @Test
  public void testGetPitchShiftedToneDSubtract2() {
    assertEquals(Tones.D.getPitchShiftedTone(-2), Tones.C);
  }

  @Test
  public void testGetPitchShiftedToneCSHARPAdd15() {
    assertEquals(Tones.C_SHARP.getPitchShiftedTone(15), Tones.E);
  }

  @Test
  public void testGetPitchShiftedToneB8Subtract24() {
    assertEquals(Tones.B.getPitchShiftedTone(-24), Tones.B);
  }

  @Test
  public void testGetPitchShiftedToneComparison1() {
    assertEquals(Tones.B.getPitchShiftedTone(6), Tones.B.getPitchShiftedTone(-6));
  }

  @Test
  public void testGetPitchShiftedToneComparison2() {
    assertEquals(Tones.C_SHARP.getPitchShiftedTone(3), Tones.F.getPitchShiftedTone(-1));
  }


}
