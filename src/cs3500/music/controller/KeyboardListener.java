package cs3500.music.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

/**
 * Created by Andrew Alcala on 6/19/2017.
 */
public class KeyboardListener implements KeyListener {

  private Map<Integer, Runnable> keyPressedMap;


  public void setKeyPressedMap(Map<Integer, Runnable> keyPressedMap) {
    this.keyPressedMap = keyPressedMap;
  }


  @Override
  public void keyTyped(KeyEvent e) {

  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (keyPressedMap.containsKey(e.getKeyCode())) {
      keyPressedMap.get(e.getKeyCode()).run();
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {

  }
}
