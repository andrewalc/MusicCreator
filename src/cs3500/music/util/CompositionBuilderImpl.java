package cs3500.music.util;

import cs3500.music.model.IMusicEditorModel;
import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.Tones;


public class CompositionBuilderImpl implements CompositionBuilder<IMusicEditorModel> {

  IMusicEditorModel model;

  public CompositionBuilderImpl() {
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
            start, end - start, instrument, volume);
    return this;
  }
}
