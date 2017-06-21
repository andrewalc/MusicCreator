package cs3500.music.controller;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;

/**
 * Created by Andrew Alcala on 6/20/2017.
 */
public class PianoMouseListener implements MouseListener {

  private Map<Integer, Runnable> mousePressedMap;


  public void setMousePressedMap(Map<Integer, Runnable> mousePressedMap) {
    this.mousePressedMap = mousePressedMap;
  }

  @Override
  public void mouseClicked(MouseEvent e) {

  }

  @Override
  public void mousePressed(MouseEvent e) {
    if (mousePressedMap.containsKey(e.getID())) {
      mousePressedMap.get(e.getID()).run();
    }
  }

  @Override
  public void mouseReleased(MouseEvent e) {

  }

  @Override
  public void mouseEntered(MouseEvent e) {

  }

  @Override
  public void mouseExited(MouseEvent e) {

  }
}
