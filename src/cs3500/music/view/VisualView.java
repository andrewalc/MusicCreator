package cs3500.music.view;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;


import javax.swing.*;
import javax.swing.text.DefaultCaret;

import cs3500.music.controller.KeyboardListener;
import cs3500.music.controller.PianoMouseListener;

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
 * At the bottom of the window, a keyboard representing octaves 1-10 will be displayed as a set
 * of white and black keys. Should a note be in play at the current beat, the respective pitch
 * that note is in will be rendered orange on the keyboard.
 * All notes are in the Arraylist Integer format of:
 * (int startingBeat, int endBeat, int instrument, int pitch, int volume)
 * EDIT: Updated to support new interface methods needed for functionality.
 * IMPORTANT= The definition of maxbeats in visual view is +1 the actual maxbeats of a midiview,
 * this is to allow new notes to be added to the beat immediately following the last midi beat.
 * Without +1, a note will be added but the midi's length does not change.
 */
public class VisualView extends JFrame implements IMusicEditorView {


  private int currentBeat;
  private int maxBeats;
  private EditorPanel editorPanel;
  private KeyboardPanel keyboardPanel;
  private JScrollPane editorPanelScrolling;
  private JPanel container;
  private Map<Integer, ArrayList<ArrayList<Integer>>> notes;
  public static final int START_SCROLLING_AT_BEAT = 16;


  /**
   * Constructor for a VisualView of a Music Editor. Require input of note information in the
   * form of an a Map of Integer to Arraylist Arraylistof integers, with each Integer key
   * representing a note's MIDI pitch int and each value being the list of notes of that pitch,
   * following the ArrayList of integer pattern of notes being represented as (int startingBeat,
   * int endBeat, int instrument, int pitch, int volume). Also requires an int representing the
   * last beat ever played in the map of music.
   *  @param notes    A Map of all notes from a piece of music.
   *
   */
  public VisualView(Map<Integer, ArrayList<ArrayList<Integer>>> notes) {
    super("MIDI Music Editor");
    // JFrame Settings
    this.notes = notes;
    this.currentBeat = 0;
    // IMPORTANT TO PRETEND IT IS 1 BEAT LONGER SEE TOP JAVA DOC.
    this.maxBeats = this.getMaxBeatFromNotes() + 1;
    setSize(1600, 900);
    setResizable(false);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setBackground(Color.WHITE);


    // Copy the Map of notes.
    TreeMap<Integer, ArrayList<ArrayList<Integer>>> sortedNotes = new TreeMap<>(new
            IntegerComparator());
    sortedNotes.putAll(notes);



    // Two panels
    keyboardPanel = new KeyboardPanel(this.getNotesAtBeat(notes, currentBeat));
    editorPanel = new EditorPanel(sortedNotes, maxBeats);

    // Create a container to combine both JPanels, set the layout.
    container = new JPanel();
    container.setLayout(null);
    container.setBounds(0, 0, 1600, 900);

    container.setBackground(Color.WHITE);
    //container.setPreferredSize(new Dimension(1600, 900));

    //container.setMinimumSize(editorPanel.getPreferredSize());
    GridBagConstraints c = new GridBagConstraints();


    //editorPanelScrolling.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

    // Add the editorPanel to the container in the correct location with constraints.
//    c.anchor = PAGE_START;
//    c.fill = HORIZONTAL;
//    c.gridx = 0;
//    c.gridy = 0;
//    c.ipadx = 0;//(int) editorPanel.getMinimumSize().getWidth();
//    c.ipady = 0;
    editorPanel.setVisible(true);
    Insets editorPanelInsets = editorPanel.getInsets();
    Dimension editorPanelSize = editorPanel.getPreferredSize();
    editorPanel.setBounds(0, 0, editorPanelSize.width,
            editorPanelSize.height);

    // Enable scroll bars.
    editorPanelScrolling = new JScrollPane();
    editorPanelScrolling.getViewport().setOpaque(false);
    editorPanelScrolling.setBorder(null);
    editorPanelScrolling.setViewportView(editorPanel);
    editorPanelScrolling.setVisible(true);
    editorPanelScrolling.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    editorPanelScrolling.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    editorPanelScrolling.setBounds(0, 0, 1595, 550);
    editorPanelScrolling.getVerticalScrollBar().getValue();
    System.out.println(editorPanelScrolling.getVerticalScrollBar().getMaximum());

    container.add(editorPanelScrolling);

    Dimension keyBoardPanelSize = keyboardPanel.getPreferredSize();
    keyboardPanel.setBounds(0, 570, keyBoardPanelSize.width, keyBoardPanelSize.height);
    container.add(keyboardPanel);



    // Add the keyboardPanel to the container in the correct location.
//    c.anchor = LAST_LINE_END;
//    c.gridx = 0;
//    c.gridy = 1;
//    c.ipady = (int) (keyboardPanel.getPreferredSize().getHeight());
//    c.insets = new Insets(20, 0, 100, 0);  //top padding
    //container.add(keyboardPanel);
    container.setFocusable(true);
    container.requestFocusInWindow();
    // Add the combined Panels to the JFrame
    //container.setLocation(0,0);
    getContentPane().add(container);
    revalidate();

  }

  @Override
  public int getMaxBeat() {
    return maxBeats;
  }

  public int getMaxBeatFromNotes() {
    int potentialMaxBeats = 0;
    for (ArrayList<ArrayList<Integer>> pitchList : this.notes.values()) {
      for (ArrayList<Integer> note : pitchList) {
        // if a note's ending beat is larger than the current potential last beat, update potential.
        int endBeat = note.get(1);
        if (endBeat > potentialMaxBeats) {
          potentialMaxBeats = endBeat;
        }
      }
    }
    return potentialMaxBeats;
  }

  /**
   * Getter for current beat.
   *
   * @return the current beat.
   */
  @Override
  public int getCurrentBeat() {
    return currentBeat;
  }

  @Override
  public void updateCurrentBeat() {

  }


  @Override
  public void setCurrentBeat(int currentBeat) {

    if (currentBeat > START_SCROLLING_AT_BEAT) {
      editorPanel.setLocation((-1 * (currentBeat - START_SCROLLING_AT_BEAT) * EditorPanel
                      .BEAT_UNIT_LENGTH),
              editorPanel.getY());
    } else {
      editorPanel.setLocation(0, 0);
    }
    System.out.println(editorPanel.getLocation());

    this.currentBeat = currentBeat;
    editorPanel.setCurrentBeat(this.currentBeat);
    keyboardPanel.setNotes(getNotesAtBeat(notes, currentBeat));
    revalidate();
    repaint();
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

  @Override
  public void addKeyListener(KeyboardListener keyboardListener) {
    container.addKeyListener(keyboardListener);
  }

  @Override
  public void forwardOneBeat() {
    if (currentBeat + 1 <= maxBeats) {
      setCurrentBeat(currentBeat + 1);
    }
  }

  @Override
  public void backOneBeat() {
    if (currentBeat - 1 >= 0) {
      setCurrentBeat(currentBeat - 1);
    }
  }

  @Override
  public void startMusic() {

  }

  @Override
  public void pauseMusic() {

  }

  @Override
  public void goToBeginning() {
    this.setCurrentBeat(0);
  }

  @Override
  public void goToEnd() {

    this.setCurrentBeat(this.getMaxBeat());
  }

  @Override
  public boolean isActive() {
    return this.isVisible();
  }

  @Override
  public boolean isPlayingMusic() {
    return false;
  }

  @Override
  public void addMouseListener(PianoMouseListener mouseListener) {
    keyboardPanel.addMouseListener(mouseListener);
  }

  @Override
  public int getKeyboardKeyPressed() {
    return keyboardPanel.getKeyboardKeyPressed();
  }

  @Override
  public void updateVisView(Map<Integer, ArrayList<ArrayList<Integer>>> allNotes) {


    this.notes = allNotes;
    this.maxBeats = getMaxBeatFromNotes() + 1; // IMPORTANT TO PRETEND IT IS 1 BEAT LONGER SEE TOP.

    // Copy the Map of notes.
    TreeMap<Integer, ArrayList<ArrayList<Integer>>> sortedNotes = new TreeMap<>(new
            IntegerComparator());
    sortedNotes.putAll(notes);
    keyboardPanel.updateInfo(this.getNotesAtBeat(notes, currentBeat));
    editorPanel.updateInfo(sortedNotes, maxBeats);

    repaint();
  }

  @Override
  public void rebuildMusic(Map<Integer, ArrayList<ArrayList<Integer>>> allNotes) {

  }

}
