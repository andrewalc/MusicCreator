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
import cs3500.music.model.IMusicEditorModel;

/**
 * A Music Editor View for playing midis.
 * EDIT: Updated to support new interface methods needed for functionality.
 */
public class MidiView implements IMusicEditorView {

  private IMusicEditorModel model;

  private Sequencer sequencer;

  /**
   * Constructor for a midi view. Require the input of the Music Editor Model to play the music of.
   *
   * @param model a Music Editor Model to play notes off of.
   */
  private MidiView(IMusicEditorModel model) {
    this.model = model;

    try {
      this.sequencer = MidiSystem.getSequencer();
      sequencer.open();
    } catch (MidiUnavailableException e) {
      System.out.println(e.getMessage());
    }

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
     * @param model Model to build a view of.
     */
    public MidiViewBuilder(IMusicEditorModel model) {
      this.view = new MidiView(model);
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
  }

  /**
   * Method to set this view's sequencer to another sequencer.
   *
   * @param sequencer New Sequencer to set to.
   */
  public void setSequencer(Sequencer sequencer) {
    this.sequencer = sequencer;
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
   * Puts all of the notes in the model in the sequencer.
   *
   * @throws InvalidMidiDataException If sequence is given an unsupported division type.
   */
  public void updateNotes() throws InvalidMidiDataException {
    // Set up a sequence
    Sequence sequence = new Sequence(Sequence.PPQ, 1);
    Track seqTrack = sequence.createTrack();


    for (int beat = 0; beat < model.getMaxBeats(); beat++) {
      ArrayList<ArrayList<Integer>> currentNotes = model.getNotesAtBeat(beat);
      // Play all notes at the current beat.
      for (ArrayList<Integer> note : currentNotes) {
        int startingBeat = note.get(0);
        int endBeat = note.get(1);
        int instrument = note.get(2);
        int pitch = note.get(3);
        int volume = note.get(4);
        int channel = 0;
        if (startingBeat == beat) {
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
    if (potentialTick <= sequencer.getTickLength() + 1) {
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
    sequencer.setTempoInMPQ(model.getTempo());
  }

  public void updateTempo() {
    sequencer.setTempoInMPQ(model.getTempo());
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
    sequencer.setTickPosition(sequencer.getTickLength() + 1);

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
  public void updateView(Map<Integer, ArrayList<ArrayList<Integer>>> allNotes) {

  }
}
