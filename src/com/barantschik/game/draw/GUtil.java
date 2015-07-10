package com.barantschik.game.draw;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Toolkit;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

public abstract class GUtil
{
	private static PixelFormat pixelFormat = new PixelFormat();
	private static ContextAttribs contextAttributes = new ContextAttribs(3, 2).	withForwardCompatible(true).withProfileCore(true);
	private static Shader whiteShader, colorShader;
	private static int sizeX = Toolkit.getDefaultToolkit().getScreenSize().width, sizeY = Toolkit.getDefaultToolkit().getScreenSize().height;
	private static Camera camera;
	
	public static void createDisplay(String title, boolean fullscreen, boolean vsync)
	{
		Display.setTitle(title);
		try
		{
			Display.setFullscreen(fullscreen);
			Display.setVSyncEnabled(vsync);
			Display.create(/*pixelFormat, contextAttributes*/);
		}
		catch(LWJGLException e)
		{
			e.printStackTrace();
		}
		
		glOrtho(0, sizeX, sizeY, 0, 1, -1);
		glClearColor(0, 0, 0, 1);
		
		glEnable(GL_BLEND);
		glEnable(GL_POINT_SMOOTH);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		whiteShader = new Shader("uniformColor");
		whiteShader.setUniformf(whiteShader.getUniformLoc("color"), 1, 1, 1);
		
		colorShader = new Shader("uniformColor");
		colorShader.setUniformf(colorShader.getUniformLoc("color"), 1, 1, 1);
	}
	
	public static void setClearColor(float r, float g, float b, float a)
	{
		glClearColor(r, g, b, a);
	}
	
	public static void clear()
	{
		glClear(GL_COLOR_BUFFER_BIT);
	}
	
	public static boolean isOpen()
	{
		return !Display.isCloseRequested();
	}
	
	public static void drawPolyT(double[][] points, Shader shader)
	{
		if(camera != null) points = camera.process(points);
		shader.use();
		glBegin(GL_TRIANGLE_FAN);
		for(int i = 0; i < points[0].length; i++) glVertex2d(points[0][i] + getX() / 2, points[1][i] + getY() / 2);
		glEnd();
		shader.stop();
	}
	
	public static void drawPoly(double[][] points, Shader shader)
	{
		shader.use();
		glBegin(GL_TRIANGLE_FAN);
		for(int i = 0; i < points[0].length; i++) glVertex2d(points[0][i], points[1][i]);
		glEnd();
		shader.stop();
	}
	
	public static void drawRect(double x1, double y1, double x2, double y2, Color c)
	{
		colorShader.setUniformf(colorShader.getUniformLoc("color"), new float[]{c.r, c.g, c.b});
		colorShader.use();
		glBegin(GL_TRIANGLE_FAN);
		glVertex2d(x1, y1);
		glVertex2d(x1, y2);
		glVertex2d(x2, y2);
		glVertex2d(x2, y1);
		glEnd();
		colorShader.stop();
	}
	
	public static void drawPointT(double x, double y, float size)
	{
		if(camera != null)
		{
			double[] coords = camera.process(new double[]{x, y, 1});
			drawPoint(coords[0] + getX() / 2, coords[1] + getY() / 2, size);
		}
		else drawPoint(x, y, size);
	}
	
	public static void drawPoint(double x, double y, float size)
	{
		whiteShader.use();
		glPointSize(size);
		glBegin(GL_POINTS);
		glVertex2d(x, y);
		glEnd();
		whiteShader.stop();
	}
	
	public static void drawString(String string, TrueTypeFont font, double x, double y, Color color)
	{
		font.drawString((float) x, (float) y, string, color);
	}
	
	public static void drawLineT(double x1, double y1, double x2, double y2)
	{
		if(camera != null)
		{
			double[][] coords = {{x1, x2},
								 {y1, y2},
								 {1, 1}};
			coords = camera.process(coords);
			drawLine(coords[0][0] + getX() / 2, coords[1][0] + getY() / 2, coords[0][1] + getX() / 2, coords[1][1] + getY() / 2);
		}
		else drawLine(x1, y1, x2, y2);
	}
	
	public static void drawLine(double x1, double y1, double x2, double y2)
	{
		whiteShader.use();
		glBegin(GL_LINES);
		glVertex2d(x1, y1);
		glVertex2d(x2, y2);
		glEnd();
		whiteShader.stop();
	}
	
	public static void drawLine(double x1, double y1, double x2, double y2, Shader s)
	{
		s.use();
		glBegin(GL_LINES);
		glVertex2d(x1, y1);
		glVertex2d(x2, y2);
		glEnd();
		s.stop();
	}
	
	public static void sync()
	{
		Display.update();
		Display.sync(60);
	}
	
	public static int getX()
	{
		return sizeX;
	}
	
	public static int getY()
	{
		return sizeY;
	}

	public static void close()
	{
		Display.destroy();
	}

	public static Camera getCamera()
	{
		return camera;
	}
	
	public static void setCamera(Camera camera)
	{
		GUtil.camera = camera;
	}

	public static Shader getWhiteShader()
	{
		return whiteShader;
	}
	
	public static Shader getColorShader()
	{
		return colorShader;
	}
}