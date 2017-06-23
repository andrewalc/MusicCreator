package cs3500.music.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

/**
 * A class representing a KeyListener for the Music Editor. This controller allows the setting of
 * a Map of Integer(KeyCode) to Runnable, a command. One must create this map first and set this
 * KeyboardListener to use that map of Runnables to respond with, depending on what keycode was
 * found on a keypress.
 */
public class KeyboardListener implements KeyListener {

  private Map<Integer, Runnable> keyPressedMap;


  /**
   * Sets the Map of Integer(Keycode) to Runnable (command) to look for on KeyPress.
   *
   * @param keyPressedMap The Map of Integer to Runnable.
   */
  public void setKeyPressedMap(Map<Integer, Runnable> keyPressedMap) {
    this.keyPressedMap = keyPressedMap;
  }


  @Override
  public void keyTyped(KeyEvent e) {
    //STUB, does not apply to controller.
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (keyPressedMap.containsKey(e.getKeyCode())) {
      keyPressedMap.get(e.getKeyCode()).run();
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    //STUB, does not apply to controller.
  }
}
