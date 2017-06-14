package cs3500.music.view;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

import cs3500.music.model.IMusicEditorModel;

/**
 * Created by Andrew Alcala on 6/13/2017.
 */
public class KeyboardPanel extends JPanel {

  public static final int NUM_WHITEKEYS = 70;
  public static final int BLKEY_WIDTH = 10;
  public static final int BLKEY_HEIGHT = 150;
  public static final int KEY_WIDTH = 20;
  public static final int KEY_HEIGHT = 300;

  public KeyboardPanel(IMusicEditorModel model) {
    setMaximumSize(new Dimension(EditorPanel.BORDER_SHIFT* 2 + (KEY_WIDTH * NUM_WHITEKEYS),
            KEY_HEIGHT));

  }

  @Override
  public void paintComponent(Graphics g){
    super.paintComponent(g);
    paintPianoKeys(g);
  }
  
  private void paintPianoKeys(Graphics g) {
    // generates 10 octaves of white keys
    g.setColor(Color.BLACK);
    for (int i = 0; i < NUM_WHITEKEYS; i++) {
      g.drawRect((i * KEY_WIDTH) + EditorPanel.BORDER_SHIFT,
              0, KEY_WIDTH, KEY_HEIGHT);
    }
    g.setColor(Color.BLACK);

    // generates the double set of black keys
    int numBlackKey = 1;
    for (int i = 0; i < NUM_WHITEKEYS / 2; i++) {
      if (numBlackKey >= NUM_WHITEKEYS) {
        break;
      } else {
        g.fillRect(numBlackKey * KEY_WIDTH - (BLKEY_WIDTH / 2) + EditorPanel.BORDER_SHIFT,
                0,
                BLKEY_WIDTH,
                BLKEY_HEIGHT);
        numBlackKey++;
        g.fillRect(numBlackKey * KEY_WIDTH - (BLKEY_WIDTH / 2) + EditorPanel.BORDER_SHIFT, 0,
                BLKEY_WIDTH,
                BLKEY_HEIGHT);
        numBlackKey += 6;
      }

    }

    // generates the triple set of black keys
    numBlackKey = 4;
    for (int i = 0; i < NUM_WHITEKEYS / 3; i++) {
      if (numBlackKey >= NUM_WHITEKEYS) {
        break;
      } else {
        g.fillRect(numBlackKey * KEY_WIDTH - (BLKEY_WIDTH / 2) + EditorPanel.BORDER_SHIFT, 0,
                BLKEY_WIDTH,
                BLKEY_HEIGHT);
        numBlackKey++;
        g.fillRect(numBlackKey * KEY_WIDTH - (BLKEY_WIDTH / 2) + EditorPanel.BORDER_SHIFT, 0,
                BLKEY_WIDTH,
                BLKEY_HEIGHT);
        numBlackKey++;
        g.fillRect(numBlackKey * KEY_WIDTH - (BLKEY_WIDTH / 2) + EditorPanel.BORDER_SHIFT, 0,
                BLKEY_WIDTH,
                BLKEY_HEIGHT);
        numBlackKey += 5;
      }
    }
  }

}
