package cs3500.music.view;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.swing.JPanel;


/**
 * JPanel representing a keyboard of octaves 1-10 as a set of white and black keys. Should a note
 * be in play at the current beat, the respective pitch that note is in will be rendered orange
 * on the keyboard.
 * All notes are in the Arraylist Integer format of:
 * (int startingBeat, int endBeat, int instrument, int pitch, int volume)
 * EDIT: Added a LinkedHashMap to contain the Rectangle area's the represent the piano keys
 * ("hitboxes"). Added methods to help tell what piano key was pressed and what pitch it was to
 * add to the composition.
 */
public class KeyboardPanel extends JPanel {

  // Constants
  public static final int NUM_WHKEYS = 70;
  public static final int WHKEY_WIDTH = 20;
  public static final int WHKEY_HEIGHT = 300;
  public static final int BLKEY_WIDTH = 10;
  public static final int BLKEY_HEIGHT = 150;

  // Contains keys of type Rectangle, representing the hitbox of a key on the piano. Each
  // rectangle corresponds to an Integer Midi pitch value.
  private LinkedHashMap<Rectangle, Integer> keyboardHitBoxes = new LinkedHashMap<>();

  // All notes that are playing at the current beat
  private ArrayList<ArrayList<Integer>> liveNotes;

  /**
   * Constructor for a KeyboardPanel. Requires a list of notes (in the Arraylist Integer format)
   * representing all notes being played at the current beat.
   *
   * @param liveNotes A list of notes representing all notes currently being played.
   */
  public KeyboardPanel(ArrayList<ArrayList<Integer>> liveNotes) {

    this.liveNotes = liveNotes;
    this.makeBlackPianoKeyHitBoxes();
    this.makeWhitePianoKeyHitBoxes();
    setBackground(VisualView.BACKGROUND_COLOR.darker().darker());
  }


  /**
   * Sets the arraylist of currently playing notes to the given Arraylist of notes.
   *
   * @param liveNotes The list of all notes currently playing.
   */
  public void setNotes(ArrayList<ArrayList<Integer>> liveNotes) {
    this.liveNotes = liveNotes;
    repaint();
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    paintPianoKeys(g);
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(EditorPanel.BORDER_SHIFT + (NUM_WHKEYS * WHKEY_WIDTH) +
            EditorPanel.BORDER_SHIFT + EditorPanel.BORDER_SHIFT, WHKEY_HEIGHT);
  }

  /**
   * Draws the visual keyboard with all keys in octaves 1-10.
   *
   * @param g Graphics g
   */
  private void paintPianoKeys(Graphics g) {
    g.setColor(Color.BLACK);

    // Look at all notes and develop a list of pitches currently being played.
    ArrayList<Integer> rangeOfPitches = new ArrayList<Integer>();
    for (ArrayList<Integer> note : liveNotes) {
      int pitch = note.get(3);
      rangeOfPitches.add(pitch);
    }
    drawWhiteKeys(g, rangeOfPitches);
    drawBlackKeys(g, rangeOfPitches);
  }

  /**
   * Generates all of the hitboxes of the black keys in the Piano panel, and sends them to the
   * keyboardHitBoxes map.
   */
  private void makeBlackPianoKeyHitBoxes() {
    // blackKeyNumber represents the MIDI pitch of the key currently being drawn. Should be
    // incremented by 2 each iteration, except when there is no next black key. In that case,
    // keyNumber increments by 1.
    int blackKeyNumber = 25;
    // Counter for sets of keys developed. If it hits 3 or 7, then there is no black key
    // so the keyNumber will be incremented by 1 instead. Once it hits 7, reset to 1.
    int blackSwitchCounter = 1;

    // Generates 10 octaves of black keys.
    for (int i = 1; i <= NUM_WHKEYS; i++) {
      if (blackSwitchCounter != 3 && blackSwitchCounter != 7) {
        // Put a hitbox of the key in our hitbox map..
        keyboardHitBoxes.put(new Rectangle(i * WHKEY_WIDTH - (BLKEY_WIDTH / 2) + EditorPanel
                .BORDER_SHIFT,
                0,
                BLKEY_WIDTH,
                BLKEY_HEIGHT), blackKeyNumber);
      }

      // Manage blackKeyNumber incrementation behavior using the switch counter.
      if (blackSwitchCounter == 3 || blackSwitchCounter == 7) {
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

  /**
   * Generates all of the hitboxes of the white keys in the Piano panel, and sends them to the
   * keyboardHitBoxes map.
   */
  private void makeWhitePianoKeyHitBoxes() {
    // keyNumber represents the MIDI pitch of the key currently being drawn. Should be incremented
    // by 2 each iteration, except when there is no next black key. In that case, keyNumber
    // increments by 1.
    int keyNumber = 24;
    // Counter for sets of keys developed. If it hits 3 or 7, then there is no attached black key
    // so the keyNumber will be incremented by 1 instead. Once it hits 7, reset to 1.
    int switchCounter = 1;

    // Generate 10 octaves of white keys.
    for (int i = 0; i < NUM_WHKEYS; i++) {

      // Put a hitbox of the key in our hitbox map..
      keyboardHitBoxes.put(new Rectangle((i * WHKEY_WIDTH) + EditorPanel.BORDER_SHIFT,
              0, WHKEY_WIDTH, WHKEY_HEIGHT), keyNumber);
      // Manage keyNumber incrementation behavior using the switch counter.
      if (switchCounter == 3 || switchCounter == 7) {
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
  }


  /**
   * Draws all white keys on this panel's keyboard.
   *
   * @param g              Graphics g.
   * @param rangeOfPitches List of MIDI pitches currently being played.
   */
  private void drawWhiteKeys(Graphics g, ArrayList<Integer> rangeOfPitches) {
    // keyNumber represents the MIDI pitch of the key currently being drawn. Should be incremented
    // by 2 each iteration, except when there is no next black key. In that case, keyNumber
    // increments by 1.
    int keyNumber = 24;
    // Counter for sets of keys developed. If it hits 3 or 7, then there is no attached black key
    // so the keyNumber will be incremented by 1 instead. Once it hits 7, reset to 1.
    int switchCounter = 1;

    // Generate 10 octaves of white keys.
    for (int i = 0; i < NUM_WHKEYS; i++) {
      // Render the white color of a key.
      g.setColor(Color.WHITE);
      g.fillRect((i * WHKEY_WIDTH) + EditorPanel.BORDER_SHIFT,
              0, WHKEY_WIDTH, WHKEY_HEIGHT);
      for (Integer pitch : rangeOfPitches) {
        // If this keyNumber is a pitch that is being played, render the key orange.
        if (keyNumber == pitch) {
          g.setColor(Color.orange);
          g.fillRect((i * WHKEY_WIDTH) + EditorPanel.BORDER_SHIFT,
                  0, WHKEY_WIDTH, WHKEY_HEIGHT);
        }
      }
      // Render the black outline of a key.
      g.setColor(Color.black);
      g.drawRect((i * WHKEY_WIDTH) + EditorPanel.BORDER_SHIFT,
              0, WHKEY_WIDTH, WHKEY_HEIGHT);

      // Manage keyNumber incrementation behavior using the switch counter.
      if (switchCounter == 3 || switchCounter == 7) {
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
  }

  /**
   * Draws all black keys on this panel's keyboard.
   *
   * @param g              Graphics g.
   * @param rangeOfPitches List of MIDI pitches currently being played.
   */
  private void drawBlackKeys(Graphics g, ArrayList<Integer> rangeOfPitches) {
    // blackKeyNumber represents the MIDI pitch of the key currently being drawn. Should be
    // incremented by 2 each iteration, except when there is no next black key. In that case,
    // keyNumber increments by 1.
    int blackKeyNumber = 25;
    // Counter for sets of keys developed. If it hits 3 or 7, then there is no black key
    // so the keyNumber will be incremented by 1 instead. Once it hits 7, reset to 1.
    int blackSwitchCounter = 1;

    // Generates 10 octaves of black keys.
    for (int i = 1; i <= NUM_WHKEYS; i++) {
      if (blackSwitchCounter != 3 && blackSwitchCounter != 7) {
        // Draw a black key
        g.setColor(Color.BLACK);
        g.fillRect(i * WHKEY_WIDTH - (BLKEY_WIDTH / 2) + EditorPanel.BORDER_SHIFT,
                0,
                BLKEY_WIDTH,
                BLKEY_HEIGHT);
        for (Integer pitch : rangeOfPitches) {
          // if this key is being played draw an orange key.
          if (blackKeyNumber == pitch) {
            g.setColor(Color.orange);
            g.fillRect(i * WHKEY_WIDTH - (BLKEY_WIDTH / 2) + EditorPanel.BORDER_SHIFT,
                    0,
                    BLKEY_WIDTH,
                    BLKEY_HEIGHT);
          }
        }
        // Draw a black outline of the black key.
        g.setColor(Color.black);
        g.drawRect(i * WHKEY_WIDTH - (BLKEY_WIDTH / 2) + EditorPanel.BORDER_SHIFT,
                0,
                BLKEY_WIDTH,
                BLKEY_HEIGHT);
      }

      // Manage blackKeyNumber incrementation behavior using the switch counter.
      if (blackSwitchCounter == 3 || blackSwitchCounter == 7) {
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

  /**
   * Looks at the click location of the user's mouse, and returns the pitch value of the piano
   * key that was clicked on.
   *
   * @return the integer Pitch value of the key that was clicked on by the user.
   * @throws IllegalArgumentException if the click location is out of bounds.
   */
  public int getPianoKeyPressed() throws IllegalArgumentException {
    Point clickLocation = this.getMousePosition();
    for (Rectangle rectangle : keyboardHitBoxes.keySet()) {
      if (rectangle.contains(clickLocation)) {
        return keyboardHitBoxes.get(rectangle);
      }
    }
    throw new IllegalArgumentException("Click location is not ontop of a key");
  }

  /**
   * Updates the keyboard panel so that it knows all of the notes at a given beat.
   *
   * @param notesAtBeat An arraylist of all of the notes at a given beat.
   */
  public void updateInfo(ArrayList<ArrayList<Integer>> notesAtBeat) {
    setNotes(notesAtBeat);
  }
}
