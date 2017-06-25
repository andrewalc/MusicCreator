package cs3500.music.tests;

import org.junit.Test;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

import cs3500.music.controller.PianoMouseListener;
import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.Tones;
import cs3500.music.view.MockCompositeView;

import static org.junit.Assert.assertEquals;

/**
 * Test class for PianoMouseListener.
 */
public class TestPianoMouseListener {
  MusicEditorModel.MusicEditorBuilder builder = new MusicEditorModel.MusicEditorBuilder();
  Appendable out = new StringBuilder();
  MockCompositeView view = new MockCompositeView(out);
  boolean audioUpdateNecessary;
  PianoMouseListener pml = new PianoMouseListener();
  private Tones holdTone;
  private int holdOctave;
  private int holdStartingBeat;
  JFrame frame = new JFrame();


  /**
   * Configures the mouse listener to the same commands as the mouse listener in the
   * controller.
   */
  private void configureMouseListener() {
    // Map of mouse buttons to runnables
    Map<Integer, Runnable> mousePresses = new HashMap<Integer, Runnable>();
    Map<Integer, Runnable> mouseReleases = new HashMap<Integer, Runnable>();


    mousePresses.put(MouseEvent.BUTTON1, () -> { //the contents of MakeCaps below
      try {
        this.out.append("Left mouse click detected\n");
      } catch (IOException e) {
        e.getMessage();
      }
      if(!view.isInPracticeMode()){
        try {
          view.getPianoKeyPressed();
          int beatDuration = 1;
          try {
            this.out.append("Adding note \n");
          } catch (IOException e) {
            e.printStackTrace();
          }
          if (view.getCurrentBeat() + beatDuration > view.getMaxBeat()) {
            view.rebuildMusic(null);
          }
          // Update the view with the added note.
          view.updateVisAddNotes(null);
          view.forwardOneBeat();
          // we added a note, so a change was made and will require rebuilding audio.
          audioUpdateNecessary = true;

        } catch (IllegalArgumentException e) {
          e.getMessage();
        }
      }
      else{
        int pitch = view.getPianoKeyPressed();
        view.practiceModeAddClickedPitch(pitch, null);
      }


    });

    mousePresses.put(MouseEvent.BUTTON3, () -> { //the contents of MakeCaps below
      try {
        this.out.append("Right mouse click detected\n");
      } catch (IOException e) {
        e.getMessage();
      }
      if(!view.isInPracticeMode()){
        try {

          int pitch = view.getPianoKeyPressed();
          int numTones = Tones.values().length;
          int beatDuration = 1;
          // Store info for on mouse release
          this.holdTone = Tones.getToneAtToneVal(pitch % numTones);
          this.holdOctave = (pitch / numTones) - 1;
          this.holdStartingBeat = view.getCurrentBeat();

          // Add the starting note
          try {
            this.out.append("Adding note \n");
          } catch (IOException e) {
            e.printStackTrace();
          }
          if (view.getCurrentBeat() + beatDuration > view.getMaxBeat()) {
            view.rebuildMusic(null);
          }
          // Update the view with the added note.
          view.updateVisAddNotes(null);
          view.startMusic();
          // we added a note, so a change was made and will require rebuilding audio.
          audioUpdateNecessary = true;
        } catch (IllegalArgumentException e) {
          e.getMessage();
        }
      }



    });

    mouseReleases.put(MouseEvent.BUTTON3, () -> { //the contents of MakeCaps below
      try {
        this.out.append("Right mouse click release detected\n");
      } catch (IOException e) {
        e.getMessage();
      }
      if(!view.isInPracticeMode()){
        try {
          int beatDuration = 1;
          try {
            this.out.append("Modifying note\n");
          } catch (IOException e) {
            e.printStackTrace();
          }
          if (view.getCurrentBeat() + beatDuration > view.getMaxBeat()) {
            view.rebuildMusic(null);
          }
          // Update the view with the added note.
          view.updateVisAddNotes(null);
          // we added a note, so a change was made and will require rebuilding audio.
          audioUpdateNecessary = true;
        } catch (IllegalArgumentException e) {
          e.getMessage();
        }
        view.pauseMusic();
      }


    });
    // add the mouse listener to the view.
    PianoMouseListener mouseListener = new PianoMouseListener();
    mouseListener.setMousePressedMap(mousePresses);
    frame.addMouseListener(mouseListener);

  }

  @Test
  public void testPianoMouseListener() {
    configureMouseListener();
    MouseEvent event = new MouseEvent(frame, MouseEvent.MOUSE_PRESSED, 0, 0, 0, 0, 1, false, 1);
    // Press space bar to play.
    frame.getMouseListeners()[0].mousePressed(event);
    assertEquals(out.toString(),
            "Left mouse click detected\n" +
                    "Called getPianoKeyPressed.\n" +
                    "Adding note \n" +
                    "Called getCurrentBeat.\n" +
                    "Called getMaxBeat.\n" +
                    "Rebuilding music.\n" +
                    "Called updateVisAddNotes.\n" +
                    "Moving forward one beat.\n");
  }

  @Test
  public void testPianoMouseListener2Clicks() {
    configureMouseListener();
    MouseEvent event = new MouseEvent(frame, MouseEvent.BUTTON1, 0, 0, 0, 0, 1, false, 1);
    System.out.println(event.getButton());
    // Click and add a note
    frame.getMouseListeners()[0].mousePressed(event);
    // Click and add a note
    frame.getMouseListeners()[0].mousePressed(event);

    assertEquals(out.toString(),
            "Left mouse click detected\n" +
                    "Called getPianoKeyPressed.\n" +
                    "Adding note \n" +
                    "Called getCurrentBeat.\n" +
                    "Called getMaxBeat.\n" +
                    "Rebuilding music.\n" +
                    "Called updateVisAddNotes.\n" +
                    "Moving forward one beat.\n" +
                    "Left mouse click detected\n" +
                    "Called getPianoKeyPressed.\n" +
                    "Adding note \n" +
                    "Called getCurrentBeat.\n" +
                    "Called getMaxBeat.\n" +
                    "Rebuilding music.\n" +
                    "Called updateVisAddNotes.\n" +
                    "Moving forward one beat.\n");
  }

  @Test
  public void testPianoMouseListenerRightClick() {
    configureMouseListener();
    MouseEvent event = new MouseEvent(frame, MouseEvent.MOUSE_PRESSED, 0, 0, 0, 0, 1, false, 3);
    System.out.println(event.getButton());
    // Click and add a note
    frame.getMouseListeners()[0].mousePressed(event);

    assertEquals(out.toString(),
            "Right mouse click detected\n" +
                    "Called getPianoKeyPressed.\n" +
                    "Called getCurrentBeat.\n" +
                    "Adding note \n" +
                    "Called getCurrentBeat.\n" +
                    "Called getMaxBeat.\n" +
                    "Rebuilding music.\n" +
                    "Called updateVisAddNotes.\n" +
                    "Starting music.\n");
  }
}
