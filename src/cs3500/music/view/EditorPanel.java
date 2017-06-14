package cs3500.music.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.swing.*;

import cs3500.music.model.IMusicEditorModel;
import cs3500.music.model.Note;
import cs3500.music.model.Pitch;


/**
 * Created by Andrew Alcala on 6/12/2017.
 */

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
  private TreeMap<Pitch, ArrayList<Note>> notes;





  public EditorPanel(IMusicEditorModel model) {
    this.rowWidth = model.getPiece().getMaxBeats() * BEAT_UNIT_LENGTH;
    this.numOfMeasures = rowWidth / MEASURE_WIDTH;
    this.notes = model.getPiece().getAllNotes();
    this.currentBeat = currentBeat;
    if(model.getPiece().getNumberOfNotes() > 1){
      // generate list of pitch strings
      Pitch currPitch = model.getPiece().getHighestPitch();
      Pitch lowest = model.getPiece().getLowestPitch();
      while (true) {
        this.pitchStrings.add(currPitch.toString());
        if (currPitch.equals(lowest)) {
          break;
        } else {
          currPitch = currPitch.getPrevPitch();
        }
      }
    }
    setMaximumSize(new Dimension(BORDER_SHIFT + PITCH_MIDI_GAP + rowWidth + BORDER_SHIFT,
            TOP_SCREEN_SHIFT + (ROW_HEIGHT * 14)));
    setFocusable(true);
    requestFocusInWindow();

    addKeyListener(new KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {

      }

      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 37){

        }
      }

      @Override
      public void keyReleased(KeyEvent e) {

      }
    });
    revalidate();
  }

  public void addNotify() {
    super.addNotify();
    requestFocus();
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    if(rowWidth > 0){
      paintMeasureNumbers(g);
      paintRows(g, this.pitchStrings);
      paintPlayLine(g, this.currentBeat);
    }

  }

  private void paintPlayLine(Graphics g, int beatNumber) {
    g.setColor(Color.RED);
    Graphics2D g2d = (Graphics2D) g.create();
    g2d.setStroke(new BasicStroke(3));
    g2d.drawLine(BORDER_SHIFT+ PITCH_MIDI_GAP + (beatNumber * BEAT_UNIT_LENGTH), TOP_SCREEN_SHIFT -
                    FONT_SIZE,
            BORDER_SHIFT + PITCH_MIDI_GAP + (beatNumber * BEAT_UNIT_LENGTH),
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

  public void paintRows(Graphics g, ArrayList<String> contents) {
    g.setColor(Color.BLACK);
    g.setFont(new Font("ComicSans", Font.BOLD, FONT_SIZE));
    Graphics2D g2d = (Graphics2D) g.create();
    g2d.setStroke(new BasicStroke(3));


    int spacing = TOP_SCREEN_SHIFT;
    for (String str : contents) {

      // generate the Pitch headers
      g.drawString(str, BORDER_SHIFT, spacing);

      for(Pitch pitch : notes.keySet()){

        if(pitch.toString().equals(str)){
          for(Note note: notes.get(pitch)){
            g2d.setColor(Color.GREEN);

            g2d.fillRect(BORDER_SHIFT + PITCH_MIDI_GAP + (note.getStartingBeat() *
                    BEAT_UNIT_LENGTH), spacing - FONT_SIZE, note.getBeats() * BEAT_UNIT_LENGTH,
                    ROW_HEIGHT);
            g2d.setColor(Color.BLACK);
            g2d.fillRect(BORDER_SHIFT + PITCH_MIDI_GAP + note.getStartingBeat() *
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
