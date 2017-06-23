package cs3500.music.tests;

import org.junit.Test;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

import cs3500.music.controller.KeyboardListener;
import cs3500.music.model.MusicEditorModel;
import cs3500.music.view.MockCompositeView;

import static org.junit.Assert.assertEquals;

/**
 * Test class for KeyboardListener.
 */
public class TestKeyboardListener {
  MusicEditorModel.MusicEditorBuilder builder = new MusicEditorModel.MusicEditorBuilder();
  Appendable out = new StringBuilder();
  MockCompositeView view = new MockCompositeView(out);
  boolean audioUpdateNecessary;
  KeyboardListener kbl = new KeyboardListener();
  JFrame frame = new JFrame();


  /**
   * Configures the keyboard listener to the same commands as the keyboard listener in the
   * controller.
   */
  private void configureKeyBoardListener() {
    audioUpdateNecessary = false;
    Map<Integer, Runnable> keyPresses = new HashMap<Integer, Runnable>();

    keyPresses.put(KeyEvent.VK_SPACE, () -> { //the contents of MakeCaps below
      if (view.isPlayingMusic()) {
        view.pauseMusic();
      } else {
        if (audioUpdateNecessary) {
          view.rebuildMusic(null);
          audioUpdateNecessary = false;
        }
        view.startMusic();

      }
    });
    keyPresses.put(KeyEvent.VK_RIGHT, () -> { //the contents of MakeCaps below
      view.forwardOneBeat();

    });
    keyPresses.put(KeyEvent.VK_LEFT, () -> { //the contents of MakeCaps below
      view.backOneBeat();
    });
    keyPresses.put(KeyEvent.VK_HOME, () -> { //the contents of MakeCaps below
      if (view.isPlayingMusic() && audioUpdateNecessary) {
        view.rebuildMusic(null);
        audioUpdateNecessary = false;
      }
      view.goToBeginning();
    });
    keyPresses.put(KeyEvent.VK_END, () -> { //the contents of MakeCaps below
      view.goToEnd();
    });

    // add the keyboard listener to the view.
    kbl.setKeyPressedMap(keyPresses);

    frame.addKeyListener(kbl);
  }

  @Test
  public void testKeyboardListenerSpacetoPlay() {
    configureKeyBoardListener();
    KeyEvent event = new KeyEvent(frame, KeyEvent.KEY_PRESSED, 0, 0, KeyEvent.VK_SPACE, 'Z');
    // Press space bar to play.
    kbl.keyPressed(event);
    assertEquals(out.toString(),
            "Checking isPlayingMusic.\n" +
                    "Starting music.\n");
  }

  @Test
  public void testKeyboardListenerSpacetoPauseAfterPlay() {
    configureKeyBoardListener();
    KeyEvent event = new KeyEvent(frame, KeyEvent.KEY_PRESSED, 0, 0, KeyEvent.VK_SPACE, 'Z');
    // Press space bar to play.
    frame.getKeyListeners()[0].keyPressed(event);
    assertEquals(out.toString(),
            "Checking isPlayingMusic.\n" +
                    "Starting music.\n");
    // Press space bar to pause now that it is playing.
    frame.getKeyListeners()[0].keyPressed(event);
    assertEquals(out.toString(),
            "Checking isPlayingMusic.\n" +
                    "Starting music.\n" +
                    "Checking isPlayingMusic.\n" +
                    "Pausing music\n");
  }

  @Test
  public void testKeyboardListenerRightArrowToMoveBeatForward() {
    configureKeyBoardListener();
    KeyEvent event = new KeyEvent(frame, KeyEvent.KEY_PRESSED, 0, 0, KeyEvent.VK_RIGHT, 'Z');
    // Move forward one beat by pressing right arrow key.
    frame.getKeyListeners()[0].keyPressed(event);
    assertEquals(out.toString(),
            "Moving forward one beat.\n");
  }

  @Test
  public void testKeyboardListenerLeftArrowToMoveBeatBackward() {
    configureKeyBoardListener();
    KeyEvent event = new KeyEvent(frame, KeyEvent.KEY_PRESSED, 0, 0, KeyEvent.VK_LEFT, 'Z');
    // Move forward one beat by pressing right arrow key.
    frame.getKeyListeners()[0].keyPressed(event);
    assertEquals(out.toString(),
            "Moving backward one beat.\n");
  }

  @Test
  public void testKeyboardListenerHomeToMoveToBeginning() {
    configureKeyBoardListener();
    KeyEvent event = new KeyEvent(frame, KeyEvent.KEY_PRESSED, 0, 0, KeyEvent.VK_HOME, 'Z');
    // Move forward one beat by pressing right arrow key.
    frame.getKeyListeners()[0].keyPressed(event);
    assertEquals(out.toString(),
            "Checking isPlayingMusic.\n" +
                    "Moving back to beginning.\n");
  }

  @Test
  public void testKeyboardListenerEndToMoveToEnd() {
    configureKeyBoardListener();
    KeyEvent event = new KeyEvent(frame, KeyEvent.KEY_PRESSED, 0, 0, KeyEvent.VK_END, 'Z');
    // Move forward one beat by pressing right arrow key.
    frame.getKeyListeners()[0].keyPressed(event);
    assertEquals(out.toString(),
            "Moving to the end.\n");
  }

  @Test
  public void testKeyboardListenerMultiTest() {
    configureKeyBoardListener();
    KeyEvent event = new KeyEvent(frame, KeyEvent.KEY_PRESSED, 0, 0, KeyEvent.VK_END, 'Z');
    // Move forward one beat by pressing right arrow key.
    frame.getKeyListeners()[0].keyPressed(event);
    event = new KeyEvent(frame, KeyEvent.KEY_PRESSED, 0, 0, KeyEvent.VK_LEFT, 'Z');
    // Move backward one beat by pressing right arrow key.
    frame.getKeyListeners()[0].keyPressed(event);
    event = new KeyEvent(frame, KeyEvent.KEY_PRESSED, 0, 0, KeyEvent.VK_LEFT, 'Z');
    // Move backward one beat by pressing right arrow key.
    frame.getKeyListeners()[0].keyPressed(event);
    event = new KeyEvent(frame, KeyEvent.KEY_PRESSED, 0, 0, KeyEvent.VK_SPACE, 'Z');
    // Play the music
    frame.getKeyListeners()[0].keyPressed(event);
    event = new KeyEvent(frame, KeyEvent.KEY_PRESSED, 0, 0, KeyEvent.VK_SPACE, 'Z');
    // Pause the music
    frame.getKeyListeners()[0].keyPressed(event);
    event = new KeyEvent(frame, KeyEvent.KEY_PRESSED, 0, 0, KeyEvent.VK_HOME, 'Z');
    // Go to the beginning
    frame.getKeyListeners()[0].keyPressed(event);
    event = new KeyEvent(frame, KeyEvent.KEY_PRESSED, 0, 0, KeyEvent.VK_SPACE, 'Z');
    // Play the music.
    frame.getKeyListeners()[0].keyPressed(event);
    assertEquals(out.toString(),
            "Moving to the end.\n" +
                    "Moving backward one beat.\n" +
                    "Moving backward one beat.\n" +
                    "Checking isPlayingMusic.\n" +
                    "Starting music.\n" +
                    "Checking isPlayingMusic.\n" +
                    "Pausing music\n" +
                    "Checking isPlayingMusic.\n" +
                    "Moving back to beginning.\n" +
                    "Checking isPlayingMusic.\n" +
                    "Starting music.\n");
  }
}
