package cs3500.music.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;

/**
 * Class representing a MouseListener for the Music Editor. This controller allows the setting of
 * a Map of Integer(Mouse Event ID) to Runnable, a command. One must create this map first and
 * set this PianoMouseListener to use that map of Runnables to respond with, solely depending on
 * when they click their mouse buttons.
 */
public class PianoMouseListener implements MouseListener {

  private Map<Integer, Runnable> mousePressedMap;


  public void setMousePressedMap(Map<Integer, Runnable> mousePressedMap) {
    this.mousePressedMap = mousePressedMap;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    //STUB, does not apply to controller.
  }

  @Override
  public void mousePressed(MouseEvent e) {
    if (mousePressedMap.containsKey(e.getID())) {
      mousePressedMap.get(e.getID()).run();
    }
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    //STUB, does not apply to controller.
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    //STUB, does not apply to controller.
  }

  @Override
  public void mouseExited(MouseEvent e) {
    //STUB, does not apply to controller.
  }
}
