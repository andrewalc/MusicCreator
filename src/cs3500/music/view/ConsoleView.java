package cs3500.music.view;

/**
 * A Console view implementation. Should receive a model's toString method and relay it to console.
 */
public class ConsoleView implements IMusicEditorView {

  String view = "";

  /**
   * Constructor for console view. Should be given a MusicEditorModel's toString method.
   */
  ConsoleView(String view) {
    this.view = view;
  }

  @Override
  public void initialize() {
    System.out.print(view);
  }
}
