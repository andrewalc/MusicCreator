package cs3500.music.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;

import javax.swing.*;

/**
 * Class representing a MouseListener for the Music Editor. This controller allows the setting of
 * a Map of Integer(Mouse Event ID) to Runnable, a command. One must create this map first and
 * set this PianoMouseListener to use that map of Runnables to respond with, solely depending on
 * when they click their mouse buttons.
 */
public class PianoMouseListener implements MouseListener {

  private Map<Integer, Runnable> mousePressedMap;
  private Map<Integer, Runnable> mouseReleasedMap;



  public void setMousePressedMap(Map<Integer, Runnable> mousePressedMap) {
    this.mousePressedMap = mousePressedMap;
  }

  public void setMouseReleasedMap(Map<Integer, Runnable> mouseReleasedMap) {
    this.mouseReleasedMap = mouseReleasedMap;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    //STUB, does not apply to controller.
  }

  @Override
  public void mousePressed(MouseEvent e) {
    try{
      mousePressedMap.get(e.getButton()).run();
    }
    catch(NullPointerException ex){
      ex.getMessage();
    }
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    try{
      mouseReleasedMap.get(e.getButton()).run();
    }
    catch(NullPointerException ex){
      ex.getMessage();
    }
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
