package cs3500.music.view;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.*;

import cs3500.music.model.IMusicEditorModel;
import cs3500.music.model.Note;
import cs3500.music.model.Pitch;
import cs3500.music.model.Tones;


public class EditorPanel extends JPanel {


  public static final int TOP_SCREEN_SHIFT = 100;
  public static final int BORDER_SHIFT = 70;
  public static final int PITCH_MIDI_GAP = 80;
  public static final int ROW_HEIGHT = 30;
  public static final int FONT_SIZE = 20;

  private static final int BEAT_UNIT_LENGTH = 40;
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
    return pitches.get(0);
  }

  private int getHighestPitch() {
    ArrayList<Integer> pitches = new ArrayList<Integer>(this.notes.keySet());
    return pitches.get(pitches.size() - 1);
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
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(BORDER_SHIFT + PITCH_MIDI_GAP + rowWidth + BORDER_SHIFT,
            0);
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
                    FONT_SIZE,
            BORDER_SHIFT + PITCH_MIDI_GAP + (this.currentBeat * BEAT_UNIT_LENGTH),
            TOP_SCREEN_SHIFT + (ROW_HEIGHT * this.pitchStrings.size()) - FONT_SIZE);
    g.setColor(Color.BLACK);
  }


  private void paintMeasureNumbers(Graphics g) {
    g.setColor(Color.BLACK);
    g.setFont(new Font("ComicSans", Font.BOLD, FONT_SIZE));
    for(int i = 0; i <= numOfMeasures; i++){
      g.drawString(""+i*4, BORDER_SHIFT + PITCH_MIDI_GAP + (i*MEASURE_WIDTH), TOP_SCREEN_SHIFT -
              FONT_SIZE*2);
    }
  }

  public void paintRows(Graphics g) {
    g.setColor(Color.BLACK);
    g.setFont(new Font("ComicSans", Font.BOLD, FONT_SIZE));
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
                            BEAT_UNIT_LENGTH), spacing - FONT_SIZE, ((endBeat - startingBeat) +
                            1) * BEAT_UNIT_LENGTH,
                    ROW_HEIGHT);
            g2d.setColor(Color.BLACK);
            g2d.fillRect(BORDER_SHIFT + PITCH_MIDI_GAP + startingBeat *
                            BEAT_UNIT_LENGTH, spacing - FONT_SIZE, BEAT_UNIT_LENGTH,
                    ROW_HEIGHT);
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
                        FONT_SIZE,
                BORDER_SHIFT + PITCH_MIDI_GAP + measureSpacing, spacing - FONT_SIZE +
                        ROW_HEIGHT);
        measureSpacing += MEASURE_WIDTH;
        count++;
      }
      // draw the measure box for this row
      g2d.drawRect(BORDER_SHIFT + PITCH_MIDI_GAP, spacing - FONT_SIZE, rowWidth, ROW_HEIGHT);
      spacing += ROW_HEIGHT;
    }

  }
}
