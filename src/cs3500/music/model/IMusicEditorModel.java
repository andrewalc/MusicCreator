package cs3500.music.model;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public interface IMusicEditorModel {

  /**
   * Creates a new piece of music for use in the editor for creating and editing music. The new
   * piece is blank, containing no notes.
   */
  void newPiece();

  /**
   * Loads a piece of music into the editor for creating and editing music.
   *
   * @param piece A piece of music.
   * @throws NullPointerException If given piece is null.
   */
  void loadPiece(Map<Integer, ArrayList<ArrayList<Integer>>> piece) throws NullPointerException;

  /**
   * Returns a copy of the piece currently loaded in the editor.
   * The ArrayList<Integer< represents a note where its integer contents are as follows:
   * (startBeat, endBeat, instrument, pitch, and volume).
   *
   * @return A copy of the piece currently loaded in the editor.
   */
  Map<Integer, ArrayList<ArrayList<Integer>>> getAllNotes();

  /**
   * Adds a note of the given tone to the piece of music in the editor. Requires a Note with a
   * Tone, an octave,starting beat number and the number of beats the note will last for.
   *
   * @param tone            The tone of the note you wish to add.
   * @param octave          The octave of the note you wish to add.
   * @param startingBeat    The starting beat of the note you wish to add.
   * @param beats           The number of beats of the note you wish to add lasts for.
   * @throws NullPointerException If the given note is null.
   */
  void addNote(Tones tone, int octave, int startingBeat, int beats, int instrument, int volume)
          throws NullPointerException;

  /**
   * Remove a note of the given tone, starting beat, and beat duration from the piece of music in
   * the editor. The tone, starting beat, and number of beats properties of the given note must be
   * exactly the same as the properties of the note you wish to remove.
   *
   * @param tone            The tone of the note you wish to remove.
   * @param octave          The octave of the note you wish to remove.
   * @param startingBeat    The starting beat of the note you wish to remove.
   * @param beats           The number of beats of the note you wish to remove lasts for.
   * @throws NullPointerException   If the given note is null.
   * @throws NoSuchElementException If the requested note is not found within the piece.
   */
  void removeNote(Tones tone, int octave, int startingBeat, int beats, int instrument, int
          volume) throws
          NullPointerException, NoSuchElementException;

  /**
   * Modifies the given note in a piece of music allowing the a changes to its tone, octave,
   * starting beat and number of beats. A octave must between 1-10 A starting beat must be at
   * least 0 and the number of beats it lasts for must be at least 1. The given note parameters
   * must match the exact parameters of the note you wish to edit.
   *
   * @param tone            The tone of the note you wish to modify.
   * @param octave          The octave of the note you wish to modify.
   * @param startingBeat    The starting beat of the note you wish to modify.
   * @param beats           The number of beats of the note you wish to modify lasts for.
   * @param instrument
   * @param volume
   * @param modTone         The new tone to set this note to.
   * @param modOctave       The new octave value to set this note to.
   * @param modStartingBeat The new starting beat value to set this note to.
   * @param modBeats        The new number of beats value to set this note to.
   * @param modInstrument
   * @param modVolume
   * @throws IllegalArgumentException If startingBeat and beats get invalid parameters
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
   * Combines the currently loaded piece of music with a given piece of music, stacking notes
   * from both into one new Piece that will be present in the editor.
   *
   * @param other A piece of music to combine an existing piece of music with on top.
   */
  void combinePieceOnTop(Map<Integer, ArrayList<ArrayList<Integer>>> other);

  /**
   * Combines the currently loaded piece of music with a given piece of music by adding all notes
   * from the given piece to the end of the piece that is currently loaded.
   *
   * @param other A piece of music to combine an existing piece of music with at the end.
   */
  void combinePieceAtEnd(Map<Integer, ArrayList<ArrayList<Integer>>> other) throws
          IllegalArgumentException;

  int getMaxBeats();

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
