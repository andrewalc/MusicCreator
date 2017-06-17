package cs3500.music.model;


import java.util.ArrayList;
import java.util.Arrays;

/**
 * A class representing a standard musical note. It contains a pitch, starting beat, the
 * number of beats it lasts for, the MIDI instrument value the note will play as, and the volume
 * that the note will play at.
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
   * the starting beat number for the note, the number of beats the note lasts for, the MIDI
   * instrument it will play as, and the volume it will play at. The octave must be within 1-10,
   * the starting beat must be atleast 0, the number of beats must be a positive integer, the
   * instrument must be within the MIDI int instrument range of 1 - 127, and the volume must be
   * 0 or greater.
   *
   * @param tone         The tone the note.
   * @param octave       The octave the tone is played in.
   * @param startingBeat The beat this note should start playing at.
   * @param beats        The number of beats this note lasts for.
   * @param instrument   The MIDI instrument value this note will play as.
   * @param volume       The volume this note will play at.
   * @throws IllegalArgumentException If the octave is not 1-10, if the startingbeat is not >= 0, if
   *                                  the note does not have a positive number of beats, if the
   *                                  instrument is not within the midi instrument range, and if the
   *                                  volume is not 0 or greater.
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
    if (instrument < 0 || instrument > 128) {
      throw new IllegalArgumentException("The note's instrument number must be between 0 and 127");
    }
    if (volume < 0) {
      throw new IllegalArgumentException("The note's volume must be at least 0.");
    }

    this.startingBeat = startingBeat;
    this.beats = beats;
    this.instrument = instrument;
    this.pitch = new Pitch(tone, octave);
    this.volume = volume;
  }

  /**
   * Copy constructor for Notes
   *
   * @param note A note to copy.
   */
  public Note(Note note) {
    this.startingBeat = note.startingBeat;
    this.beats = note.beats;
    this.instrument = note.instrument;
    this.pitch = note.pitch;
    this.volume = note.volume;
  }

  /**
   * Returns a string representation of a Note at a given beat. Should the beat match the
   * starting beat, this note is begining to play and returns an "  X  " string. Should the beat
   * be within playing range but not the starting beat, the note is sustaining and will return a
   * "  |  " string. Should the beat not be within playing range an empty string "     " will be
   * returned.
   *
   * @param beat The integer beat to check this notes behavior at.
   * @return A string representing the note's behavior at the given beat.
   */
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
   * Converts a given ArrayList of Integer to a Note object. A proper note representation of an
   * arraylist of integer is as follows, (int startingBeat, int endBeat, int instrument, int
   * pitch, int volume). The Arraylist must have these 5 integers within it and have the proper
   * integer values for those respective spots. Starting beat is the int where a beat begins,
   * endBeat is an int beat that represents what beat a note stops playing at. int instrument
   * represents the MIDI integer instrument this note will play as. int pitch is the official MIDI
   * int pitch value this note will play as. Finally int volume represents the MIDI volume value
   * a note will play as.
   *
   * @param note Arraylist of integer containing five integers representing the information needed
   *             to play a midi note.
   * @return A Note object representing the given arraylist of integer.
   * @throws IllegalArgumentException If note parameters are invalid for the Note constructor.
   */
  public static Note convertArrayListIntegerToNote(ArrayList<Integer> note) throws
          IllegalArgumentException {
    int startingBeat = note.get(0);
    int endBeat = note.get(1);
    int instrument = note.get(2);
    int pitch = note.get(3);
    int volume = note.get(4);
    int numTones = Tones.values().length;
    return new Note(Tones.getToneAtToneVal(pitch % numTones), (pitch / numTones) - 1,
            startingBeat, (endBeat - startingBeat) + 1, instrument, volume);
  }

  /**
   * Converts this Note to the ArrayList of Integer format for a note in preparation for MIDI
   * playback. The output ArrayList of Integer will be formatted as follows, (int startingBeat,
   * int endBeat, int instrument, int pitch, int volume). Starting beat is the int where a beat
   * begins, endBeat is an int beat that represents what beat a note stops playing at. int
   * instrument represents the MIDI integer instrument this note will play as. int pitch is the
   * official MIDI int pitch value this note will play as. Finally int volume represents the MIDI
   * volume value a note will play as.
   */
  public ArrayList<Integer> convertNoteToArrayListInteger() {
    ArrayList<Integer> primitiveNote = new ArrayList<Integer>(Arrays.asList(
            this.getStartingBeat(),
            this.getStartingBeat() + this.getBeats() - 1,
            this.getInstrument(),
            this.getPitch().convertPitchToIntPitch(),
            this.getVolume()));
    return primitiveNote;
  }

  /**
   * Equality method for comparing a note with another object.
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
      return this.startingBeat == compare.startingBeat && this.beats == compare.beats
              && this.instrument == compare.instrument && this.pitch.equals(compare.pitch)
              && this.volume == compare.volume;
    }
  }

  /**
   * Hashcode method for notes.
   *
   * @return The hashcode int of this note.
   */
  public int hashCode() {
    return this.pitch.hashCode() * ((this.startingBeat * this.beats) * 21) +
            (this.volume * this.instrument);
  }

  /**
   * Returns this note's pitch.
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
   * Returns the int representing the note's instrument.
   *
   * @return The note's instrument value.
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
