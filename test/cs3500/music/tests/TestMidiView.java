package cs3500.music.tests;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.sound.midi.Sequencer;

import cs3500.music.model.IMusicEditorModel;
import cs3500.music.model.MusicEditorModel;
import cs3500.music.util.MusicReader;
import cs3500.music.view.IMusicEditorView;
import cs3500.music.view.MidiView;
import cs3500.music.view.MockSequencer;

import static org.junit.Assert.assertEquals;

/**
 * Tester class for MidiView.
 */
public class TestMidiView {
  MusicEditorModel.MusicEditorBuilder builder = new MusicEditorModel.MusicEditorBuilder();
  IMusicEditorView mockView;
  Appendable out = new StringBuilder();
  Sequencer mock = new MockSequencer(out);

  void initData(String fileName) {
    try {
      MusicReader.parseFile(new FileReader(fileName), builder);
    } catch (FileNotFoundException e) {
      System.out.println(e.getMessage());

    }
    IMusicEditorModel model = builder.build();

    MidiView.MidiViewBuilder mockViewBuilder = new MidiView.MidiViewBuilder(model.getAllNotes());
    mockViewBuilder.setSequencer(mock);
    mockView = mockViewBuilder.build();
  }

  @Test
  public void testMockViewOutputMary() {
    initData("mary-little-lamb.txt");
    mockView.initialize();
    assertEquals(out.toString(), "MockSequencer was opened.\n" +
            "checking sequence \n" +
            "Note On msg found\n" +
            "Note On msg found\n" +
            "Note Off msg found\n" +
            "Completed note pairing: note 0 2 1 64 72\n" +
            "Note On msg found\n" +
            "Note Off msg found\n" +
            "Completed note pairing: note 2 4 1 62 72\n" +
            "Note On msg found\n" +
            "Note Off msg found\n" +
            "Completed note pairing: note 4 6 1 60 71\n" +
            "Note On msg found\n" +
            "Note Off msg found\n" +
            "Completed note pairing: note 0 7 1 55 70\n" +
            "Note Off msg found\n" +
            "Completed note pairing: note 6 8 1 62 79\n" +
            "Note On msg found\n" +
            "Note On msg found\n" +
            "Note Off msg found\n" +
            "Completed note pairing: note 8 10 1 64 85\n" +
            "Note On msg found\n" +
            "Note Off msg found\n" +
            "Completed note pairing: note 10 12 1 64 78\n" +
            "Note On msg found\n" +
            "Note Off msg found\n" +
            "Completed note pairing: note 8 15 1 55 79\n" +
            "Note Off msg found\n" +
            "Completed note pairing: note 12 15 1 64 74\n" +
            "Note On msg found\n" +
            "Note On msg found\n" +
            "Note Off msg found\n" +
            "Completed note pairing: note 16 18 1 62 75\n" +
            "Note On msg found\n" +
            "Note Off msg found\n" +
            "Completed note pairing: note 18 20 1 62 77\n" +
            "Note On msg found\n" +
            "Note Off msg found\n" +
            "Completed note pairing: note 16 24 1 55 77\n" +
            "Note Off msg found\n" +
            "Completed note pairing: note 20 24 1 62 75\n" +
            "Note On msg found\n" +
            "Note On msg found\n" +
            "Note Off msg found\n" +
            "Completed note pairing: note 24 26 1 55 79\n" +
            "Note Off msg found\n" +
            "Completed note pairing: note 24 26 1 64 82\n" +
            "Note On msg found\n" +
            "Note Off msg found\n" +
            "Completed note pairing: note 26 28 1 67 84\n" +
            "Note On msg found\n" +
            "Note Off msg found\n" +
            "Completed note pairing: note 28 32 1 67 75\n" +
            "Note On msg found\n" +
            "Note On msg found\n" +
            "Note Off msg found\n" +
            "Completed note pairing: note 32 34 1 64 73\n" +
            "Note On msg found\n" +
            "Note Off msg found\n" +
            "Completed note pairing: note 34 36 1 62 69\n" +
            "Note On msg found\n" +
            "Note Off msg found\n" +
            "Completed note pairing: note 36 38 1 60 71\n" +
            "Note On msg found\n" +
            "Note Off msg found\n" +
            "Completed note pairing: note 32 40 1 55 78\n" +
            "Note Off msg found\n" +
            "Completed note pairing: note 38 40 1 62 80\n" +
            "Note On msg found\n" +
            "Note On msg found\n" +
            "Note Off msg found\n" +
            "Completed note pairing: note 40 42 1 64 84\n" +
            "Note On msg found\n" +
            "Note Off msg found\n" +
            "Completed note pairing: note 42 44 1 64 76\n" +
            "Note On msg found\n" +
            "Note Off msg found\n" +
            "Completed note pairing: note 44 46 1 64 74\n" +
            "Note On msg found\n" +
            "Note Off msg found\n" +
            "Completed note pairing: note 40 48 1 55 79\n" +
            "Note Off msg found\n" +
            "Completed note pairing: note 46 48 1 64 77\n" +
            "Note On msg found\n" +
            "Note On msg found\n" +
            "Note Off msg found\n" +
            "Completed note pairing: note 48 50 1 62 75\n" +
            "Note On msg found\n" +
            "Note Off msg found\n" +
            "Completed note pairing: note 50 52 1 62 74\n" +
            "Note On msg found\n" +
            "Note Off msg found\n" +
            "Completed note pairing: note 52 54 1 64 81\n" +
            "Note On msg found\n" +
            "Note Off msg found\n" +
            "Completed note pairing: note 48 56 1 55 78\n" +
            "Note Off msg found\n" +
            "Completed note pairing: note 54 56 1 62 70\n" +
            "Note On msg found\n" +
            "Note On msg found\n" +
            "Note Off msg found\n" +
            "Completed note pairing: note 56 64 1 52 72\n" +
            "Note Off msg found\n" +
            "Completed note pairing: note 56 64 1 60 73\n" +
            "102 total short messages parsed successfully.\n" +
            "MockSequencer started playing.\n" +
            "MockSequencer tempo set to 200000.0\n");

  }

  @Test
  public void testMidiViewEmpty() {
    MusicEditorModel.MusicEditorBuilder builder = new MusicEditorModel.MusicEditorBuilder();
    builder.setTempo(245);
    MidiView.MidiViewBuilder mockViewBuilderTest = new MidiView.MidiViewBuilder(builder.build()
            .getAllNotes());
    mockViewBuilderTest.setSequencer(mock);
    MidiView mockViewTest = mockViewBuilderTest.build();
    mockViewTest.initialize();
    assertEquals(out.toString(), "MockSequencer was opened.\n" +
            "checking sequence \n" +
            "0 total short messages parsed successfully.\n" +
            "MockSequencer started playing.\n" +
            "MockSequencer tempo set to 245.0\n");
  }

  @Test
  public void testMidiViewBuilding() {
    MusicEditorModel.MusicEditorBuilder builder = new MusicEditorModel.MusicEditorBuilder();
    builder.addNote(1, 4, 5, 24, 12);
    builder.addNote(2, 3, 4, 55, 42);
    builder.setTempo(245);
    MidiView.MidiViewBuilder mockViewBuilderTest = new MidiView.MidiViewBuilder(builder.build()
            .getAllNotes());
    mockViewBuilderTest.setSequencer(mock);
    MidiView mockViewTest = mockViewBuilderTest.build();
    mockViewTest.initialize();
    assertEquals(out.toString(), "MockSequencer was opened.\n" +
            "checking sequence \n" +
            "Note On msg found\n" +
            "Note On msg found\n" +
            "Note Off msg found\n" +
            "Completed note pairing: note 2 3 4 55 42\n" +
            "Note Off msg found\n" +
            "Completed note pairing: note 1 4 4 24 12\n" +
            "6 total short messages parsed successfully.\n" +
            "MockSequencer started playing.\n" +
            "MockSequencer tempo set to 245.0\n");
  }

  @Test
  public void testMidiViewBuilding2() {
    MusicEditorModel.MusicEditorBuilder builder = new MusicEditorModel.MusicEditorBuilder();
    builder.addNote(1, 4, 5, 24, 12);
    builder.addNote(2, 3, 4, 55, 42);
    builder.addNote(53, 67, 4, 64, 42);
    builder.addNote(100, 3000, 4, 33, 42);
    builder.setTempo(2342);
    MidiView.MidiViewBuilder mockViewBuilderTest = new MidiView.MidiViewBuilder(builder.build()
            .getAllNotes());
    mockViewBuilderTest.setSequencer(mock);
    MidiView mockViewTest = mockViewBuilderTest.build();
    mockViewTest.initialize();
    assertEquals(out.toString(), "MockSequencer was opened.\n" +
            "checking sequence \n" +
            "Note On msg found\n" +
            "Note On msg found\n" +
            "Note Off msg found\n" +
            "Completed note pairing: note 2 3 4 55 42\n" +
            "Note Off msg found\n" +
            "Completed note pairing: note 1 4 4 24 12\n" +
            "Note On msg found\n" +
            "Note Off msg found\n" +
            "Completed note pairing: note 53 67 4 64 42\n" +
            "Note On msg found\n" +
            "Note Off msg found\n" +
            "Completed note pairing: note 100 3000 4 33 42\n" +
            "12 total short messages parsed successfully.\n" +
            "MockSequencer started playing.\n" +
            "MockSequencer tempo set to 2342.0\n");
  }


}