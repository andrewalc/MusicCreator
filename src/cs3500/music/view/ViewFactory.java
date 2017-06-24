package cs3500.music.view;

import cs3500.music.model.IMusicEditorModel;

/**
 * Factory for a view. Accepts "console", "visual", or "midi".
 */
public class ViewFactory {

  IMusicEditorModel model;

  public ViewFactory(IMusicEditorModel model) {
    this.model = model;
  }

  /**
   * Retrieves the requested view given a string viewType.
   *
   * @param viewType String representing the type of view to create.
   * @return Requested view type.
   */
  public IMusicEditorView getView(String viewType) {
    if (viewType == null) {
      return null;
    }
    if (viewType.equals("composite")) {
      MidiView.MidiViewBuilder builder = new MidiView.MidiViewBuilder(model.getAllNotes());
      builder.setTempo(model.getTempo());
      MidiView midiView = builder.build();
      VisualView visView = new VisualView(model.getAllNotes());
      // set repeats
      visView.receiveRepeatPairs(model.getRepeatPairs());
      return new CompositeView(midiView, visView);
    }
    if (viewType.equals("console")) {
      return new ConsoleView(model.getAllNotes());
    }
    if (viewType.equals("visual")) {
      VisualView visView = new VisualView(model.getAllNotes());
      // set repeats
      visView.receiveRepeatPairs(model.getRepeatPairs());
      return visView;
    }
    if (viewType.equals("midi")) {
      MidiView.MidiViewBuilder builder = new MidiView.MidiViewBuilder(model.getAllNotes());
      builder.setTempo(model.getTempo());
      return builder.build();
    }
    throw new IllegalArgumentException("Invalid view type. Must one of: \"console\" , \"visual\"," +
            " \"midi\", or \"composite\"");
  }
}

