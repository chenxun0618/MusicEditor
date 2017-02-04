package cs3500.music.view;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

/**
 * a GuiView interface that's designed for the GuiViewImpl
 */
public interface GuiView extends IMusicEditorView {

  /**
   * renders the image of the GUI view
   * @param currentBeat the current beat of the song
   */
  void render(int currentBeat);

  /**
   * jumps to the end of the GUI view
   */
  void jumpToEnd();

  /**
   * jumps to the start of the GUI view
   */
  void jumpToStart();

  /**
   * scrolls right to the next view
   */
  void scrollRight();

  /**
   * scrolls left to the next view
   */
  void scrollLeft();

  /**
   * scrolls up to the next view
   */
  void scrollUp();

  /**
   * scrolls down to the next view
   */
  void scrollDown();

  /**
   * adds key listener
   */
  void addKeyListener(KeyListener listener);

  /**
   * adds mouse listener
   */
  void addMouseListener(MouseListener listener);

  /**
   * returns the note size constant
   */
  int getNoteSize();

}
