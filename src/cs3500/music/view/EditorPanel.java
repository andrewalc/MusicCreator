package cs3500.music.view;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.*;

import cs3500.music.tests.Tones;


public class EditorPanel extends JPanel {


  public static final int TOP_SCREEN_SHIFT = 100;
  public static final int BORDER_SHIFT = 70;
  public static final int PITCH_MIDI_GAP = 80;
  private int rowHeight;
  private int fontSize;

  private static final int BEAT_UNIT_LENGTH = 20;
  private static final int MEASURE_WIDTH = BEAT_UNIT_LENGTH * 4;
  private int rowWidth;
  private int numOfMeasures;

  private int currentBeat;
  private ArrayList<String> pitchStrings = new ArrayList<String>();
  private Map<Integer, ArrayList<ArrayList<Integer>>> notes;


  private int getNumberOfNotes() {
    int count = 0;
    for (ArrayList<ArrayList<Integer>> pitchBucket : notes.values()) {
      count += pitchBucket.size();
    }
    return count;
  }

  private int getLowestPitch() {
    ArrayList<Integer> pitches = new ArrayList<Integer>(this.notes.keySet());
    if (pitches.size() > 0) {
      return pitches.get(0);
    } else {
      throw new IllegalArgumentException("There are no pitches in the editor.");
    }
  }

  private int getHighestPitch() {
    ArrayList<Integer> pitches = new ArrayList<Integer>(this.notes.keySet());
    if (pitches.size() > 0) {
      return pitches.get(pitches.size() - 1);
    } else {
      throw new IllegalArgumentException("There are no pitches in the editor.");
    }
  }

  public void setCurrentBeat(int currentBeat) {
    this.currentBeat = currentBeat;
  }


  public EditorPanel(Map<Integer, ArrayList<ArrayList<Integer>>> notes, int maxBeats) {
    this.rowWidth = maxBeats * BEAT_UNIT_LENGTH;
    this.numOfMeasures = rowWidth / MEASURE_WIDTH;
    this.notes = notes;
    this.currentBeat = 0;
    if (this.getNumberOfNotes() > 1) {
      // generate list of pitch strings
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
    System.out.println(pitchStrings.size());
    if (pitchStrings.size() > 25) {
      this.fontSize = 13;
      this.rowHeight = 13;
    } else {
      this.fontSize = 20;
      this.rowHeight = 20;
    }

  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(BORDER_SHIFT + PITCH_MIDI_GAP + rowWidth + BORDER_SHIFT,
            TOP_SCREEN_SHIFT + (rowHeight * pitchStrings.size()));
  }

  private String convertIntPitchToStringPitch(int pitch) {
    int numTones = Tones.values().length;
    return Tones.getToneAtToneVal(pitch % numTones).toString() + ((pitch / numTones) - 1);
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    if(rowWidth > 0){
      paintMeasureNumbers(g);
      paintRows(g);
      paintPlayLine(g);
    }

  }

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


  private void paintMeasureNumbers(Graphics g) {
    g.setColor(Color.BLACK);
    g.setFont(new Font("ComicSans", Font.BOLD, fontSize));
    for(int i = 0; i <= numOfMeasures; i++){
      g.drawString(""+i*4, BORDER_SHIFT + PITCH_MIDI_GAP + (i*MEASURE_WIDTH), TOP_SCREEN_SHIFT -
              fontSize * 2);
    }
  }

  public void paintRows(Graphics g) {
    g.setColor(Color.BLACK);
    g.setFont(new Font("ComicSans", Font.BOLD, fontSize));
    Graphics2D g2d = (Graphics2D) g.create();
    g2d.setStroke(new BasicStroke(3));


    int spacing = TOP_SCREEN_SHIFT;
    for (String str : this.pitchStrings) {

      // generate the Pitch headers
      g.drawString(str, BORDER_SHIFT, spacing);

      for (Integer pitch : notes.keySet()) {

        if (convertIntPitchToStringPitch(pitch).equals(str)) {
          for (ArrayList<Integer> note : notes.get(pitch)) {
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
      spacing += rowHeight;
    }

  }
}
