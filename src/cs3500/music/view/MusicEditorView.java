package cs3500.music.view;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.*;

import static java.awt.GridBagConstraints.HORIZONTAL;
import static java.awt.GridBagConstraints.LAST_LINE_END;
import static java.awt.GridBagConstraints.PAGE_START;

/**
 * Created by Andrew Alcala on 6/12/2017.
 */
public class MusicEditorView extends JFrame implements IMusicEditorView {

  private int currentBeat;
  private int maxBeats;

  public MusicEditorView(Map<Integer, ArrayList<ArrayList<Integer>>> notes, int maxBeats) {
    super("MIDI Music Editor");
    this.currentBeat = 0;
    this.maxBeats = maxBeats;
    setSize(1600, 900);
    setResizable(true);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setBackground(Color.WHITE);


    TreeMap<Integer, ArrayList<ArrayList<Integer>>> sortedNotes = new TreeMap<>(new
            IntegerComparator());
    sortedNotes.putAll(notes);



    //menu bar stuff
    JMenuBar menuBar = new JMenuBar();
    JMenu fileMenu = new JMenu("File", true);
    menuBar.add(fileMenu);
    JMenuItem mItemNew = new JMenuItem("New");
    JMenuItem mItemLoad = new JMenuItem("Load");
    JMenuItem mItemSave = new JMenuItem("Save");
    JMenuItem mItemExit = new JMenuItem("Exit");
    fileMenu.add(mItemNew);
    fileMenu.add(mItemLoad);
    fileMenu.add(mItemSave);
    fileMenu.add(mItemExit);
    setJMenuBar(menuBar);



    // running stuff
    JPanel container = new JPanel();
    container.setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();

    // Two panels
    KeyboardPanel keyboardPanel = new KeyboardPanel(this.getNotesAtBeat(notes, currentBeat));
    EditorPanel editorPanel = new EditorPanel(sortedNotes, maxBeats);


    // Enable scrolling
    JScrollPane editorScrPanel = new JScrollPane(editorPanel);
    editorScrPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    editorScrPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    editorScrPanel.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));
    c.anchor = PAGE_START;
    c.fill = HORIZONTAL;
    c.gridx = 0;
    c.gridy = 0;
    c.ipadx = (int) editorScrPanel.getPreferredSize().getWidth();
    c.ipady = (int) (getHeight() + 100 - (keyboardPanel.getPreferredSize().getHeight()));
    container.add(editorScrPanel, c);
    c.anchor = LAST_LINE_END;
    c.gridx = 0;
    c.gridy = 1;
    c.ipady = (int) (keyboardPanel.getPreferredSize().getHeight());
    c.insets = new Insets(20, 0, 100, 0);  //top padding
    container.add(keyboardPanel, c);
    container.setFocusable(true);
    container.requestFocusInWindow();
    container.addKeyListener(new KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {

      }

      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 37 && currentBeat > 0) {
          currentBeat--;
          editorPanel.setCurrentBeat(currentBeat);
          keyboardPanel.setCurrentNotes(getNotesAtBeat(notes, currentBeat));
        } else if (e.getKeyCode() == 39 && currentBeat < maxBeats) {
          currentBeat++;
          editorPanel.setCurrentBeat(currentBeat);
          keyboardPanel.setCurrentNotes(getNotesAtBeat(notes, currentBeat));
        }
        repaint();
      }

      @Override
      public void keyReleased(KeyEvent e) {

      }
    });
    getContentPane().add(container);
  }

  public ArrayList<ArrayList<Integer>> getNotesAtBeat(Map<Integer, ArrayList<ArrayList<Integer>>>
                                                              notes, int beat) throws
          IllegalArgumentException {
    if (beat < 0 || beat > maxBeats) {
      throw new IllegalArgumentException("beat must be within the beat bounds of the music piece.");
    }
    ArrayList<ArrayList<Integer>> notesAtBeat = new ArrayList<ArrayList<Integer>>();
    for (ArrayList<ArrayList<Integer>> pitchBucket : notes.values()) {
      for (ArrayList<Integer> note : pitchBucket) {
        int startingBeat = note.get(0);
        int endBeat = note.get(1);
        if (beat >= startingBeat && beat < endBeat + 1) {
          notesAtBeat.add(note);
        }
      }
    }
    return notesAtBeat;
  }

  @Override
  public void initialize() {
    this.setVisible(true);
  }
}
