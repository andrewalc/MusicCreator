package cs3500.music.view;

/**
 * A Console view implementation. Should receive a model's toString method and relay it to console.
 */
public class ConsoleView implements IMusicEditorView {

  private String consoleOutput = "";

  /**
   * Constructor for console view. Should be given a MusicEditorModel's toString method.
   */
  ConsoleView(String consoleOutput) {
    this.consoleOutput = consoleOutput;
  }

  /**
   * Returns the string that will be outputted by initialize() to console when it is called.
   *
   * @return Console string to be outputted.
   */
  public String getConsoleOutput() {
    return consoleOutput;
  }

  @Override
  public void initialize() {
    System.out.print(consoleOutput);
  }
}
