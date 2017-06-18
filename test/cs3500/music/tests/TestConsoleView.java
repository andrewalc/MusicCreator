package cs3500.music.tests;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.swing.text.View;

import cs3500.music.model.IMusicEditorModel;
import cs3500.music.model.MusicEditorModel;
import cs3500.music.util.MusicReader;
import cs3500.music.view.ConsoleView;
import cs3500.music.view.IMusicEditorView;
import cs3500.music.view.MidiView;
import cs3500.music.view.ViewFactory;

import static org.junit.Assert.assertEquals;

/**
 * Test class for ConsoleView, to see if output matches the model toString.
 */
public class TestConsoleView {
  MusicEditorModel.MusicEditorBuilder builder = new MusicEditorModel.MusicEditorBuilder();
  IMusicEditorModel model;
  ConsoleView view;

  void initData(String fileName) {
    try {
      MusicReader.parseFile(new FileReader(fileName), builder);
    } catch (FileNotFoundException e) {
      System.out.println(e.getMessage());

    }
    model = builder.build();
    ViewFactory viewFactory = new ViewFactory(model);
    view = (ConsoleView) viewFactory.getView("console");
  }

  @Test
  public void testConsoleViewMary() {
    initData("mary-little-lamb.txt");
    assertEquals(view.getConsoleOutput(), model.toString());
    assertEquals(view.getConsoleOutput(), "    E3   F3  F#3   G3  G#3   A3  A#3   B3   C4  C#4   " +
            "D4  D#4   E4   F4  F#4   G4 \n" +
            " 0                 X                                            X                 \n" +
            " 1                 |                                            |                 \n" +
            " 2                 |                                  X         |                 \n" +
            " 3                 |                                  |                           \n" +
            " 4                 |                        X         |                           \n" +
            " 5                 |                        |                                     \n" +
            " 6                 |                        |         X                           \n" +
            " 7                 |                                  |                           \n" +
            " 8                 X                                  |         X                 \n" +
            " 9                 |                                            |                 \n" +
            "10                 |                                            X                 \n" +
            "11                 |                                            |                 \n" +
            "12                 |                                            X                 \n" +
            "13                 |                                            |                 \n" +
            "14                 |                                            |                 \n" +
            "15                 |                                            |                 \n" +
            "16                 X                                  X                           \n" +
            "17                 |                                  |                           \n" +
            "18                 |                                  X                           \n" +
            "19                 |                                  |                           \n" +
            "20                 |                                  X                           \n" +
            "21                 |                                  |                           \n" +
            "22                 |                                  |                           \n" +
            "23                 |                                  |                           \n" +
            "24                 X                                  |         X                 \n" +
            "25                 |                                            |                 \n" +
            "26                 |                                            |              X  \n" +
            "27                                                                             |  \n" +
            "28                                                                             X  \n" +
            "29                                                                             |  \n" +
            "30                                                                             |  \n" +
            "31                                                                             |  \n" +
            "32                 X                                            X              |  \n" +
            "33                 |                                            |                 \n" +
            "34                 |                                  X         |                 \n" +
            "35                 |                                  |                           \n" +
            "36                 |                        X         |                           \n" +
            "37                 |                        |                                     \n" +
            "38                 |                        |         X                           \n" +
            "39                 |                                  |                           \n" +
            "40                 X                                  |         X                 \n" +
            "41                 |                                            |                 \n" +
            "42                 |                                            X                 \n" +
            "43                 |                                            |                 \n" +
            "44                 |                                            X                 \n" +
            "45                 |                                            |                 \n" +
            "46                 |                                            X                 \n" +
            "47                 |                                            |                 \n" +
            "48                 X                                  X         |                 \n" +
            "49                 |                                  |                           \n" +
            "50                 |                                  X                           \n" +
            "51                 |                                  |                           \n" +
            "52                 |                                  |         X                 \n" +
            "53                 |                                            |                 \n" +
            "54                 |                                  X         |                 \n" +
            "55                 |                                  |                           \n" +
            "56  X              |                        X         |                           \n" +
            "57  |                                       |                                     \n" +
            "58  |                                       |                                     \n" +
            "59  |                                       |                                     \n" +
            "60  |                                       |                                     \n" +
            "61  |                                       |                                     \n" +
            "62  |                                       |                                     \n" +
            "63  |                                       |                                     \n" +
            "64  |                                       |                                     \n" +
            "65                                                                                \n");

  }

  @Test
  public void testConsoleiViewEmpty() {
    MusicEditorModel.MusicEditorBuilder builder = new MusicEditorModel.MusicEditorBuilder();
    builder.setTempo(245);
    model = builder.build();
    ViewFactory viewFactory = new ViewFactory(model);
    view = (ConsoleView) viewFactory.getView("console");
    assertEquals(view.getConsoleOutput(), model.toString());
    assertEquals(view.getConsoleOutput(), "");
  }

  @Test
  public void testConsoleViewBuilding() {
    MusicEditorModel.MusicEditorBuilder builder = new MusicEditorModel.MusicEditorBuilder();
    builder.addNote(1, 4, 5, 24, 12);
    builder.addNote(2, 3, 4, 55, 42);
    builder.setTempo(245);
    model = builder.build();
    ViewFactory viewFactory = new ViewFactory(model);
    view = (ConsoleView) viewFactory.getView("console");
    assertEquals(view.getConsoleOutput(), model.toString());
    assertEquals(view.getConsoleOutput(), "   C1  C#1   D1  D#1   E1   F1  F#1   G1  G#1   A1  " +
            "A#1   B1   C2  C#2   D2  D#2   E2   F2  F#2   G2  G#2   A2  A#2   B2   C3  C#3   D3 " +
            " D#3   E3   F3  F#3   G3 \n" +
            "0                                                                                   " +
            "                                                                             \n" +
            "1  X                                                                                " +
            "                                                                             \n" +
            "2  |                                                                                " +
            "                                                                          X  \n" +
            "3  |                                                                                " +
            "                                                                          |  \n" +
            "4  |                                                                                " +
            "                                                                             \n" +
            "5                                                                                   " +
            "                                                                             \n");

  }

  @Test
  public void testConsoleViewBuilding2() {
    MusicEditorModel.MusicEditorBuilder builder = new MusicEditorModel.MusicEditorBuilder();
    builder.addNote(1, 4, 5, 24, 12);
    builder.addNote(2, 3, 4, 55, 42);
    builder.addNote(53, 67, 4, 64, 42);
    builder.addNote(100, 3000, 4, 33, 42);
    builder.setTempo(2342);
    model = builder.build();
    ViewFactory viewFactory = new ViewFactory(model);
    view = (ConsoleView) viewFactory.getView("console");
    assertEquals(view.getConsoleOutput(), model.toString());
  }

}
