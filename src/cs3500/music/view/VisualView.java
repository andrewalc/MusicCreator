package cs3500.music.view;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.*;

import static java.awt.GridBagConstraints.HORIZONTAL;
import static java.awt.GridBagConstraints.LAST_LINE_END;
import static java.awt.GridBagConstraints.PAGE_START;

/**
 * Implementation of an IMusicEditor Visual view. This view will produce a window that contains
 * all note information in rows of pitches within the pitch range of the of the highest and
 * lowest note. Note are represented as rectangles beginning at their marked starting pitch and
 * ending on their end beat. The rectangles will be black at the beginnning, representing the
 * starting beat, and will continue along a length of a green rectangle of its duration. A red
 * line will be drawn at the current beat, that is the beat that is currently being played.
 * Pressing the left and right arrow keys will advance the current beat and the red line by one
 * beat forward (right) or backward (left).
 * <p>
 * At the bottom of the window, a keyboard representing octaves 1-10 will be displayed as a set
 * of white and black keys. Should a note be in play at the current beat, the respective pitch
 * that note is in will be rendered orange on the keyboard.
 * All notes are in the Arraylist Integer format of:
 * (int startingBeat, int endBeat, int instrument, int pitch, int volume)
 */
public class VisualView extends JFrame implements IMusicEditorView {

  private int currentBeat;
  private int maxBeats;

  /**
   * Constructor for a VisualView of a Music Editor. Require input of note information in the
   * form of an a Map of Integer to Arraylist Arraylistof integers, with each Integer key
   * representing a note's MIDI pitch int and each value being the list of notes of that pitch,
   * following the ArrayList of integer pattern of notes being represented as (int startingBeat,
   * int endBeat, int instrument, int pitch, int volume). Also requires an int representing the
   * last beat ever played in the map of music.
   *
   * @param notes    A Map of all notes from a piece of music.
   * @param maxBeats Int representing the last beat ever played in the Map of all notes.
   */
  public VisualView(Map<Integer, ArrayList<ArrayList<Integer>>> notes, int maxBeats) {
    super("MIDI Music Editor");
    // JFrame Settings
    this.currentBeat = 0;
    this.maxBeats = maxBeats;
    setSize(1600, 900);
    setResizable(true);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setBackground(Color.WHITE);


    // Copy the Map of notes.
    TreeMap<Integer, ArrayList<ArrayList<Integer>>> sortedNotes = new TreeMap<>(new
            IntegerComparator());
    sortedNotes.putAll(notes);


    // Create a container to combine both JPanels, set the layout.
    JPanel container = new JPanel();
    container.setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();

    // Two panels
    KeyboardPanel keyboardPanel = new KeyboardPanel(this.getNotesAtBeat(notes, currentBeat));
    EditorPanel editorPanel = new EditorPanel(sortedNotes, maxBeats);


    // Enable scroll bars.
    JScrollPane editorPanelScrolling = new JScrollPane(editorPanel);
    editorPanelScrolling.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    editorPanelScrolling.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    editorPanelScrolling.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));

    // Add the editorPanel to the container in the correct location with constraints.
    c.anchor = PAGE_START;
    c.fill = HORIZONTAL;
    c.gridx = 0;
    c.gridy = 0;
    c.ipadx = (int) editorPanelScrolling.getPreferredSize().getWidth();
    c.ipady = (int) (getHeight() + 100 - (keyboardPanel.getPreferredSize().getHeight()));
    container.add(editorPanelScrolling, c);

    // Add the keyboardPanel to the container in the correct location.
    c.anchor = LAST_LINE_END;
    c.gridx = 0;
    c.gridy = 1;
    c.ipady = (int) (keyboardPanel.getPreferredSize().getHeight());
    c.insets = new Insets(20, 0, 100, 0);  //top padding
    container.add(keyboardPanel, c);
    container.setFocusable(true);
    container.requestFocusInWindow();
    // Add a keylistener to move the current beat when right or left is pressed.
    container.addKeyListener(new KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {
      }

      @Override
      public void keyPressed(KeyEvent e) {
        // If the user presses left.
        if (e.getKeyCode() == 37 && currentBeat > 0) {
          currentBeat--;
        }
        // If the user presses right.
        else if (e.getKeyCode() == 39 && currentBeat < maxBeats) {
          currentBeat++;
        }
        editorPanel.setCurrentBeat(currentBeat);
        keyboardPanel.setNotes(getNotesAtBeat(notes, currentBeat));
        repaint();
      }

      @Override
      public void keyReleased(KeyEvent e) {
      }
    });

    // Add the combined Panels to the JFrame
    getContentPane().add(container);
  }

  /**
   * Returns all notes that are playing at the given beat in the given Map. Notes must be
   * following the ArrayList of Integer format, represented as (int startingBeat,
   * int endBeat, int instrument, int pitch, int volume).
   *
   * @param notes The Map of all notes being played.
   * @param beat  The beat to fetch notes playing at.
   * @return An arraylist of notes playing at the given beat.
   * @throws IllegalArgumentException If the given beat is out of range, must be atleast 0 and not
   *                                  exceed maxBeats.
   */
  private ArrayList<ArrayList<Integer>> getNotesAtBeat(Map<Integer, ArrayList<ArrayList<Integer>>>
                                                               notes, int beat) throws
          IllegalArgumentException {
    if (beat < 0 || beat > maxBeats) {
      throw new IllegalArgumentException("beat must be within the beat bounds of the music piece.");
    }
    ArrayList<ArrayList<Integer>> notesAtBeat = new ArrayList<ArrayList<Integer>>();
    for (ArrayList<ArrayList<Integer>> pitchBucket : notes.values()) {
      for (ArrayList<Integer> note : pitchBucket) {
        int startingBeat = note.get(0);
        int endBeat = note.get(1);
        if (beat >= startingBeat && beat < endBeat + 1) {
          notesAtBeat.add(note);
        }
      }
    }
    return notesAtBeat;
  }

  @Override
  public void initialize() {
    this.setVisible(true);
  }
}