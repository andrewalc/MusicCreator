package cs3500.music.tests;


/**
 * A class representing a standard musical note. It contains a pitch, starting beat, and the
 * number of beats it lasts for. Note provides a connection between Pitch and timing for musical
 * play.
 */
public class Note {

  private Pitch pitch;
  private int startingBeat;
  private int beats;
  private int instrument;
  private int volume;
  public final static String STR_NOTE_HEAD = "  X  ";
  public final static String STR_NOTE_SUSTAIN = "  |  ";
  public final static String STR_NOTE_EMPTY = "     ";


  /**
   * Constructor for Notes. Requires a valid tone (C C# D# E etc.), the octave the tone is in,
   * the starting beat number for the note, and the number of beats the note lasts for. The
   * octave must be within 1-10, the starting beat must be atleast 0, and the number of beats
   * must be a positive integer.
   *
   * @param tone         The tone the note.
   * @param octave       The octave the tone is played in.
   * @param startingBeat The beat this note should start playing at.
   * @param beats        The number of beats this note lasts for.
   * @throws IllegalArgumentException If the octave is not 1-10, if the startingbeat is not >= 0,
   *                                  and if the note does not have a positive number of beats.
   * @throws NullPointerException     If the given tone is null.
   */
  public Note(Tones tone, int octave, int startingBeat, int beats, int instrument, int volume)
          throws IllegalArgumentException,
          NullPointerException {
    if (tone == null) {
      throw new NullPointerException("Given tone is null.");
    }
    if (octave < 1 || octave > 10) {
      throw new IllegalArgumentException("Must be an octave between 1 and 10");
    }
    if (startingBeat < 0) {
      throw new IllegalArgumentException("Starting beat must be 0 or greater");
    }
    if (beats <= 0) {
      throw new IllegalArgumentException("The note must have a positive number of beats");
    }
    this.pitch = new Pitch(tone, octave);
    this.startingBeat = startingBeat;
    this.beats = beats;
    //@TODO ADD CONSTRAINTS
    this.instrument = instrument;
    this.volume = volume;
  }

  /**
   * Copy constructor for Notes
   *
   * @param note A note to copy.
   */
  public Note(Note note) {
    this.pitch = note.getPitch();
    this.startingBeat = note.startingBeat;
    this.beats = note.beats;
  }

  public String noteSymbolString(int beat) {
    if (beat == this.startingBeat) {
      return STR_NOTE_HEAD;
    } else if (beat > this.startingBeat && beat <= this.startingBeat + this.beats) {
      return STR_NOTE_SUSTAIN;
    } else {
      return STR_NOTE_EMPTY;
    }
  }

  /**
   * Equality method for comparing a note with another object.
   * The state of the note does not affect the equality of the note.
   *
   * @param other An object for comparison
   * @return boolean of whether this Note and the other object are equal.
   */
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    } else if (!(other instanceof Note)) {
      return false;
    } else {
      Note compare = (Note) other;
      return this.pitch.equals(compare.pitch) && this.startingBeat ==
              compare.startingBeat && this.beats == compare.beats;
    }
  }

  /**
   * Hashcode method for notes.
   *
   * @return The hashcode int of this note.
   */
  public int hashCode() {
    return this.pitch.hashCode() * this.startingBeat * this.beats;
  }

  /**
   * Getter for this note's pitch.
   *
   * @return This note's pitch.
   */
  public Pitch getPitch() {
    return pitch;
  }

  /**
   * Returns this note's starting beat.
   *
   * @return the note's starting beat.
   */
  public int getStartingBeat() {
    return startingBeat;
  }

  /**
   * Returns how many beats this note lasts for.
   *
   * @return int representing the number of beats this note lasts for.
   */
  public int getBeats() {
    return beats;
  }

  /**
   * Returns the int representing the note's instrument
   *
   * @return The notes instrument
   * @TODO ADD CONSTRAINTS
   */
  public int getInstrument() {
    return instrument;
  }

  /**
   * Returns the note's volume.
   *
   * @return The note's volume.
   */
  public int getVolume() {
    return volume;
  }

  /**
   * Setter for startingBeat.
   *
   * @param startingBeat The beat this note should start at.
   */
  public void setStartingBeat(int startingBeat) {
    if (startingBeat < 0) {
      throw new IllegalArgumentException("Starting beat must be 0 or greater");
    }
    this.startingBeat = startingBeat;
  }

  /**
   * Setter for beats.
   *
   * @param beats The amount of beats to set this note to.
   */
  public void setBeats(int beats) {
    if (beats <= 0) {
      throw new IllegalArgumentException("The note must have a positive number of beats");
    }
    this.beats = beats;
  }
}
