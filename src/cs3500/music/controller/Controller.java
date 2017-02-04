package cs3500.music.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import cs3500.music.model.IMusicEditorModel;
import cs3500.music.model.Note;
import cs3500.music.model.Pitch;
import cs3500.music.view.GuiView;
import jdk.nashorn.internal.objects.annotations.Getter;

/**
 * a Controller for the music editor that executes all the controls
 */
public class Controller implements ActionListener {
  private IMusicEditorModel model;
  private GuiView view;
  private KeyboardHandler kbh;

  public Controller(IMusicEditorModel model, GuiView view) {
    this.model = model;
    this.view = view;
    configureKeyBoardListener();
    configureMousedListener();
  }

  private void configureMousedListener() {
    Map<Integer, Runnable> mouseClicks, mousePresses, mouseReleases;
    MouseHandler mh = new MouseHandler();

    mouseClicks = new HashMap<>();
    mousePresses = new HashMap<>();
    mouseReleases = new HashMap<>();

    mouseClicks.put(2, () -> {
      final JFrame frame = new JFrame();
      List<Pitch> pitches = model.getPitches();
      frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

      Point2D posn = mh.getMousePosn();
      int head = (int) posn.getX() / view.getNoteSize() - 2;

      int pitch = pitches.size() -
              ((int) posn.getY() - view.getNoteSize() / 4) / view.getNoteSize();

      for (Note n : pitches.get(pitch).getNotes()) {
        if (n.getHead() + n.getSustain() >= head && head >= n.getHead()) {
          int removeNote = JOptionPane.showConfirmDialog(frame,
                  String.format("Do you want to remove this note? %s Start: %d End: %d",
                          n.getPitch().printPitch(), n.getHead(), n.getHead() + n.getSustain()));
          if (removeNote == JOptionPane.YES_OPTION) {
            view.removeNote(n);
          }
        }
      }
    });


    mh.setMouseClickedMap(mouseClicks);
    mh.setMousePressedMap(mousePresses);
    mh.setMouseReleasedMap(mouseReleases);

    view.addMouseListener(mh);
  }


  private void configureKeyBoardListener() {
    Map<Character, Runnable> keyTypes = new HashMap<>();
    Map<Integer, Runnable> keyPresses = new HashMap<>();
    Map<Integer, Runnable> keyReleases = new HashMap<>();

    keyTypes.put('r', new ReStart());

    keyTypes.put('s', new Start());

    keyTypes.put('a', new AddNote());

    keyReleases.put(KeyEvent.VK_SPACE, new Pause());

    keyReleases.put(KeyEvent.VK_F9, new JumpToEnd());

    keyReleases.put(KeyEvent.VK_F7, new JumpToStart());

    keyReleases.put(KeyEvent.VK_LEFT, new ScrollLeft());

    keyReleases.put(KeyEvent.VK_RIGHT, new ScrollRight());

    keyReleases.put(KeyEvent.VK_UP, new ScrollUp());

    keyReleases.put(KeyEvent.VK_DOWN, new ScrollDown());

    KeyboardHandler kbh = new KeyboardHandler();
    kbh.setKeyTypedMap(keyTypes);
    kbh.setKeyPressedMap(keyPresses);
    kbh.setKeyReleasedMap(keyReleases);

    view.addKeyListener(kbh);

    this.kbh = kbh;

  }

  @Override
  public void actionPerformed(ActionEvent e) {

  }

  // THESE CLASSES ARE NESTED INSIDE THE CONTROLLER,
  // SO THAT THEY HAVE ACCESS TO THE VIEW
  private class Start implements Runnable {
    public void run() {
      view.start();
    }
  }

  private class Pause implements Runnable {
    public void run() {
      view.pause();
    }
  }

  private class ReStart implements Runnable {
    public void run() {
      view.reStart();
    }
  }

  private class JumpToEnd implements Runnable {
    public void run() {
      view.jumpToEnd();
    }
  }

  private class JumpToStart implements Runnable {
    public void run() {
      view.jumpToStart();
    }
  }

  private class AddNote implements Runnable {
    public void run() {
      final JFrame frame = new JFrame();
      frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
      int addNote = JOptionPane.showConfirmDialog(frame, "Do you want to add notes?");
      while (addNote == JOptionPane.YES_OPTION) {
        Note n = new Note();
        try {
          String noteInfo = JOptionPane.showInputDialog(frame,
                  "The note's midi value (0 - 127)", 60);
          n.setPitch(Integer.parseInt(noteInfo));
          String noteHead = JOptionPane.showInputDialog(frame, "The note's starting beat", 0);
          n.setHead(Integer.parseInt(noteHead));
          String noteSustain = JOptionPane.showInputDialog(frame, "The note's sustain", 3);
          n.setSustain(Integer.parseInt(noteSustain));
          String noteInstrument = JOptionPane.showInputDialog(frame, "The note's instrument", 1);
          n.setInstrument(Integer.parseInt(noteInstrument));
          String noteVolume = JOptionPane.showInputDialog(frame, "The note's volume", 100);
          n.setVolume(Integer.parseInt(noteVolume));
          view.addNote(n);
        } catch (NumberFormatException e) {

        }
        addNote = JOptionPane.showConfirmDialog(frame, "Do you want to add more notes?");
      }
    }
  }

  private class ScrollLeft implements Runnable {
    public void run() {
      view.scrollLeft();
    }
  }

  private class ScrollRight implements Runnable {
    public void run() {
      view.scrollRight();
    }
  }

  private class ScrollUp implements Runnable {
    public void run() {
      view.scrollUp();
    }
  }

  private class ScrollDown implements Runnable {
    public void run() {
      view.scrollDown();
    }
  }

  @Getter
  public KeyboardHandler getKbh() {
    return kbh;
  }

}
