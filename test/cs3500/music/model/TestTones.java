//package cs3500.music.model;
//
//import static org.junit.Assert.assertEquals;
//
//import org.junit.Test;
//
//public class TestTones {
//
//  //testing toString method
//
//  @Test
//  public void testToStringC() {
//    assertEquals(Tones.C.toString(), "C");
//  }
//
//  public void testToStringCSHARP() {
//    assertEquals(Tones.C_SHARP.toString(), "C#");
//  }
//
//  @Test
//  public void testToStringB() {
//    assertEquals(Tones.B.toString(), "B");
//  }
//
//  @Test
//  public void testToStringE() {
//    assertEquals(Tones.E.toString(), "E");
//  }
//
//  @Test
//  public void testToStringDSHARP() {
//    assertEquals(Tones.D_SHARP.toString(), "D#");
//  }
//
//
//  // testing getNextTone
//
//  @Test
//  public void testGetNextToneC() {
//    assertEquals(Tones.C.getNextTone(), Tones.C_SHARP);
//  }
//
//  @Test
//  public void testGetNextToneCSHARP() {
//    assertEquals(Tones.C_SHARP.getNextTone(), Tones.D);
//  }
//
//  @Test
//  public void testGetNextToneB() {
//    assertEquals(Tones.B.getNextTone(), Tones.C);
//  }
//
//  @Test
//  public void testGetNextToneE() {
//    assertEquals(Tones.E.getNextTone(), Tones.F);
//  }
//
//  @Test
//  public void testGetNextToneDSHARP() {
//    assertEquals(Tones.D_SHARP.getNextTone(), Tones.E);
//  }
//
//  @Test
//  public void testGetNextToneGSHARP() {
//    assertEquals(Tones.G_SHARP.getNextTone(), Tones.A);
//  }
//
//  // testing getPrevTone
//  @Test
//  public void testGetPrevToneC() {
//    assertEquals(Tones.C.getPrevTone(), Tones.B);
//  }
//
//  @Test
//  public void testGetPrevToneCSHARP() {
//    assertEquals(Tones.C_SHARP.getPrevTone(), Tones.C);
//  }
//
//  @Test
//  public void testGetPrevToneB() {
//    assertEquals(Tones.B.getPrevTone(), Tones.A_SHARP);
//  }
//
//  @Test
//  public void testGetPrevToneE() {
//    assertEquals(Tones.E.getPrevTone(), Tones.D_SHARP);
//  }
//
//  @Test
//  public void testGetPrevToneDSHARP() {
//    assertEquals(Tones.D_SHARP.getPrevTone(), Tones.D);
//  }
//
//  @Test
//  public void testGetPrevToneGSHARP() {
//    assertEquals(Tones.G_SHARP.getPrevTone(), Tones.G);
//  }
//
//  // testing getPitchShiftedTone
//
//  @Test
//  public void testGetPitchShiftedToneBelow() {
//    assertEquals(Tones.C.getPitchShiftedTone(-1), Tones.B);
//  }
//
//  @Test
//  public void testGetPitchShiftedToneBelow2() {
//    assertEquals(Tones.C.getPitchShiftedTone(-9999), Tones.A);
//  }
//
//  @Test
//  public void testGetPitchShiftedToneOver() {
//    assertEquals(Tones.C.getPitchShiftedTone(9999), Tones.D_SHARP);
//  }
//
//  @Test
//  public void testGetPitchShiftedToneCAdd3() {
//    assertEquals(Tones.C.getPitchShiftedTone(3), Tones.D_SHARP);
//  }
//
//  @Test
//  public void testGetPitchShiftedToneDSubtract2() {
//    assertEquals(Tones.D.getPitchShiftedTone(-2), Tones.C);
//  }
//
//  @Test
//  public void testGetPitchShiftedToneCSHARPAdd15() {
//    assertEquals(Tones.C_SHARP.getPitchShiftedTone(15), Tones.E);
//  }
//
//  @Test
//  public void testGetPitchShiftedToneB8Subtract24() {
//    assertEquals(Tones.B.getPitchShiftedTone(-24), Tones.B);
//  }
//
//  @Test
//  public void testGetPitchShiftedToneComparison1() {
//    assertEquals(Tones.B.getPitchShiftedTone(6), Tones.B.getPitchShiftedTone(-6));
//  }
//
//  @Test
//  public void testGetPitchShiftedToneComparison2() {
//    assertEquals(Tones.C_SHARP.getPitchShiftedTone(3), Tones.F.getPitchShiftedTone(-1));
//  }
//
//
//}
