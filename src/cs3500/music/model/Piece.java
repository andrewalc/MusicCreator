package cs3500.music.model;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.TreeMap;

/**
 * A Class representing a piece of music, a collection of Notes to be played in sequence. All
 * notes are stored in a TreeMap with their respective Pitch keys. The TreeMap is ordered by
 * pitch levels, where C1 would be the lowest and B10 would be the highest. The TreeMap only
 * contains keys for Pitches that are present in the piece and will update itself should notes be
 * added or removed.
 */
public class Piece {

  private TreeMap<Pitch, ArrayList<Note>> allNotes;
  // represents the very last beat number ever reached in this piece
  private int maxBeats = 0;

  /**
   * Constructor that produces a new empty Piece of music, with no notes assigned.
   */
  public Piece() {
    allNotes = new TreeMap<Pitch, ArrayList<Note>>(new PitchComparator());
  }

  /**
   * Copy constructor for a Piece.
   *
   * @param copy A given piece to copy into a new Piece.
   */
  public Piece(Piece copy) {
      TreeMap<Pitch, ArrayList<Note>> newAllNotes = new TreeMap<Pitch, ArrayList<Note>>(new
              PitchComparator());
      newAllNotes.putAll(copy.allNotes);
      this.allNotes = newAllNotes;
      this.updateMaxBeats();
  }

  /**
   * Constructor that takes in a TreeMap of pitches to ArrayList of notes and inserts those notes
   * into a new Piece.
   */
  public Piece(TreeMap<Pitch, ArrayList<Note>> allNotes) {
    TreeMap<Pitch, ArrayList<Note>> newAllNotes = new TreeMap<Pitch, ArrayList<Note>>(new
            PitchComparator());
    newAllNotes.putAll(allNotes);
    this.allNotes = newAllNotes;
    this.updateMaxBeats();

  }


  /**
   * Getter for maxBeats.
   *
   * @return The current maxB
   */
  public int getMaxBeats() {
    return maxBeats;
  }

  /**
   * Returns the total number of notes contained in this piece.
   *
   * @return Integer of how many notes there are in this piece.
   */
  public int getNumberOfNotes() {
    int noteCount = 0;
    for (ArrayList<Note> pitchList : this.allNotes.values()) {
      for (Note note : pitchList) {
        noteCount++;
      }
    }
    return noteCount;
  }

  /**
   * Method that returns a copy of the TreeMap of all notes in this piece of music.
   *
   * @return A copy of the piece's TreeMap of notes.
   */
  public TreeMap<Pitch, ArrayList<Note>> getAllNotes() {
    TreeMap<Pitch, ArrayList<Note>> newAllNotes = new TreeMap<Pitch, ArrayList<Note>>(this
            .allNotes);
    return newAllNotes;
  }

  /**
   * Adds a given note to this piece of music. The note will be put into the piece's treemap by
   * using the note's Pitch as its key. Updates the maxBeat count accordingly.
   *
   * @param note The note you wish to add to the piece of music.
   * @throws NullPointerException If the given note is null.
   */
  protected void addNote(Note note) throws NullPointerException {
    if (note == null) {
      throw new NullPointerException("Given note is null");
    }
    Pitch pitch = note.getPitch();
    // check if the piece has a key for notes with the given pitch.
    if (allNotes.containsKey(pitch)) {
      allNotes.get(pitch).add(note);
    } else {
      // If the key is not found, put a new empty ArrayList<Note> using the pitch as it's key.
      allNotes.put(pitch, new ArrayList<Note>());
      allNotes.get(pitch).add(note);
    }
    this.updateMaxBeats();
  }

  /**
   * Removes a given not from this piece of music. The given note must mirror the parameters of
   * the note you wish to remove which includes having the same tone, octave, starting beat,
   * and beats. Throws an exception if the given note is not found. Updates the maxBeat count
   * accordingly.
   *
   * @param note A note mirror the parameters of the note you wish to remove from the piece.
   * @throws NullPointerException   If the given note is null.
   * @throws NoSuchElementException If the requested note is not found within the piece.
   */
  protected void removeNote(Note note) throws NullPointerException, NoSuchElementException {
    if (note == null) {
      throw new NullPointerException("Given note is null");
    }
    Pitch pitch = note.getPitch();
    // If the given note is in this piece of music remove it.
    if (allNotes.containsKey(pitch) && allNotes.get(pitch).contains(note)) {
      allNotes.get(pitch).remove(note);
      // If the pitch bucket no longer has notes in it, remove the bucket.
      if (allNotes.get(pitch).size() == 0) {
        allNotes.remove(pitch);
      }
    } else {
      throw new NoSuchElementException("Cannot remove given note, it does not exist in this " +
              "piece");
    }
    this.updateMaxBeats();
  }


  /**
   * Modifies the given note in a piece of music allowing the a changes to its tone, octave,
   * starting beat and number of beats. A octave must between 1-10 A starting beat must be at
   * least 0 and the number of beats it lasts for must be at least 1. The given note must match
   * the exact parameters of the note you wish to edit.
   *
   * @param note         A note mirroring the parameters of a note you wish to modify in the piece.
   * @param tone         The new tone to set this note to.
   * @param octave       The new octave value to set this note to.
   * @param startingBeat The new starting beat value to set this note to.
   * @param beats        The new number of beats value to set this note to.
   * @throws IllegalArgumentException If startingBeat and beats get invalid parameters
   * @throws NoSuchElementException   if the given note is not found in the piece.
   * @throws NullPointerException     If the given note is null.
   */
  protected void modifyNote(Note note, Tones tone, int octave, int startingBeat, int beats, int
          instrument, int volume) throws
          NullPointerException, IllegalArgumentException {
    if (note == null || tone == null) {
      throw new NullPointerException("Note and tone cannot be null");
    }
    Pitch originalPitch = note.getPitch();
    // check if the given note is in the piece
    if (allNotes.containsKey(originalPitch) && allNotes.get(originalPitch).contains(note)) {
      this.removeNote(note);
      this.addNote(new Note(tone, octave, startingBeat, beats, instrument, volume));
    } else {
      throw new NoSuchElementException("Cannot modify given note, it does not exist in this " +
              "piece");
    }
    this.updateMaxBeats();
  }

  /**
   * Method for updating the piece current maximum beat. The maxBeats field should always be
   * equal to the very last beat that any note ends on in the whole piece. Should be used
   * every time a note is added or removed from this piece, whether through the add or remove
   * note methods or after calling the copy constructor or the TreeMap constructor.
   */
  private void updateMaxBeats() {
    int potentialMaxBeats = 0;
    for (ArrayList<Note> pitchList : this.allNotes.values()) {
      for (Note note : pitchList) {
        // if a note's ending beat is larger than the current potential last beat, update potential.
        int endBeat = note.getStartingBeat() + note.getBeats();
        if (endBeat > potentialMaxBeats) {
          potentialMaxBeats = endBeat;
        }
      }
    }
    this.maxBeats = potentialMaxBeats;
  }

  /**
   * Gets the lowest pitch that is ever played by a note in this piece of music.
   *
   * @return A Pitch representing the lowest pitch present in this piece.
   * @throws NoSuchElementException If there are no notes in the piece.
   */
  public Pitch getLowestPitch() throws NoSuchElementException {
    if (this.allNotes.keySet().size() > 0) {
      return this.allNotes.firstKey();
    } else {
      throw new NoSuchElementException("Cannot retrieve the requested pitch. There are no notes" +
              "in this piece of music.");
    }
  }

  /**
   * Gets the highest pitch that is ever played by a note in this piece of music.
   *
   * @return A Pitch representing the highest pitch present in this piece.
   * @throws NoSuchElementException If there are no notes in the piece.
   */
  public Pitch getHighestPitch() throws NoSuchElementException {
    if (this.allNotes.keySet().size() > 0) {
      return this.allNotes.lastKey();
    } else {
      throw new NoSuchElementException("Cannot retrieve the requested pitch. There are no notes" +
              " in this piece of music.");
    }
  }

  /**
   * Prints out a string that is separated into pitch columns from the piece's lowest to highest
   * pitch and beat number rows. Notes that are present will appear as "  X  " at its head, and
   * then will continue as "  |  " if the note is sustaining. An empty note symbol "     "
   * indicates there is no note of that pitch present at that beat. Returns an empty String if
   * this piece contains no notes.
   *
   * @return a String representing this piece's music.
   */
  @Override
  public String toString() {
    String result = "";
    if (this.getNumberOfNotes() == 0) {
      return "";
    }
    result += this.generatePitchHeaderString();
    for (int currBeat = 0; currBeat <= this.getMaxBeats(); currBeat++) {
      result += this.generateBeatRow(currBeat);
    }
    return result;
  }

  /**
   * Method that generates the first line of the toString method, displaying a range of pitches
   * beginning from this piece's lowest Pitch to this piece's highest pitch. Each column is five
   * characters long and padding for spaces varies on how many characters the pitch's toString is.
   *
   * @return A String displaying a range of pitches from this piece's lowest pitch to its highest.
   */
  private String generatePitchHeaderString() {
    String result = "";
    // number of characters needed for beats column
    int beatPadding = ("" + this.getMaxBeats()).length();
    Pitch highest = this.getHighestPitch();
    Pitch currPitch = this.getLowestPitch();
    for (int i = 0; i < beatPadding; i++) {
      result += " ";
    }
    while (true) {
      result += this.generatePitchSpacing(currPitch.toString());
      if (currPitch.equals(highest)) {
        break;
      } else {
        currPitch = currPitch.getNextPitch();
      }
    }
    return result + "\n";
  }

  /**
   * Method that generates the space padding for each individual pitch's toString given as the
   * pitchString. There are three possible paddings, "  C1 " for 2 characters, " C#1 " for 3, and
   * " C#10" for 4. The given pitchString should never be less than 2 or exceed a size of 4. This
   * method should only be used with Pitch toStrings.
   *
   * @param pitchString A String representing a pitch's toString call.
   * @return A String with the given pitchString padded with the appropriate number of spaces.
   */
  private String generatePitchSpacing(String pitchString) throws IllegalArgumentException {
    switch (pitchString.length()) {
      case 2:
        return "  " + pitchString + " ";
      case 3:
        return " " + pitchString + " ";
      case 4:
        return " " + pitchString;
      default:
        throw new IllegalArgumentException("The given Pitch String should be a size of 2 3 or 4. " +
                "It is currently " + pitchString.length());
    }
  }

  /**
   * Generates the spaces at the beginning of a beat row for beats that have less digits than the
   * piece's maximum beat. Should the max be 2 digits, 1 digit beats should have one space in
   * front(max = 20, row 6 would be " 6"). Should the max be 3 digits, 1 digit beats have one space
   * and 2 digit beats have two spaces (max = 155, row 6 would be "  6", row 24 would be " 24"),
   * and so on.
   *
   * @param currBeat an Integer representing what beat row is being generated.
   * @return A String of the given currBeat with the appropriate padding of spaces in front.
   */
  private String generateBeatSpacing(int currBeat) {
    String result = "";
    int beatPadding = ("" + this.getMaxBeats()).length();
    int currBeatPadding = ("" + currBeat).length();
    int necessaryPadding = beatPadding - currBeatPadding;
    for (int i = 0; i < necessaryPadding; i++) {
      result += " ";
    }
    return result += currBeat;
  }

  /**
   * Generates a string representing a row for a given beat, showing all notes that are being
   * played in their respective pitches, aligned to the pitch columns. The string begins with the
   * current beat number and then prints out a 5 character note symbol representing whether a
   * note is not present "     ", a note is at its head "  X  ", or a note is sustaining "  |  ".
   *
   * @param currBeat Integer presenting the beat this row is being generated for.
   * @return A String presenting the beat number, and all notes that play on this beat.
   */
  private String generateBeatRow(int currBeat) {
    String result = "";
    Pitch currPitch = this.getLowestPitch();
    Pitch highest = this.getHighestPitch();
    // Start with the beat number with appropriate space padding.
    result += this.generateBeatSpacing(currBeat);

    while (true) {
      // If this pitch has no notes, there are no notes to be played.
      if (!this.allNotes.containsKey(currPitch)) {
        result += "     ";

      } else {
        ArrayList<String> candidates = new ArrayList<String>();
        for (Note note : this.allNotes.get(currPitch)) {
          // Check if this note should be playing at the current beat, and if so add it's
          // note symbol is added to the list of candidates.
          // EDIT: getBeats needed to be subtracted by one, toStrings were outputting an extra beat.
          if (currBeat >= note.getStartingBeat() && currBeat <= note.getStartingBeat() +
                  note.getBeats() - 1) {
            candidates.add(note.noteSymbolString(currBeat));
          }
        }
        // Choose a symbol to print out. If there is any note head it takes priority, followed by
        // any sustains. If the candidates list is empty, an empty note symbol is printed.
        if (candidates.contains(Note.STR_NOTE_HEAD)) {
          result += Note.STR_NOTE_HEAD;
        } else if (candidates.contains(Note.STR_NOTE_SUSTAIN)) {
          result += Note.STR_NOTE_SUSTAIN;
        } else {
          result += Note.STR_NOTE_EMPTY;
        }
      }
      // Continue appending more note symbols until the current pitch has reached the highest
      // pitch in this piece of music.
      if (!currPitch.equals(highest)) {
        currPitch = currPitch.getNextPitch();
      } else {
        break;
      }
    }
    return result + "\n";
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    } else if (!(other instanceof Piece)) {
      return false;
    } else {
      Piece compare = (Piece) other;
      return this.allNotes.equals(compare.allNotes);
    }
  }

  @Override
  public int hashCode() {
    return (31 * this.allNotes.hashCode()) + this.toString().hashCode();
  }

  public void updateEmptyMapBuckets() {
    for (Pitch pitch : this.allNotes.keySet()) {
      if (allNotes.get(pitch).size() == 0) {
        allNotes.remove(pitch);
      }
    }
  }
}
