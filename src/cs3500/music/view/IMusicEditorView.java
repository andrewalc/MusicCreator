package cs3500.music.view;

import java.util.ArrayList;
import java.util.Map;

import cs3500.music.controller.KeyboardListener;
import cs3500.music.controller.PianoMouseListener;

/**
 * An interface representting a Music Editor View for a Music Editor Model.
 * EDIT: Originally only had initialize. Includes many more methods needed to perform the actions
 * necessary.
 */
public interface IMusicEditorView {

  /**
   * Creates and displays the view for this view implementation. The method should begin all
   * operations necessary to display the view to the user when it is called.
   */
  void initialize();

  /**
   * Adds a KeyboardListener keylistener to the appropriate locations in this view. The keylistener
   * should be directed towards the JFrame and it's Panels. This keylistener will check for
   * keypresses, specifically it should be able to able keyevents that should trigger play/pause,
   * moving a beat forward or backward, and jumping to the beginning or end.
   *
   * @param keyboardListener The KeyboardListener
   */
  void addKeyListener(KeyboardListener keyboardListener);

  void forwardOneBeat();

  void backOneBeat();

  void startMusic();

  void pauseMusic();

  void goToBeginning();

  void goToEnd();

  int getMaxBeat();

  void setCurrentBeat(int currentBeat);

  int getCurrentBeat();

  void updateCurrentBeat();

  boolean isActive();

  boolean isPlayingMusic();

  void addMouseListener(PianoMouseListener mouseListener);

  int getKeyboardKeyPressed();

  void updateVisView(Map<Integer, ArrayList<ArrayList<Integer>>> allNotes);

  void rebuildMusic(Map<Integer, ArrayList<ArrayList<Integer>>> allNotes);

}
