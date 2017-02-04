package cs3500.music.view;

import java.awt.*;

import javax.swing.*;

import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import cs3500.music.model.IMusicEditorModel;
import cs3500.music.model.Note;
import cs3500.music.model.Pitch;

/**
 * A skeleton Frame (i.e., a window) in Swing
 */
public class GuiViewImpl extends javax.swing.JFrame implements GuiView {
  private IMusicEditorModel model;
  private final JPanel displayPanel;
  private int lineX = 2;
  private int noteSize = 20;
  private int frameWidth;
  private int frameHeight;
  private int displayedWindow;
  private int currentWindow;

  /**
   * Creates new GuiView
   */
  public GuiViewImpl(int width, int height, IMusicEditorModel model) {
    super();
    this.frameWidth = width;
    this.frameHeight = height;
    this.displayedWindow = frameWidth;
    this.currentWindow = 0;
    this.model = model;
    this.setTitle("Music Editor");
    this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    this.setLocation(160, 168);
    this.displayPanel = new myPanel();
    this.getContentPane().add(displayPanel);

    JScrollPane scrolls = new JScrollPane(displayPanel);
    this.add(scrolls);
    this.setPreferredSize(new Dimension(width, height));
    this.pack();
    this.setVisible(true);
  }

  /**
   * rounds up the given number to be divisible by 4
   * @param i a number
   * @return the closest number rounded up from i that's divisible by 4
   */
  private int roundUp(int i) {
    while (i % 4 != 0) {
      i++;
    }
    return i;
  }

  @Override
  public int getNoteSize() {
    return noteSize;
  }

  @Override
  public void start() {

  }

  @Override
  public void pause() {

  }

  @Override
  public void reStart() {
    displayPanel.setBounds(0, 0, displayPanel.getSize().width, displayPanel.getSize().height);
    displayedWindow = frameWidth;
    currentWindow = 0;
    displayPanel.scrollRectToVisible(new Rectangle(0, 0, frameWidth, frameHeight));
  }

  @Override
  public int getCurrentBeat() {
    return 0;
  }

  @Override
  public void addNote(Note n) {
    List<Note> notes = new ArrayList<>();
    notes.add(n);
    model.addNotes(notes);
  }

  @Override
  public void removeNote(Note n) {
    List<Note> notes = new ArrayList<>();
    notes.add(n);
    model.removeNotes(notes);
  }

  @Override
  public void jumpToEnd() {
    displayPanel.setBounds(0, 0, displayPanel.getSize().width, displayPanel.getSize().height);
    displayPanel.scrollRectToVisible(
            new Rectangle(displayPanel.getBounds().width, 0, frameWidth, frameHeight));
    currentWindow = displayPanel.getSize().width - frameWidth;
  }

  @Override
  public void jumpToStart() {
    displayPanel.setBounds(0, 0, displayPanel.getSize().width, displayPanel.getSize().height);
    displayPanel.scrollRectToVisible(new Rectangle(0, 0, frameWidth, frameHeight));
    currentWindow = 0;
  }

  @Override
  public void scrollRight() {
    if (currentWindow < displayPanel.getSize().width - frameWidth) {
      displayPanel.setBounds(0, 0, displayPanel.getSize().width, displayPanel.getSize().height);
      displayPanel.scrollRectToVisible(
              new Rectangle(currentWindow + frameWidth, 0, frameWidth, frameHeight));
      currentWindow += frameWidth;
    }
  }

  @Override
  public void scrollLeft() {
    if (currentWindow > 0) {
      displayPanel.setBounds(0, 0, displayPanel.getSize().width, displayPanel.getSize().height);
      System.out.print(displayPanel.getBounds().y);
      displayPanel.scrollRectToVisible(
              new Rectangle(currentWindow - frameWidth, 0, frameWidth, frameHeight));
      System.out.print(displayPanel.getBounds().y);
      currentWindow -= frameWidth;
    }
  }

  @Override
  public void scrollUp() {
    displayPanel.setBounds(0, 0, displayPanel.getSize().width, displayPanel.getSize().height);
    displayPanel.scrollRectToVisible(
            new Rectangle(currentWindow, 0, frameWidth, frameHeight));
  }

  @Override
  public void scrollDown() {
    displayPanel.setBounds(0, 0, displayPanel.getSize().width, displayPanel.getSize().height);
    displayPanel.scrollRectToVisible(
            new Rectangle(currentWindow, frameHeight, frameWidth, frameHeight));
  }

  private class myPanel extends JPanel {

    myPanel() {
      super();
      setBackground(new Color(238, 238, 238));
    }

    @Override
    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      List<Pitch> pitches = model.getPitches();
      int duration = roundUp(model.getDuration() + 1);

      for (int i = 0; i <= duration; i += 4) {
        g.drawString(Integer.toString(i),
                noteSize * 2 + noteSize * 4 * (i / 4), noteSize);
      }

      for (int i = 0; i < pitches.size(); i++) {
        g.setColor(Color.BLACK);
        Pitch p = pitches.get(i);
        int pitchY = pitches.size() - i + 1;
        g.drawString(p.printPitch(), 0, pitchY * noteSize);
        pitchY -= 1;

        for (Note n : p.getNotes()) {
          Color headColor = new Color(64, 121, 202);
          Color sustainColor = new Color(147, 186, 241);
          g.setColor(headColor);
          g.fillRect(noteSize * (n.getHead() + 2),
                  pitchY * noteSize + noteSize / 4, noteSize, noteSize);
          g.setColor(sustainColor);

          int sustain = n.getSustain();
          while (sustain > 0) {
            g.fillRect(noteSize * (n.getHead() + 2 + sustain),
                    pitchY * noteSize + noteSize / 4, noteSize, noteSize);
            sustain -= 1;
          }
        }

        g.setColor(Color.BLACK);
        for (int j = 0; j < duration; j += 4) {
          g.drawRect(noteSize * 2 + noteSize * 4 * (j / 4)
                  , pitchY * noteSize + noteSize / 4, noteSize * 4, noteSize);
        }

        g.setColor(Color.RED);
        g.drawLine(lineX * noteSize, pitchY * noteSize + noteSize / 4,
                lineX * noteSize, (pitchY + 1) * noteSize + noteSize / 4);
      }

      setPreferredSize(new Dimension(getPanelWidth() * noteSize, getPanelHeight() * noteSize));
    }

    private int getPanelWidth() {
      return roundUp(model.getDuration() + 1) + 4;
    }

    private int getPanelHeight() {
      return model.getPitches().size() + 2;
    }
  }

  /**
   * draws the red line that indicates the current position
   */
  public void render(int currentBeat) {
    lineX = currentBeat + 2;
    if (lineX * noteSize >= displayedWindow) {
      currentWindow = displayedWindow;
      displayedWindow += frameWidth;
      displayPanel.scrollRectToVisible(new Rectangle
              (lineX * noteSize, 0, frameWidth, frameHeight));
    }
    displayPanel.repaint();
  }

  @Override
  public void addMouseListener(MouseListener listener) {
    super.addMouseListener(listener);
    displayPanel.addMouseListener(listener);
  }
}
