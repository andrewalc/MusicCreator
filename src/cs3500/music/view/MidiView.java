package cs3500.music.view;

import java.util.ArrayList;
import java.util.Map;


import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

import cs3500.music.controller.KeyboardListener;
import cs3500.music.controller.PianoMouseListener;

/**
 * A Music Editor View for playing midis.
 * EDIT: Updated to support new interface methods needed for functionality.
 * Also removed the use of a model, instead uses the map of notes as input.
 */
public class MidiView implements IMusicEditorView {

  private Map<Integer, ArrayList<ArrayList<Integer>>> notes;

  private Sequencer sequencer;

  private int tempo;

  /**
   * Constructor for a midi view. Require the input of the Music Editor Model to play the music of.
   */
  private MidiView(Map<Integer, ArrayList<ArrayList<Integer>>> allNotes) {
    this.notes = allNotes;

    try {
      this.sequencer = MidiSystem.getSequencer();
      sequencer.open();
    } catch (MidiUnavailableException e) {
      System.out.println(e.getMessage());
    }

  }

  public void setTempo(int tempo) {
    this.tempo = tempo;
  }

  /**
   * Builder class for a MidiView.
   */
  public static final class MidiViewBuilder {
    MidiView view;

    /**
     * Constructor for a MidiViewBuilder, requires a IMusicEditorModel to build a view of.
     * Initially built with a standard MidiSystem sequencer.
     *
     * @param allNotes Map of notes to build a view of.
     */
    public MidiViewBuilder(Map<Integer, ArrayList<ArrayList<Integer>>> allNotes) {
      this.view = new MidiView(allNotes);
    }

    /**
     * Builds the constructed MIDI view.
     *
     * @return A Midi view this builder created.
     */
    public MidiView build() {
      return view;
    }

    /**
     * Sets a new sequencer to override the default MidiSequencer.
     *
     * @param sequencer New sequencer to set.
     * @return The MIDIViewBuilder
     */
    public MidiViewBuilder setSequencer(Sequencer sequencer) {
      this.view.setSequencer(sequencer);
      return this;
    }

    /**
     * Sets a new tempo.
     *
     * @param tempo Tempo to set
     * @return The MIDIViewBuilder
     */
    public MidiViewBuilder setTempo(int tempo) {
      this.view.setTempo(tempo);
      return this;
    }
  }

  /**
   * Method to set this view's sequencer to another sequencer.
   *
   * @param sequencer New Sequencer to set to.
   */
  public void setSequencer(Sequencer sequencer) {
    this.sequencer = sequencer;
  }

  private int getModelMaxBeats() {
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

  /**
   * Returns all notes that are playing at the given beat in the given Map. Notes must be
   * following the ArrayList of Integer format, represented as (int startingBeat,
   * int endBeat, int instrument, int pitch, int volume).
   *
   * @param beat The beat to fetch notes playing at.
   * @return An arraylist of notes playing at the given beat.
   * @throws IllegalArgumentException If the given beat is out of range, must be atleast 0 and not
   *                                  exceed maxBeats.
   */
  private ArrayList<ArrayList<Integer>> getNotesAtBeat(int beat) throws
          IllegalArgumentException {
    if (beat < 0 || beat > this.getModelMaxBeats()) {
      throw new IllegalArgumentException("beat must be within the beat bounds of the music piece.");
    }
    ArrayList<ArrayList<Integer>> notesAtBeat = new ArrayList<ArrayList<Integer>>();
    for (ArrayList<ArrayList<Integer>> pitchBucket : notes.values()) {
      for (ArrayList<Integer> note : pitchBucket) {
        int startingBeat = note.get(0);
        int endBeat = note.get(1);
        if (beat >= startingBeat && beat < endBeat + 1) {
          notesAtBeat.add(note);
        }
      }
    }
    return notesAtBeat;
  }


  /**
   * Relevant classes and methods from the javax.sound.midi library:
   * <ul>
   * <li>{@link MidiSystem#getSynthesizer()}</li>
   * <li>{@link Synthesizer}
   * <ul>
   * <li>{@link Synthesizer#open()}</li>
   * <li>{@link Synthesizer#getReceiver()}</li>
   * <li>{@link Synthesizer#getChannels()}</li>
   * </ul>
   * </li>
   * <li>{@link Receiver}
   * <ul>
   * <li>{@link Receiver#send(MidiMessage, long)}</li>
   * <li>{@link Receiver#close()}</li>
   * </ul>
   * </li>
   * <li>{@link MidiMessage}</li>
   * <li>{@link ShortMessage}</li>
   * <li>{@link MidiChannel}
   * <ul>
   * <li>{@link MidiChannel#getProgram()}</li>
   * <li>{@link MidiChannel#programChange(int)}</li>
   * </ul>
   * </li>
   * </ul>
   *
   * @see <a href="https://en.wikipedia.org/wiki/General_MIDI"> https://en.wikipedia
   * .org/wiki/General_MIDI </a>
   */


  /**
   * Puts all of the notes in the model in the sequencer. Any pitch above G#9 (128) will be
   * ignored as the midi player cannot go higher.
   *
   * @throws InvalidMidiDataException If sequence is given an unsupported division type.
   */
  public void updateNotes() throws InvalidMidiDataException {

    // Set up a sequence
    Sequence sequence = new Sequence(Sequence.PPQ, 1);
    Track seqTrack = sequence.createTrack();


    for (int beat = 0; beat <= this.getModelMaxBeats(); beat++) {
      ArrayList<ArrayList<Integer>> currentNotes = this.getNotesAtBeat(beat);
      // Play all notes at the current beat.
      for (ArrayList<Integer> note : currentNotes) {
        int startingBeat = note.get(0);
        int endBeat = note.get(1);
        int instrument = note.get(2);
        int pitch = note.get(3);
        int volume = note.get(4);
        int channel = 0;
        if (startingBeat == beat && pitch < 128) {
          // Percussion is on channel 9
          MidiMessage start = new ShortMessage(ShortMessage.NOTE_ON, channel, pitch, volume);
          MidiMessage change = new ShortMessage(ShortMessage.PROGRAM_CHANGE, channel,
                  instrument, 0);
          MidiMessage stop = new ShortMessage(ShortMessage.NOTE_OFF, channel, pitch, volume);
          seqTrack.add(new MidiEvent(change, startingBeat));
          seqTrack.add(new MidiEvent(start, startingBeat));
          seqTrack.add(new MidiEvent(stop, endBeat));
        }
      }
    }

    System.out.println("Music built successfully.");
    // put the notes in the sequencer.
    sequencer.setSequence(sequence);


  }

  @Override
  public void initialize() {
    try {
      updateNotes();
    } catch (InvalidMidiDataException e) {
      System.out.println(e.getMessage());
    }
  }

  @Override
  public void addKeyListener(KeyboardListener keyboardListener) {

  }

  @Override
  public void forwardOneBeat() {
    long potentialTick = sequencer.getTickPosition() + 1;
    if (potentialTick <= sequencer.getTickLength()) {
      sequencer.setTickPosition(potentialTick);
    }
  }

  @Override
  public void backOneBeat() {
    long potentialTick = sequencer.getTickPosition() - 1;
    if (potentialTick >= 0) {
      sequencer.setTickPosition(potentialTick);
    }
  }

  @Override
  public void startMusic() {
    sequencer.start();
    sequencer.setTempoInMPQ(this.tempo);
  }

  public void updateTempo() {
    sequencer.setTempoInMPQ(this.tempo);
  }

  @Override
  public void pauseMusic() {
    sequencer.stop();
  }

  @Override
  public void goToBeginning() {
    sequencer.setTickPosition(0);
  }

  @Override
  public void goToEnd() {
    sequencer.setTickPosition(sequencer.getTickLength());

  }

  @Override
  public int getMaxBeat() {
    return (int) sequencer.getTickLength();
  }

  @Override
  public void setCurrentBeat(int currentBeat) {
    sequencer.setTickPosition(currentBeat);
  }

  @Override
  public int getCurrentBeat() {
    return (int) sequencer.getTickPosition();
  }

  @Override
  public void updateCurrentBeat() {

  }


  @Override
  public boolean isActive() {
    return sequencer.isOpen();
  }

  @Override
  public boolean isPlayingMusic() {
    return sequencer.isRunning();
  }

  @Override
  public void addMouseListener(PianoMouseListener mouseListener) {

  }

  @Override
  public int getKeyboardKeyPressed() {
    return 0;
  }

  @Override
  public void updateVisView(Map<Integer, ArrayList<ArrayList<Integer>>> allNotes) {
  }

  @Override
  public void rebuildMusic(Map<Integer, ArrayList<ArrayList<Integer>>> allNotes) {
    try {
      int keepCurrentBeat = this.getCurrentBeat();
      this.notes = allNotes;
      sequencer.close();
      this.sequencer = (MidiSystem.getSequencer());
      sequencer.open();
      updateNotes();
      setCurrentBeat(keepCurrentBeat);
    } catch (InvalidMidiDataException e) {
      e.getMessage();
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }
  }
}
