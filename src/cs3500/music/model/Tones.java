package cs3500.music.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Enumeration representing all tones C through B ranging from octave 1 to octave 10.
 */
public enum Tones {
  C("C", 0), C_SHARP("C#", 1), D("D", 2), D_SHARP("D#", 3), E("E", 4), F("F", 5), F_SHARP("F#",
          6), G("G", 7),G_SHARP("G#", 8), A("A", 9), A_SHARP("A#", 10), B("B", 11);
  private final String tone;
  private final int toneVal;
  private final static int NUM_TONES = 12;


  /**
   * Constructor for a Tone, requires an String representing this tone's pitch structured with the
   * pitch letter first, and a sharp (if it is present).
   * is in. Ex. C, C#, A, E, Etc.
   *
   * @param tone String representing the tone, structured as described above.
   */
  Tones(String tone, int toneVal) {
    this.tone = tone;
    this.toneVal = toneVal;
  }

  /**
   * Method that returns the tone's pitch as a string of the pitch letter and a sharp should it
   * have one.
   *
   * @return String representing the tone's pitch.
   */
  public String getToneStr() {
    return this.tone;
  }

  /**
   * Method that returns the tone's tone value, C starting at 0 and B ending at 11.
   * @return This tone's tone value.
   */
  public int getToneVal() { return this.toneVal; }

  /**
   * Method that returns a tone that is one pitch higher than this tone.
   *
   * @return A Tone that is one pitch higher than this tone.
   */
  public Tones getNextTone() {
    return getPitchShiftedTone(1);
  }

  /**
   * Method that returns a tone that is one pitch lower than this tone.
   *
   * @return A Tone that is one pitch higher than this tone.
   */
  public Tones getPrevTone() {
    return getPitchShiftedTone(-1);
  }

  /**
   * Method that returns a tone that is a given integer of pitches away from this tone.
   * A shift integer is entered to determine how many pitches up or down the requested
   * tone should be. Should the requested tone be beyond or under scope of octaves in this
   * enumeration (Less than 1 or more than 10), an IllegalArgumentException is thrown.
   *
   * @return A Tone that is a given number of pitches higher or lower than this tone.
   */
  public Tones getPitchShiftedTone(int shift) {
    ArrayList<Tones> values = new ArrayList<Tones>(Arrays.asList(Tones.values()));
    // get the index of the next tone in the arraylist of all tones
    // (this tones index + the shift mod the size all tones) mod the size of all tones
    int potentialIndex =
            (values.indexOf(this) + (((shift % NUM_TONES) + NUM_TONES) % NUM_TONES))
                    % NUM_TONES;
    return values.get(potentialIndex);
  }

  public static Tones getToneAtToneVal(int toneVal) {
    ArrayList<Tones> tones = new ArrayList<Tones>(Arrays.asList(Tones.values()));
    return tones.get(toneVal);
  }

  /**
   * toString method returning the tone's pitch and octave as a string.
   *
   * @return The pitch and octave as a string.
   */
  public String toString() {
    return this.getToneStr();
  }
}