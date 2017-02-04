package cs3500.music.view;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import javax.swing.*;

import cs3500.music.model.Note;
import jdk.nashorn.internal.objects.annotations.Getter;

/**
 * a mock of GuiViewImpl
 */
public class GuiMock extends JFrame implements GuiView {
  private String tester;
  private int tester2;

  public GuiMock() {
    tester = "";
    tester2 = 1;
  }

  @Override
  public void render(int currentBeat) {
    tester = "this is rendered";
  }

  @Override
  public void jumpToEnd() {
    tester = "this is the end";
  }

  @Override
  public void jumpToStart() {
    tester = "this is the start";
  }

  @Override
  public void scrollRight() {
    tester = "scrolled to right";
  }

  @Override
  public void scrollLeft() {
    tester = "scrolled to left";
  }

  @Override
  public void scrollUp() {
    tester = "scrolled up";
  }

  @Override
  public void scrollDown() {
    tester = "scrolled down";
  }

  @Override
  public void addKeyListener(KeyListener listener) {
    super.addKeyListener(listener);
    tester = "key listener added";
  }

  @Override
  public void addMouseListener(MouseListener listener) {
    super.addMouseListener(listener);
    tester = "mouse listener added";
  }

  @Override
  public int getNoteSize() {
    return 0;
  }

  @Override
  public void start() {
    tester = "this is started";
  }

  @Override
  public void pause() {
    tester = "this is paused";
  }

  @Override
  public void reStart() {
    tester = "this is restarted";
  }

  @Override
  public int getCurrentBeat() {
    return 0;
  }

  @Override
  public void addNote(Note n) {
    tester = "note is added";
  }

  @Override
  public void removeNote(Note n) {
    tester = "note is removed";
  }

  @Getter
  public String getTester() {
    return tester;
  }

  public void testKeyBoard() {
    if (tester2 == 1) {
      this.addKeyListener(null);
    }
    else {
      this.addMouseListener(null);
    }
  }

  public void setTester2(int i) {
    this.tester2 = i;
  }
}
