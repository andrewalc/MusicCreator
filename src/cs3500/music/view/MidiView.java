package cs3500.music.view;

import com.sun.media.sound.SimpleInstrument;
import com.sun.media.sound.SimpleSoundbank;

import java.util.ArrayList;

import javax.sound.midi.*;

import cs3500.music.model.IMusicEditorModel;

/**
 * A skeleton for MIDI playback
 */
public class MidiView implements IMusicEditorView {
  private final Synthesizer synth;
  private final Receiver receiver;
  private final IMusicEditorModel model;

  public MidiView(IMusicEditorModel model) throws MidiUnavailableException {
    this.synth = MidiSystem.getSynthesizer();
    this.receiver = synth.getReceiver();
    this.synth.open();
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
   * .org/wiki/General_MIDI
   * </a>
   */

  public void playNote() throws InvalidMidiDataException {
    int currentTime = 0;
    int tempo = model.getTempo();

    for (int beat = 0; beat < model.getMaxBeats(); beat++) {
      ArrayList<ArrayList<Integer>> currentNotes = model.getNotesAtBeat(beat);
      for (ArrayList<Integer> note : currentNotes) {

        int startingBeat = note.get(0);
        int endBeat = note.get(1);
        int instrument = note.get(2);
        int pitch = note.get(3);
        int volume = note.get(4);
        if (startingBeat == beat) {
          MidiMessage start = new ShortMessage(ShortMessage.NOTE_ON, 0, pitch, volume);
          MidiMessage change = new ShortMessage(ShortMessage.PROGRAM_CHANGE, 0, instrument, 0);
          MidiMessage stop = new ShortMessage(ShortMessage.NOTE_OFF, 0, pitch, volume);
          this.receiver.send(change, currentTime);
          this.receiver.send(start, currentTime);
          this.receiver.send(stop, this.synth.getMicrosecondPosition() + (tempo * (endBeat -
                  startingBeat + 1)));
        }
      }

      currentTime += tempo;

      try {

        Thread.sleep(tempo / 1000);

      } catch (InterruptedException e) {
        System.out.println(e.getMessage());
      }

    }

    /* 
    The receiver does not "block", i.e. this method
    immediately moves to the next line and closes the 
    receiver without waiting for the synthesizer to 
    finish playing. 
    
    You can make the program artificially "wait" using
    Thread.sleep. A better solution will be forthcoming
    in the subsequent assignments.
    */
    this.receiver.close(); // Only call this once you're done playing *all* notes
  }

  @Override
  public void initialize() {
    try {
      playNote();
    } catch (InvalidMidiDataException e) {
      System.out.println(e.getMessage());
    }
  }
}
