package cs3500.music.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * A Comparator for Pitches, used in sorting the TreeMap in music pieces. It compares two pitches
 * by their Tone and octave. Rules for greater than or less then are as follows: A pitch of a
 * higher octave is greater. Should octaves be the same, Tone is considered next where the
 * ordering from least to greatest is C, C#, D, D#, E, F, F#, G, G#, A, A#, B. Should both octave
 * and tone be the same, then they are equivalent.
 */
public class PitchComparator implements Comparator<Pitch> {
  @Override
  public int compare(Pitch o1, Pitch o2) {
    if (o1.equals(o2)) {
      return 0;
    } else if (o1.getOctave() < o2.getOctave()) {
      return -1;
    } else if (o1.getOctave() > o2.getOctave()) {
      return 1;
    } else {
      ArrayList<Tones> values = new ArrayList<Tones>(Arrays.asList(Tones.values()));
      int firstIndex = values.indexOf(o1.getTone());
      int secondIndex = values.indexOf(o2.getTone());
      return firstIndex - secondIndex;
    }
  }
}
