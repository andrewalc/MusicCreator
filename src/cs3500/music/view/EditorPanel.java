package cs3500.music.view;

import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import javax.swing.JPanel;
import javax.swing.plaf.synth.ColorType;

import cs3500.music.model.Tones;


/**
 * Class for the editor panel. All painting methods account for scrolling and have and if
 * statement dealing with the proper shift should the startingbeat be over the defined
 * START_SCROLLING_AT_BEAT threshold in the VisualView.
 * Edit: Added paintCovers to render sidebars to the player.
 */
public class EditorPanel extends JPanel {

  // Constants
  public static final int TOP_SCREEN_SHIFT = 100;
  public static final int BORDER_SHIFT = 70;
  public static final int PITCH_MIDI_GAP = 80;
  public static final int BEAT_UNIT_LENGTH = 20;
  public static final int MEASURE_WIDTH = BEAT_UNIT_LENGTH * 4;
  // private fields
  private int rowHeight;
  private int fontSize;
  private int rowWidth;
  private int numOfMeasures;
  private int currentBeat;
  private int maxBeats;
  private boolean practiceMode = false;
  private ArrayList<String> pitchStrings = new ArrayList<String>();
  private Map<Integer, ArrayList<ArrayList<Integer>>> notes;
  private Color textColor = new Color(220, 223, 224);
  private Color linesColor = new Color(0, 0, 0);
  private Color noteHeadColor = Color.orange;
  private Color noteBody = new Color(165, 144, 0);
  private Color gridBackgroundColor = new Color(71, 71, 71);
  private Map<Integer, Integer> repeatPairs;
  private Color singletonRepeat = new Color(255, 255, 255);
  private ArrayList<Color> repeatColors =
          new ArrayList<>(Arrays.asList(Color.CYAN, Color.MAGENTA, Color.YELLOW, Color. GREEN));

  /**
   * Constructor for an EditorPanel. Requires the input of a Map of Integer MIDI pitches to
   * Arraylist of Arraylist of Integer where Arraylist of Integer is the note format stated in
   * the IMusicEditorModel and IMusicEditorView interface.
   *
   * @param notes    Map of all notes to be displayed in the editor.
   * @param maxBeats Integer value of the last possible beat in the Map of notes.
   */
  public EditorPanel(Map<Integer, ArrayList<ArrayList<Integer>>> notes, int maxBeats,
                     Map<Integer, Integer> repeatPairs) {
    this.currentBeat = 0;
    this.maxBeats = maxBeats;
    this.rowWidth = (this.maxBeats * BEAT_UNIT_LENGTH);
    this.numOfMeasures = rowWidth / MEASURE_WIDTH;
    this.notes = notes;
    this.repeatPairs = repeatPairs;
    generatePitchStrings();
    setFontAndRowHeight();
    setBackground(VisualView.BACKGROUND_COLOR);


  }

  /**
   * Helper method that sets the row height and font size, scaled based on the number of pitches
   * being observed on the screen currently.
   */
  private void setFontAndRowHeight() {
    // Row height and font scaling for bigger sets of notes.
    if (pitchStrings.size() > 25) {
      this.fontSize = 13;
      this.rowHeight = 13;
    } else {
      this.fontSize = 20;
      this.rowHeight = 20;
    }
  }

  /**
   * A Helper method that generates all of the labels for the pitches along the left side of the
   * editor panel.
   */
  private void generatePitchStrings() {
    pitchStrings = new ArrayList<>();
    // Generate list of pitch strings
    if (this.getNumberOfNotes() > 0) {
      int currPitch = this.getHighestPitch();
      int lowest = this.getLowestPitch();
      while (true) {
        this.pitchStrings.add(this.convertIntPitchToStringPitch(currPitch));
        if (currPitch == lowest) {
          break;
        } else {
          currPitch -= 1;
        }
      }
    }
  }

  /**
   * Returns the total number of notes contained in the editor's Map of notes.
   *
   * @return Number of notes in the editor.
   */
  private int getNumberOfNotes() {
    int count = 0;
    for (ArrayList<ArrayList<Integer>> pitchBucket : notes.values()) {
      count += pitchBucket.size();
    }
    return count;
  }

  /**
   * Returns the MIDI pitch integer value for the lowest pitch played by a note in the editor.
   *
   * @return MIDI pitch int of the lowest pitch found in the editor.
   */
  private int getLowestPitch() {
    ArrayList<Integer> pitches = new ArrayList<Integer>(this.notes.keySet());
    if (pitches.size() > 0) {
      return pitches.get(0);
    } else {
      throw new IllegalArgumentException("There are no pitches in the editor.");
    }
  }

  /**
   * Returns the MIDI pitch integer value for the highest pitch played by a note in the editor.
   *
   * @return MIDI pitch int of the highest pitch found in the editor.
   */
  private int getHighestPitch() {
    ArrayList<Integer> pitches = new ArrayList<Integer>(this.notes.keySet());
    if (pitches.size() > 0) {
      return pitches.get(pitches.size() - 1);
    } else {
      throw new IllegalArgumentException("There are no pitches in the editor.");
    }
  }

  /**
   * Setter for the editor's current beat.
   */
  public void setCurrentBeat(int currentBeat) {
    this.currentBeat = currentBeat;
  }


  @Override
  public Dimension getPreferredSize() {
    return new Dimension(Integer.MAX_VALUE,
            TOP_SCREEN_SHIFT + (rowHeight * pitchStrings.size()));
  }

  /**
   * Converts a MIDI pitch to a string.
   *
   * @param pitch the MIDI pitch to convert.
   * @return A String representing the pitch.
   */
  private String convertIntPitchToStringPitch(int pitch) {
    int numTones = Tones.values().length;
    return Tones.getToneAtToneVal(pitch % numTones).toString() + ((pitch / numTones) - 1);
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (rowWidth > 0) {
      paintMeasureNumbers(g);
      paintRows(g);
      paintRepeatPairs(g);
      paintPlayLine(g);
      paintMeasureNumbers(g);
      paintCovers(g);
      paintRowPitches(g);
    }
  }

  private void paintRepeatPairs(Graphics g) {

    Graphics2D g2d = (Graphics2D) g.create();
    g2d.setStroke(new BasicStroke(5));
    for(Integer keyEndingBeat: this.repeatPairs.keySet()){
      if(keyEndingBeat == -1){
        g2d.setColor(singletonRepeat);
      }
      else{
        g2d.setColor(Color.GREEN);//this.repeatColors.get(new Random().nextInt(this.repeatColors.size() - 1)));
      }
      int startingBeatCandidate = this.repeatPairs.get(keyEndingBeat);
      //draw the starting beat repeat line
      g2d.drawLine(BORDER_SHIFT + PITCH_MIDI_GAP + (startingBeatCandidate * BEAT_UNIT_LENGTH),
              TOP_SCREEN_SHIFT -
                      fontSize,
              BORDER_SHIFT + PITCH_MIDI_GAP + (startingBeatCandidate * BEAT_UNIT_LENGTH),
              TOP_SCREEN_SHIFT + (rowHeight * this.pitchStrings.size()) - fontSize);
      //draw the ending beat repeat line
      g2d.drawLine(BORDER_SHIFT + PITCH_MIDI_GAP + (keyEndingBeat * BEAT_UNIT_LENGTH),
              TOP_SCREEN_SHIFT -
                      fontSize,
              BORDER_SHIFT + PITCH_MIDI_GAP + (keyEndingBeat * BEAT_UNIT_LENGTH),
              TOP_SCREEN_SHIFT + (rowHeight * this.pitchStrings.size()) - fontSize);

    }

  }

  /**
   * Writes all of the Pitch labels along the left side of the editor panel.
   *
   * @param g the Graphics being used to generate the text objects being displayed on screen.
   */
  private void paintRowPitches(Graphics g) {
    g.setColor(textColor);

    int spacing = TOP_SCREEN_SHIFT;

    // Go through all pitches we need to render as rows.
    for (String currentPitch : this.pitchStrings) {
      if (currentBeat > VisualView.START_SCROLLING_AT_BEAT) {
        // generate the Pitch headers
        g.drawString(currentPitch, BORDER_SHIFT + ((currentBeat - VisualView
                .START_SCROLLING_AT_BEAT) * BEAT_UNIT_LENGTH), spacing);
        spacing += rowHeight;
      } else {
        // generate the Pitch headers
        g.drawString(currentPitch, BORDER_SHIFT, spacing);
        spacing += rowHeight;
      }
    }
  }


  /**
   * Draws the red play line at the current beat of the editor.
   *
   * @param g Graphics g
   */
  private void paintPlayLine(Graphics g) {
    g.setColor(Color.RED);
    Graphics2D g2d = (Graphics2D) g.create();
    g2d.setStroke(new BasicStroke(3));
    g2d.drawLine(BORDER_SHIFT + PITCH_MIDI_GAP + (this.currentBeat * BEAT_UNIT_LENGTH),
            TOP_SCREEN_SHIFT -
                    fontSize,
            BORDER_SHIFT + PITCH_MIDI_GAP + (this.currentBeat * BEAT_UNIT_LENGTH),
            TOP_SCREEN_SHIFT + (rowHeight * this.pitchStrings.size()) - fontSize);

  }


  /**
   * Draws the measure number markers that appear at the top of the editor.
   *
   * @param g Grpahics g
   */
  private void paintMeasureNumbers(Graphics g) {
    g.setColor(textColor);
    g.setFont(new Font("ComicSans", Font.BOLD, fontSize));
    for (int i = 0; i <= numOfMeasures; i++) {
      g.drawString("" + i * 4, BORDER_SHIFT + PITCH_MIDI_GAP + (i * MEASURE_WIDTH),
              TOP_SCREEN_SHIFT -
                      fontSize * 2);
    }
  }

  /**
   * Draws all rows of pitches in the editor. This includes drawing the pitch string on the left,
   * the pitch row rectangle itself, and drawing all notes contained within that pitch row.
   *
   * @param g Graphics g.
   */
  public void paintRows(Graphics g) {
    g.setColor(linesColor);
    g.setFont(new Font("ComicSans", Font.BOLD, fontSize));

    // Create a g2d graphics object
    Graphics2D g2d = (Graphics2D) g.create();
    g2d.setStroke(new BasicStroke(3));

    // Begin with spacing from the top screen shift, every row will then add a row height to spacing
    int spacing = TOP_SCREEN_SHIFT;

    // Go through all pitches we need to render as rows.
    for (String currentPitch : this.pitchStrings) {
      g2d.setColor(gridBackgroundColor);
      g2d.fillRect(BORDER_SHIFT + PITCH_MIDI_GAP, spacing - fontSize, rowWidth, rowHeight);

      // generate the Pitch headers
      //g.drawString(currentPitch, BORDER_SHIFT, spacing);

      for (Integer pitchKey : notes.keySet()) {
        if (convertIntPitchToStringPitch(pitchKey).equals(currentPitch)) {
          // Draw each note on this row.
          for (ArrayList<Integer> note : notes.get(pitchKey)) {
            int startingBeat = note.get(0);
            int endBeat = note.get(1);
            g2d.setColor(noteBody);

            g2d.fillRect(BORDER_SHIFT + PITCH_MIDI_GAP + (startingBeat *
                            BEAT_UNIT_LENGTH), spacing - fontSize, ((endBeat - startingBeat) +
                            1) * BEAT_UNIT_LENGTH,
                    rowHeight);
            g2d.setColor(noteHeadColor);
            g2d.fillRect(BORDER_SHIFT + PITCH_MIDI_GAP + (startingBeat *
                            BEAT_UNIT_LENGTH), spacing - fontSize, BEAT_UNIT_LENGTH,
                    rowHeight);
          }
        }
      }


      // draw the measure lines for this row
      int count = 0;
      int measureSpacing = MEASURE_WIDTH;
      while (count < numOfMeasures) {
        g2d.setColor(linesColor);
        g2d.drawLine(BORDER_SHIFT + PITCH_MIDI_GAP + measureSpacing, spacing -
                        fontSize,
                BORDER_SHIFT + PITCH_MIDI_GAP + measureSpacing, spacing - fontSize +
                        rowHeight);
        measureSpacing += MEASURE_WIDTH;
        count++;
      }
      g2d.setColor(linesColor);
      // draw the measure box for this row
      g2d.drawRect(BORDER_SHIFT + PITCH_MIDI_GAP, spacing - fontSize, rowWidth, rowHeight);

      // Once the row is done, our y spacing is now another row height down.
      spacing += rowHeight;
    }

  }

  /**
   * Updates the info within the editor panel, sending it the latest notes, maxBeats, rowWidth, and
   * number of measures. Then calls methods to redraw everything accordingly.
   *
   * @param sortedNotes All of the notes in the Piece.
   * @param maxBeats    the maximum number of beats in the Piece.
   */
  public void updateInfo(TreeMap<Integer, ArrayList<ArrayList<Integer>>> sortedNotes, int
          maxBeats, Map<Integer, Integer> repeatPairs) {
    this.notes = sortedNotes;
    this.maxBeats = maxBeats;
    this.rowWidth = (this.maxBeats * BEAT_UNIT_LENGTH);
    this.numOfMeasures = rowWidth / MEASURE_WIDTH;
    this.repeatPairs = repeatPairs;

    generatePitchStrings();
    setFontAndRowHeight();
    repaint();

  }

  /**
   * Paints the side panels in the editor panel to make things look cleaner and more uniform.
   *
   * @param g Graphics.
   */
  private void paintCovers(Graphics g) {
    int lineStroke = 1;
    int initialPositionFurthest = PITCH_MIDI_GAP + (BEAT_UNIT_LENGTH * 69) - 8;
    if (currentBeat > VisualView.START_SCROLLING_AT_BEAT) {
      g.setColor(VisualView.BACKGROUND_COLOR);
      g.fillRect(((currentBeat - VisualView.START_SCROLLING_AT_BEAT) * BEAT_UNIT_LENGTH),
              fontSize,
              BORDER_SHIFT + PITCH_MIDI_GAP - lineStroke,
              this.getHeight());
      g.fillRect(((currentBeat - VisualView.START_SCROLLING_AT_BEAT) * BEAT_UNIT_LENGTH) +
                      initialPositionFurthest, fontSize, 500,
              this.getHeight());
    } else {
      g.setColor(VisualView.BACKGROUND_COLOR);
      g.fillRect(0, fontSize, BORDER_SHIFT + PITCH_MIDI_GAP - lineStroke,
              this.getHeight());
      g.fillRect(initialPositionFurthest, fontSize, 500,
              this.getHeight());
    }
  }

  public void updateRepeatPairs(Map<Integer, Integer> repeatPairs) {
    this.repeatPairs = repeatPairs;
    repaint();
  }

  public void beginPracticeMode(){
    this.practiceMode = true;
    gridBackgroundColor = Color.blue;
    repaint();
  }

  public void endPracticeMode(){
    this.practiceMode = false;
    gridBackgroundColor = new Color(71, 71, 71);
    repaint();
  }
}
