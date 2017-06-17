package cs3500.music.model;


import java.util.ArrayList;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * An interface representing a Music editor model. In this model, music notes suitable for MIDI are
 * represented as an ArrayList of Integer of size 5. The ArrayList of Integer is formatted as
 * follows, (int startingBeat, int endBeat, int instrument, int pitch, int volume). Starting beat
 * is the int where a beat begins, endBeat is an int beat that represents what beat a note stops
 * playing at. int instrument represents the MIDI integer instrument this note will play as. int
 * pitch is the official MIDI int pitch value this note will play as. Finally int volume represents
 * the MIDI volume value a note will play as.
 * Notes should be stored into a Map of Integer to ArrayList ArrayList of Integer, where the
 * Integer keys are MIDI note pitches, and their values are ArrayLists that contain all notes
 * that share that buckets key pitch.
 * To make adding, removing, and modifying notes easier, the Tones enumeration should be used in
 * those respective methods to describe the tone of a note,
 * ranging from C to B.
 */
public interface IMusicEditorModel {

  /**
   * Creates a new piece of music for use in the editor for creating and editing music. The new
   * piece is blank, containing no notes.
   */
  void newPiece();

  /**
   * Loads a piece of music into the editor for creating and editing music.
   *
   * @param piece A piece of music represented as a Map of Integers to ArrayList ArrayList of
   *              Integers.
   * @throws NullPointerException If given piece is null.
   */
  void loadPiece(Map<Integer, ArrayList<ArrayList<Integer>>> piece) throws NullPointerException;

  /**
   * Returns a copy of the piece currently loaded in the editor.
   * The ArrayList of Integer represents a note where its integer contents are as follows:
   * (startBeat, endBeat, instrument, pitch, and volume).
   *
   * @return A copy of the piece currently loaded in the editor.
   */
  Map<Integer, ArrayList<ArrayList<Integer>>> getAllNotes();

  /**
   * Adds a note to the editor. Requires a valid tone (C C# D# E etc.), the octave the tone is in,
   * the starting beat number for the note, the number of beats the note lasts for, the MIDI
   * instrument it will play as, and the volume it will play at. The octave must be within 1-10,
   * the starting beat must be atleast 0, the number of beats must be a positive integer, the
   * instrument must be within the MIDI int instrument range of 1 - 127, and the volume must be
   * 0 or greater.
   *
   * @param tone         The tone of the note you wish to add.
   * @param octave       The octave of the note you wish to add.
   * @param startingBeat The starting beat of the note you wish to add.
   * @param beats        The number of beats of the note you wish to add lasts for.
   * @param instrument   The MIDI integer instrument this note should play as.
   * @param volume       The MIDI volume this note should play at.
   * @throws NullPointerException If the given note is null.
   */

  void addNote(Tones tone, int octave, int startingBeat, int beats, int instrument, int volume)
          throws NullPointerException;

  /**
   * Remove a note from the editor. The tone, starting beat, number of beats, instrument value,
   * and volume value of the given note must be exactly the same as the properties of the note
   * you wish to remove.
   *
   * @param tone         The tone of the note you wish to remove.
   * @param octave       The octave of the note you wish to remove.
   * @param startingBeat The starting beat of the note you wish to remove.
   * @param beats        The number of beats of the note you wish to remove lasts for.
   * @param instrument   The MIDI integer instrument this note should play as.
   * @param volume       The MIDI volume this note should play at.
   * @throws NullPointerException   If the given note is null.
   * @throws NoSuchElementException If the requested note is not found within the piece.
   */
  void removeNote(Tones tone, int octave, int startingBeat, int beats, int instrument, int
          volume) throws
          NullPointerException, NoSuchElementException;

  /**
   * Modifies the given note in a piece of music allowing the a changes to its tone, octave,
   * starting beat, number of beats, instrument, and volume. The first 6 parameters must match
   * the parameters of the note you wish to modify, the next 6 "mod" parameters are the
   * parameters you wish to set this note to.
   *
   * @param tone            The tone of the note you wish to modify.
   * @param octave          The octave of the note you wish to modify.
   * @param startingBeat    The starting beat of the note you wish to modify.
   * @param beats           The number of beats of the note you wish to modify lasts for.
   * @param modTone         The new tone to set this note to.
   * @param modOctave       The new octave value to set this note to.
   * @param modStartingBeat The new starting beat value to set this note to.
   * @param modBeats        The new number of beats value to set this note to.
   * @throws IllegalArgumentException If startingBeat, octave, beats, instrument, volume get invalid
   *                                  parameters.
   * @throws NoSuchElementException   if the given note is not found in the piece.
   * @throws NullPointerException     If the given note is null.
   */

  void modifyNote(Tones tone, int octave, int startingBeat, int beats, int instrument, int
          volume, Tones modTone, int modOctave, int modStartingBeat, int modBeats, int
                          modInstrument, int modVolume) throws
          NullPointerException, IllegalArgumentException, NoSuchElementException;

  /**
   * Returns an ArrayList of all notes that are currently playing at this beat. The given beat
   * must be within the bounds of the piece of music's beats or else an exception will be thrown.
   *
   * @param beat an Integer representing the beat to look for note playing in.
   * @return An ArrayList of note that are playing at the given beat.
   */
  ArrayList<ArrayList<Integer>> getNotesAtBeat(int beat) throws IllegalArgumentException;

  /**
   * Combines the currently loaded music with a given Map of notes, stacking notes
   * from both directly ontop one another that will be present in the editor.
   *
   * @param other A Map of music to combine existing music with on top.
   */
  void combinePieceOnTop(Map<Integer, ArrayList<ArrayList<Integer>>> other);

  /**
   * Combines the currently loaded music with a given Map of notes by adding all notes
   * from the given piece to the end of the piece that is currently loaded.
   *
   * @param other A Map of music to combine existing music with at the end.
   */
  void combinePieceAtEnd(Map<Integer, ArrayList<ArrayList<Integer>>> other);

  /**
   * Returns the furthest, last beat that ever plays in the editor's currently loaded piece of
   * music.
   *
   * @return integer representing the last beat that plays in the editor.
   */
  int getMaxBeats();

  /**
   * Returns the tempo of the music in the editor.
   *
   * @return The tempo of the music in the editor.
   */
  int getTempo();

  /**
   * Sets the tempo of the music in the editor.
   *
   * @param tempo The value to set the music's tempo to.
   */
  void setTempo(int tempo);

  /**
   * Returns the lowest pitch ever played by a note currently in editor. The returned int is MIDI
   * pitch representation of that pitch, where 24 is C1 and all pitches up are one above.
   *
   * @return MIDI pitch value of the lowest pitch currently in the editor.
   * @throws IllegalArgumentException If the model has no notes.
   */
  int getLowestPitch() throws IllegalArgumentException;

  /**
   * Returns the highest pitch ever played by a note currently in editor. The returned int is MIDI
   * pitch representation of that pitch, where 24 is C1 and all pitches up are one above.
   *
   * @return MIDI pitch value of the highest pitch currently in the editor.
   * @throws IllegalArgumentException If the model has no notes.
   */
  int getHighestPitch() throws IllegalArgumentException;

  /**
   * Returns the total number of notes currently in the editor.
   *
   * @return The total number of notes currently in the editor.
   */
  int getNumberOfNotes();

  /**
   * Prints out a string that is separated into pitch columns from the piece's lowest to highest
   * pitch and beat number rows. Notes that are present will appear as "  X  " at its head, and
   * then will continue as "  |  " if the note is sustaining. An empty note symbol "     "
   * indicates there is no note of that pitch present at that beat. Returns an empty String if
   * the piece contains no notes.
   *
   * @return a String representing the loaded piece's music.
   */
  String toString();

}
