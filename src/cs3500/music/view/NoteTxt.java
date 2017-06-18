package cs3500.music.view;

/**
 * Class mimic
 */
public class NoteTxt {
  int startingBeat;
  int endBeat;
  int pitch;
  int instrument;
  int volume;

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
