package cs3500.music.view;

import java.util.ArrayList;
import java.util.Map;

import cs3500.music.controller.KeyboardListener;
import cs3500.music.controller.PianoMouseListener;

/**
 * A Console view implementation. Should receive a model's toString method and relay it to console.
 * EDIT: Updated to support new interface methods needed for functionality, although most of
 * these will not see use in the console view.
 */
public class ConsoleView implements IMusicEditorView {

  private String consoleOutput = "";

  /**
   * Constructor for console view. Should be given a MusicEditorModel's toString method.
   */
  ConsoleView(String consoleOutput) {
    this.consoleOutput = consoleOutput;
  }

  /**
   * Returns the string that will be outputted by initialize() to console when it is called.
   *
   * @return Console string to be outputted.
   */
  public String getConsoleOutput() {
    return consoleOutput;
  }

  @Override
  public void initialize() {
    System.out.print(consoleOutput);
  }


  // Console view does not need to respond to any of these methods.
  @Override
  public void addKeyListener(KeyboardListener keyboardListener) {

  }

  @Override
  public void forwardOneBeat() {

  }

  @Override
  public void backOneBeat() {

  }

  @Override
  public void startMusic() {

  }

  @Override
  public void pauseMusic() {

  }

  @Override
  public void goToBeginning() {

  }

  @Override
  public void goToEnd() {

  }

  @Override
  public int getMaxBeat() {
    return 0;
  }

  @Override
  public void setCurrentBeat(int currentBeat) {

  }

  @Override
  public int getCurrentBeat() {
    return 0;
  }

  @Override
  public void updateCurrentBeat() {

  }


  @Override
  public boolean isActive() {
    return true;
  }

  @Override
  public boolean isPlayingMusic() {
    return false;
  }

  @Override
  public void addMouseListener(PianoMouseListener mouseListener) {

  }

  @Override
  public int getKeyboardKeyPressed() {
    return 0;
  }

  @Override
  public void updateVisView(Map<Integer, ArrayList<ArrayList<Integer>>> allNotes) {

  }

  @Override
  public void rebuildMusic(Map<Integer, ArrayList<ArrayList<Integer>>> allNotes) {

  }
}
