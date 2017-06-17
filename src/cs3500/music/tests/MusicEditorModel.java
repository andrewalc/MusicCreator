package cs3500.music.tests;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;

import cs3500.music.util.*;

/**
 * Class for the music editor tests implementation.
 * EDIT:
 * Changed add, remove, and modify methods to take in Tone and ints to build a note rather than
 * requesting a note iteself.
 * Changed Note return types to be represented as an ArrayList of integers of size 6.
 * Changed instances of type Piece to a Hashmap of Integer to ArrayList ArrayList of Integers to
 * represent a collection of notes.
 */
public final class MusicEditorModel implements IMusicEditorModel {
  private Piece piece;

  public MusicEditorModel() {
    this.newPiece();
  }

  public static final class MusicEditorBuilder implements CompositionBuilder<IMusicEditorModel> {

    IMusicEditorModel model;

    public MusicEditorBuilder() {
      model = new MusicEditorModel();
    }

    @Override
    public IMusicEditorModel build() {
      return model;
    }

    @Override
    public CompositionBuilder<IMusicEditorModel> setTempo(int tempo) {
      model.setTempo(tempo);
      return this;
    }

    @Override
    public CompositionBuilder<IMusicEditorModel> addNote(int start, int end, int instrument, int
            pitch, int volume) {
      int numTones = Tones.values().length;
      model.addNote(Tones.getToneAtToneVal(pitch % numTones), (pitch / numTones) - 1,
              start, (end - start) + 1, instrument, volume);
      return this;
    }
  }

  @Override
  public void newPiece() {
    this.piece = new Piece();
  }

  @Override
  public void loadPiece(Map<Integer, ArrayList<ArrayList<Integer>>> piece) throws
          NullPointerException {
    if (piece == null) {
      throw new NullPointerException("Given piece is null.");
    }
    TreeMap<Pitch, ArrayList<Note>> allNotes = new TreeMap<Pitch, ArrayList<Note>>(new
            PitchComparator());

    for (Integer pitch : piece.keySet()) {
      allNotes.put(Pitch.convertIntPitchToPitch(pitch), new ArrayList<Note>());
    }
    for (ArrayList<ArrayList<Integer>> pitchBucket : piece.values()) {
      for (ArrayList<Integer> note : pitchBucket) {
        ArrayList<Note> correspondingPitchBucket = allNotes.get(Pitch.convertIntPitchToPitch(note
                .get
                (3)));
        correspondingPitchBucket.add(Note.convertArrayListIntegerToNote(note));
      }
    }
    this.piece = new Piece(allNotes);
    this.piece.updateEmptyMapBuckets();
  }

  private ArrayList<Integer> convertNoteToArrayListInteger(Note note) {
    ArrayList<Integer> primitiveNote = new ArrayList<Integer>(Arrays.asList(
            note.getStartingBeat(),
            note.getStartingBeat() + note.getBeats() - 1,
            note.getInstrument(),
            note.getPitch().hashCode(),
            note.getVolume()));
    return primitiveNote;
  }

  @Override
  public Map<Integer, ArrayList<ArrayList<Integer>>> getAllNotes() {
    Piece transfer = new Piece(this.piece);
    HashMap<Integer, ArrayList<ArrayList<Integer>>> noteMap = new HashMap<>();
    for (Pitch pitch : transfer.getAllNotes().keySet()) {
      noteMap.put(pitch.hashCode(), new ArrayList<ArrayList<Integer>>());
    }
    for (ArrayList<Note> pitchBucket : transfer.getAllNotes().values()) {
      for (Note note : pitchBucket) {
        ArrayList<Integer> convertedNote = convertNoteToArrayListInteger(note);
        ArrayList<ArrayList<Integer>> bucket = noteMap.get(note.getPitch().hashCode());
        bucket.add(convertedNote);
      }
    }
    return noteMap;
  }

  @Override
  public void addNote(Tones tone, int octave, int startingBeat, int beats, int instrument, int
          volume) throws
          NullPointerException {
    this.piece.addNote(new Note(tone, octave, startingBeat, beats, instrument, volume));
  }

  @Override
  public void removeNote(Tones tone, int octave, int startingBeat, int beats, int instrument, int
          volume) throws
          NullPointerException, NoSuchElementException {
    this.piece.removeNote(new Note(tone, octave, startingBeat, beats, instrument, volume));
  }

  @Override
  public void modifyNote(Tones tone, int octave, int startingBeat, int beats, int instrument, int
          volume, Tones modTone, int
                                 modOctave, int modStartingBeat, int modBeats, int modInstrument,
                         int modVolume)
          throws
          NullPointerException, IllegalArgumentException, NoSuchElementException {
    this.piece.modifyNote(new Note(tone, octave, startingBeat, beats, instrument, volume),
            modTone, modOctave, modStartingBeat, modBeats, modInstrument, modVolume);
  }

  @Override
  public ArrayList<ArrayList<Integer>> getNotesAtBeat(int beat) throws IllegalArgumentException {
    if (beat < 0 || beat > this.piece.getMaxBeats()) {
      throw new IllegalArgumentException("beat must be within the beat bounds of the music piece.");
    }
    ArrayList<ArrayList<Integer>> notesAtBeat = new ArrayList<ArrayList<Integer>>();
    for (ArrayList<Note> pitchBucket : this.piece.getAllNotes().values()) {
      for (Note note : pitchBucket) {
        if (beat >= note.getStartingBeat() && beat < note.getStartingBeat() + note.getBeats()) {
          notesAtBeat.add(convertNoteToArrayListInteger(note));
        }
      }
    }
    return notesAtBeat;
  }

  @Override
  public void combinePieceOnTop(Map<Integer, ArrayList<ArrayList<Integer>>> other) throws
          IllegalArgumentException {

    for (ArrayList<ArrayList<Integer>> pitchBucket : other.values()) {
      for (ArrayList<Integer> note : pitchBucket) {
        Note convertedNote = Note.convertArrayListIntegerToNote(note);
        this.addNote(convertedNote.getPitch().getTone(),
                convertedNote.getPitch().getOctave(),
                convertedNote.getStartingBeat(), convertedNote.getBeats(),
                convertedNote.getInstrument(), convertedNote.getVolume());
      }
    }
  }


  private int convertPitchToIntPitch(Pitch pitch) {
    return ((pitch.getOctave() + 1) * 12) + pitch.getTone().getToneVal();
  }

  @Override
  public void combinePieceAtEnd(Map<Integer, ArrayList<ArrayList<Integer>>> other) {
    // add to dummy to prevent concurrent modification errors
    Map<Integer, ArrayList<ArrayList<Integer>>> dummy = new HashMap<>();
    dummy.putAll(this.getAllNotes());


    // generates all buckets necessary
    for (Integer pitch : other.keySet()) {
      if (!dummy.containsKey(pitch)) {
        dummy.put(pitch, new ArrayList<ArrayList<Integer>>());
      }
    }

    for (ArrayList<ArrayList<Integer>> pitchBucket : other.values()) {
      for (ArrayList<Integer> note : pitchBucket) {
        int pitch = note.get(3);
        ArrayList<ArrayList<Integer>> notesBucket = dummy.get(pitch);
        ArrayList<Integer> shiftedNote = new ArrayList<>(Arrays.asList(note.get(0) +
                        this.getMaxBeats() + 1, note.get(1) + this.getMaxBeats() + 1, note.get(2),
                note.get(3), note.get(4)));
        notesBucket.add(shiftedNote);
      }
    }
    this.loadPiece(dummy);
  }

  @Override
  public int getMaxBeats() {
    return this.piece.getMaxBeats();
  }

  @Override
  public String toString() {
    return this.piece.toString();

  }

  @Override
  public int getTempo() {
    return this.piece.getTempo();
  }

  @Override
  public void setTempo(int tempo) {
    this.piece.setTempo(tempo);
  }

  @Override
  public int getLowestPitch() {
    ArrayList<Pitch> pitches = new ArrayList<Pitch>(this.piece.getAllNotes().keySet());
    if (pitches.size() > 0) {
      return this.convertPitchToIntPitch(pitches.get(0));
    } else {
      throw new IllegalArgumentException("There are no pitches in the editor.");
    }
  }

  @Override
  public int getHighestPitch() {
    ArrayList<Pitch> pitches = new ArrayList<Pitch>(this.piece.getAllNotes().keySet());
    if (pitches.size() > 0) {
      return this.convertPitchToIntPitch(pitches.get(pitches.size() - 1));
    } else {
      throw new IllegalArgumentException("There are no pitches in the editor.");
    }
  }

  @Override
  public int getNumberOfNotes() {
    return this.piece.getNumberOfNotes();
  }
}
