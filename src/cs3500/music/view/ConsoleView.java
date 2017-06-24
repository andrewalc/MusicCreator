package cs3500.music.view;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import cs3500.music.controller.KeyboardListener;
import cs3500.music.controller.PianoMouseListener;
import cs3500.music.model.Tones;

/**
 * A Console view implementation. Should receive a model's toString method and relay it to console.
 * EDIT: Updated to support new interface methods needed for functionality, although most of
 * these will not see use in the console view.
 */
public class ConsoleView implements IMusicEditorView {

  private Map<Integer, ArrayList<ArrayList<Integer>>> notes;
  public static final String STR_NOTE_HEAD = "  X  ";
  public static final String STR_NOTE_SUSTAIN = "  |  ";
  public static final String STR_NOTE_EMPTY = "     ";

  /**
   * Constructor for console view. Should be given a MusicEditorModel's toString method.
   */
  public ConsoleView(Map<Integer, ArrayList<ArrayList<Integer>>> notes) {
    Map sortedNotes = new TreeMap<>(new IntegerComparator());
    sortedNotes.putAll(notes);
    this.notes = sortedNotes;
  }

  @Override
  public void initialize() {
    System.out.print(this.toString());
  }


  @Override
  public int getMaxBeat() {
    int potentialMaxBeats = 0;
    for (ArrayList<ArrayList<Integer>> pitchList : this.notes.values()) {
      for (ArrayList<Integer> note : pitchList) {
        // if a note's ending beat is larger than the current potential last beat, update potential.
        int endBeat = note.get(1);
        if (endBeat > potentialMaxBeats) {
          potentialMaxBeats = endBeat;
        }
      }
    }
    return potentialMaxBeats;
  }

  @Override
  public String toString() {
    String result = "";
    if (this.getNumberOfNotes() == 0) {
      return "";
    }
    result += this.generatePitchHeaderString();
    // getMaxBeat + 1 to show extra measure
    for (int currBeat = 0; currBeat <= this.getMaxBeat() + 1; currBeat++) {
      result += this.generateBeatRow(currBeat);
    }
    return result;
  }

  /**
   * Converts a MIDI pitch to a string.
   *
   * @param pitch the MIDI pitch to convert.
   * @return A String representing the pitch.
   */
  private String convertIntPitchToStringPitch(int pitch) {
    int numTones = Tones.values().length;
    return Tones.getToneAtToneVal(pitch % numTones).toString() + ((pitch / numTones) - 1);
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
    int beatPadding = ("" + this.getMaxBeat()).length();
    int highest = this.getHighestPitch();
    int currPitch = this.getLowestPitch();
    for (int i = 0; i < beatPadding; i++) {
      result += " ";
    }
    while (true) {
      result += this.generatePitchSpacing(convertIntPitchToStringPitch(currPitch));
      if (currPitch == highest) {
        break;
      } else {
        // get next pitch
        currPitch = currPitch + 1;
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
    int beatPadding = ("" + this.getMaxBeat()).length();
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
    int currPitch = this.getLowestPitch();
    int highest = this.getHighestPitch();
    // Start with the beat number with appropriate space padding.
    result += this.generateBeatSpacing(currBeat);

    while (true) {
      // If this pitch has no notes, there are no notes to be played.
      if (!this.notes.containsKey(currPitch)) {
        result += "     ";

      } else {
        ArrayList<String> candidates = new ArrayList<String>();
        for (ArrayList<Integer> note : this.notes.get(currPitch)) {
          // Check if this note should be playing at the current beat, and if so add it's
          // note symbol is added to the list of candidates.
          if (currBeat >= note.get(0) && currBeat <= note.get(1)) {
            candidates.add(noteSymbolString(note.get(0), note.get(1), currBeat));
          }
        }
        // Choose a symbol to print out. If there is any note head it takes priority, followed by
        // any sustains. If the candidates list is empty, an empty note symbol is printed.
        if (candidates.contains(STR_NOTE_HEAD)) {
          result += STR_NOTE_HEAD;
        } else if (candidates.contains(STR_NOTE_SUSTAIN)) {
          result += STR_NOTE_SUSTAIN;
        } else {
          result += STR_NOTE_EMPTY;
        }
      }
      // Continue appending more note symbols until the current pitch has reached the highest
      // pitch in this piece of music.
      if (currPitch != highest) {
        currPitch = currPitch + 1; // next beat
      } else {
        break;
      }
    }
    return result + "\n";
  }

  private String noteSymbolString(Integer startingBeat, Integer endBeat, int currBeat) {
    if (currBeat == startingBeat) {
      return STR_NOTE_HEAD;
    } else if (currBeat > startingBeat && currBeat <= endBeat) {
      return STR_NOTE_SUSTAIN;
    } else {
      return STR_NOTE_EMPTY;
    }
  }

  /**
   * Returns the total number of notes contained in the editor's Map of notes.
   *
   * @return Number of notes in the editor.
   */
  private int getNumberOfNotes() {
    int count = 0;
    for (ArrayList<ArrayList<Integer>> pitchBucket : notes.values()) {
      count += pitchBucket.size();
    }
    return count;
  }


  /**
   * Returns the MIDI pitch integer value for the lowest pitch played by a note in the editor.
   *
   * @return MIDI pitch int of the lowest pitch found in the editor.
   */
  private int getLowestPitch() {
    ArrayList<Integer> pitches = new ArrayList<Integer>(this.notes.keySet());
    if (pitches.size() > 0) {
      return pitches.get(0);
    } else {
      throw new IllegalArgumentException("There are no pitches in the editor.");
    }
  }

  /**
   * Returns the MIDI pitch integer value for the highest pitch played by a note in the editor.
   *
   * @return MIDI pitch int of the highest pitch found in the editor.
   */
  private int getHighestPitch() {
    ArrayList<Integer> pitches = new ArrayList<Integer>(this.notes.keySet());
    if (pitches.size() > 0) {
      return pitches.get(pitches.size() - 1);
    } else {
      throw new IllegalArgumentException("There are no pitches in the editor.");
    }
  }


  // Console view does not need to respond to any of these methods.
  @Override
  public void addKeyListener(KeyboardListener keyboardListener) {
    //STUB, does not apply to view.
  }

  @Override
  public void forwardOneBeat() {
    //STUB, does not apply to view.
  }

  @Override
  public void backOneBeat() {
    //STUB, does not apply to view.

  }

  @Override
  public void startMusic() {
    //STUB, does not apply to view.

  }

  @Override
  public void pauseMusic() {
    //STUB, does not apply to view.

  }

  @Override
  public void goToBeginning() {
    //STUB, does not apply to view.

  }

  @Override
  public void goToEnd() {
    //STUB, does not apply to view.

  }

  @Override
  public void setCurrentBeat(int currentBeat) {
    //STUB, does not apply to view.

  }

  @Override
  public int getCurrentBeat() {
    //STUB, does not apply to view.
    return 0;
  }

  @Override
  public void updateCurrentBeat() {
    //STUB, does not apply to view.

  }


  @Override
  public boolean isActive() {
    return true;
  }

  @Override
  public boolean isPlayingMusic() {
    return false;
  }

  @Override
  public void addMouseListener(PianoMouseListener mouseListener) {
    //STUB, does not apply to view.

  }

  @Override
  public int getPianoKeyPressed() {
    //STUB, does not apply to view.
    return 0;
  }

  @Override
  public void updateVisAddNotes(Map<Integer, ArrayList<ArrayList<Integer>>> allNotes) {
    //STUB, does not apply to view.

  }

  @Override
  public void rebuildMusic(Map<Integer, ArrayList<ArrayList<Integer>>> allNotes) {
    //STUB, does not apply to view.

  }

  @Override
  public void receiveRepeatPairs(Map<Integer, Integer> repeatPairs) {
    //STUB, does not apply to view.
  }

  @Override
  public Map<Integer, Integer> getRepeatPairs() {
    //STUB, does not apply to view.
    return null;
  }

  @Override
  public void resetRepeatPassings() {
    //STUB, does not apply to view.
  }

  @Override
  public void setTempo(int tempo) {
    //STUB, does not apply to view.
  }
}
