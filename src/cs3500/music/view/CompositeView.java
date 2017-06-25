package cs3500.music.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import cs3500.music.controller.KeyboardListener;
import cs3500.music.controller.PianoMouseListener;

/**
 * A Composite Music Editor View, combining both the visual and midi views into one synchronized
 * view.
 */
public class CompositeView implements IMusicEditorView {

  private MidiView midiView;
  private VisualView visualView;
  private Map<Integer, ArrayList<Integer>> repeatPairsAndPass = new HashMap<>();
  private boolean practiceMode = false;


  private ArrayList<Integer> pMClickedPitches = new ArrayList<>();

  /**
   * Constructor for the composite view. Requires a MidiView and a VisualView.
   *
   * @param midiView   The Midiview to run.
   * @param visualView The VisualView to run.
   */
  public CompositeView(MidiView midiView, VisualView visualView) {
    this.midiView = midiView;
    this.visualView = visualView;
  }


  @Override
  public void initialize() {
    midiView.initialize();
    visualView.initialize();
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
    // If the midi is playing, it will dictate what the current beat is defined for the visual.
    // Otherwise, the visual view will dictate what the current beat is and make the midi follow.
    if (midiView.isPlayingMusic()) {
      for (Integer keyEndingBeat : this.getRepeatPairsAndPass().keySet()) {
        int startingBeatingCandidate = this.getRepeatPairsAndPass().get(keyEndingBeat).get(0);
        if ((midiView.getCurrentBeat() == keyEndingBeat) &&
                this.getRepeatPairsAndPass().get(keyEndingBeat).get(1) == 1) {
          midiView.pauseMusic();
          midiView.setCurrentBeat(startingBeatingCandidate);
          midiView.startMusic();
          // We passed the repeat so set its passing value to -1.
          this.getRepeatPairsAndPass().put(keyEndingBeat,
                  new ArrayList<Integer>(Arrays.asList(startingBeatingCandidate, -1)));
        }
      }
      visualView.setCurrentBeat(midiView.getCurrentBeat());
      //startMusic();

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
  public int getPianoKeyPressed() {
    return visualView.getPianoKeyPressed();
  }

  @Override
  public void updateVisAddNotes(Map<Integer, ArrayList<ArrayList<Integer>>> allNotes) {
    visualView.updateVisAddNotes(allNotes);
  }

  @Override
  public void rebuildMusic(Map<Integer, ArrayList<ArrayList<Integer>>> allNotes) {
    System.out.println("Rebuilding music...");
    midiView.rebuildMusic(allNotes);

  }

  @Override
  public void receiveRepeatPairs(Map<Integer, Integer> repeatPairs) {
    visualView.receiveRepeatPairs(repeatPairs);
    midiView.receiveRepeatPairs(repeatPairs);

    // create the map with repeat pairs, keys are endingbeat, values are an arraylist of integer
    // of size 2. The arraylist holds the startingbeat in index 0,  and holds a integer, either
    // -1 or 1, representing whether this pair is active(has not been passed). -1 means it has been
    // passed, 1 means it has not been passed and is active.
    for (Integer keyEndingBeat : this.getRepeatPairs().keySet()) {
      int startingBeatingCandidate = this.getRepeatPairs().get(keyEndingBeat);
      this.repeatPairsAndPass.put(keyEndingBeat,
              new ArrayList<Integer>(Arrays.asList(startingBeatingCandidate, 1)));
    }
  }

  @Override
  public Map<Integer, Integer> getRepeatPairs() {
    return visualView.getRepeatPairs();
  }

  @Override
  public void resetRepeatPassings() {
    for (Integer keyEndingBeat : this.getRepeatPairs().keySet()) {
      int startingBeatingCandidate = this.getRepeatPairs().get(keyEndingBeat);
      this.repeatPairsAndPass.put(keyEndingBeat,
              new ArrayList<Integer>(Arrays.asList(startingBeatingCandidate, 1)));
    }
  }

  @Override
  public void setTempo(int tempo) {
    midiView.setTempo(tempo);
  }

  @Override
  public void beginPracticeMode() {
    this.practiceMode = true;
    visualView.beginPracticeMode();
    this.pauseMusic();
  }

  @Override
  public void endPracticeMode() {
    this.practiceMode = false;
    visualView.endPracticeMode();
  }

  @Override
  public boolean isInPracticeMode() {
    return this.practiceMode;
  }

  @Override
  public void practiceModeChecking(ArrayList<ArrayList<Integer>> notesAtBeat) {
    boolean allPitchesHit = true;
    ArrayList<Integer> pitchesRequired = new ArrayList<>();
    for (ArrayList<Integer> note : notesAtBeat) {
      pitchesRequired.add(note.get(3));
    }
    for (Integer requiredPitch : pitchesRequired) {
      if (!this.pMClickedPitches.contains(requiredPitch)) {
        allPitchesHit = false;
      }
    }

    if (allPitchesHit) {
      this.pMResetClickedPitches();
      this.forwardOneBeat();
    }
  }

  @Override
  public void practiceModeAddClickedPitch(Integer addedPitch, ArrayList<ArrayList<Integer>> notesAtBeat) {
    boolean isValidBeat = false;
    for (ArrayList<Integer> note : notesAtBeat) {
      if (note.get(3) == addedPitch) {
        isValidBeat = true;
        break;
      }
    }

    if (isValidBeat && !pMClickedPitches.contains(addedPitch)) {
      this.pMClickedPitches.add(addedPitch);
      visualView.practiceModeAddClickedPitch(addedPitch, notesAtBeat);
    }
  }

  private void pMResetClickedPitches() {
    this.pMClickedPitches = new ArrayList<>();
  }

  private Map<Integer, ArrayList<Integer>> getRepeatPairsAndPass() {
    return this.repeatPairsAndPass;
  }


}
