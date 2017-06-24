package cs3500.music.view;

import java.io.FileNotFoundException;
import java.io.FileReader;

import cs3500.music.controller.MusicEditorController;
import cs3500.music.model.IMusicEditorModel;
import cs3500.music.model.MusicEditorModel;
import cs3500.music.util.MusicReader;

/**
 * Class used for running the music editor.
 * EDIT: Added controller, the controller takes in the model and view and begins the Music Editor
 * by calling beginControl.
 */
class MusicEditor {

  /**
   * Main method for running a music editor.
   *
   * @param args Require two string args, a fileName for the txt file to parse music from and a
   *             viewType string. viewType accepted are "console", "visual", "midi", and
   *             "composite".
   */
  public static void main(String[] args) {
//    // Need two string args, filename and viewtype.
//    if (args.length != 2) {
//      throw new IllegalArgumentException("Requires two arguments: [File name] and " +
//              "[View type] \n Ex. \"mary-little-lamb.txt\" + \"visual\" ");
//    }
//    String fileName = args[0];
//    String viewType = args[1];

    // Build a Music editor model with notes from the file given.
    MusicEditorModel.MusicEditorBuilder modelBuilder = new MusicEditorModel.MusicEditorBuilder();
    try {
      MusicReader.parseFile(new FileReader("df-ttfaf.txt"), modelBuilder);
    } catch (FileNotFoundException e) {
      System.out.println(e.getMessage());
    }
    IMusicEditorModel model = modelBuilder.build();
    // Generate a view for the built model base on viewtype input.
    ViewFactory factory = new ViewFactory(model);
    IMusicEditorView view = factory.getView("composite");
    // Start the controller.
    MusicEditorController controller = new MusicEditorController(model, view);
    controller.beginControl();
  }
}