package cs3500.music.model;


import java.util.ArrayList;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Class for the music editor model implementation.
 * EDIT:
 * Changed add, remove, and modify methods to take in Tone and ints to build a note rather than
 * requesting a note iteself.
 */
public class MusicEditorModel implements IMusicEditorModel {
  private Piece piece;

  public MusicEditorModel() {
    this.newPiece();
  }

  @Override
  public void newPiece() {
    this.piece = new Piece();
  }

  @Override
  public void loadPiece(Piece piece) throws NullPointerException {
    if (piece == null) {
      throw new NullPointerException("Given piece is null.");
    }
    this.piece = piece;
  }

  @Override
  public Piece getPiece() {
    return new Piece(this.piece);
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
  public ArrayList<Note> getNotesAtBeat(int beat) throws IllegalArgumentException {
    if (beat < 0 || beat > this.piece.getMaxBeats()) {
      throw new IllegalArgumentException("beat must be within the beat bounds of the music piece.");
    }
    ArrayList<Note> notesAtBeat = new ArrayList<Note>();
    for (ArrayList<Note> pitchList : this.piece.getAllNotes().values()) {
      for (Note note : pitchList) {
        if (beat >= note.getStartingBeat() && beat <= note.getStartingBeat() + note.getBeats()) {
          notesAtBeat.add(note);
        }
      }
    }
    return notesAtBeat;
  }

  @Override
  public void combinePieceOnTop(Piece other) throws IllegalArgumentException {
    Map<Pitch, ArrayList<Note>> otherNotes = other.getAllNotes();
    for (ArrayList<Note> pitchList : otherNotes.values()) {
      for (Note note : pitchList) {
        this.addNote(note.getPitch().getTone(), note.getPitch().getOctave(), note.getStartingBeat(),
                note.getBeats(), note.getInstrument(), note.getVolume());
      }
    }
  }

  @Override
  public void combinePieceAtEnd(Piece other) {
    // add to dummy to prevent concurrent modification errors
    Piece dummy = new Piece();
    for (ArrayList<Note> pitchList : this.piece.getAllNotes().values()) {
      for (Note note : pitchList) {
        dummy.addNote(note);
      }
    }
    Map<Pitch, ArrayList<Note>> otherNotes = other.getAllNotes();
    for (ArrayList<Note> pitchList : otherNotes.values()) {
      for (Note note : pitchList) {
        dummy.addNote(new Note(note.getPitch().getTone(), note.getPitch().getOctave(), note
                .getStartingBeat() + this.piece.getMaxBeats() + 1, note.getBeats(),
                note.getInstrument(), note.getVolume()));
      }
    }
    this.loadPiece(dummy);
  }

  @Override
  public String toString() {
    return this.piece.toString();

  }
}
