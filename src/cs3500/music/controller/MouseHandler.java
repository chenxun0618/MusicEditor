package cs3500.music.controller;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.util.Map;

import jdk.nashorn.internal.objects.annotations.Getter;

/**
 * a mouse handler class that handles the mouse inputs
 */
public class MouseHandler implements MouseListener {
  private Map<Integer, Runnable> mouseClickedMap, mousePressedMap, mouseReleasedMap;
  private Point2D posn;

  /**
   * default constructor
   */
  MouseHandler() {
    posn = new Point(-1, -1);
  }

  /**
   * Set the map for mouse clicked events.
   */
  public void setMouseClickedMap(Map<Integer, Runnable> map) {
    mouseClickedMap = map;
  }

  /**
   * Set the map for mouse pressed events.
   */
  public void setMousePressedMap(Map<Integer, Runnable> map) {
    mousePressedMap = map;
  }

  /**
   * Set the map for mouse released events.
   */
  public void setMouseReleasedMap(Map<Integer, Runnable> map) {
    mouseReleasedMap = map;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    if (mouseClickedMap.containsKey(e.getClickCount()))
      mouseClickedMap.get(e.getClickCount()).run();
    posn.setLocation(e.getX(), e.getY());
  }

  @Override
  public void mousePressed(MouseEvent e) {
    if (mousePressedMap.containsKey(e.getButton()))
      mousePressedMap.get(e.getButton()).run();
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    if (mouseReleasedMap.containsKey(e.getButton()))
      mouseReleasedMap.get(e.getButton()).run();
  }

  @Override
  public void mouseEntered(MouseEvent e) {

  }

  @Override
  public void mouseExited(MouseEvent e) {

  }

  @Getter
  public Point2D getMousePosn() {
    return this.posn;
  }
}
