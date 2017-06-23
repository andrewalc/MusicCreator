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
  JFrame frame = new JFrame();


  /**
   * Configures the mouse listener to the same commands as the mouse listener in the
   * controller.
   */
  private void configureMouseListener() {
    Map<Integer, Runnable> mousePresses = new HashMap<Integer, Runnable>();

    mousePresses.put(MouseEvent.MOUSE_PRESSED, () -> { //the contents of MakeCaps below
      try {
        try {
          this.out.append("Mouse click detected\n");
        } catch (IOException e) {
          e.printStackTrace();
        }
        int pitch = view.getPianoKeyPressed();
        int numTones = Tones.values().length;
        int beatDuration = 1;
        try {
          this.out.append("Adding note \n"); // This is where model.addNote would be.
        } catch (IOException e) {
          e.getMessage();
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
            "Mouse click detected\n" +
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
    MouseEvent event = new MouseEvent(frame, MouseEvent.MOUSE_PRESSED, 0, 0, 0, 0, 1, false, 1);
    // Click and add a note
    frame.getMouseListeners()[0].mousePressed(event);
    // Click and add a note
    frame.getMouseListeners()[0].mousePressed(event);

    assertEquals(out.toString(),
            "Mouse click detected\n" +
                    "Called getPianoKeyPressed.\n" +
                    "Adding note \n" +
                    "Called getCurrentBeat.\n" +
                    "Called getMaxBeat.\n" +
                    "Rebuilding music.\n" +
                    "Called updateVisAddNotes.\n" +
                    "Moving forward one beat.\n" +
                    "Mouse click detected\n" +
                    "Called getPianoKeyPressed.\n" +
                    "Adding note \n" +
                    "Called getCurrentBeat.\n" +
                    "Called getMaxBeat.\n" +
                    "Rebuilding music.\n" +
                    "Called updateVisAddNotes.\n" +
                    "Moving forward one beat.\n");
  }
}
