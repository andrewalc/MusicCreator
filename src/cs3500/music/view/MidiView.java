package cs3500.music.view;

import java.util.ArrayList;

import javax.sound.midi.*;

import cs3500.music.model.IMusicEditorModel;

/**
 * A Music Editor View for playing midis.
 */
public class MidiView implements IMusicEditorView {

  private final IMusicEditorModel model;

  /**
   * Constructor for a midi view. Require the input of the Music Editor Model to play the music of.
   *
   * @param model a Music Editor Model to play notes off of.
   */
  public MidiView(IMusicEditorModel model) {
    this.model = model;
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
   * Plays all of the notes in the model in sequence.
   *
   * @throws InvalidMidiDataException If sequence is given an unsupported division type.
   */
  public void playNotes() throws InvalidMidiDataException {
    try {
      int tempo = model.getTempo();
      // Set up an empty sequence with an empty track
      Sequencer sequencer = MidiSystem.getSequencer();
      sequencer.open();
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
      System.out.println("Notes parsed successfully.");

      // Play the music!
      sequencer.setSequence(sequence);
      sequencer.start();
      sequencer.setTempoInMPQ(tempo);

    } catch (MidiUnavailableException e) {
      System.out.println(e.getMessage());
    }
  }

  @Override
  public void initialize() {
    try {
      playNotes();
    } catch (InvalidMidiDataException e) {
      System.out.println(e.getMessage());
    }
  }
}
