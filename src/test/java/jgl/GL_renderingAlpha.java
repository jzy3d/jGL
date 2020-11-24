package jgl;

import org.junit.Assert;
import org.junit.Test;

import jgl.context.gl_util;

public class GL_renderingAlpha {
	@Test
	public void whenClearColor_thenBufferIsResetToThisColor_ButAlphaRemains255() {
		// Given openGL
		GLCanvas canvas = new GLCanvas();
		GL gl = canvas.getGL();
		GLU glu = canvas.getGLU();
		GLUT glut = canvas.getGLUT();
		
		glut.glutCreateWindow(canvas); // register canvas for rendering image into it.


	    //gl.glEnable(GL.GL_ALPHA_TEST);
	    
	    float R = 1.0f;
	    float G = 0.0f;
	    float B = 0.0f;
	    float A_query = 1.0f; // semi-alpha not registered by jGL
	    float A_expect = 1.0f; // which store a full alpha
	    // see gl_colorbuffer.set_clear_color(...)
	    
		// ----------------------------------------------------
	    // When defining the clear color
		gl.glClearColor(R, G, B, A_query);
		
		// Then the clea color is properly set
		int clearColor = gl.getContext().ColorBuffer.IntClearColor;
		
		Assert.assertEquals((int)(R*255), gl_util.ItoR(clearColor));
		Assert.assertEquals((int)(G*255), gl_util.ItoG(clearColor));
		Assert.assertEquals((int)(B*255), gl_util.ItoB(clearColor));
		Assert.assertEquals((int)(A_expect*255), gl_util.ItoA(clearColor));
		
		// ----------------------------------------------------
		// When defining viewport and calling glClear
		gl.glViewport(0, 0, 100, 100); // a 10x10 color buffer
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		
		// Then colorbuffer is set with no alpha value
		int[] colorBuffer = gl.getContext().ColorBuffer.Buffer;
		
		
		Assert.assertEquals((int)(R*255), gl_util.ItoR(colorBuffer[0]));
		Assert.assertEquals((int)(G*255), gl_util.ItoG(colorBuffer[0]));
		Assert.assertEquals((int)(B*255), gl_util.ItoB(colorBuffer[0]));
		Assert.assertEquals((int)(A_expect*255), gl_util.ItoA(colorBuffer[0]));
		
		
		// --------------------------------------------------
		// When drawing a polygon on top
		
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glBegin(GL.GL_POLYGON);
		gl.glColor4f(0, 0, 1, 0.25f);
		gl.glVertex3f(0, 0, 0);
		gl.glVertex3f(1, 0, 0);
		gl.glVertex3f(1, 1, 0);
		gl.glVertex3f(0, 1, 0);
		gl.glEnd();
		
		
		// being above plane 0, looking at an
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glOrtho(-2, 2, -2, 2, -2, 2);
		glu.gluLookAt(0, 0, 1, 0, 0, 0, 0, 0, 1);
		
		gl.glFlush();
		gl.debugWriteImageTo("target/renderingAlpha.png");
	}
}
