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

  public IMusicEditorView getView(String viewType) {
    if (viewType == null) {
      return null;
    }
    if (viewType.equals("console")) {
      return new ConsoleView(model.toString());
    }
    if (viewType.equals("visual")) {
      return new VisualView(model.getAllNotes(), model.getMaxBeats());
    }
    if (viewType.equals("midi")) {
      MidiView.MidiViewBuilder builder = new MidiView.MidiViewBuilder(model);
      return builder.build();
    }
    throw new IllegalArgumentException("Invalid view type. Must one of: \"console\" , \"visual\"," +
            " or \"midi\"");
  }
}

