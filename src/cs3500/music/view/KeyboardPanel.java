package cs3500.music.view;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;
import java.util.ArrayList;

import javax.swing.*;

import cs3500.music.model.IMusicEditorModel;

/**
 * Created by Andrew Alcala on 6/13/2017.
 */
public class KeyboardPanel extends JPanel {

  // @TODO dont need to us public static everywhere
  public static final int NUM_WHITEKEYS = 70;
  public static final int NUM_BLACKKEYS = 50;
  public static final int BLKEY_WIDTH = 10;
  public static final int BLKEY_HEIGHT = 150;
  public static final int KEY_WIDTH = 20;
  public static final int KEY_HEIGHT = 300;
  ArrayList<ArrayList<Integer>> currentNotes;


  public KeyboardPanel(ArrayList<ArrayList<Integer>> currentNotes) {
    this.currentNotes = currentNotes;
    setMaximumSize(new Dimension(EditorPanel.BORDER_SHIFT * 2 + (KEY_WIDTH * NUM_WHITEKEYS),
            KEY_HEIGHT));

  }

  public void setCurrentNotes(ArrayList<ArrayList<Integer>> currentNotes) {
    this.currentNotes = currentNotes;
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    paintPianoKeys(g);
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(EditorPanel.BORDER_SHIFT + (NUM_WHITEKEYS * KEY_WIDTH) +
            EditorPanel.BORDER_SHIFT, KEY_HEIGHT);
  }

  private void paintPianoKeys(Graphics g) {
    ArrayList<Integer> currentPitches = new ArrayList<Integer>();
    for (ArrayList<Integer> note : currentNotes) {
      int pitch = note.get(3);
      currentPitches.add(pitch);
    }

    // generates 10 octaves of white keys
    g.setColor(Color.BLACK);

    int keyNumber = 24;
    int switchCounter = 1;

    for (int i = 0; i < NUM_WHITEKEYS; i++) {
      for (Integer pitch : currentPitches) {
        if (keyNumber == pitch) {

          g.setColor(Color.orange);
          g.fillRect((i * KEY_WIDTH) + EditorPanel.BORDER_SHIFT,
                  0, KEY_WIDTH, KEY_HEIGHT);
        }
      }
      g.setColor(Color.black);
      g.drawRect((i * KEY_WIDTH) + EditorPanel.BORDER_SHIFT,
              0, KEY_WIDTH, KEY_HEIGHT);


      if (switchCounter == 3 || switchCounter == 7) { // may be 3/6 or 2/7
        keyNumber += 1;
        if (switchCounter == 7) {
          switchCounter = 1;
        } else {
          switchCounter += 1;
        }
      } else {
        keyNumber += 2;
        switchCounter += 1;
      }
    }


    g.setColor(Color.BLACK);

    // generates black keys
    int blackKeyNumber = 25;
    int blackSwitchCounter = 1;
    for (int i = 1; i <= NUM_WHITEKEYS; i++) {
      if (blackSwitchCounter != 3 && blackSwitchCounter != 7) {

        g.setColor(Color.BLACK);
        g.fillRect(i * KEY_WIDTH - (BLKEY_WIDTH / 2) + EditorPanel.BORDER_SHIFT,
                0,
                BLKEY_WIDTH,
                BLKEY_HEIGHT);
        for (Integer pitch : currentPitches) {
          if (blackKeyNumber == pitch) {
            g.setColor(Color.orange);
            g.fillRect(i * KEY_WIDTH - (BLKEY_WIDTH / 2) + EditorPanel.BORDER_SHIFT,
                    0,
                    BLKEY_WIDTH,
                    BLKEY_HEIGHT);
          }
        }
        g.setColor(Color.black);
        g.drawRect(i * KEY_WIDTH - (BLKEY_WIDTH / 2) + EditorPanel.BORDER_SHIFT,
                0,
                BLKEY_WIDTH,
                BLKEY_HEIGHT);
      }

      if (blackSwitchCounter == 3 || blackSwitchCounter == 7) { // may be 3/6 or 2/7
        blackKeyNumber += 1;
        if (blackSwitchCounter == 7) {
          blackSwitchCounter = 1;
        } else {
          blackSwitchCounter += 1;
        }
      } else {
        blackKeyNumber += 2;
        blackSwitchCounter += 1;
      }
    }
  }


}
