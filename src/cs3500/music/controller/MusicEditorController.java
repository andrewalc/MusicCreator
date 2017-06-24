package cs3500.music.controller;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import cs3500.music.model.IMusicEditorModel;
import cs3500.music.model.Tones;
import cs3500.music.view.IMusicEditorView;

/**
 * Class representing the Music Editor Controller. Takes in a IMusicEditorModel and an
 * IMusicEditorView and allows a user to control the Editor using key presses and mouse clicks.
 * The constructor builds a keylistener KeyBoardListener and a mouselistener PianoMouseListener
 * and sets the listeners with the appropriate commands the it will respond with on an event. To
 * start the editor, one must call the beginControl method.
 */
public class MusicEditorController {
  private IMusicEditorModel model;

  private IMusicEditorView view;
  // Used for finding out when it is necessary to rebuild the midi audio.
  private boolean audioUpdateNecessary = false;
  private int potentialStartingRepeat = -1;
  private int potentialEndingRepeat = -1;
  private final int tempoIncrement;

  /**
   * Constructor for a MusicEditorController. Requires a model and view to use to run both vies
   * simultaneously.
   *
   * @param model The IMusicEditorModel to use in the composite view.
   * @param view  The IMusicEditorView to use in the composite view.
   */
  public MusicEditorController(IMusicEditorModel model, IMusicEditorView view) {
    this.model = model;
    this.view = view;
    this.tempoIncrement = (int) (model.getTempo() / 10);
    this.configureKeyBoardListener();
    this.configureMouseListener();

  }

  /**
   * Creates and sets a keyboard listener for the view
   * When space is pressed, the music will play if it wasn't playing already, or it will pause if
   * music was already playing.
   * When right arrow key is pressed, the editor will move the playhead forward one beat.
   * When left arrow key is pressed, the editor will move the playhead backward one beat.
   * When the home key is pressed, the editor will move the playhead to the beginning of the music.
   * When the end key is pressed, the editor will move the playhead to the end of the music.
   */
  private void configureKeyBoardListener() {
    Map<Integer, Runnable> keyPresses = new HashMap<Integer, Runnable>();

    keyPresses.put(KeyEvent.VK_SPACE, () -> { //the contents of MakeCaps below
      if (view.isPlayingMusic()) {
        view.pauseMusic();
      } else {
        if (audioUpdateNecessary) {
          view.rebuildMusic(model.getAllNotes());
          audioUpdateNecessary = false;
        }
        view.startMusic();

      }
    });
    keyPresses.put(KeyEvent.VK_UP, () -> { //the contents of MakeCaps below
      model.setRepeatFromTheTop(view.getCurrentBeat());
      view.receiveRepeatPairs(model.getRepeatPairs());

    });
    keyPresses.put(KeyEvent.VK_DOWN, () -> { //the contents of MakeCaps below

      if(this.potentialStartingRepeat == -1 && this.potentialEndingRepeat == -1){
        model.setRepeatPair(view.getCurrentBeat(), -1);
        this.potentialStartingRepeat = view.getCurrentBeat();
      }
      else {
        if(view.getCurrentBeat() <= this.potentialStartingRepeat){
          throw new IllegalArgumentException("Ending beat must be after starting beat");
        }
        this.potentialEndingRepeat = view.getCurrentBeat();
        try{
          model.setRepeatPair(this.potentialStartingRepeat, this.potentialEndingRepeat);
        }
        catch (IllegalArgumentException e){
          System.out.println(e.getMessage());
        }
        this.potentialStartingRepeat = -1;
        this.potentialEndingRepeat = -1;
      }
      view.receiveRepeatPairs(model.getRepeatPairs());

    });
    keyPresses.put(KeyEvent.VK_SLASH, () -> { //the contents of MakeCaps below
      model.resetRepeatPairs();
      view.receiveRepeatPairs(model.getRepeatPairs());
      this.potentialStartingRepeat = -1;
      this.potentialEndingRepeat = -1;
    });
    keyPresses.put(KeyEvent.VK_RIGHT, () -> { //the contents of MakeCaps below
      view.forwardOneBeat();

    });
    keyPresses.put(KeyEvent.VK_LEFT, () -> { //the contents of MakeCaps below
      view.backOneBeat();
    });

    keyPresses.put(KeyEvent.VK_HOME, () -> { //the contents of MakeCaps below
      if (view.isPlayingMusic() && audioUpdateNecessary) {
        view.rebuildMusic(model.getAllNotes());
        audioUpdateNecessary = false;
      }
      view.goToBeginning();
      view.resetRepeatPassings();
    });
    keyPresses.put(KeyEvent.VK_END, () -> { //the contents of MakeCaps below
      view.goToEnd();
    });

    keyPresses.put(KeyEvent.VK_COMMA, () -> { //the contents of MakeCaps below
      model.setTempo(model.getTempo() + tempoIncrement);
      view.setTempo(model.getTempo());
    });
    keyPresses.put(KeyEvent.VK_PERIOD, () -> { //the contents of MakeCaps below
      model.setTempo(model.getTempo() - tempoIncrement);
      view.setTempo(model.getTempo());
    });

    // add the keyboard listener to the view.
    KeyboardListener kbd = new KeyboardListener();
    kbd.setKeyPressedMap(keyPresses);
    view.addKeyListener(kbd);

  }

  /**
   * Creates and sets a mouse listener for the view.
   * Whenever a mouse button is pressed, it will check if the mouse clicked on one of the
   * keyboard keys. If it did, then it will add a note of duration 1 to the editor at the
   * playhead beat with the pitch of the piano key that was clicked.
   */
  private void configureMouseListener() {
    Map<Integer, Runnable> mousePresses = new HashMap<Integer, Runnable>();

    mousePresses.put(MouseEvent.MOUSE_PRESSED, () -> { //the contents of MakeCaps below
      try {
        int pitch = view.getPianoKeyPressed();
        int numTones = Tones.values().length;
        int beatDuration = 1;
        model.addNote(Tones.getToneAtToneVal(pitch % numTones), (pitch / numTones) - 1,
                view.getCurrentBeat(), beatDuration, 0, 100);
        if (view.getCurrentBeat() + beatDuration > view.getMaxBeat()) {
          view.rebuildMusic(model.getAllNotes());
        }
        // Update the view with the added note.
        view.updateVisAddNotes(model.getAllNotes());
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
    view.addMouseListener(mouseListener);

  }


  /**
   * Initializes the view and begins the editor with user control.
   */
  public void beginControl() {
    System.out.println("Initializing MIDI Editor...");
    view.initialize();
    while (view.isActive()) {
      view.updateCurrentBeat();
      //System.out.print(" "); // Needed to boost performance.
    }
  }
}
