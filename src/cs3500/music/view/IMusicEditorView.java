package cs3500.music.view;

import java.util.ArrayList;
import java.util.Map;

import cs3500.music.controller.KeyboardListener;
import cs3500.music.controller.PianoMouseListener;

/**
 * An interface representting a Music Editor View for a Music Editor Model.
 * EDIT: Originally only had initialize. Includes many more methods needed to perform the actions
 * necessary.
 */
public interface IMusicEditorView {


  /**
   * Creates and displays the view for this view implementation. The method should begin all
   * operations necessary to display the view to the user when it is called.
   */
  void initialize();

  /**
   * Adds a KeyboardListener keylistener to the appropriate locations in this view. The keylistener
   * should be directed towards the JFrame and it's Panels. This keylistener will check for
   * keypresses, specifically it should be able to able keyevents that should trigger play/pause,
   * moving a beat forward or backward, and jumping to the beginning or end.
   *
   * @param keyboardListener The KeyboardListener
   */
  void addKeyListener(KeyboardListener keyboardListener);

  /**
   * Causes the music player to advance it's playhead one beat forward.
   */
  void forwardOneBeat();

  /**
   * Causes the music player to advance it's playhead one beat backward.
   */
  void backOneBeat();

  /**
   * Causes the music player to begin playing music at the from the playhead marker, its current
   * beat.
   */
  void startMusic();

  /**
   * Causes the music player to pause its music, leaving the music player stopped on the beat
   * that it was paused on.
   */
  void pauseMusic();

  /**
   * Moves the playhead marker view to the beginning of the music piece.
   */
  void goToBeginning();

  /**
   * Moves the playhead marker and view to the end of the music piece.
   */
  void goToEnd();

  /**
   * Returns the last possible beat ever played in the music.
   *
   * @return The furthest and last beat value music is ever played at.
   */
  int getMaxBeat();

  /**
   * Sets the current beat to the given value, moving the playhead marker to the given beat.
   */
  void setCurrentBeat(int currentBeat);

  /**
   * Returns the beat the playhead is currently at.
   *
   * @return The beat the music editor is currently at.
   */
  int getCurrentBeat();

  /**
   * Refreshes the view to update it's current beat. Should music be playing, the visual should
   * keep up with the music's current beat. Should the music be stopped, the music should keep up
   * with the visual. This method serves the purpose of keeping both visual and music in sync and
   * should be called repeatedly. Mostly for Composite Views.
   */
  void updateCurrentBeat();

  /**
   * Returns whether this view is active. A view is active if there is a visual of any form, or
   * if there is music playing.
   */
  boolean isActive();

  /**
   * Returns whether the view is currently playing music.
   *
   * @return If any music is playing in the editor.
   */
  boolean isPlayingMusic();

  /**
   * Adds a PianoMouseListener MouseListener to the view. The PianoMousListener serves the purpose
   * of detecting mouse clicks on the piano keyboard visual on screen. The listener will be able
   * to tell which key was picked and add a note to the composition of that pitch at the current
   * beat. Should be added to the JFrame or JPanel component that contains the piano keyboard.
   *
   * @param mouseListener The PianoMousListener MouseListener to add.
   */
  void addMouseListener(PianoMouseListener mouseListener);

  /**
   * Returns the MIDI pitch of the piano key that was just clicked on. This method will be called
   * by the PianoMouseListener on every click to check for the click location and determine which
   * key was pressed.
   *
   * @return The MIDI pitch of the piano key that was pressed.
   */
  int getPianoKeyPressed();

  /**
   * Updates the visual view to adjust for newly added notes. Should a new note be a new highest
   * pitch or new lowest pitch, the view should extend the play grid to allow for those rows to
   * exist. Should a note be added to the end of a composition, the view should update the grid
   * to allow it one more beat afterward to allow the creation of more notes. Requires the new
   * Map of all notes to be rendered.
   *
   * @param allNotes A Map of all notes to be rendered.
   */
  void updateVisAddNotes(Map<Integer, ArrayList<ArrayList<Integer>>> allNotes);

  /**
   * Updates the midi view's sequencer and make it rebuild to account for new notes that were
   * added to the composition. Requires the new Map of all notes to be played by the midi view.
   */
  void rebuildMusic(Map<Integer, ArrayList<ArrayList<Integer>>> allNotes);

  /**
   * Allows the visual view to receive a map of repeat pairs to display visually in the song
   */
  void receiveRepeatPairs(Map<Integer, Integer> repeatPairs);

  /**
   * Getter for the map of repeat pairs.
   *
   * @return The map of repeat pairs.
   */
  Map<Integer, Integer> getRepeatPairs();

  /**
   * Resets the status of all repeats to be able to repeat again on next playback.
   */
  void resetRepeatPassings();

  /**
   * Sets the tempo for the music to play at.
   *
   * @param tempo Tempo to play the music at.
   */
  void setTempo(int tempo);

  /**
   * Transitions the editor into practice mode, for hitting keys to advance the song.
   */
  void beginPracticeMode();

  /**
   * Transisitions the editor back to standard mode and ends practice mode.
   */
  void endPracticeMode();

  /**
   * Returns whether this view is currently in practice mode.
   *
   * @return Is the view in practice mode.
   */
  boolean isInPracticeMode();

  /**
   * Method that allows progress in the editor when practice mode is on.
   * @param notesAtBeat
   */
  void practiceModeChecking(ArrayList<ArrayList<Integer>> notesAtBeat);

  /**
   * Adds a clicked piano key's pitch to the list of pitches needed to pass the current beat in
   * practice mdoe.
   * @param addedPitch The keyboard key pitch that was pressed
   * @param notesAtBeat The list of notes playing that the current beat that the key was pressed.
   */
  void practiceModeAddClickedPitch(Integer addedPitch, ArrayList<ArrayList<Integer>> notesAtBeat);
}
