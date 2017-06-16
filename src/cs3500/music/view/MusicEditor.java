package cs3500.music.view;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
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

//    model1.addNote(0,3,1,55, 64);
//    model1.addNote(0,3,1, 68, 64);
//    model1.addNote(6, 9, 1, 67, 64);
//    model1.addNote( 7, 11, 1, 65, 64);
//    model1.addNote(6, 9, 1, 55, 64);
//    model1.addNote(25, 78, 3, 67, 64);
//    model1.addNote(7, 106, 1, 25,  64);
//    model1.setTempo(100000);


    try {
      MusicReader.parseFile(new FileReader("odyssey.txt"), model1);
    } catch (FileNotFoundException e) {
      System.out.println(e.getMessage());
    }


    IMusicEditorModel modelFinal = model1.build();

    ViewFactory factory = new ViewFactory(modelFinal);
    IMusicEditorView view2 = factory.getView("visual");
    view2.initialize();
    IMusicEditorView view1 = factory.getView("midi");
    view1.initialize();



  }
}