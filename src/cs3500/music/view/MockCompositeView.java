package cs3500.music.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import cs3500.music.controller.KeyboardListener;
import cs3500.music.controller.PianoMouseListener;

/**
 * A Mock Composite Music Editor View, combining both the visual and midi views into one
 * synchronized view.
 */
public class MockCompositeView implements IMusicEditorView {
  private Appendable ap;
  private boolean isPlayingMusic;
  private boolean isActive;
  private int currTick = 0;
  private int maxTick = 5;


  /**
   * Constructor for the composite view. Requires a MidiView and a VisualView.
   */
  public MockCompositeView(Appendable ap) {
    this.ap = ap;
    isPlayingMusic = false;
    isActive = true;
  }


  @Override
  public void initialize() {
    try {
      this.ap.append("initializing view.\n");
    } catch (IOException e) {
      e.getMessage();
    }
  }

  @Override
  public void addKeyListener(KeyboardListener keyboardListener) {
    try {
      this.ap.append("Adding KeyListner.\n");
    } catch (IOException e) {
      e.getMessage();
    }
  }

  @Override
  public void forwardOneBeat() {
    try {
      this.ap.append("Moving forward one beat.\n");
    } catch (IOException e) {
      e.getMessage();
    }
  }

  @Override
  public void backOneBeat() {
    try {
      this.ap.append("Moving backward one beat.\n");
    } catch (IOException e) {
      e.getMessage();
    }
  }

  @Override
  public void startMusic() {
    try {
      this.ap.append("Starting music.\n");
    } catch (IOException e) {
      e.getMessage();
    }
    isPlayingMusic = true;
  }

  @Override
  public void pauseMusic() {
    try {
      this.ap.append("Pausing music\n");
    } catch (IOException e) {
      e.getMessage();
    }
    isPlayingMusic = false;
  }

  @Override
  public void goToBeginning() {
    try {
      this.ap.append("Moving back to beginning.\n");
    } catch (IOException e) {
      e.getMessage();
    }
  }

  @Override
  public void goToEnd() {
    try {
      this.ap.append("Moving to the end.\n");
    } catch (IOException e) {
      e.getMessage();
    }
  }

  @Override
  public int getMaxBeat() {
    try {
      this.ap.append("Called getMaxBeat.\n");
    } catch (IOException e) {
      e.getMessage();
    }
    return 0;
  }

  @Override
  public void setCurrentBeat(int currentBeat) {
    try {
      this.ap.append("Setting current beat to " + currentBeat + "\n");
    } catch (IOException e) {
      e.getMessage();
    }
  }

  @Override
  public int getCurrentBeat() {
    try {
      this.ap.append("Called getCurrentBeat.\n");
    } catch (IOException e) {
      e.getMessage();
    }
    return 0;
  }

  @Override
  public void updateCurrentBeat() {
    try {
      this.ap.append("Called updateCurrentBeat.\n");
    } catch (IOException e) {
      e.getMessage();
    }
    currTick++;
    if (currTick == maxTick) {
      stopMock();
    }
  }


  @Override
  public boolean isActive() {
    try {
      this.ap.append("Checking if view isActive.\n");
    } catch (IOException e) {
      e.getMessage();
    }
    return isActive;
  }

  @Override
  public boolean isPlayingMusic() {
    try {
      this.ap.append("Checking isPlayingMusic.\n");
    } catch (IOException e) {
      e.getMessage();
    }
    return isPlayingMusic;
  }

  @Override
  public void addMouseListener(PianoMouseListener mouseListener) {
    try {
      this.ap.append("Adding mouse listener.\n");
    } catch (IOException e) {
      e.getMessage();
    }
  }

  @Override
  public int getPianoKeyPressed() {
    try {
      this.ap.append("Called getPianoKeyPressed.\n");
    } catch (IOException e) {
      e.getMessage();
    }
    return 1;
  }

  @Override
  public void updateVisAddNotes(Map<Integer, ArrayList<ArrayList<Integer>>> allNotes) {
    try {
      this.ap.append("Called updateVisAddNotes.\n");
    } catch (IOException e) {
      e.getMessage();
    }
  }

  @Override
  public void rebuildMusic(Map<Integer, ArrayList<ArrayList<Integer>>> allNotes) {
    try {
      this.ap.append("Rebuilding music.\n");
    } catch (IOException e) {
      e.getMessage();
    }

  }

  @Override
  public void receiveRepeatPairs(Map<Integer, Integer> repeatPairs) {
    try {
      this.ap.append("Receiving repeat pairs.\n");
    } catch (IOException e) {
      e.getMessage();
    }
  }

  @Override
  public Map<Integer, Integer> getRepeatPairs() {
    try {
      this.ap.append("Getting repeat pairs.\n");
    } catch (IOException e) {
      e.getMessage();
    }
    return null;
  }

  @Override
  public void resetRepeatPassings() {
    try {
      this.ap.append("Resetting status of all repeat pairs.\n");
    } catch (IOException e) {
      e.getMessage();
    }
  }

  @Override
  public void setTempo(int tempo) {
    try {
      this.ap.append("Setting the views tempo to " + tempo + "\n");
    } catch (IOException e) {
      e.getMessage();
    }
  }

  @Override
  public void beginPracticeMode() {

  }

  @Override
  public void endPracticeMode() {

  }

  @Override
  public boolean isInPracticeMode() {
    return false;
  }

  @Override
  public void practiceModeChecking(ArrayList<ArrayList<Integer>> notesAtBeat) {

  }

  @Override
  public void practiceModeAddClickedPitch(Integer addedPitch, ArrayList<ArrayList<Integer>> notesAtBeat) {

  }

  /**
   * Used to stop the mock view.
   */
  public void stopMock() {
    isActive = false;
  }

}
