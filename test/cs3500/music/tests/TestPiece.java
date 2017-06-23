package cs3500.music.tests;

import org.junit.Test;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.TreeMap;

import cs3500.music.model.Note;
import cs3500.music.model.Piece;
import cs3500.music.model.Pitch;
import cs3500.music.model.Tones;

import static org.junit.Assert.assertEquals;

/**
 * Test class for Piece.
 */
public class TestPiece {
  Piece p0; // assigned as an empty piece
  Piece p1; // a piece with notes added beforehand
  TreeMap<Pitch, ArrayList<Note>> p1TreeMap; // the treemap from p1

  // Pre-created notes for easy access
  Note c1For0And4 = new Note(Tones.C, 1, 0, 4, 1, 10);
  Note cSharp1For0And4 = new Note(Tones.C_SHARP, 1, 0, 4, 1, 10);
  Note d1For6And4 = new Note(Tones.D, 1, 6, 4, 1, 10);
  Note d1For7And5 = new Note(Tones.D, 1, 7, 5, 1, 10);

  Note dSharp1For6And4 = new Note(Tones.D_SHARP, 1, 6, 4, 1, 10);
  Note cSharp2For3And2 = new Note(Tones.C_SHARP, 2, 3, 2, 1, 10);
  Note cSharp2For10And1 = new Note(Tones.C_SHARP, 2, 10, 1, 1, 10);


  void initData() {

    p0 = new Piece();
    p1 = new Piece();
    p1.addNote(c1For0And4);
    p1.addNote(cSharp1For0And4);
    p1.addNote(d1For6And4);
    p1.addNote(d1For7And5);
    p1.addNote(dSharp1For6And4);
    p1.addNote(cSharp2For3And2);
    p1.addNote(cSharp2For10And1);

    p1TreeMap = p1.getAllNotes();

  }

  // testing constructors
  @Test
  public void testPieceEmptyConstructor() {
    initData();
    Piece test = new Piece();
    assertEquals(test.getAllNotes().size(), 0);
    assertEquals(test.getMaxBeats(), 0);
  }

  @Test
  public void testPieceCopyConstructor() {
    initData();
    Piece copy = new Piece(p1);
    assertEquals(copy.getMaxBeats(), 12);
    assertEquals(copy.getLowestPitch(), new Pitch(Tones.C, 1));
    assertEquals(copy.getHighestPitch(), new Pitch(Tones.C_SHARP, 2));
    assertEquals(copy.getNumberOfNotes(), 7);
    assertEquals(p1, copy);
  }

  @Test
  public void testPieceTreeMapConstructor() {
    initData();
    Piece test = new Piece(p1TreeMap);
    assertEquals(test.getMaxBeats(), 12);
    assertEquals(test.getLowestPitch(), new Pitch(Tones.C, 1));
    assertEquals(test.getHighestPitch(), new Pitch(Tones.C_SHARP, 2));
    assertEquals(test.getNumberOfNotes(), 7);
    assertEquals(test.getAllNotes(), p1TreeMap);

  }

  // testing addNote

  @Test(expected = NullPointerException.class)
  public void testAddNoteNull() {
    initData();
    p0.addNote(null);
  }

  @Test
  public void testAddNote1() {
    initData();
    assertEquals(p0.getNumberOfNotes(), 0);
    p0.addNote(c1For0And4);
    assertEquals(p0.getNumberOfNotes(), 1);
    assertEquals(p0.getLowestPitch(), new Pitch(Tones.C, 1));
    assertEquals(p0.getHighestPitch(), new Pitch(Tones.C, 1));
    assertEquals(p0.getMaxBeats(), 4);
  }

  @Test
  public void testAddNote2() {
    initData();
    assertEquals(p0.getNumberOfNotes(), 0);
    p0.addNote(c1For0And4);
    p0.addNote(dSharp1For6And4);
    assertEquals(p0.getNumberOfNotes(), 2);
    assertEquals(p0.getLowestPitch(), new Pitch(Tones.C, 1));
    assertEquals(p0.getHighestPitch(), new Pitch(Tones.D_SHARP, 1));
    assertEquals(p0.getMaxBeats(), 10);
  }

  @Test
  public void testAddNote3ExactSame() {
    initData();
    assertEquals(p0.getNumberOfNotes(), 0);
    p0.addNote(cSharp2For10And1);
    p0.addNote(cSharp2For10And1);
    assertEquals(p0.getNumberOfNotes(), 2);
    assertEquals(p0.getLowestPitch(), new Pitch(Tones.C_SHARP, 2));
    assertEquals(p0.getHighestPitch(), new Pitch(Tones.C_SHARP, 2));
    assertEquals(p0.getMaxBeats(), 11);
  }

  @Test
  public void testAddNote4Overlap() {
    initData();
    assertEquals(p0.getNumberOfNotes(), 0);
    p0.addNote(d1For6And4);
    p0.addNote(d1For7And5);
    assertEquals(p0.getNumberOfNotes(), 2);
    assertEquals(p0.getLowestPitch(), new Pitch(Tones.D, 1));
    assertEquals(p0.getHighestPitch(), new Pitch(Tones.D, 1));
    assertEquals(p0.getMaxBeats(), 12);
  }

  // testing removeNote

  @Test(expected = NullPointerException.class)
  public void testRemoveNoteNull() {
    initData();
    p1.removeNote(null);
  }

  @Test(expected = NoSuchElementException.class)
  public void testRemoveNoteNotFound1() {
    initData();
    p1.removeNote(new Note(Tones.B, 10, 0, 10, 1, 10));
  }

  @Test(expected = NoSuchElementException.class)
  public void testRemoveNoteNotFound2() {
    initData();
    // close to D1For6and4
    p1.removeNote(new Note(Tones.D, 1, 6, 5, 1, 10));
  }

  @Test(expected = NoSuchElementException.class)
  public void testRemoveNoteNotFound3() {
    initData();
    // close to D1For6and4
    p1.removeNote(new Note(Tones.D, 2, 6, 4, 1, 10));
  }

  @Test
  public void testRemoveNote1() {
    initData();
    assertEquals(p1.getNumberOfNotes(), 7);
    p1.removeNote(d1For6And4);
    assertEquals(p1.getNumberOfNotes(), 6);
  }

  @Test
  public void testRemoveNote2() {
    initData();
    assertEquals(p1.getNumberOfNotes(), 7);
    assertEquals(p1.getLowestPitch(), new Pitch(Tones.C, 1));
    p1.removeNote(c1For0And4);
    assertEquals(p1.getNumberOfNotes(), 6);
    assertEquals(p1.getLowestPitch(), new Pitch(Tones.C_SHARP, 1));

  }

  @Test
  public void testRemoveNote3() {
    initData();
    assertEquals(p1.getNumberOfNotes(), 7);
    assertEquals(p1.getHighestPitch(), new Pitch(Tones.C_SHARP, 2));
    p1.removeNote(cSharp2For10And1);
    assertEquals(p1.getNumberOfNotes(), 6);
    assertEquals(p1.getHighestPitch(), new Pitch(Tones.C_SHARP, 2));
    p1.removeNote(cSharp2For3And2);
    assertEquals(p1.getNumberOfNotes(), 5);
    assertEquals(p1.getHighestPitch(), new Pitch(Tones.D_SHARP, 1));

  }

  // testing modifyNote

  @Test(expected = NullPointerException.class)
  public void testModifyNoteNullNote() {
    initData();
    p1.modifyNote(null, Tones.C, 1, 0, 10, 1, 10);
  }

  @Test(expected = NullPointerException.class)
  public void testModifyNoteNullTone() {
    initData();
    p1.modifyNote(d1For6And4, null, 1, 0, 10, 1, 10);
  }

  @Test(expected = NoSuchElementException.class)
  public void testModifyNoteNotFound() {
    initData();
    p1.modifyNote(new Note(Tones.C, 1, 0, 1, 1, 10), Tones.C, 1, 0, 10, 1, 10);
  }

  @Test
  public void testModifyNote1() {
    initData();
    assertEquals(p1.getNumberOfNotes(), 7);
    assertEquals(p1.getHighestPitch(), new Pitch(Tones.C_SHARP, 2));
    p1.modifyNote(cSharp2For10And1, Tones.A_SHARP, 8, 0, 2, 1, 10);
    assertEquals(p1.getNumberOfNotes(), 7);
    assertEquals(p1.getHighestPitch(), new Pitch(Tones.A_SHARP, 8));
  }

  @Test
  public void testModifyNote2() {
    initData();
    assertEquals(p1.getNumberOfNotes(), 7);
    assertEquals(p1.getLowestPitch(), new Pitch(Tones.C, 1));
    p1.modifyNote(c1For0And4, Tones.D, 2, 7, 10, 1, 10);
    assertEquals(p1.getNumberOfNotes(), 7);
    assertEquals(p1.getLowestPitch(), new Pitch(Tones.C_SHARP, 1));
    p1.modifyNote(cSharp1For0And4, Tones.C_SHARP, 5, 5, 3, 1, 10);
    assertEquals(p1.getNumberOfNotes(), 7);
    assertEquals(p1.getLowestPitch(), new Pitch(Tones.D, 1));

  }

  // testing getLowestPitch and getHighestPitch exceptions

  @Test(expected = NoSuchElementException.class)
  public void testGetLowestPitchNoNotes() {
    initData();
    p0.getLowestPitch();
  }

  @Test(expected = NoSuchElementException.class)
  public void testGetHighestPitchNoNotes() {
    initData();
    p0.getHighestPitch();
  }

  // testing toString method

  @Test
  public void testToStringEmptyPiece() {
    initData();
    assertEquals(p0.toString(), "");
  }

  @Test
  public void testToStringPiece1() {
    initData();
    assertEquals(p1.toString(),
            "    C1  C#1   D1  D#1   E1   F1  F#1   G1  G#1   A1  A#1   B1   C2  C#2 \n" +
                    " 0  X    X                                                              \n" +
                    " 1  |    |                                                              \n" +
                    " 2  |    |                                                              \n" +
                    " 3  |    |                                                           X  \n" +
                    " 4                                                                   |  \n" +
                    " 5                                                                      \n" +
                    " 6            X    X                                                    \n" +
                    " 7            X    |                                                    \n" +
                    " 8            |    |                                                    \n" +
                    " 9            |    |                                                    \n" +
                    "10            |                                                      X  \n" +
                    "11            |                                                         \n" +
                    "12                                                                      \n");
  }

  @Test
  public void testToStringPieceAdd() {
    initData();
    assertEquals(p1.toString(),
            "    C1  C#1   D1  D#1   E1   F1  F#1   G1  G#1   A1  A#1   B1   C2  C#2 \n" +
                    " 0  X    X                                                              \n" +
                    " 1  |    |                                                              \n" +
                    " 2  |    |                                                              \n" +
                    " 3  |    |                                                           X  \n" +
                    " 4                                                                   |  \n" +
                    " 5                                                                      \n" +
                    " 6            X    X                                                    \n" +
                    " 7            X    |                                                    \n" +
                    " 8            |    |                                                    \n" +
                    " 9            |    |                                                    \n" +
                    "10            |                                                      X  \n" +
                    "11            |                                                         \n" +
                    "12                                                                      \n");

    p1.addNote(new Note(Tones.G, 1, 1, 5, 1, 10));
    assertEquals(p1.toString(),
            "    C1  C#1   D1  D#1   E1   F1  F#1   G1  G#1   A1  A#1   B1   C2  C#2 \n" +
                    " 0  X    X                                                              \n" +
                    " 1  |    |                             X                                \n" +
                    " 2  |    |                             |                                \n" +
                    " 3  |    |                             |                             X  \n" +
                    " 4                                     |                             |  \n" +
                    " 5                                     |                                \n" +
                    " 6            X    X                                                    \n" +
                    " 7            X    |                                                    \n" +
                    " 8            |    |                                                    \n" +
                    " 9            |    |                                                    \n" +
                    "10            |                                                      X  \n" +
                    "11            |                                                         \n" +
                    "12                                                                      \n");

  }

  @Test
  public void testToStringPieceAddToEmpty() {
    initData();
    assertEquals(p0.toString(),
            "");

    p0.addNote(new Note(Tones.G, 1, 1, 5, 1, 10));
    assertEquals(p0.toString(),
            "   G1 \n" +
                    "0     \n" +
                    "1  X  \n" +
                    "2  |  \n" +
                    "3  |  \n" +
                    "4  |  \n" +
                    "5  |  \n" +
                    "6     \n");
    p0.addNote(new Note(Tones.B, 1, 0, 8, 1, 10));
    assertEquals(p0.toString(),
            "   G1  G#1   A1  A#1   B1 \n" +
                    "0                      X  \n" +
                    "1  X                   |  \n" +
                    "2  |                   |  \n" +
                    "3  |                   |  \n" +
                    "4  |                   |  \n" +
                    "5  |                   |  \n" +
                    "6                      |  \n" +
                    "7                      |  \n" +
                    "8                         \n");
    p0.addNote(new Note(Tones.B, 1, 6, 4, 1, 10));
    assertEquals(p0.toString(),
            "    G1  G#1   A1  A#1   B1 \n" +
                    " 0                      X  \n" +
                    " 1  X                   |  \n" +
                    " 2  |                   |  \n" +
                    " 3  |                   |  \n" +
                    " 4  |                   |  \n" +
                    " 5  |                   |  \n" +
                    " 6                      X  \n" +
                    " 7                      |  \n" +
                    " 8                      |  \n" +
                    " 9                      |  \n" +
                    "10                         \n");
    p0.addNote(new Note(Tones.F, 1, 4, 16, 1, 10));
    assertEquals(p0.toString(),
            "    F1  F#1   G1  G#1   A1  A#1   B1 \n" +
                    " 0                                X  \n" +
                    " 1            X                   |  \n" +
                    " 2            |                   |  \n" +
                    " 3            |                   |  \n" +
                    " 4  X         |                   |  \n" +
                    " 5  |         |                   |  \n" +
                    " 6  |                             X  \n" +
                    " 7  |                             |  \n" +
                    " 8  |                             |  \n" +
                    " 9  |                             |  \n" +
                    "10  |                                \n" +
                    "11  |                                \n" +
                    "12  |                                \n" +
                    "13  |                                \n" +
                    "14  |                                \n" +
                    "15  |                                \n" +
                    "16  |                                \n" +
                    "17  |                                \n" +
                    "18  |                                \n" +
                    "19  |                                \n" +
                    "20                                   \n");


  }

  @Test
  public void testToStringPieceAddOctave10And3DigitBeats() {
    initData();
    assertEquals(p0.toString(),
            "");
    p0.addNote(new Note(Tones.F, 10, 40, 123, 1, 10));
    p0.addNote(new Note(Tones.B, 10, 50, 30, 1, 10));

    assertEquals(p0.toString(),
            "    F10  F#10 G10  G#10 A10  A#10 B10 \n" +
                    "  0                                   \n" +
                    "  1                                   \n" +
                    "  2                                   \n" +
                    "  3                                   \n" +
                    "  4                                   \n" +
                    "  5                                   \n" +
                    "  6                                   \n" +
                    "  7                                   \n" +
                    "  8                                   \n" +
                    "  9                                   \n" +
                    " 10                                   \n" +
                    " 11                                   \n" +
                    " 12                                   \n" +
                    " 13                                   \n" +
                    " 14                                   \n" +
                    " 15                                   \n" +
                    " 16                                   \n" +
                    " 17                                   \n" +
                    " 18                                   \n" +
                    " 19                                   \n" +
                    " 20                                   \n" +
                    " 21                                   \n" +
                    " 22                                   \n" +
                    " 23                                   \n" +
                    " 24                                   \n" +
                    " 25                                   \n" +
                    " 26                                   \n" +
                    " 27                                   \n" +
                    " 28                                   \n" +
                    " 29                                   \n" +
                    " 30                                   \n" +
                    " 31                                   \n" +
                    " 32                                   \n" +
                    " 33                                   \n" +
                    " 34                                   \n" +
                    " 35                                   \n" +
                    " 36                                   \n" +
                    " 37                                   \n" +
                    " 38                                   \n" +
                    " 39                                   \n" +
                    " 40  X                                \n" +
                    " 41  |                                \n" +
                    " 42  |                                \n" +
                    " 43  |                                \n" +
                    " 44  |                                \n" +
                    " 45  |                                \n" +
                    " 46  |                                \n" +
                    " 47  |                                \n" +
                    " 48  |                                \n" +
                    " 49  |                                \n" +
                    " 50  |                             X  \n" +
                    " 51  |                             |  \n" +
                    " 52  |                             |  \n" +
                    " 53  |                             |  \n" +
                    " 54  |                             |  \n" +
                    " 55  |                             |  \n" +
                    " 56  |                             |  \n" +
                    " 57  |                             |  \n" +
                    " 58  |                             |  \n" +
                    " 59  |                             |  \n" +
                    " 60  |                             |  \n" +
                    " 61  |                             |  \n" +
                    " 62  |                             |  \n" +
                    " 63  |                             |  \n" +
                    " 64  |                             |  \n" +
                    " 65  |                             |  \n" +
                    " 66  |                             |  \n" +
                    " 67  |                             |  \n" +
                    " 68  |                             |  \n" +
                    " 69  |                             |  \n" +
                    " 70  |                             |  \n" +
                    " 71  |                             |  \n" +
                    " 72  |                             |  \n" +
                    " 73  |                             |  \n" +
                    " 74  |                             |  \n" +
                    " 75  |                             |  \n" +
                    " 76  |                             |  \n" +
                    " 77  |                             |  \n" +
                    " 78  |                             |  \n" +
                    " 79  |                             |  \n" +
                    " 80  |                                \n" +
                    " 81  |                                \n" +
                    " 82  |                                \n" +
                    " 83  |                                \n" +
                    " 84  |                                \n" +
                    " 85  |                                \n" +
                    " 86  |                                \n" +
                    " 87  |                                \n" +
                    " 88  |                                \n" +
                    " 89  |                                \n" +
                    " 90  |                                \n" +
                    " 91  |                                \n" +
                    " 92  |                                \n" +
                    " 93  |                                \n" +
                    " 94  |                                \n" +
                    " 95  |                                \n" +
                    " 96  |                                \n" +
                    " 97  |                                \n" +
                    " 98  |                                \n" +
                    " 99  |                                \n" +
                    "100  |                                \n" +
                    "101  |                                \n" +
                    "102  |                                \n" +
                    "103  |                                \n" +
                    "104  |                                \n" +
                    "105  |                                \n" +
                    "106  |                                \n" +
                    "107  |                                \n" +
                    "108  |                                \n" +
                    "109  |                                \n" +
                    "110  |                                \n" +
                    "111  |                                \n" +
                    "112  |                                \n" +
                    "113  |                                \n" +
                    "114  |                                \n" +
                    "115  |                                \n" +
                    "116  |                                \n" +
                    "117  |                                \n" +
                    "118  |                                \n" +
                    "119  |                                \n" +
                    "120  |                                \n" +
                    "121  |                                \n" +
                    "122  |                                \n" +
                    "123  |                                \n" +
                    "124  |                                \n" +
                    "125  |                                \n" +
                    "126  |                                \n" +
                    "127  |                                \n" +
                    "128  |                                \n" +
                    "129  |                                \n" +
                    "130  |                                \n" +
                    "131  |                                \n" +
                    "132  |                                \n" +
                    "133  |                                \n" +
                    "134  |                                \n" +
                    "135  |                                \n" +
                    "136  |                                \n" +
                    "137  |                                \n" +
                    "138  |                                \n" +
                    "139  |                                \n" +
                    "140  |                                \n" +
                    "141  |                                \n" +
                    "142  |                                \n" +
                    "143  |                                \n" +
                    "144  |                                \n" +
                    "145  |                                \n" +
                    "146  |                                \n" +
                    "147  |                                \n" +
                    "148  |                                \n" +
                    "149  |                                \n" +
                    "150  |                                \n" +
                    "151  |                                \n" +
                    "152  |                                \n" +
                    "153  |                                \n" +
                    "154  |                                \n" +
                    "155  |                                \n" +
                    "156  |                                \n" +
                    "157  |                                \n" +
                    "158  |                                \n" +
                    "159  |                                \n" +
                    "160  |                                \n" +
                    "161  |                                \n" +
                    "162  |                                \n" +
                    "163                                   \n");

  }

  @Test
  public void testToStringPieceRemove() {
    initData();
    assertEquals(p1.toString(),
            "    C1  C#1   D1  D#1   E1   F1  F#1   G1  G#1   A1  A#1   B1   C2  C#2 \n" +
                    " 0  X    X                                                              \n" +
                    " 1  |    |                                                              \n" +
                    " 2  |    |                                                              \n" +
                    " 3  |    |                                                           X  \n" +
                    " 4                                                                   |  \n" +
                    " 5                                                                      \n" +
                    " 6            X    X                                                    \n" +
                    " 7            X    |                                                    \n" +
                    " 8            |    |                                                    \n" +
                    " 9            |    |                                                    \n" +
                    "10            |                                                      X  \n" +
                    "11            |                                                         \n" +
                    "12                                                                      \n");

    p1.removeNote(new Note(Tones.D, 1, 7, 5, 1, 10));
    assertEquals(p1.toString(),
            "    C1  C#1   D1  D#1   E1   F1  F#1   G1  G#1   A1  A#1   B1   C2  C#2 \n" +
                    " 0  X    X                                                              \n" +
                    " 1  |    |                                                              \n" +
                    " 2  |    |                                                              \n" +
                    " 3  |    |                                                           X  \n" +
                    " 4                                                                   |  \n" +
                    " 5                                                                      \n" +
                    " 6            X    X                                                    \n" +
                    " 7            |    |                                                    \n" +
                    " 8            |    |                                                    \n" +
                    " 9            |    |                                                    \n" +
                    "10                                                                   X  \n" +
                    "11                                                                      \n");
    p1.removeNote(new Note(Tones.D, 1, 6, 4, 1, 10));
    assertEquals(p1.toString(),
            "    C1  C#1   D1  D#1   E1   F1  F#1   G1  G#1   A1  A#1   B1   C2  C#2 \n" +
                    " 0  X    X                                                              \n" +
                    " 1  |    |                                                              \n" +
                    " 2  |    |                                                              \n" +
                    " 3  |    |                                                           X  \n" +
                    " 4                                                                   |  \n" +
                    " 5                                                                      \n" +
                    " 6                 X                                                    \n" +
                    " 7                 |                                                    \n" +
                    " 8                 |                                                    \n" +
                    " 9                 |                                                    \n" +
                    "10                                                                   X  \n" +
                    "11                                                                      \n");


  }

  @Test
  public void testToStringPieceRemoveUntilEmpty() {
    initData();
    assertEquals(p1.toString(),
            "    C1  C#1   D1  D#1   E1   F1  F#1   G1  G#1   A1  A#1   B1   C2  C#2 \n" +
                    " 0  X    X                                                              \n" +
                    " 1  |    |                                                              \n" +
                    " 2  |    |                                                              \n" +
                    " 3  |    |                                                           X  \n" +
                    " 4                                                                   |  \n" +
                    " 5                                                                      \n" +
                    " 6            X    X                                                    \n" +
                    " 7            X    |                                                    \n" +
                    " 8            |    |                                                    \n" +
                    " 9            |    |                                                    \n" +
                    "10            |                                                      X  \n" +
                    "11            |                                                         \n" +
                    "12                                                                      \n");

    p1.removeNote(new Note(Tones.D, 1, 7, 5, 1, 10));
    assertEquals(p1.toString(),
            "    C1  C#1   D1  D#1   E1   F1  F#1   G1  G#1   A1  A#1   B1   C2  C#2 \n" +
                    " 0  X    X                                                              \n" +
                    " 1  |    |                                                              \n" +
                    " 2  |    |                                                              \n" +
                    " 3  |    |                                                           X  \n" +
                    " 4                                                                   |  \n" +
                    " 5                                                                      \n" +
                    " 6            X    X                                                    \n" +
                    " 7            |    |                                                    \n" +
                    " 8            |    |                                                    \n" +
                    " 9            |    |                                                    \n" +
                    "10                                                                   X  \n" +
                    "11                                                                      \n");
    p1.removeNote(new Note(Tones.D, 1, 6, 4, 1, 10));
    assertEquals(p1.toString(),
            "    C1  C#1   D1  D#1   E1   F1  F#1   G1  G#1   A1  A#1   B1   C2  C#2 \n" +
                    " 0  X    X                                                              \n" +
                    " 1  |    |                                                              \n" +
                    " 2  |    |                                                              \n" +
                    " 3  |    |                                                           X  \n" +
                    " 4                                                                   |  \n" +
                    " 5                                                                      \n" +
                    " 6                 X                                                    \n" +
                    " 7                 |                                                    \n" +
                    " 8                 |                                                    \n" +
                    " 9                 |                                                    \n" +
                    "10                                                                   X  \n" +
                    "11                                                                      \n");
    p1.removeNote(new Note(Tones.C_SHARP, 2, 3, 2, 1, 10));
    p1.removeNote(new Note(Tones.C_SHARP, 2, 10, 1, 1, 10));
    assertEquals(p1.toString(),
            "    C1  C#1   D1  D#1 \n" +
                    " 0  X    X            \n" +
                    " 1  |    |            \n" +
                    " 2  |    |            \n" +
                    " 3  |    |            \n" +
                    " 4                    \n" +
                    " 5                    \n" +
                    " 6                 X  \n" +
                    " 7                 |  \n" +
                    " 8                 |  \n" +
                    " 9                 |  \n" +
                    "10                    \n");
    p1.removeNote(new Note(Tones.D_SHARP, 1, 6, 4, 1, 10));
    assertEquals(p1.toString(),
            "   C1  C#1 \n" +
                    "0  X    X  \n" +
                    "1  |    |  \n" +
                    "2  |    |  \n" +
                    "3  |    |  \n" +
                    "4          \n");
    p1.removeNote(new Note(Tones.C, 1, 0, 4, 1, 10));
    assertEquals(p1.toString(),
            "  C#1 \n" +
                    "0  X  \n" +
                    "1  |  \n" +
                    "2  |  \n" +
                    "3  |  \n" +
                    "4     \n");
    p1.removeNote(new Note(Tones.C_SHARP, 1, 0, 4, 1, 10));
    assertEquals(p1.toString(),
            "");

  }

  @Test
  public void testToStringPieceModifyNote1() {
    initData();
    assertEquals(p1.toString(),
            "    C1  C#1   D1  D#1   E1   F1  F#1   G1  G#1   A1  A#1   B1   C2  C#2 \n" +
                    " 0  X    X                                                              \n" +
                    " 1  |    |                                                              \n" +
                    " 2  |    |                                                              \n" +
                    " 3  |    |                                                           X  \n" +
                    " 4                                                                   |  \n" +
                    " 5                                                                      \n" +
                    " 6            X    X                                                    \n" +
                    " 7            X    |                                                    \n" +
                    " 8            |    |                                                    \n" +
                    " 9            |    |                                                    \n" +
                    "10            |                                                      X  \n" +
                    "11            |                                                         \n" +
                    "12                                                                      \n");
    p1.modifyNote(new Note(Tones.C, 1, 0, 4, 1, 10), Tones.G, 1, 2, 3, 1, 10);
    assertEquals(p1.toString(),
            "   C#1   D1  D#1   E1   F1  F#1   G1  G#1   A1  A#1   B1   C2  C#2 \n" +
                    " 0  X                                                              \n" +
                    " 1  |                                                              \n" +
                    " 2  |                             X                                \n" +
                    " 3  |                             |                             X  \n" +
                    " 4                                |                             |  \n" +
                    " 5                                                                 \n" +
                    " 6       X    X                                                    \n" +
                    " 7       X    |                                                    \n" +
                    " 8       |    |                                                    \n" +
                    " 9       |    |                                                    \n" +
                    "10       |                                                      X  \n" +
                    "11       |                                                         \n" +
                    "12                                                                 \n");
    p1.modifyNote(d1For7And5, Tones.A_SHARP, 1, 10, 5, 1, 10);
    assertEquals(p1.toString(),
            "   C#1   D1  D#1   E1   F1  F#1   G1  G#1   A1  A#1   B1   C2  C#2 \n" +
                    " 0  X                                                              \n" +
                    " 1  |                                                              \n" +
                    " 2  |                             X                                \n" +
                    " 3  |                             |                             X  \n" +
                    " 4                                |                             |  \n" +
                    " 5                                                                 \n" +
                    " 6       X    X                                                    \n" +
                    " 7       |    |                                                    \n" +
                    " 8       |    |                                                    \n" +
                    " 9       |    |                                                    \n" +
                    "10                                               X              X  \n" +
                    "11                                               |                 \n" +
                    "12                                               |                 \n" +
                    "13                                               |                 \n" +
                    "14                                               |                 \n" +
                    "15                                                                 \n");
    p1.modifyNote(dSharp1For6And4, Tones.D_SHARP, 1, 3, 3, 1, 10);
    assertEquals(p1.toString(),
            "   C#1   D1  D#1   E1   F1  F#1   G1  G#1   A1  A#1   B1   C2  C#2 \n" +
                    " 0  X                                                              \n" +
                    " 1  |                                                              \n" +
                    " 2  |                             X                                \n" +
                    " 3  |         X                   |                             X  \n" +
                    " 4            |                   |                             |  \n" +
                    " 5            |                                                    \n" +
                    " 6       X                                                         \n" +
                    " 7       |                                                         \n" +
                    " 8       |                                                         \n" +
                    " 9       |                                                         \n" +
                    "10                                               X              X  \n" +
                    "11                                               |                 \n" +
                    "12                                               |                 \n" +
                    "13                                               |                 \n" +
                    "14                                               |                 \n" +
                    "15                                                                 \n");

  }

  @Test
  public void testToStringManageExactDuplicateRemove() {
    initData();
    p0.addNote(dSharp1For6And4);
    p0.addNote(cSharp1For0And4);
    p0.addNote(cSharp1For0And4);
    assertEquals(p0.toString(),
            "   C#1   D1  D#1 \n" +
                    " 0  X            \n" +
                    " 1  |            \n" +
                    " 2  |            \n" +
                    " 3  |            \n" +
                    " 4               \n" +
                    " 5               \n" +
                    " 6            X  \n" +
                    " 7            |  \n" +
                    " 8            |  \n" +
                    " 9            |  \n" +
                    "10               \n");
    assertEquals(p0.getNumberOfNotes(), 3);

    //remove ONE
    p0.removeNote(cSharp1For0And4);
    assertEquals(p0.toString(),
            "   C#1   D1  D#1 \n" +
                    " 0  X            \n" +
                    " 1  |            \n" +
                    " 2  |            \n" +
                    " 3  |            \n" +
                    " 4               \n" +
                    " 5               \n" +
                    " 6            X  \n" +
                    " 7            |  \n" +
                    " 8            |  \n" +
                    " 9            |  \n" +
                    "10               \n");
    assertEquals(p0.getNumberOfNotes(), 2);

    //remove the other
    p0.removeNote(cSharp1For0And4);
    assertEquals(p0.toString(),
            "   D#1 \n" +
                    " 0     \n" +
                    " 1     \n" +
                    " 2     \n" +
                    " 3     \n" +
                    " 4     \n" +
                    " 5     \n" +
                    " 6  X  \n" +
                    " 7  |  \n" +
                    " 8  |  \n" +
                    " 9  |  \n" +
                    "10     \n");
    assertEquals(p0.getNumberOfNotes(), 1);


  }

  @Test
  public void testToStringManageExactDuplicateModify() {
    initData();
    // three duplicates
    p0.addNote(dSharp1For6And4);
    p0.addNote(cSharp1For0And4);
    p0.addNote(cSharp1For0And4);
    p0.addNote(cSharp1For0And4);

    assertEquals(p0.toString(),
            "   C#1   D1  D#1 \n" +
                    " 0  X            \n" +
                    " 1  |            \n" +
                    " 2  |            \n" +
                    " 3  |            \n" +
                    " 4               \n" +
                    " 5               \n" +
                    " 6            X  \n" +
                    " 7            |  \n" +
                    " 8            |  \n" +
                    " 9            |  \n" +
                    "10               \n");
    assertEquals(p0.getNumberOfNotes(), 4);
    //should only modify ONE
    p0.modifyNote(cSharp1For0And4, Tones.D, 1, 2, 3, 1, 10);
    assertEquals(p0.toString(),
            "   C#1   D1  D#1 \n" +
                    " 0  X            \n" +
                    " 1  |            \n" +
                    " 2  |    X       \n" +
                    " 3  |    |       \n" +
                    " 4       |       \n" +
                    " 5               \n" +
                    " 6            X  \n" +
                    " 7            |  \n" +
                    " 8            |  \n" +
                    " 9            |  \n" +
                    "10               \n");
    assertEquals(p0.getNumberOfNotes(), 4);

    // modify another
    p0.modifyNote(cSharp1For0And4, Tones.D, 1, 9, 1, 1, 10);
    assertEquals(p0.toString(),
            "   C#1   D1  D#1 \n" +
                    " 0  X            \n" +
                    " 1  |            \n" +
                    " 2  |    X       \n" +
                    " 3  |    |       \n" +
                    " 4       |       \n" +
                    " 5               \n" +
                    " 6            X  \n" +
                    " 7            |  \n" +
                    " 8            |  \n" +
                    " 9       X    |  \n" +
                    "10               \n");
    assertEquals(p0.getNumberOfNotes(), 4);
  }

  @Test
  public void testMaxBeats1() {
    initData();
    assertEquals(p1.getMaxBeats(), 12);
  }

  @Test
  public void testMaxBeats2() {
    initData();
    assertEquals(p0.getMaxBeats(), 0);
  }

  @Test
  public void testGetNumNotes11() {
    initData();

    assertEquals(p1.getNumberOfNotes(), 7);
  }

  @Test
  public void testGetNumNotes2() {
    initData();

    assertEquals(p0.getNumberOfNotes(), 0);
  }

  @Test
  public void testGetLowestPitch1() {
    initData();

    assertEquals(p1.getLowestPitch().toString(), "C1");
  }

  @Test(expected = NoSuchElementException.class)
  public void testGetLowestPitch2() {
    initData();

    p0.getLowestPitch();
  }

  @Test
  public void testGetHighestPitch1() {
    initData();

    assertEquals(p1.getHighestPitch().toString(), "C#2");
  }

  @Test(expected = NoSuchElementException.class)
  public void testGetHighestPitch2() {
    initData();

    p0.getHighestPitch();
  }

  @Test
  public void testGetTempoGood1() {
    initData();

    assertEquals(p1.getTempo(), 220000); // Default tempo.
  }

  @Test
  public void testGetTempoGood2() {
    initData();

    assertEquals(p0.getTempo(), 220000); // Default tempo.
  }

  @Test
  public void testSetTempoGood1() {
    initData();
    p1.setTempo(5);

    assertEquals(p1.getTempo(), 5);
  }

  @Test
  public void testSetTempoGood2() {
    initData();
    p0.setTempo(15);

    assertEquals(p0.getTempo(), 15);
  }


}
