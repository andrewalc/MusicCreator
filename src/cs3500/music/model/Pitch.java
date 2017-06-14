package cs3500.music.model;

/**
 * Class representing a musical pitch, the combination of tone and octave.
 */
public class Pitch {

  private Tones tone;
  private int octave;

  /**
   * Constructor for pitches. Requires a valid tone (C C# D# E etc.) and the octave the tone is in.
   * Throws an error if the octave is not between 1 and 10.
   *
   * @throws IllegalArgumentException If the octave is not 1-10.
   * @throws NullPointerException     If tone is null.
   */
  public Pitch(Tones tone, int octave) throws NullPointerException, IllegalArgumentException {
    if (tone == null) {
      throw new NullPointerException("Tone is null");
    }
    if (octave < 1 || octave > 10) {
      throw new IllegalArgumentException("Must be an octave between 1 and 10");
    }
    this.tone = tone;
    this.octave = octave;
  }

  /**
   * Copy constructor for a pitch.
   *
   * @param pitch The pitch to make a copy of.
   * @throws NullPointerException If given pitch is null.
   */
  public Pitch(Pitch pitch) throws NullPointerException {
    if (pitch == null) {
      throw new NullPointerException("Pitch is null.");
    }
    this.tone = pitch.getTone();
    this.octave = pitch.getOctave();
  }

  /**
   * Getter for this pitch's tone.
   *
   * @return This pitch's tone.
   */
  public Tones getTone() {
    return tone;
  }

  /**
   * Getter for this pitch's octave.
   *
   * @return This pitch's octave.
   */
  public int getOctave() {
    return octave;
  }


  /**
   * Returns a pitch that is one higher in tone than this one. Should the next pitch be over the
   * 10 octave limit an IllegalArgumentException is thrown.
   *
   * @return A Pitch that is one higher in tone than this one.
   * @throws IllegalArgumentException if the new pitch is outside of the 1- 10 octave limit.
   */
  public Pitch getNextPitch() throws IllegalArgumentException {
    return getNextOrPrevPitch(this.tone.getNextTone(), true);
  }

  /**
   * Returns a pitch that is one higher in tone than this one. Should the next pitch be over the
   * 10 octave limit an IllegalArgumentException is thrown.
   *
   * @return A Pitch that is one higher in tone than this one.
   * @throws IllegalArgumentException if the new pitch is outside of the 1- 10 octave limit.
   */
  public Pitch getPrevPitch() throws IllegalArgumentException {
    return getNextOrPrevPitch(this.tone.getPrevTone(), false);

  }

  /**
   * Returns a pitch that is one higher or lower in tone than this one. Should the requested
   * pitch be outside of the 1- 10 octave limit an IllegalArgumentException is thrown. Requires
   * the input of the next tone and a cutOffTone indicating when the octave should go up should
   * the cutOffTone match the requested tone.
   *
   * @param potentialTone A tone that is one up or below this tone.
   * @param next          a boolean decided whether to get the next or previous tone.
   * @return A pitch that is one higher or lower than this one.
   * @throws IllegalArgumentException if the new pitch is outside of the 1- 10 octave limit.
   */
  private Pitch getNextOrPrevPitch(Tones potentialTone, boolean next) throws
          IllegalArgumentException {
    int potentialOctave = this.octave;
    if (next) {
      if (potentialTone == Tones.C) {
        potentialOctave += 1;
      }
    } else {
      if (potentialTone == Tones.B) {
        potentialOctave += -1;
      }
    }

    if (potentialOctave > 10 || potentialOctave < 1) {
      throw new IllegalArgumentException("Pitch must be in an octave between 1-10");
    }

    return new Pitch(potentialTone, potentialOctave);
  }


  /**
   * toString method for a Pitch. Formatted as Tone information first followed immediately by the
   * octave it is in. (ex. C2, D#7, E10, etc.)
   *
   * @return A String representing this pitches tone and octave.
   */
  public String toString() {
    return tone.toString() + this.octave;
  }

  /**
   * Equality method for comparing a pitch with another object.
   *
   * @param other An object for comparison
   * @return boolean of whether this Pitch and the other object are equal.
   */
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    } else if (!(other instanceof Pitch)) {
      return false;
    } else {
      Pitch compare = (Pitch) other;
      return this.tone == compare.tone && this.octave == compare.octave;
    }
  }

  /**
   * Hashcode method for pitches.
   *
   * @return The hashcode int of this pitch.
   */
  public int hashCode() {
    return (this.tone.getToneVal() * 100) + this.octave;
  }
}
