package cs3500.music.view;

import javax.sound.midi.MidiUnavailableException;

import cs3500.music.model.IMusicEditorModel;

/**
 * Created by Andrew Alcala on 6/15/2017.
 */
public class ViewFactory {

  IMusicEditorModel model;

  public ViewFactory(IMusicEditorModel model) {
    this.model = model;
  }

  IMusicEditorView getView(String viewType) {
    if (viewType == null) {
      return null;
    }
    if (viewType.equals("console")) {
      return new ConsoleView(model.toString());
    }
    if (viewType.equals("visual")) {
      return new MusicEditorView(model.getAllNotes(), model.getMaxBeats());
    }
    if (viewType.equals("midi")) {
      try {
        return new MidiView(model);
      } catch (MidiUnavailableException e) {
        System.out.println(e.getMessage());
      }
    }
    throw new IllegalArgumentException("Invalid view type. Must one of: \"console\" , \"visual\"," +
            " or \"midi\"");
  }
}

