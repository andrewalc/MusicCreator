package cs3500.music.tests;


        import org.junit.Test;

        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.Map;
        import java.util.TreeMap;

        import cs3500.music.view.IntegerComparator;

        import static org.junit.Assert.assertEquals;

/**
 * Created by raymo on 6/16/2017.
 */
public class TestMusicEditorModelAdditional {
  MusicEditorModel model0 = new MusicEditorModel();
  MusicEditorModel model1 = new MusicEditorModel();
  Map<Integer, ArrayList<ArrayList<Integer>>> p0 = new TreeMap<>(new IntegerComparator());
  Map<Integer, ArrayList<ArrayList<Integer>>> p1 = new TreeMap<>(new IntegerComparator());

  // Pre-created notes for easy access
  //Note cc1For0And4 = new Note(Tones.C, 1, 0, 4, 1, 10);
  ArrayList<Integer> c1For0And4 = new ArrayList<>(Arrays.asList(0, 3, 1, 24, 64));
  // Note cSdharp1For0And4 = new Note(Tones.C_SHARP, 1, 0, 4, 1, 10);
  ArrayList<Integer> cSharp1For0And4 = new ArrayList<>(Arrays.asList(0, 3, 1, 25, 64));
  // Note dd1For6And4 = new Note(Tones.D, 1, 6, 4, 1, 10);
  ArrayList<Integer> d1For6And4 = new ArrayList<>(Arrays.asList(6, 9, 1, 26, 64));
  // Note dd1For7And5 = new Note(Tones.D, 1, 7, 5, 1, 10);
  ArrayList<Integer> d1For7And5 = new ArrayList<>(Arrays.asList(7, 11, 1, 26, 64));

  // Note ddSharp1For6And4 = new Note(Tones.D_SHARP, 1, 6, 4, 1, 10);
  ArrayList<Integer> dSharp1For6And4 = new ArrayList<>(Arrays.asList(6, 9, 1, 27, 64));

  //  Note cScharp2For3And2 = new Note(Tones.C_SHARP, 2, 3, 2, 1, 10);
  ArrayList<Integer> cSharp2For3And2 = new ArrayList<>(Arrays.asList(3, 4, 1, 37, 64));
  //  Note cScharp2For10And1 = new Note(Tones.C_SHARP, 2, 10, 1, 1, 10);
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
        p1.put(note.get(3), new ArrayList<>());
        p1.get(note.get(3)).add(note);
      }
    }
    model1.loadPiece(p1);
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
    assertEquals(model0.getTempo(), 0);
  }

  @Test
  public void testGetTempoGood2() {
    initData();
    assertEquals(model1.getTempo(), 0);
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
}
