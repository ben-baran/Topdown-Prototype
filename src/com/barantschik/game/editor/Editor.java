package com.barantschik.game.editor;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import com.barantschik.game.anim.AnimD;
import com.barantschik.game.draw.Box;
import com.barantschik.game.draw.GUtil;
import com.barantschik.game.draw.Polygon;
import com.barantschik.game.log.Logger;
import com.barantschik.game.physics.Physics;
import com.barantschik.game.run.Scene;
import com.barantschik.game.run.SceneObject;
import com.barantschik.game.run.topdown.EnemyFactory;
import com.barantschik.game.run.topdown.ObserverFactory;
import com.barantschik.game.util.KeyHandler;
import com.barantschik.game.util.MouseHandler;

public class Editor extends SceneObject
{
	private final static double CAMERA_SPEED = 30, CAMERA_SCALE_COEF = 0.95, CAMERA_ROTATION_SPEED = 0.05, MIN_SCALE = 0.4, MAX_SCALE = 290;
	private final static double SELECTOR_SCALE = 1.5;
	private final static double DEFAULT_CAMERA_GRID_SIZE = 1.5, GRID_DEFAULT = 75, MIN_GRID_SCREEN_SIZE = 25, MAX_GRID_SCREEN_SIZE = 250, GRID_ADJUST = 1;
	
	private boolean gridOn = false, inputBlocked = false;
	private Tool tool;
	private SceneObject selected;
	private double gridScreenSize = GRID_DEFAULT, gridSize = GRID_DEFAULT;
	
	public Editor()
	{
		setLayer(Double.POSITIVE_INFINITY);
	}
	
	public void update()
	{
		double scaleAvg = (GUtil.getCamera().getSizeY().get() + GUtil.getCamera().getSizeX().get()) / 2;
		gridSize = gridScreenSize * Math.pow(2, (int) (Math.log(scaleAvg / DEFAULT_CAMERA_GRID_SIZE) / Math.log(2)));
		
		if(!inputBlocked)
		{			
			double sin = Math.sin(-GUtil.getCamera().getTheta().get()), cos = Math.cos(-GUtil.getCamera().getTheta().get());
			if(KeyHandler.has(Keyboard.KEY_W))
			{
				GUtil.getCamera().getCenterX().adjust(CAMERA_SPEED * scaleAvg * sin);
				GUtil.getCamera().getCenterY().adjust(-CAMERA_SPEED * scaleAvg * cos);
			}
			if(KeyHandler.has(Keyboard.KEY_S))
			{
				GUtil.getCamera().getCenterX().adjust(-CAMERA_SPEED * scaleAvg * sin);
				GUtil.getCamera().getCenterY().adjust(CAMERA_SPEED * scaleAvg * cos);
			}
			if(KeyHandler.has(Keyboard.KEY_A))
			{
				GUtil.getCamera().getCenterX().adjust(-CAMERA_SPEED * scaleAvg * cos);
				GUtil.getCamera().getCenterY().adjust(-CAMERA_SPEED * scaleAvg * sin);
			}
			if(KeyHandler.has(Keyboard.KEY_D))
			{
				GUtil.getCamera().getCenterX().adjust(CAMERA_SPEED * scaleAvg * cos);
				GUtil.getCamera().getCenterY().adjust(CAMERA_SPEED * scaleAvg * sin);
			}
			if(KeyHandler.has(Keyboard.KEY_Q)) GUtil.getCamera().getTheta().adjust(CAMERA_ROTATION_SPEED);
			if(KeyHandler.has(Keyboard.KEY_E)) GUtil.getCamera().getTheta().adjust(-CAMERA_ROTATION_SPEED);
			
			if(KeyHandler.has(Keyboard.KEY_LSHIFT) && KeyHandler.has(Keyboard.KEY_R))
			{
				if(gridScreenSize < MAX_GRID_SCREEN_SIZE) gridScreenSize += GRID_ADJUST;
			}
			else if(KeyHandler.has(Keyboard.KEY_R) && scaleAvg > MIN_SCALE)
			{
				GUtil.getCamera().getSizeX().scale(CAMERA_SCALE_COEF);
				GUtil.getCamera().getSizeY().scale(CAMERA_SCALE_COEF);
			}
			
			if(KeyHandler.has(Keyboard.KEY_LSHIFT) && KeyHandler.has(Keyboard.KEY_F))
			{
				if(gridScreenSize > MIN_GRID_SCREEN_SIZE) gridScreenSize -= GRID_ADJUST;
			}
			else if(KeyHandler.has(Keyboard.KEY_F) && scaleAvg < MAX_SCALE)
			{
				GUtil.getCamera().getSizeX().scale(1 / CAMERA_SCALE_COEF);
				GUtil.getCamera().getSizeY().scale(1 / CAMERA_SCALE_COEF);
			}
			if(KeyHandler.has(Keyboard.KEY_LCONTROL) && KeyHandler.pressed(Keyboard.KEY_SPACE))
			{
				Logger.log("Fixed camera angle and grid scale");
				GUtil.getCamera().getTheta().set(0);
				gridScreenSize = GRID_DEFAULT;
			}
		}
		
		if(tool == null)
		{
			if(MouseHandler.pressed(0))
			{
				ArrayList<double[]> selectorPoints = new ArrayList<double[]>();
				double[] mouseCoords = GUtil.getCamera().mouseGlobal();
				double curSelectorScale = SELECTOR_SCALE * GUtil.getCamera().getSizeX().get();
				selectorPoints.add(new double[]{mouseCoords[0], mouseCoords[1] - curSelectorScale, 1});
				selectorPoints.add(new double[]{mouseCoords[0] + curSelectorScale, mouseCoords[1] + curSelectorScale, 1});
				selectorPoints.add(new double[]{mouseCoords[0] - curSelectorScale, mouseCoords[1] + curSelectorScale, 1});
				Polygon selector = new Polygon(selectorPoints);
				
				for(String name : Scene.getAll().keySet()) 
				{
					SceneObject object = Scene.get(name);
					double[] intersection = null;
					if(object instanceof Box) intersection = Physics.intersecting(selector.getCoords(), ((Box) object).getCoords());
					else if(object instanceof Polygon) intersection = Physics.intersecting(selector.getCoords(), ((Polygon) object).getCoords());
					if(intersection != null)
					{
						Logger.log("Selected: " + name);
						selected = object;
						break;
					}
				}
			}
			if(KeyHandler.pressed(Keyboard.KEY_TAB)) gridOn = !gridOn;
			if(KeyHandler.pressed(Keyboard.KEY_1))
			{
				Logger.log("Now making polygon");
				tool = new CreatePolygon(this);
			}
			if(KeyHandler.pressed(Keyboard.KEY_2))
			{
				Logger.log("Now making box");
				tool = new CreateBox(this);
			}
			if(KeyHandler.pressed(Keyboard.KEY_3))
			{
				Logger.log("Now making observer");
				tool = new CreateObject(this, new ObserverFactory());
			}
			if(KeyHandler.pressed(Keyboard.KEY_4))
			{
				Logger.log("Now making enemy");
				tool = new CreateObject(this, new EnemyFactory());
			}
			if(KeyHandler.has(Keyboard.KEY_LCONTROL) && KeyHandler.pressed(Keyboard.KEY_P) && selected != null)
			{
				Logger.log("Now editing properties of: " + selected.getName());
				tool = PropertyEditor.makePropertyEditor(this, selected);
			}
			if(KeyHandler.pressed(Keyboard.KEY_BACK) && selected != null)
			{
				Logger.log("Deleted " + selected.getName());
				Scene.remove(selected);
				selected = null;
			}
		}
		else tool.update();
		
		if(tool != null && tool.isDone()) tool = null;
	}

	public void draw()
	{
		if(isActive())
		{			
			if(gridOn)
			{
				double[][] cameraCoords = {GUtil.getCamera().mouseGlobal(new double[]{0, 0}),
										   GUtil.getCamera().mouseGlobal(new double[]{GUtil.getX(), 0}),
										   GUtil.getCamera().mouseGlobal(new double[]{0, GUtil.getY()}),
										   GUtil.getCamera().mouseGlobal(new double[]{GUtil.getX(), GUtil.getY()})};
				double minX = Double.POSITIVE_INFINITY, minY = minX, maxX = Double.NEGATIVE_INFINITY, maxY = maxX;
				for(int i = 0; i < cameraCoords.length; i++)
				{
					minX = Math.min(minX, cameraCoords[i][0]);
					minY = Math.min(minY, cameraCoords[i][1]);
					maxX = Math.max(maxX, cameraCoords[i][0]);
					maxY = Math.max(maxY, cameraCoords[i][1]);
				}
				int startX = (int) (((int) (minX / gridSize) - 1) * gridSize), startY = (int) (((int) (minY / gridSize) - 1) * gridSize);
				int endX = (int) (((int) (maxX / gridSize) + 1) * gridSize), endY = (int) (((int) (maxY / gridSize) + 1) * gridSize);
				
				for(double i = startX; i < endX; i += gridSize) GUtil.drawLineT(i, minY, i, maxY);
				for(double i = startY; i < endY; i += gridSize) GUtil.drawLineT(minX, i, maxX, i);
			}
			
			if(tool != null) tool.draw();
		}
	}
	
	public int getAttrI(String name)
	{
		return 0;
	}

	public Object getAttr(String name)
	{
		return null;
	}

	public double getAttrD(String name)
	{
		return 0;
	}

	public AnimD getAttrAD(String name)
	{
		return null;
	}

	public double[] mouse()
	{
		double[] coords = GUtil.getCamera().mouseGlobal();
		if(gridOn)
		{
			coords[0] = Math.round(coords[0] / gridSize) * gridSize;
			coords[1] = Math.round(coords[1] / gridSize) * gridSize;
		}
		return coords;
	}

	public boolean isInputBlocked()
	{
		return inputBlocked;
	}
	
	public void setInputBlocked(boolean inputBlocked)
	{
		this.inputBlocked = inputBlocked;
	}
}