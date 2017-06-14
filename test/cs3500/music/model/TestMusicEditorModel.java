package cs3500.music.model;


import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;

public class TestMusicEditorModel {
  MusicEditorModel model0 = new MusicEditorModel();
  MusicEditorModel model1 = new MusicEditorModel();


  Piece p0; // assigned as an empty piece
  Piece p1; // a piece with notes added beforehand
  // Pre-created notes for easy access
  Note c1For0And4 = new Note(Tones.C, 1, 0, 4);
  Note cSharp1For0And4 = new Note(Tones.C_SHARP, 1, 0, 4);
  Note d1For6And4 = new Note(Tones.D, 1, 6, 4);
  Note d1For7And5 = new Note(Tones.D, 1, 7, 5);

  Note dSharp1For6And4 = new Note(Tones.D_SHARP, 1, 6, 4);
  Note cSharp2For3And2 = new Note(Tones.C_SHARP, 2, 3, 2);
  Note cSharp2For10And1 = new Note(Tones.C_SHARP, 2, 10, 1);


  void initData() {

    p1 = new Piece();
    p1.addNote(c1For0And4);
    p1.addNote(cSharp1For0And4);
    p1.addNote(d1For6And4);
    p1.addNote(d1For7And5);
    p1.addNote(dSharp1For6And4);
    p1.addNote(cSharp2For3And2);
    p1.addNote(cSharp2For10And1);
    model1.loadPiece(p1);
  }

  // test loadPiece

  @Test(expected = NullPointerException.class)
  public void testLoadPieceNull() {
    initData();
    model0.loadPiece(null);
  }

  @Test
  public void testLoadPiece() {
    initData();
    model0.loadPiece(p1);
    assertEquals(model1.getPiece(), model1.getPiece());
  }

  @Test
  public void testLoadPiece2() {
    initData();
    model1.loadPiece(new Piece());
    assertEquals(model0.getPiece(), model1.getPiece());
  }

  // testing addNote

  @Test(expected = NullPointerException.class)
  public void testAddNoteNull() {
    initData();
    model0.addNote(null, 2, 4, 2);
  }

  @Test
  public void testAddNote1() {
    initData();
    assertEquals(model0.getPiece().getNumberOfNotes(), 0);
    model0.addNote(Tones.C, 1, 0, 4);
    assertEquals(model0.getPiece().getNumberOfNotes(), 1);
    assertEquals(model0.getPiece().getLowestPitch(), new Pitch(Tones.C, 1));
    assertEquals(model0.getPiece().getHighestPitch(), new Pitch(Tones.C, 1));
    assertEquals(model0.getPiece().getMaxBeats(), 4);
  }

  @Test
  public void testAddNote2() {
    initData();
    assertEquals(model0.getPiece().getNumberOfNotes(), 0);
    model0.addNote(Tones.C, 1, 0, 4);
    model0.addNote(Tones.D_SHARP, 1, 6, 4);
    assertEquals(model0.getPiece().getNumberOfNotes(), 2);
    assertEquals(model0.getPiece().getLowestPitch(), new Pitch(Tones.C, 1));
    assertEquals(model0.getPiece().getHighestPitch(), new Pitch(Tones.D_SHARP, 1));
    assertEquals(model0.getPiece().getMaxBeats(), 10);
  }

  @Test
  public void testAddNote3ExactSame() {
    initData();
    assertEquals(model0.getPiece().getNumberOfNotes(), 0);
    model0.addNote(Tones.C_SHARP, 2, 10, 1);
    model0.addNote(Tones.C_SHARP, 2, 10, 1);
    assertEquals(model0.getPiece().getNumberOfNotes(), 2);
    assertEquals(model0.getPiece().getLowestPitch(), new Pitch(Tones.C_SHARP, 2));
    assertEquals(model0.getPiece().getHighestPitch(), new Pitch(Tones.C_SHARP, 2));
    assertEquals(model0.getPiece().getMaxBeats(), 11);
  }

  @Test
  public void testAddNote4Overlap() {
    initData();
    assertEquals(model0.getPiece().getNumberOfNotes(), 0);
    model0.addNote(Tones.D, 1, 6, 4);
    model0.addNote(Tones.D, 1, 7, 5);
    assertEquals(model0.getPiece().getNumberOfNotes(), 2);
    assertEquals(model0.getPiece().getLowestPitch(), new Pitch(Tones.D, 1));
    assertEquals(model0.getPiece().getHighestPitch(), new Pitch(Tones.D, 1));
    assertEquals(model0.getPiece().getMaxBeats(), 12);
  }

  // testing removeNote

  @Test(expected = NullPointerException.class)
  public void testRemoveNoteNull() {
    initData();
    model1.removeNote(null, 0, 0, 1);
  }

  @Test(expected = NoSuchElementException.class)
  public void testRemoveNoteNotFound1() {
    initData();
    model1.removeNote(Tones.B, 10, 0, 10);
  }

  @Test(expected = NoSuchElementException.class)
  public void testRemoveNoteNotFound2() {
    initData();
    // close to D1For6and4
    model1.removeNote(Tones.D, 1, 6, 5);
  }

  @Test(expected = NoSuchElementException.class)
  public void testRemoveNoteNotFound3() {
    initData();
    // close to D1For6and4
    model1.removeNote(Tones.D, 2, 6, 4);
  }

  @Test
  public void testRemoveNote1() {
    initData();
    assertEquals(model1.getPiece().getNumberOfNotes(), 7);
    model1.removeNote(Tones.D, 1, 6, 4);
    assertEquals(model1.getPiece().getNumberOfNotes(), 6);
  }

  @Test
  public void testRemoveNote2() {
    initData();
    assertEquals(model1.getPiece().getNumberOfNotes(), 7);
    assertEquals(model1.getPiece().getLowestPitch(), new Pitch(Tones.C, 1));
    model1.removeNote(Tones.C, 1, 0, 4);
    assertEquals(model1.getPiece().getNumberOfNotes(), 6);
    assertEquals(model1.getPiece().getLowestPitch(), new Pitch(Tones.C_SHARP, 1));

  }

  @Test
  public void testRemoveNote3() {
    initData();
    assertEquals(model1.getPiece().getNumberOfNotes(), 7);
    assertEquals(model1.getPiece().getHighestPitch(), new Pitch(Tones.C_SHARP, 2));
    model1.removeNote(Tones.C_SHARP, 2, 10, 1);
    assertEquals(model1.getPiece().getNumberOfNotes(), 6);
    assertEquals(model1.getPiece().getHighestPitch(), new Pitch(Tones.C_SHARP, 2));
    model1.removeNote(Tones.C_SHARP, 2, 3, 2);
    assertEquals(model1.getPiece().getNumberOfNotes(), 5);
    assertEquals(model1.getPiece().getHighestPitch(), new Pitch(Tones.D_SHARP, 1));

  }

  // testing modifyNote

  @Test(expected = NullPointerException.class)
  public void testModifyNoteNullNote() {
    initData();
    model1.modifyNote(null, 3, 1, 10, Tones.A, 4, 9, 12);
  }

  @Test(expected = NullPointerException.class)
  public void testModifyNoteNullTone() {
    initData();
    model1.modifyNote(Tones.D, 1, 6, 4, null, 1, 0, 10);
  }

  @Test(expected = NoSuchElementException.class)
  public void testModifyNoteNotFound() {
    initData();
    model1.modifyNote(Tones.C, 1, 0, 1, Tones.C, 1, 0, 10);
  }

  @Test
  public void testModifyNote1() {
    initData();
    assertEquals(model1.getPiece().getNumberOfNotes(), 7);
    assertEquals(model1.getPiece().getHighestPitch(), new Pitch(Tones.C_SHARP, 2));
    model1.modifyNote(Tones.C_SHARP, 2, 10, 1, Tones.A_SHARP, 8, 0, 2);
    assertEquals(model1.getPiece().getNumberOfNotes(), 7);
    assertEquals(model1.getPiece().getHighestPitch(), new Pitch(Tones.A_SHARP, 8));
  }

  @Test
  public void testModifyNote2() {
    initData();
    assertEquals(model1.getPiece().getNumberOfNotes(), 7);
    assertEquals(model1.getPiece().getLowestPitch(), new Pitch(Tones.C, 1));
    model1.modifyNote(Tones.C, 1, 0, 4, Tones.D, 2, 7, 10);
    assertEquals(model1.getPiece().getNumberOfNotes(), 7);
    assertEquals(model1.getPiece().getLowestPitch(), new Pitch(Tones.C_SHARP, 1));
    model1.modifyNote(Tones.C_SHARP, 1, 0, 4, Tones.C_SHARP, 5, 5, 3);
    assertEquals(model1.getPiece().getNumberOfNotes(), 7);
    assertEquals(model1.getPiece().getLowestPitch(), new Pitch(Tones.D, 1));

  }

  // testing getNotesAtBeat

  @Test(expected = IllegalArgumentException.class)
  public void testGetNotesAtBeatOutOfBoundsBeatNeg1() {
    initData();
    model1.getNotesAtBeat(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetNotesAtBeatOutOfBoundsBeatOver() {
    initData();
    model1.getNotesAtBeat(13);
  }

  @Test
  public void testGetNotesAtBeatNoBeats() {
    initData();
    model1.removeNote(Tones.C_SHARP, 2, 3, 2);
    assertEquals(model1.getNotesAtBeat(5), new ArrayList<Note>());

  }

  @Test
  public void testGetNotesAtBeatOneBeat() {
    initData();
    assertEquals(model1.getNotesAtBeat(5),
            new ArrayList<Note>(Arrays.asList(cSharp2For3And2)));

  }

  @Test
  public void testGetNotesAtBeatTwoBeats() {
    initData();
    assertEquals(model1.getNotesAtBeat(11),
            new ArrayList<Note>(Arrays.asList(d1For7And5, cSharp2For10And1)));

  }

  @Test
  public void testGetNotesAtBeatThreeBeats() {
    initData();
    assertEquals(model1.getNotesAtBeat(4),
            new ArrayList<Note>(Arrays.asList(c1For0And4, cSharp1For0And4, cSharp2For3And2)));

  }

  @Test
  public void testGetNotesAtBeatThreeBeatsTwoOverlap() {
    initData();
    assertEquals(model1.getNotesAtBeat(7),
            new ArrayList<Note>(Arrays.asList(d1For6And4, d1For7And5, dSharp1For6And4)));

  }

  //testing combinePieceOnTop

  @Test
  public void testCombinePieceOnTop() {
    initData();
    model0.addNote(Tones.G, 1, 3, 2);
    model0.addNote(Tones.A, 1, 5, 3);
    assertEquals(model0.toString(),
            "   G1  G#1   A1 \n" +
                    "0               \n" +
                    "1               \n" +
                    "2               \n" +
                    "3  X            \n" +
                    "4  |            \n" +
                    "5            X  \n" +
                    "6            |  \n" +
                    "7            |  \n" +
                    "8               \n");
    assertEquals(model1.toString(),
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
    model1.combinePieceOnTop(model0.getPiece());
    assertEquals(model1.toString(),
            "    C1  C#1   D1  D#1   E1   F1  F#1   G1  G#1   A1  A#1   B1   C2  C#2 \n" +
                    " 0  X    X                                                              \n" +
                    " 1  |    |                                                              \n" +
                    " 2  |    |                                                              \n" +
                    " 3  |    |                             X                             X  \n" +
                    " 4                                     |                             |  \n" +
                    " 5                                               X                      \n" +
                    " 6            X    X                             |                      \n" +
                    " 7            X    |                             |                      \n" +
                    " 8            |    |                                                    \n" +
                    " 9            |    |                                                    \n" +
                    "10            |                                                      X  \n" +
                    "11            |                                                         \n" +
                    "12                                                                      \n");
  }

  @Test
  public void testCombinePieceOnTopOverlap() {
    initData();
    model0.addNote(Tones.G, 1, 3, 2);
    model0.addNote(Tones.A, 1, 5, 3);
    model0.addNote(Tones.C_SHARP, 1, 3, 4);
    assertEquals(model0.toString(),
            "  C#1   D1  D#1   E1   F1  F#1   G1  G#1   A1 \n" +
                    "0                                             \n" +
                    "1                                             \n" +
                    "2                                             \n" +
                    "3  X                             X            \n" +
                    "4  |                             |            \n" +
                    "5  |                                       X  \n" +
                    "6  |                                       |  \n" +
                    "7                                          |  \n" +
                    "8                                             \n");
    assertEquals(model1.toString(),
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
    model1.combinePieceOnTop(model0.getPiece());
    assertEquals(model1.toString(),
            "    C1  C#1   D1  D#1   E1   F1  F#1   G1  G#1   A1  A#1   B1   C2  C#2 \n" +
                    " 0  X    X                                                              \n" +
                    " 1  |    |                                                              \n" +
                    " 2  |    |                                                              \n" +
                    " 3  |    X                             X                             X  \n" +
                    " 4       |                             |                             |  \n" +
                    " 5       |                                       X                      \n" +
                    " 6       |    X    X                             |                      \n" +
                    " 7            X    |                             |                      \n" +
                    " 8            |    |                                                    \n" +
                    " 9            |    |                                                    \n" +
                    "10            |                                                      X  \n" +
                    "11            |                                                         \n" +
                    "12                                                                      \n");
  }

  @Test
  public void testCombinePieceOnTopElongate() {
    initData();
    model0.addNote(Tones.G, 1, 3, 2);
    model0.addNote(Tones.A, 1, 5, 3);
    model0.addNote(Tones.F_SHARP, 1, 10, 14);
    assertEquals(model0.toString(),
            "   F#1   G1  G#1   A1 \n" +
                    " 0                    \n" +
                    " 1                    \n" +
                    " 2                    \n" +
                    " 3       X            \n" +
                    " 4       |            \n" +
                    " 5                 X  \n" +
                    " 6                 |  \n" +
                    " 7                 |  \n" +
                    " 8                    \n" +
                    " 9                    \n" +
                    "10  X                 \n" +
                    "11  |                 \n" +
                    "12  |                 \n" +
                    "13  |                 \n" +
                    "14  |                 \n" +
                    "15  |                 \n" +
                    "16  |                 \n" +
                    "17  |                 \n" +
                    "18  |                 \n" +
                    "19  |                 \n" +
                    "20  |                 \n" +
                    "21  |                 \n" +
                    "22  |                 \n" +
                    "23  |                 \n" +
                    "24                    \n");
    assertEquals(model1.toString(),
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
    model1.combinePieceOnTop(model0.getPiece());
    assertEquals(model1.toString(),
            "    C1  C#1   D1  D#1   E1   F1  F#1   G1  G#1   A1  A#1   B1   C2  C#2 \n" +
                    " 0  X    X                                                              \n" +
                    " 1  |    |                                                              \n" +
                    " 2  |    |                                                              \n" +
                    " 3  |    |                             X                             X  \n" +
                    " 4                                     |                             |  \n" +
                    " 5                                               X                      \n" +
                    " 6            X    X                             |                      \n" +
                    " 7            X    |                             |                      \n" +
                    " 8            |    |                                                    \n" +
                    " 9            |    |                                                    \n" +
                    "10            |                   X                                  X  \n" +
                    "11            |                   |                                     \n" +
                    "12                                |                                     \n" +
                    "13                                |                                     \n" +
                    "14                                |                                     \n" +
                    "15                                |                                     \n" +
                    "16                                |                                     \n" +
                    "17                                |                                     \n" +
                    "18                                |                                     \n" +
                    "19                                |                                     \n" +
                    "20                                |                                     \n" +
                    "21                                |                                     \n" +
                    "22                                |                                     \n" +
                    "23                                |                                     \n" +
                    "24                                                                      \n");
  }

  // testing combinePieceAtEnd

  @Test
  public void testCombinePieceAtEnd() {
    initData();
    model0.addNote(Tones.G, 1, 0, 2);
    model0.addNote(Tones.A, 1, 5, 3);
    assertEquals(model0.toString(),
            "   G1  G#1   A1 \n" +
                    "0  X            \n" +
                    "1  |            \n" +
                    "2               \n" +
                    "3               \n" +
                    "4               \n" +
                    "5            X  \n" +
                    "6            |  \n" +
                    "7            |  \n" +
                    "8               \n");
    assertEquals(model1.toString(),
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
    model1.combinePieceAtEnd(model0.getPiece());
    assertEquals(model1.toString(),
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
                    "12                                                                      \n" +
                    "13                                     X                                \n" +
                    "14                                     |                                \n" +
                    "15                                                                      \n" +
                    "16                                                                      \n" +
                    "17                                                                      \n" +
                    "18                                               X                      \n" +
                    "19                                               |                      \n" +
                    "20                                               |                      \n" +
                    "21                                                                      \n");
  }

  @Test
  public void testCombinePieceAtEndDuplicate() {
    initData();
    assertEquals(model1.toString(),
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
    model1.combinePieceAtEnd(p1);
    assertEquals(model1.toString(),
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
                    "12                                                                      \n" +
                    "13  X    X                                                              \n" +
                    "14  |    |                                                              \n" +
                    "15  |    |                                                              \n" +
                    "16  |    |                                                           X  \n" +
                    "17                                                                   |  \n" +
                    "18                                                                      \n" +
                    "19            X    X                                                    \n" +
                    "20            X    |                                                    \n" +
                    "21            |    |                                                    \n" +
                    "22            |    |                                                    \n" +
                    "23            |                                                      X  \n" +
                    "24            |                                                         \n" +
                    "25                                                                      \n");
  }

}
