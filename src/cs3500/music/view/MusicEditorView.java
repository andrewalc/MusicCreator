package cs3500.music.view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.*;

import cs3500.music.model.IMusicEditorModel;

/**
 * Created by Andrew Alcala on 6/12/2017.
 */
public class MusicEditorView extends JFrame {

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
    container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
    // Two panels
    KeyboardPanel keyboardPanel = new KeyboardPanel(this.getNotesAtBeat(notes, currentBeat));
    EditorPanel editorPanel = new EditorPanel(sortedNotes, maxBeats);


    // Enable scrolling
    JScrollPane editorScrPanel = new JScrollPane(editorPanel);
    editorScrPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    editorScrPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    editorScrPanel.setMaximumSize(editorPanel.getMaximumSize());

    container.add(editorScrPanel);
    container.add(keyboardPanel);
    container.setFocusable(true);
    container.requestFocusInWindow();
    container.addKeyListener(new KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {

      }

      @Override
      public void keyPressed(KeyEvent e) {
        System.out.println(e);
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
    setVisible(true);

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
        if (beat >= startingBeat && beat < endBeat) {
          notesAtBeat.add(note);
        }
      }
    }
    return notesAtBeat;
  }
}
