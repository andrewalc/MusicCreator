package cs3500.music.view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JPanel;

import cs3500.music.model.Tones;


public class EditorPanel extends JPanel {


  public static final int TOP_SCREEN_SHIFT = 100;
  public static final int BORDER_SHIFT = 70;
  public static final int PITCH_MIDI_GAP = 80;
  private static final int BEAT_UNIT_LENGTH = 20;
  private static final int MEASURE_WIDTH = BEAT_UNIT_LENGTH * 4;
  private int rowHeight;
  private int fontSize;
  private int rowWidth;
  private int numOfMeasures;
  private int currentBeat;
  private ArrayList<String> pitchStrings = new ArrayList<String>();
  private Map<Integer, ArrayList<ArrayList<Integer>>> notes;

  /**
   * Constructor for an EditorPanel. Requires the input of a Map of Integer MIDI pitches to
   * Arraylist of Arraylist of Integer where Arraylist of Integer is the note format stated in
   * the IMusicEditorModel and IMusicEditorView interface.
   *
   * @param notes    Map of all notes to be displayed in the editor.
   * @param maxBeats Integer value of the last possible beat in the Map of notes.
   */
  public EditorPanel(Map<Integer, ArrayList<ArrayList<Integer>>> notes, int maxBeats) {
    this.currentBeat = 0;
    this.rowWidth = maxBeats * BEAT_UNIT_LENGTH;
    this.numOfMeasures = rowWidth / MEASURE_WIDTH;
    this.notes = notes;

    // Generate list of pitch strings
    if (this.getNumberOfNotes() > 1) {
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
    return new Dimension(BORDER_SHIFT + PITCH_MIDI_GAP + rowWidth + BORDER_SHIFT,
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
      paintPlayLine(g);
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
    g.setColor(Color.BLACK);
  }


  /**
   * Draws the measure number markers that appear at the top of the editor.
   *
   * @param g Grpahics g
   */
  private void paintMeasureNumbers(Graphics g) {
    g.setColor(Color.BLACK);
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
    g.setColor(Color.BLACK);
    g.setFont(new Font("ComicSans", Font.BOLD, fontSize));

    // Create a g2d graphics object
    Graphics2D g2d = (Graphics2D) g.create();
    g2d.setStroke(new BasicStroke(3));

    // Begin with spacing from the top screen shift, every row will then add a row height to spacing
    int spacing = TOP_SCREEN_SHIFT;

    // Go through all pitches we need to render as rows.
    for (String currentPitch : this.pitchStrings) {

      // generate the Pitch headers
      g.drawString(currentPitch, BORDER_SHIFT, spacing);

      for (Integer pitchKey : notes.keySet()) {
        if (convertIntPitchToStringPitch(pitchKey).equals(currentPitch)) {
          // Draw each note on this row.
          for (ArrayList<Integer> note : notes.get(pitchKey)) {
            int startingBeat = note.get(0);
            int endBeat = note.get(1);
            g2d.setColor(Color.GREEN);

            g2d.fillRect(BORDER_SHIFT + PITCH_MIDI_GAP + (startingBeat *
                            BEAT_UNIT_LENGTH), spacing - fontSize, ((endBeat - startingBeat) +
                            1) * BEAT_UNIT_LENGTH,
                    rowHeight);
            g2d.setColor(Color.BLACK);
            g2d.fillRect(BORDER_SHIFT + PITCH_MIDI_GAP + startingBeat *
                            BEAT_UNIT_LENGTH, spacing - fontSize, BEAT_UNIT_LENGTH,
                    rowHeight);
            g2d.setColor(Color.GREEN);

          }
        }
      }


      // draw the measure lines for this row
      int count = 0;
      int measureSpacing = MEASURE_WIDTH;
      while (count < numOfMeasures) {
        g2d.setColor(Color.BLACK);
        g2d.drawLine(BORDER_SHIFT + PITCH_MIDI_GAP + measureSpacing, spacing -
                        fontSize,
                BORDER_SHIFT + PITCH_MIDI_GAP + measureSpacing, spacing - fontSize +
                        rowHeight);
        measureSpacing += MEASURE_WIDTH;
        count++;
      }
      // draw the measure box for this row
      g2d.drawRect(BORDER_SHIFT + PITCH_MIDI_GAP, spacing - fontSize, rowWidth, rowHeight);

      // Once the row is done, our y spacing is now another row height down.
      spacing += rowHeight;
    }

  }
}
