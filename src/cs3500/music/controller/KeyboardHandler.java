package cs3500.music.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

import jdk.nashorn.internal.objects.annotations.Getter;

/**
 * a keyboard handler class that handles the key inputs
 */
public class KeyboardHandler implements KeyListener {
  private Map<Character, Runnable> keyTypedMap;
  private Map<Integer, Runnable> keyPressedMap, keyReleasedMap;

  /**
   * Empty default constructor
   */
  public KeyboardHandler() { }

  /**
   * Set the map for key typed events. Key typed events in Java Swing are characters
   */
  public void setKeyTypedMap(Map<Character, Runnable> map) {
    keyTypedMap = map;
  }

  /**
   * Set the map for key pressed events. Key pressed events in Java Swing are integer codes
   */
  public void setKeyPressedMap(Map<Integer, Runnable> map) {
    keyPressedMap = map;
  }

  /**
   * Set the map for key released events. Key released events in Java Swing are integer codes
   */
  public void setKeyReleasedMap(Map<Integer, Runnable> map) {
    keyReleasedMap = map;
  }

  /**
   * This is called when the view detects that a key has been typed. Find if anything has been
   * mapped to this key character and if so, execute it
   */
  @Override
  public void keyTyped(KeyEvent e) {
    if (keyTypedMap.containsKey(e.getKeyChar()))
      keyTypedMap.get(e.getKeyChar()).run();
  }

  /**
   * This is called when the view detects that a key has been pressed. Find if anything has been
   * mapped to this key code and if so, execute it
   */
  @Override
  public void keyPressed(KeyEvent e) {
    if (keyPressedMap.containsKey(e.getKeyCode()))
      keyPressedMap.get(e.getKeyCode()).run();
  }

  /**
   * This is called when the view detects that a key has been released. Find if anything has been
   * mapped to this key code and if so, execute it
   */
  @Override
  public void keyReleased(KeyEvent e) {
    if (keyReleasedMap.containsKey(e.getKeyCode()))
      keyReleasedMap.get(e.getKeyCode()).run();
  }

  @Getter
  public Map<Character, Runnable> getKeyTypedMap() {
    Map<Character, Runnable> map;
    map = keyTypedMap;
    return map;
  }

  @Getter
  public Map<Integer, Runnable> getKeyReleasedMap() {
    Map<Integer, Runnable> map;
    map = keyReleasedMap;
    return map;
  }
}
