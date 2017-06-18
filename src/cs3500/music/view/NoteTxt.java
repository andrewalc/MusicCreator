package cs3500.music.view;

/**
 * Class that mimics the note format for outputting note to a txt file in the style of the  cs3500
 * assignment example files. The toString method will return the note in the style.
 */
public class NoteTxt {
  int startingBeat;
  int endBeat;
  int pitch;
  int instrument;
  int volume;

  /**
   * Constructor to construct a notetxt object. Require the startingbeat the note plays at, the
   * endBeat the note stops at, the MIDI int instrument value, the MIDI int pitch value, and the
   * volume.
   *
   * @param startingBeat Starting beat.
   * @param endBeat      Ending beat.
   * @param instrument   MIDI instrument value.
   * @param pitch        MIDI pitch value.
   * @param volume       volume level.
   */
  public NoteTxt(int startingBeat, int endBeat, int instrument, int pitch, int volume) {
    this.startingBeat = startingBeat;
    this.endBeat = endBeat;
    this.pitch = pitch;
    this.instrument = instrument;
    this.volume = volume;
  }


  @Override
  public String toString() {
    return "note " + startingBeat + " " + endBeat + " " + instrument + " " + pitch + " " + volume;
  }
}
