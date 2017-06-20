package cs3500.music.view;

import cs3500.music.controller.KeyboardListener;

/**
 * An interface representting a Music Editor View for a Music Editor Model.
 * EDIT: Originally only had initialize. Includes many more methods needed to perform the actions
 * necessary.
 */
public interface IMusicEditorView {

  /**
   * Creates the view for this view implementation. The method should begin all operations
   * necessary to display the view to the user when it is called.
   */
  void initialize();

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


}
