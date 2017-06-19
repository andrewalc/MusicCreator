package cs3500.music.tests;

import org.junit.Test;


import java.util.ArrayList;
import java.util.Arrays;

import cs3500.music.model.Note;
import cs3500.music.model.Pitch;
import cs3500.music.model.Tones;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Test class for Notes.
 */
public class TestNote {

  // testing Note Constructor
  @Test(expected = NullPointerException.class)
  public void testNoteCreationNull() {
    Note test = new Note(null, 2, 0, 2, 1, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNoteCreationBadOctave0() {
    Note test = new Note(Tones.C, 0, 0, 2, 1, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNoteCreationBadOctaveNeg1() {
    Note test = new Note(Tones.D, -1, 0, 4, 1, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNoteCreationBadOctave20() {
    Note test = new Note(Tones.B, 20, 6, 2, 1, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNoteCreationBadStartingBeatNeg1() {
    Note test = new Note(Tones.B, 4, -1, 2, 1, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNoteCreationBadStartingBeatNeg999() {
    Note test = new Note(Tones.B, 4, -999, 2, 1, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNoteCreationBadNumOfBeatsNeg1() {
    Note test = new Note(Tones.G, 4, 424, -1, 1, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNoteCreationBadNumOfBeatsNeg23456() {
    Note test = new Note(Tones.E, 4, 12, -23456, 1, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNoteCreationBadVolume() {
    Note test = new Note(Tones.E, 4, 12, -23456, 1, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNoteCreationBadInstrumentUnder() {
    Note test = new Note(Tones.E, 4, 12, -23456, -1, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNoteCreationBadInstrumentOver() {
    Note test = new Note(Tones.E, 4, 12, -23456, 128, 0);
  }

  @Test
  public void testNoteCreation1() {
    Note test = new Note(Tones.E, 2, 0, 4, 1, 5);
    assertEquals(test.getPitch().toString(), "E2");
    assertEquals(test.getPitch().getOctave(), 2);
    assertEquals(test.getStartingBeat(), 0);
    assertEquals(test.getBeats(), 4);
    assertEquals(test.getInstrument(), 1);
    assertEquals(test.getVolume(), 5);

  }

  @Test
  public void testNoteCreation2() {
    Note test = new Note(Tones.G_SHARP, 9, 4, 2, 2, 3);
    assertEquals(test.getPitch().toString(), "G#9");
    assertEquals(test.getPitch().getOctave(), 9);
    assertEquals(test.getStartingBeat(), 4);
    assertEquals(test.getBeats(), 2);
    assertEquals(test.getInstrument(), 2);
    assertEquals(test.getVolume(), 3);

  }

  @Test
  public void testNoteCreation3() {
    Note test = new Note(Tones.B, 2, 255, 90, 23, 10);
    assertEquals(test.getPitch().toString(), "B2");
    assertEquals(test.getPitch().getOctave(), 2);
    assertEquals(test.getStartingBeat(), 255);
    assertEquals(test.getBeats(), 90);
    assertEquals(test.getInstrument(), 23);
    assertEquals(test.getVolume(), 10);

  }

  // testing Note equals method

  @Test
  public void testEqualsExact1() {
    Note note1 = new Note(Tones.E, 2, 0, 4, 1, 10);
    assertEquals(note1.equals(note1), true);
  }

  @Test
  public void testEqualsExact2() {
    Note note1 = new Note(Tones.F_SHARP, 4, 64, 9, 1, 10);
    assertEquals(note1.equals(note1), true);
  }

  @Test
  public void testEqualsNotAnInstanceOf1() {
    Note note1 = new Note(Tones.F_SHARP, 4, 64, 9, 1, 10);
    assertNotEquals(note1.equals("32"), true);
  }

  @Test
  public void testEqualsNotAnInstanceOf2() {
    Note note1 = new Note(Tones.F_SHARP, 4, 64, 9, 1, 10);
    assertNotEquals(note1.equals(new Pitch(Tones.F_SHARP, 4)), true);
  }

  @Test
  public void testEqualsNotes1() {
    Note note1 = new Note(Tones.F_SHARP, 4, 64, 9, 1, 10);
    Note note2 = new Note(Tones.F_SHARP, 4, 64, 9, 1, 10);
    assertEquals(note1.equals(note2), true);
  }

  @Test
  public void testEqualsNotes2() {
    Note note1 = new Note(Tones.E, 10, 86, 10, 1, 10);
    Note note2 = new Note(Tones.E, 10, 86, 10, 1, 10);
    assertEquals(note1.equals(note2), true);
  }

  @Test
  public void testEqualsNote3() {
    Note note1 = new Note(Tones.E, 10, 86, 10, 1, 10);
    Note note2 = new Note(note1);
    assertEquals(note1.equals(note2), true);
  }

  @Test
  public void testNotEqualsNotes1() {
    Note note1 = new Note(Tones.E, 10, 86, 10, 1, 10);
    Note note2 = new Note(Tones.F, 10, 86, 10, 1, 10);
    assertNotEquals(note1.equals(note2), true);
  }

  @Test
  public void testNotEqualsNotes2() {
    Note note1 = new Note(Tones.E, 10, 86, 10, 1, 10);
    Note note2 = new Note(note1);
    note2.setStartingBeat(0);
    assertNotEquals(note1.equals(note2), true);
  }

  // testing Note hashcode method

  @Test
  public void testEqualHashcodeExact1() {
    Note note1 = new Note(Tones.E, 2, 0, 4, 1, 10);
    assertEquals(note1.hashCode(), note1.hashCode());
  }

  @Test
  public void testEqualHashcodeExact2() {
    Note note1 = new Note(Tones.F_SHARP, 4, 64, 9, 1, 10);
    assertEquals(note1.hashCode(), note1.hashCode());
  }

  @Test
  public void testEqualHashcodeNotes1() {
    Note note1 = new Note(Tones.F_SHARP, 4, 64, 9, 1, 10);
    Note note2 = new Note(Tones.F_SHARP, 4, 64, 9, 1, 10);
    assertEquals(note1.hashCode(), note2.hashCode());
  }

  @Test
  public void testEqualHashcodeNotes2() {
    Note note1 = new Note(Tones.E, 10, 86, 10, 1, 10);
    Note note2 = new Note(Tones.E, 10, 86, 10, 1, 10);
    assertEquals(note1.hashCode(), note2.hashCode());
  }

  @Test
  public void testEqualHashcodeNote3() {
    Note note1 = new Note(Tones.E, 10, 86, 10, 1, 10);
    Note note2 = new Note(note1);
    assertEquals(note1.hashCode(), note2.hashCode());
  }

  @Test
  public void testNotEqualHashcodeNotes1() {
    Note note1 = new Note(Tones.E, 10, 86, 10, 1, 10);
    Note note2 = new Note(Tones.F, 10, 86, 10, 1, 10);
    assertNotEquals(note1.hashCode(), note2.hashCode());
  }

  @Test
  public void testNotEqualHashcodeNotes2() {
    Note note1 = new Note(Tones.E, 10, 86, 10, 1, 10);
    Note note2 = new Note(note1);
    note2.setStartingBeat(0);
    assertNotEquals(note1.hashCode(), note2.hashCode());
  }

  // testing setters

  @Test(expected = IllegalArgumentException.class)
  public void testSetStartingBeatInvalid() {
    Note note1 = new Note(Tones.E, 10, 86, 10, 1, 10);
    note1.setStartingBeat(-1);
  }

  @Test
  public void testSetStartingBeat() {
    Note note1 = new Note(Tones.E, 10, 86, 10, 1, 10);
    note1.setStartingBeat(0);
    assertEquals(note1, new Note(Tones.E, 10, 0, 10, 1, 10));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetBeatsInvalid() {
    Note note1 = new Note(Tones.E, 10, 86, 10, 1, 10);
    note1.setBeats(-1);
  }

  @Test
  public void testSetBeats() {
    Note note1 = new Note(Tones.E, 10, 86, 10, 1, 10);
    note1.setBeats(7);
    assertEquals(note1, new Note(Tones.E, 10, 86, 7, 1, 10));
  }

  // testing noteSymbolString

  @Test
  public void testNoteSymbolString1() {
    Note test = new Note(Tones.C, 1, 2, 4, 1, 10);
    assertEquals(test.noteSymbolString(1), "     ");
    assertEquals(test.noteSymbolString(2), "  X  ");
    assertEquals(test.noteSymbolString(3), "  |  ");
    assertEquals(test.noteSymbolString(4), "  |  ");
    assertEquals(test.noteSymbolString(5), "  |  ");
    assertEquals(test.noteSymbolString(6), "  |  ");
    assertEquals(test.noteSymbolString(7), "     ");

  }

  @Test
  public void testNoteSymbolString2() {
    Note test = new Note(Tones.F_SHARP, 5, 402, 2, 1, 10);
    assertEquals(test.noteSymbolString(401), "     ");
    assertEquals(test.noteSymbolString(402), "  X  ");
    assertEquals(test.noteSymbolString(403), "  |  ");
    assertEquals(test.noteSymbolString(404), "  |  ");
    assertEquals(test.noteSymbolString(405), "     ");
  }

  @Test
  public void testNoteSymbolString3() {
    Note test = new Note(Tones.D_SHARP, 1, 9, 1, 1, 10);
    assertEquals(test.noteSymbolString(8), "     ");
    assertEquals(test.noteSymbolString(9), "  X  ");
    assertEquals(test.noteSymbolString(10), "  |  ");
    assertEquals(test.noteSymbolString(11), "     ");
  }

  // testing convertArrayListIntegerToNote

  @Test(expected = IllegalArgumentException.class)
  public void testConvertArrayListIntegerToNoteBadStartingBeat() {
    Note.convertArrayListIntegerToNote(new ArrayList<Integer>(Arrays.asList(-11, 2, 5, 32, 10)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConvertArrayListIntegerToNoteBadEndBeat() {
    Note.convertArrayListIntegerToNote(new ArrayList<Integer>(Arrays.asList(454, 5, 5, 32, 10)));
  }

  @Test
  public void testConvertArrayListIntegerToNote1() {
    Note note = Note.convertArrayListIntegerToNote(new ArrayList<Integer>(Arrays.asList(1, 5, 12,
            24,
            10)));
    assertEquals(note, new Note(Tones.C, 1, 1, 5, 12, 10));
  }

  @Test
  public void testConvertArrayListIntegerToNote2() {
    Note note = Note.convertArrayListIntegerToNote(new ArrayList<Integer>(Arrays.asList(6, 7, 34,
            25,
            22)));
    assertEquals(note, new Note(Tones.C_SHARP, 1, 6, 2, 34, 22));
  }

  @Test
  public void testConvertArrayListIntegerToNote3() {
    Note note = Note.convertArrayListIntegerToNote(new ArrayList<Integer>(Arrays.asList(123, 130,
            122, 60, 64)));
    assertEquals(note, new Note(Tones.C, 4, 123, 8, 122, 64));
  }

  // testing convertNoteToArrayListInteger

  @Test
  public void testConverNoteToArrayListInteger1() {
    Note note = new Note(Tones.C, 1, 3, 4, 5, 10);
    assertEquals(note.convertNoteToArrayListInteger(), new ArrayList<Integer>(Arrays.asList(3, 6,
            5, 24, 10)));
  }

  @Test
  public void testConverNoteToArrayListInteger2() {
    Note note = new Note(Tones.C, 4, 10, 1, 23, 44);
    assertEquals(note.convertNoteToArrayListInteger(), new ArrayList<Integer>(Arrays.asList(10, 10,
            23, 60, 44)));
  }

  @Test
  public void testConverNoteToArrayListInteger3() {
    Note note = new Note(Tones.D_SHARP, 2, 4, 3, 22, 1);
    assertEquals(note.convertNoteToArrayListInteger(), new ArrayList<Integer>(Arrays.asList(4, 6,
            22, 39, 1)));
  }

}
