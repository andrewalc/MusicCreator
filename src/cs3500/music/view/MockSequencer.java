package cs3500.music.view;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.ControllerEventListener;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;
import javax.sound.midi.Transmitter;

/**
 * A Mock Sequencer used for testing MIDI view.
 */
public class MockSequencer implements Sequencer {
  private Appendable ap;

  public MockSequencer(Appendable ap) {
    this.ap = ap;
  }

  @Override
  public void open() throws MidiUnavailableException {
    try {
      this.ap.append("MockSequencer was opened.\n");
    } catch (IOException e) {
      e.getMessage();
    }

  }

  @Override
  public void setSequence(Sequence sequence) throws InvalidMidiDataException {
    try {
      this.ap.append("checking sequence \n");
      // We will return a list of notes
      //ArrayList<NoteTxt> notes = new ArrayList<>();

      int trackCount = 0;
      int messageCount = 0;
      // We will store messages as int arrays with pitch tick fired and volume in this arraylist.
      // {pitch, tick, volume}

      // ArrayList<Integer[]> represents the list of all messages contained in an arraylist of a
      // single track's messages.

      ArrayList<ArrayList<Integer[]>> allNoteOnMsgs = new ArrayList<>();

      for (Track track : sequence.getTracks()) {
        // The current track we are parsing needs a place in our {Pitch, Tick, volume} arraylist
        allNoteOnMsgs.add(new ArrayList<Integer[]>());

        int instrument = 0;

        // parse through all messages (events) in this track
        for (int i = 0; i < track.size(); i++) {

          MidiEvent event = track.get(i);

          // 144 = NOTE ON , 128 == NOTE OFF, if NOTE ON has volume (velocity) of 0 treat as NOTE
          // OFF.


          if (event.getMessage() instanceof ShortMessage) {

            ShortMessage message = (ShortMessage) event.getMessage();
            // The tick that this message is being fired.
            int tickMsgFired = (int) event.getTick();
            int msgCommand = message.getCommand();


            // If the message we have is a NOTE ON message add the {pitch, tick, volume} to our
            // arraylist.

            if (msgCommand == 144) {
              this.ap.append("Note On msg found\n");

              int pitch = message.getData1();
              int volume = message.getData2();
              // All messages of this track in our NOTE ON arraylist
              ArrayList<Integer[]> currentTrackNoteOns = allNoteOnMsgs.get(trackCount);

              // If NOTE ON volume == 0, treat as a NOTE OFF and find its matching NOTE ON starting
              // message. Once found, create a note and add it to the list.
              if (volume == 0) {
                for (int k = 0; k < currentTrackNoteOns.size(); k++) {
                  Integer[] noteOnMsg = currentTrackNoteOns.get(k);
                  if (noteOnMsg[0] == pitch) {
                    int startingTickMsgFired = noteOnMsg[1];
                    int startingBeat = startingTickMsgFired;
                    int endBeat = tickMsgFired;

                    // Volume was 0, now make sure its the original's.
                    volume = noteOnMsg[2];

                    // Create a note and print it.
                    //notes.add(new NoteTxt(startingBeat, endBeat, instrument, pitch, volume));
                    this.ap.append("Completed note pairing: " + new NoteTxt(startingBeat, endBeat,
                            instrument, pitch, volume).toString() + "\n");
                    // remove the NOTE ON information from the arraylist of pitch, tick.
                    currentTrackNoteOns.remove(noteOnMsg);
                    // We found our 'fake' NOTE ON's corresponding NOTE ON. We don't need to look or
                    // remove anything else further.
                    break;
                  }
                }
              } else {
                // This is a real NOTE ON message, add the {pitch tick volume} to NOTE ON arraylist.
                currentTrackNoteOns.add(new Integer[]{pitch, tickMsgFired, volume});
              }

            }
            // NOTE OFF message found. Find the matching NOTE ON message and create a note.
            else if (msgCommand == 128) {
              this.ap.append("Note Off msg found\n");

              int pitch = message.getData1();
              int volume = message.getData2();

              //In our developing arraylist, we want to get the arraylist that represents this
              // track's NOTE ON messages.
              ArrayList<Integer[]> currentTrackNoteOns = allNoteOnMsgs.get(trackCount);
              // Parse our arraylist and find this NOTE OFF msg's corresponding NOTE ON by looking
              // first NOTE ON that matches this NOTE OFF's pitch.
              for (int j = 0; j < currentTrackNoteOns.size(); j++) {
                Integer[] noteOnMsg = currentTrackNoteOns.get(j);
                if (noteOnMsg[0] == pitch) {
                  int startingTickFired = noteOnMsg[1];
                  int startingBeat = startingTickFired;
                  int endBeat = tickMsgFired;

                  // convert information into a note and store it.
                  //notes.add(new NoteTxt(startingBeat, endBeat, instrument, pitch, volume));
                  this.ap.append("Completed note pairing: " + new NoteTxt(startingBeat, endBeat,
                          instrument, pitch, volume).toString() + "\n");
                  // remove the NOTE ON information from the arraylist of NOTE ONS
                  currentTrackNoteOns.remove(noteOnMsg);
                  // We found our NOTE OFF's corresponding NOTE ON. We don't need to look or remove
                  // anything else further.
                  break;
                }
              }
            }
            // If the ShortMessage is a program change, get the instrument.
            else if (msgCommand == 192) {
              instrument = message.getData1();

            }
            messageCount++;
          }
        }
        // Time to check the next track.
        trackCount += 1;
      }
      this.ap.append(messageCount + " total short messages parsed successfully.\n");

    } catch (IOException e) {
      e.getMessage();
    }
  }


  @Override
  public void start() {
    try {
      this.ap.append("MockSequencer started playing.\n");
    } catch (IOException e) {
      e.getMessage();
    }
  }


  @Override
  public void setTempoInMPQ(float mpq) {
    try {
      this.ap.append("MockSequencer tempo set to " + mpq + "\n");
    } catch (IOException e) {
      e.getMessage();
    }
  }

  @Override
  public void setSequence(InputStream stream) throws IOException, InvalidMidiDataException {

  }

  @Override
  public Sequence getSequence() {
    return null;
  }

  @Override
  public void stop() {

  }

  @Override
  public boolean isRunning() {
    return false;
  }

  @Override
  public void startRecording() {

  }

  @Override
  public void stopRecording() {

  }

  @Override
  public boolean isRecording() {
    return false;
  }

  @Override
  public void recordEnable(Track track, int channel) {

  }

  @Override
  public void recordDisable(Track track) {

  }

  @Override
  public float getTempoInBPM() {
    return 0;
  }

  @Override
  public void setTempoInBPM(float bpm) {

  }

  @Override
  public float getTempoInMPQ() {
    return 0;
  }

  @Override
  public void setTempoFactor(float factor) {

  }

  @Override
  public float getTempoFactor() {
    return 0;
  }

  @Override
  public long getTickLength() {
    return 0;
  }

  @Override
  public long getTickPosition() {
    return 0;
  }

  @Override
  public void setTickPosition(long tick) {

  }

  @Override
  public long getMicrosecondLength() {
    return 0;
  }

  @Override
  public Info getDeviceInfo() {
    return null;
  }

  @Override
  public void close() {

  }

  @Override
  public boolean isOpen() {
    return false;
  }

  @Override
  public long getMicrosecondPosition() {
    return 0;
  }

  @Override
  public int getMaxReceivers() {
    return 0;
  }

  @Override
  public int getMaxTransmitters() {
    return 0;
  }

  @Override
  public Receiver getReceiver() throws MidiUnavailableException {
    return null;
  }

  @Override
  public List<Receiver> getReceivers() {
    return null;
  }

  @Override
  public Transmitter getTransmitter() throws MidiUnavailableException {
    return null;
  }

  @Override
  public List<Transmitter> getTransmitters() {
    return null;
  }

  @Override
  public void setMicrosecondPosition(long microseconds) {

  }

  @Override
  public void setMasterSyncMode(SyncMode sync) {

  }

  @Override
  public SyncMode getMasterSyncMode() {
    return null;
  }

  @Override
  public SyncMode[] getMasterSyncModes() {
    return new SyncMode[0];
  }

  @Override
  public void setSlaveSyncMode(SyncMode sync) {

  }

  @Override
  public SyncMode getSlaveSyncMode() {
    return null;
  }

  @Override
  public SyncMode[] getSlaveSyncModes() {
    return new SyncMode[0];
  }

  @Override
  public void setTrackMute(int track, boolean mute) {

  }

  @Override
  public boolean getTrackMute(int track) {
    return false;
  }

  @Override
  public void setTrackSolo(int track, boolean solo) {

  }

  @Override
  public boolean getTrackSolo(int track) {
    return false;
  }

  @Override
  public boolean addMetaEventListener(MetaEventListener listener) {
    return false;
  }

  @Override
  public void removeMetaEventListener(MetaEventListener listener) {

  }

  @Override
  public int[] addControllerEventListener(ControllerEventListener listener, int[] controllers) {
    return new int[0];
  }

  @Override
  public int[] removeControllerEventListener(ControllerEventListener listener, int[] controllers) {
    return new int[0];
  }

  @Override
  public void setLoopStartPoint(long tick) {

  }

  @Override
  public long getLoopStartPoint() {
    return 0;
  }

  @Override
  public void setLoopEndPoint(long tick) {

  }

  @Override
  public long getLoopEndPoint() {
    return 0;
  }

  @Override
  public void setLoopCount(int count) {

  }

  @Override
  public int getLoopCount() {
    return 0;
  }
}
