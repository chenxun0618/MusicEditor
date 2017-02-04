package cs3500.music.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import javax.swing.*;

import cs3500.music.model.Note;

/**
 * a combination of gui view & midi view
 */
public class GuiMidiViewImpl implements GuiView, ActionListener {
  private GuiView gui;
  private IMusicEditorView midi;

  /**
   * constructs a GuiMidiViewImpl with a gui view and a midi view
   * @param gui a GuiView
   * @param midi an IMusicEditorView
   */
  public GuiMidiViewImpl(GuiView gui, IMusicEditorView midi) {
    this.gui = gui;
    this.midi = midi;
    Timer timer = new Timer(0, this);
    timer.start();
  }

  public void start() {
    midi.start();
  }

  public void pause() {
    midi.pause();
  }

  public void reStart() {
    midi.reStart();
    gui.reStart();
  }

  @Override
  public int getCurrentBeat() {
    return midi.getCurrentBeat();
  }

  @Override
  public void render(int currentBeat) {
    gui.render(currentBeat);
  }

  @Override
  public void jumpToEnd() {
    gui.jumpToEnd();
  }

  @Override
  public void jumpToStart() {
    gui.jumpToStart();
  }

  @Override
  public void scrollRight() {
    gui.scrollRight();
  }

  @Override
  public void scrollLeft() {
    gui.scrollLeft();
  }

  @Override
  public void scrollUp() {
    gui.scrollUp();
  }

  @Override
  public void scrollDown() {
    gui.scrollDown();
  }

  @Override
  public void addMouseListener(MouseListener listener) {
    gui.addMouseListener(listener);
  }

  @Override
  public int getNoteSize() {
    return gui.getNoteSize();
  }

  @Override
  public void addKeyListener(KeyListener listener) {
    gui.addKeyListener(listener);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    gui.render(midi.getCurrentBeat());
  }

  @Override
  public void addNote(Note n) {
    midi.addNote(n);
    gui.addNote(n);
  }

  @Override
  public void removeNote(Note n) {
    midi.removeNote(n);
    gui.removeNote(n);
  }

}
