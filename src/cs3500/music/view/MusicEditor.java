package cs3500.music.view;

import java.awt.*;

import javax.swing.*;

import cs3500.music.model.IMusicEditorModel;
import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.Note;
import cs3500.music.model.Piece;
import cs3500.music.model.Tones;

/**
 * Created by Andrew Alcala on 6/12/2017.
 */


class MusicEditor {




  public static void main(String[] args) {
    Note c1For0And4 = new Note(Tones.C, 1, 0, 4);
    Note cSharp1For0And4 = new Note(Tones.C_SHARP, 1, 0, 4);
    Note d1For6And4 = new Note(Tones.D, 1, 6, 4);
    Note d1For7And5 = new Note(Tones.D, 1, 7, 5);

    Note dSharp1For6And4 = new Note(Tones.D_SHARP, 1, 6, 4);
    Note cSharp2For3And2 = new Note(Tones.C_SHARP, 2, 3, 2);
    Note cSharp2For10And1 = new Note(Tones.C_SHARP, 2, 10, 1);
    MusicEditorModel model1 = new MusicEditorModel();
    model1.addNote(Tones.C, 1, 0, 4);
    model1.addNote(Tones.C_SHARP, 1, 0, 4);
    model1.addNote(Tones.D, 1, 6, 4);
    model1.addNote(Tones.D, 1, 7, 5);
    model1.addNote(Tones.D_SHARP, 1, 6, 4);
   // model1.addNote(Tones.C_SHARP, 2, 3, 2);
    //model1.addNote(Tones.C_SHARP, 2, 10, 1);
    //model1.addNote(Tones.G_SHARP, 1,7,100);
    JFrame gui1 = new MusicEditorView(model1);
    gui1.revalidate();
  }
}