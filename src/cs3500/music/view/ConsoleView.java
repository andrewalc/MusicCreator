package cs3500.music.view;

/**
 * Created by Andrew Alcala on 6/15/2017.
 */
public class ConsoleView implements IMusicEditorView {

  String view = "";

  ConsoleView(String view) {
    this.view = view;
  }

  @Override
  public void initialize() {
    System.out.print(view);
  }
}
