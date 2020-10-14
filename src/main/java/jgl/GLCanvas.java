/*
 * @(#)GLCanvas.java 0.1 01/04/18
 *
 * jGL 3-D graphics library for Java
 * Copyright (c) 2001 Robin Bing-Yu Chen (robin@csie.ntu.edu.tw)
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or any later version. the GNU Lesser
 * General Public License should be included with this distribution
 * in the file LICENSE.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 */

package jgl;

import java.awt.AWTEvent;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * GLCanvas is the canvas class of jGL 2.4.
 *
 * @version 0.1, 18 Apr 2001
 * @author Robin Bing-Yu Chen
 */

public class GLCanvas extends Canvas {

  protected GL myGL = new GL();
  protected GLU myGLU = new GLU(myGL);
  protected GLUT myUT = new GLUT(myGL);
  
  public void processEvent(AWTEvent e) {
    myUT.processEvent(e);
    super.processEvent(e);
  }

  public void glut_enable_events(long cap, boolean state) {
    if (state)
      enableEvents(cap);
    else
      disableEvents(cap);
  }

  public void update(Graphics g) {
    paint(g);
  }

  public void paint(Graphics g) {
    myGL.glXSwapBuffers(g, this);
    
    //firePostPaintEvents(g);
  }

  // ************ RETRIEVE RENDERING CONTEXT ************ //

  public GL getGL() {
    return myGL;
  }

  public GLU getGLU() {
    return myGLU;
  }

  public GLUT getGLUT() {
    return myUT;
  }
  
  // ************ LISTEN PAINT METHOD CALLS ************ //
  
  /*public interface PaintListener{
    public void postPaint(Graphics g);
  }
  
  List<PaintListener> listeners = new ArrayList<>();

  public List<PaintListener> getListeners() {
    return listeners;
  }

  public void setListeners(List<PaintListener> listeners) {
    this.listeners = listeners;
  }
  
  public void addPaintListener(PaintListener listener) {
    this.listeners.add(listener);
  }
  
  public void firePostPaintEvents(Graphics g) {
    for(PaintListener listener: listeners) {
      listener.postPaint(g);
    }
  }*/
  
  // ************ MANUAL REPAINT ************ //

  
  /** 
   * Can be used to update image if camera has changed position.
   * 
   *  FIXME : Warning if this is invoked by a thread external to AWT, 
   *  maybe this will require to redraw GL 
   *  while GL is already used by AWT. */
  public void forceRepaint() {
    // This makes GLUT invoke the myReshape function
    processEvent(new ComponentEvent(this, ComponentEvent.COMPONENT_RESIZED));

    // This triggers copy of newly generated picture to the GLCanvas
    repaint();
  }
  
}
