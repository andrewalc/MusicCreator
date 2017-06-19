package cs3500.music.view;

/**
 * A Composite Music Editor View, combining both the visual and midi views into one synchronized
 * view.
 */
public class CompositeView implements IMusicEditorView {

  MidiView midiView;
  VisualView visualView;
  private int currentBeat;

  public CompositeView(MidiView midiView, VisualView visualView) {
    this.midiView = midiView;
    this.visualView = visualView;
    this.currentBeat = visualView.getCurrentBeat();
  }


  @Override
  public void initialize() {
    midiView.initialize();
    visualView.initialize();
    while (midiView.getSequencer().isRunning()) {
      System.out.println("Running");
      this.currentBeat = (int) this.midiView.getSequencer().getTickPosition();
      visualView.setCurrentBeat(this.currentBeat);
    }
  }
}
