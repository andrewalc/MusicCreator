package cs3500.music.tests;


import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;

import cs3500.music.model.IMusicEditorModel;
import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.Tones;
import cs3500.music.view.IntegerComparator;

import static org.junit.Assert.assertEquals;

/**
 * Test class for MusicEditorModels.
 */
public class TestMusicEditorModel {
  MusicEditorModel.MusicEditorBuilder builder = new MusicEditorModel.MusicEditorBuilder();
  MusicEditorModel.MusicEditorBuilder builder2 = new MusicEditorModel.MusicEditorBuilder();

  IMusicEditorModel model0 = builder.build();
  IMusicEditorModel model1 = builder2.build();
  Map<Integer, ArrayList<ArrayList<Integer>>> p0 = new TreeMap<>(new IntegerComparator());
  Map<Integer, ArrayList<ArrayList<Integer>>> p1 = new TreeMap<>(new IntegerComparator());


  // Pre-created notes for easy access
  ArrayList<Integer> c1For0And4 = new ArrayList<>(Arrays.asList(0, 3, 1, 24, 64));
  ArrayList<Integer> cSharp1For0And4 = new ArrayList<>(Arrays.asList(0, 3, 1, 25, 64));
  ArrayList<Integer> d1For6And4 = new ArrayList<>(Arrays.asList(6, 9, 1, 26, 64));
  ArrayList<Integer> d1For7And5 = new ArrayList<>(Arrays.asList(7, 11, 1, 26, 64));
  ArrayList<Integer> dSharp1For6And4 = new ArrayList<>(Arrays.asList(6, 9, 1, 27, 64));
  ArrayList<Integer> cSharp2For3And2 = new ArrayList<>(Arrays.asList(3, 4, 1, 37, 64));
  ArrayList<Integer> cSharp2For10And1 = new ArrayList<>(Arrays.asList(10, 10, 1, 37, 64));

  void initData() {
    ArrayList<ArrayList<Integer>> allNotes = new ArrayList<>();
    allNotes.add(c1For0And4);
    allNotes.add((cSharp1For0And4));
    allNotes.add(d1For6And4);
    allNotes.add(d1For7And5);
    allNotes.add(dSharp1For6And4);
    allNotes.add(cSharp2For3And2);
    allNotes.add(cSharp2For10And1);
    for (ArrayList<Integer> note : allNotes) {
      if (p1.containsKey(note.get(3))) {
        p1.get(note.get(3)).add(note);
      } else {
        p1.put(note.get(3), new ArrayList<ArrayList<Integer>>());
        p1.get(note.get(3)).add(note);
      }
    }
    model1.loadPiece(p1);
  }

  @Test
  public void testModelBuilder() {
    MusicEditorModel.MusicEditorBuilder test = new MusicEditorModel.MusicEditorBuilder();
    test.setTempo(100);
    test.addNote(2, 3, 45, 24, 10);
    IMusicEditorModel model = test.build();
    assertEquals(model.getNumberOfNotes(), 1);
    assertEquals(model.getHighestPitch(), 24);
    assertEquals(model.getMaxBeats(), 4);
    assertEquals(model.getTempo(), 100);

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
    assertEquals(model1, model1);
  }


  // testing addNote

  @Test(expected = NullPointerException.class)
  public void testAddNoteNull() {
    initData();
    model0.addNote(null, 2, 4, 2, 1, 10);
  }

  @Test
  public void testAddNote1() {
    initData();
    assertEquals(model0.getNumberOfNotes(), 0);
    model0.addNote(Tones.C, 1, 0, 4, 1, 10);
    assertEquals(model0.getNumberOfNotes(), 1);
    assertEquals(model0.getLowestPitch(), 24);
    assertEquals(model0.getHighestPitch(), 24);
    assertEquals(model0.getMaxBeats(), 4);
  }

  @Test
  public void testAddNote2() {
    initData();
    assertEquals(model0.getNumberOfNotes(), 0);
    model0.addNote(Tones.C, 1, 0, 4, 1, 10);
    model0.addNote(Tones.D_SHARP, 1, 6, 4, 1, 10);
    assertEquals(model0.getNumberOfNotes(), 2);
    assertEquals(model0.getLowestPitch(), 24);
    assertEquals(model0.getHighestPitch(), 27);
    assertEquals(model0.getMaxBeats(), 10);
  }

  @Test
  public void testAddNote3ExactSame() {
    initData();
    assertEquals(model0.getNumberOfNotes(), 0);
    model0.addNote(Tones.C_SHARP, 2, 10, 1, 1, 10);
    model0.addNote(Tones.C_SHARP, 2, 10, 1, 1, 10);
    assertEquals(model0.getNumberOfNotes(), 2);
    assertEquals(model0.getLowestPitch(), 37);
    assertEquals(model0.getHighestPitch(), 37);
    assertEquals(model0.getMaxBeats(), 11);
  }

  @Test
  public void testAddNote4Overlap() {
    initData();
    assertEquals(model0.getNumberOfNotes(), 0);
    model0.addNote(Tones.D, 1, 6, 4, 1, 10);
    model0.addNote(Tones.D, 1, 7, 5, 1, 10);
    assertEquals(model0.getNumberOfNotes(), 2);
    assertEquals(model0.getLowestPitch(), 26);
    assertEquals(model0.getHighestPitch(), 26);
    assertEquals(model0.getMaxBeats(), 12);
  }

  // testing removeNote

  @Test(expected = NullPointerException.class)
  public void testRemoveNoteNull() {
    initData();
    model1.removeNote(null, 0, 0, 1, 1, 10);
  }

  @Test(expected = NoSuchElementException.class)
  public void testRemoveNoteNotFound1() {
    initData();
    model1.removeNote(Tones.B, 10, 0, 10, 1, 10);
  }

  @Test(expected = NoSuchElementException.class)
  public void testRemoveNoteNotFound2() {
    initData();
    // close to D1For6and4
    model1.removeNote(Tones.D, 1, 6, 5, 1, 10);
  }

  @Test(expected = NoSuchElementException.class)
  public void testRemoveNoteNotFound3() {
    initData();
    // close to D1For6and4
    model1.removeNote(Tones.D, 2, 6, 4, 1, 10);
  }

  @Test
  public void testRemoveNote1() {
    initData();
    assertEquals(model1.getNumberOfNotes(), 7);
    model1.removeNote(Tones.D, 1, 6, 4, 1, 64);
    assertEquals(model1.getNumberOfNotes(), 6);
  }

  @Test
  public void testRemoveNote2() {
    initData();
    assertEquals(model1.getNumberOfNotes(), 7);
    assertEquals(model1.getLowestPitch(), 24);
    model1.removeNote(Tones.C, 1, 0, 4, 1, 64);
    assertEquals(model1.getNumberOfNotes(), 6);
    assertEquals(model1.getLowestPitch(), 25);

  }

  @Test
  public void testRemoveNote3() {
    initData();
    assertEquals(model1.getNumberOfNotes(), 7);
    assertEquals(model1.getHighestPitch(), 37);
    model1.removeNote(Tones.C_SHARP, 2, 10, 1, 1, 64);
    assertEquals(model1.getNumberOfNotes(), 6);
    assertEquals(model1.getHighestPitch(), 37);
    model1.removeNote(Tones.C_SHARP, 2, 3, 2, 1, 64);
    assertEquals(model1.getNumberOfNotes(), 5);
    assertEquals(model1.getHighestPitch(), 27);

  }

  // testing modifyNote

  @Test(expected = NullPointerException.class)
  public void testModifyNoteNullNote() {
    initData();
    model1.modifyNote(null, 3, 1, 10, 1, 10, Tones.A, 4, 9, 12, 1, 10);
  }

  @Test(expected = NullPointerException.class)
  public void testModifyNoteNullTone() {
    initData();
    model1.modifyNote(Tones.D, 1, 6, 4, 1, 10, null, 1, 0, 10, 1, 10);
  }

  @Test(expected = NoSuchElementException.class)
  public void testModifyNoteNotFound() {
    initData();
    model1.modifyNote(Tones.C, 1, 0, 1, 1, 10, Tones.C, 1, 0, 10, 1, 10);
  }

  @Test
  public void testModifyNote1() {
    initData();
    assertEquals(model1.getNumberOfNotes(), 7);
    assertEquals(model1.getHighestPitch(), 37);
    model1.modifyNote(Tones.C_SHARP, 2, 10, 1, 1, 64, Tones.A_SHARP, 8, 0, 2, 1, 10);
    assertEquals(model1.getNumberOfNotes(), 7);
    assertEquals(model1.getHighestPitch(), 118);
  }

  @Test
  public void testModifyNote2() {
    initData();
    assertEquals(model1.getNumberOfNotes(), 7);
    assertEquals(model1.getLowestPitch(), 24);
    model1.modifyNote(Tones.C, 1, 0, 4, 1, 64, Tones.D, 2, 7, 10, 1, 10);
    assertEquals(model1.getNumberOfNotes(), 7);
    assertEquals(model1.getLowestPitch(), 25);
    model1.modifyNote(Tones.C_SHARP, 1, 0, 4, 1, 64, Tones.C_SHARP, 5, 5, 3, 1, 10);
    assertEquals(model1.getNumberOfNotes(), 7);
    assertEquals(model1.getLowestPitch(), 26);

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
    model1.removeNote(Tones.C_SHARP, 2, 3, 2, 1, 64);
    assertEquals(model1.getNotesAtBeat(5), new ArrayList<ArrayList<Integer>>());

  }

  @Test
  public void testGetNotesAtBeatOneBeat() {
    initData();
    assertEquals(model1.getNotesAtBeat(4),
            new ArrayList<ArrayList<Integer>>(Arrays.asList(cSharp2For3And2)));

  }

  @Test
  public void testGetNotesAtBeatTwoBeats() {
    initData();
    assertEquals(model1.getNotesAtBeat(10),
            new ArrayList<ArrayList<Integer>>(Arrays.asList(d1For7And5, cSharp2For10And1)));

  }

  @Test
  public void testGetNotesAtBeatThreeBeats() {
    initData();
    assertEquals(model1.getNotesAtBeat(3),
            new ArrayList<ArrayList<Integer>>(Arrays.asList(c1For0And4, cSharp1For0And4,
                    cSharp2For3And2)));

  }

  @Test
  public void testGetNotesAtBeatThreeBeatsTwoOverlap() {
    initData();
    assertEquals(model1.getNotesAtBeat(7),
            new ArrayList<ArrayList<Integer>>(Arrays.asList(d1For6And4, d1For7And5,
                    dSharp1For6And4)));

  }

  //testing combinePieceOnTop

  @Test
  public void testCombinePieceOnTop() {
    initData();
    model0.addNote(Tones.G, 1, 3, 2, 1, 10);
    model0.addNote(Tones.A, 1, 5, 3, 1, 10);
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
    model1.combinePieceOnTop(model0.getAllNotes());
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
    model0.addNote(Tones.G, 1, 3, 2, 1, 10);
    model0.addNote(Tones.A, 1, 5, 3, 1, 10);
    model0.addNote(Tones.C_SHARP, 1, 3, 4, 1, 10);
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
    model1.combinePieceOnTop(model0.getAllNotes());
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
    model0.addNote(Tones.G, 1, 3, 2, 1, 10);
    model0.addNote(Tones.A, 1, 5, 3, 1, 10);
    model0.addNote(Tones.F_SHARP, 1, 10, 14, 1, 10);
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
    model1.combinePieceOnTop(model0.getAllNotes());
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
    model0.addNote(Tones.G, 1, 0, 2, 1, 10);
    model0.addNote(Tones.A, 1, 5, 3, 1, 10);
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
    model1.combinePieceAtEnd(model0.getAllNotes());
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


  @Test
  public void testGetMaxBeat1() {
    initData();
    assertEquals(model1.getMaxBeats(), 12);
  }

  @Test
  public void testGetMaxBeat2() {
    initData();
    assertEquals(model0.getMaxBeats(), 0);
  }

  @Test
  public void testGetTempoGood1() {
    initData();
    assertEquals(model0.getTempo(), 220000); // Default tempo.
  }

  @Test
  public void testGetTempoGood2() {
    initData();
    assertEquals(model1.getTempo(), 220000); // Default tempo.
  }

  @Test
  public void testSetTempoGood1() {
    initData();
    model1.setTempo(5);
    assertEquals(model1.getTempo(), 5);
  }

  @Test
  public void testSetTempoGood2() {
    initData();
    model0.setTempo(10);
    assertEquals(model0.getTempo(), 10);
  }

  @Test
  public void testSetTempoGood3() {
    initData();
    model1.setTempo(0);
    assertEquals(model1.getTempo(), 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetHighestPitchThrow() {
    initData();
    model0.getHighestPitch();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetLowestPitchThrow() {
    initData();
    model0.getHighestPitch();
  }
}

