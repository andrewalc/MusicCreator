package cs3500.music.view;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.swing.*;

import cs3500.music.model.IMusicEditorModel;
import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.Note;
import cs3500.music.model.Piece;
import cs3500.music.model.Tones;
import cs3500.music.util.MusicReader;

/**
 * Created by Andrew Alcala on 6/12/2017.
 */


class MusicEditor {




  public static void main(String[] args) {
    MusicEditorModel.MusicEditorBuilder model1 = new MusicEditorModel.MusicEditorBuilder();

    /*model1.addNote(0,3,1,24, 10);
    model1.addNote(0,3,1, 25, 10);
    model1.addNote(6, 9, 1, 26, 10);
    model1.addNote( 7, 11, 1, 26, 10);
    model1.addNote(6, 9, 1, 27, 10);
    model1.addNote(25, 78, 3, 39, 10);
    model1.addNote(7, 106, 1, 32,  10);*/
    model1.setTempo(1000000);
    try {
      MusicReader.parseFile(new FileReader("mary-little-lamb.txt"), model1);
    } catch (FileNotFoundException e) {
      System.out.println(e.getMessage());
    }


    IMusicEditorModel modelFinal = model1.build();

    ViewFactory factory = new ViewFactory(modelFinal);
    IMusicEditorView view = factory.getView("midi");
    view.initialize();

  }
}