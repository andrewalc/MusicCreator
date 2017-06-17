package cs3500.music.view;

import java.io.FileNotFoundException;
import java.io.FileReader;

import cs3500.music.model.IMusicEditorModel;
import cs3500.music.model.MusicEditorModel;
import cs3500.music.util.MusicReader;

/**
 * Created by Andrew Alcala on 6/12/2017.
 */


class MusicEditor {

  public static void main(String[] args) {

    if (args.length != 2) {
      throw new IllegalArgumentException("Requires two arguments: [File name] and " +
              "[View type] \n Ex. \"mary-little-lamb.txt\" + \"visual\" ");
    }
    String fileName = args[0];
    String viewType = args[1];

    MusicEditorModel.MusicEditorBuilder modelBuilder = new MusicEditorModel.MusicEditorBuilder();
    try {
      MusicReader.parseFile(new FileReader(fileName), modelBuilder);
    } catch (FileNotFoundException e) {
      System.out.println(e.getMessage());
    }
    IMusicEditorModel model = modelBuilder.build();
    ViewFactory factory = new ViewFactory(model);
    IMusicEditorView view = factory.getView(viewType);
    view.initialize();
  }
}