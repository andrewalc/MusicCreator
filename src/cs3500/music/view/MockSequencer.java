package cs3500.music.view;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.sound.midi.ControllerEventListener;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
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
