package cs3500.music.view;

import java.util.ArrayList;
import java.util.Map;

import cs3500.music.controller.KeyboardListener;
import cs3500.music.controller.PianoMouseListener;

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
    visualView.setCurrentBeat(0);


  }

  @Override
  public void addKeyListener(KeyboardListener keyboardListener) {
    visualView.addKeyListener(keyboardListener);
  }

  @Override
  public void forwardOneBeat() {
    if (midiView.isPlayingMusic()) {
      midiView.forwardOneBeat();

    } else {
      visualView.forwardOneBeat();
    }

    updateCurrentBeat();
  }

  @Override
  public void backOneBeat() {
    if (midiView.isPlayingMusic()) {
      midiView.backOneBeat();

    } else {
      visualView.backOneBeat();
    }
    updateCurrentBeat();
  }

  @Override
  public void startMusic() {
    midiView.startMusic();
  }

  @Override
  public void pauseMusic() {
    midiView.pauseMusic();
  }

  @Override
  public void goToBeginning() {
    if (midiView.isPlayingMusic()) {
      midiView.goToBeginning();

    } else {
      visualView.goToBeginning();
    }
    updateCurrentBeat();
  }

  @Override
  public void goToEnd() {
    if (midiView.isPlayingMusic()) {
      midiView.goToEnd();

    } else {
      visualView.goToEnd();
    }
    updateCurrentBeat();
  }

  @Override
  public int getMaxBeat() {
    return visualView.getMaxBeat();
  }

  @Override
  public void setCurrentBeat(int currentBeat) {
    midiView.setCurrentBeat(currentBeat);
    visualView.setCurrentBeat(currentBeat);
  }

  @Override
  public int getCurrentBeat() {
    if (midiView.getCurrentBeat() == visualView.getCurrentBeat()) {
      return midiView.getCurrentBeat();
    } else {
      throw new IllegalArgumentException("ERROR: current beats are out of sync!");
    }
  }

  @Override
  public void updateCurrentBeat() {
    if (midiView.isPlayingMusic()) {
      visualView.setCurrentBeat(midiView.getCurrentBeat());
    } else {
      midiView.setCurrentBeat(visualView.getCurrentBeat());
    }
    midiView.updateTempo();

  }


  @Override
  public boolean isActive() {
    return visualView.isActive() || midiView.isActive();
  }

  @Override
  public boolean isPlayingMusic() {
    return midiView.isPlayingMusic();
  }

  @Override
  public void addMouseListener(PianoMouseListener mouseListener) {
    visualView.addMouseListener(mouseListener);
  }

  @Override
  public int getKeyboardKeyPressed() {
    return visualView.getKeyboardKeyPressed();
  }

  @Override
  public void updateVisView(Map<Integer, ArrayList<ArrayList<Integer>>> allNotes) {
    visualView.updateVisView(allNotes);
  }

  @Override
  public void rebuildMusic(Map<Integer, ArrayList<ArrayList<Integer>>> allNotes) {
    System.out.println("Rebuilding music...");
    midiView.rebuildMusic(allNotes);

  }

}
