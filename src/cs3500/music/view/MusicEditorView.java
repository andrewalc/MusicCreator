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

  public MusicEditorView(Map<Integer, ArrayList<ArrayList<Integer>>> notes, int maxBeats) {
    super("MIDI Music Editor");
    currentBeat = 0;
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
    JPanel keyboardPanel = new KeyboardPanel();
    JPanel editorPanel = new EditorPanel(sortedNotes, maxBeats);

    // Enable scrolling
    JScrollPane editorScrPanel = new JScrollPane(editorPanel);
    editorScrPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    editorScrPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    editorScrPanel.setMaximumSize(editorPanel.getMaximumSize());

    container.add(editorScrPanel);
    container.add(keyboardPanel);

    getContentPane().add(container);
    setVisible(true);
    requestFocus();

  }
}