package cs3500.music.view;

import cs3500.music.controller.KeyboardListener;

/**
 * An interface representting a Music Editor View for a Music Editor Model.
 */
public interface IMusicEditorView {

  /**
   * Creates the view for this view implementation. The method should begin all operations
   * necessary to display the view to the user when it is called.
   */
  void initialize();

  void addKeyListener(KeyboardListener keyboardListener);

  void startMusic();

  void pauseMusic();

  void goToBeginning();

  void goToEnd();
}
