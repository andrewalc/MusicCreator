package cs3500.music.controller;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import cs3500.music.model.IMusicEditorModel;
import cs3500.music.view.CompositeView;
import cs3500.music.view.IMusicEditorView;

/**
 * Created by Andrew Alcala on 6/19/2017.
 */
public class MusicEditorController {
  IMusicEditorModel model;
  IMusicEditorView view;

  public MusicEditorController(IMusicEditorModel model, IMusicEditorView view) {
    this.model = model;
    this.view = view;
  }

  /**
   * Creates and sets a keyboard listener for the view
   * In effect it creates snippets of code as Runnable object, one for each time a key
   * is typed, pressed and released, only for those that the program needs.
   * <p>
   * In this example, we need to toggle color when user TYPES 'd', make the message
   * all caps when the user PRESSES 'c' and reverts to the original string when the
   * user RELEASES 'c'. Thus we create three snippets of code and put them in the appropriate map.
   * This example shows how to create these snippets of code using lambda functions, a new feature
   * in Java 8.
   * <p>
   * For more information on Java 8's syntax of lambda expressions, go here:
   * https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html
   * <p>
   * The above tutorial has specific example for GUI listeners.
   * <p>
   * Last we create our KeyboardListener object, set all its maps and then give it to
   * the view.
   */
  private void configureKeyBoardListener() {
    Map<Integer, Runnable> keyPresses = new HashMap<Integer, Runnable>();

    keyPresses.put(KeyEvent.VK_P, () -> { //the contents of MakeCaps below
              view.startMusic();
            }

    );


    KeyboardListener kbd = new KeyboardListener();
    kbd.setKeyPressedMap(keyPresses);

    view.addKeyListener(kbd);

  }

}
