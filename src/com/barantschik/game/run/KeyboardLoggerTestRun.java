package com.barantschik.game.run;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;

import com.barantschik.game.anim.AnimFunc;
import com.barantschik.game.anim.Bezier;
import com.barantschik.game.anim.EaseAnim;
import com.barantschik.game.draw.Box;
import com.barantschik.game.draw.GUtil;
import com.barantschik.game.draw.Shader;
import com.barantschik.game.log.LogMessage;
import com.barantschik.game.log.Logger;
import com.barantschik.game.util.KeyHandler;
import com.barantschik.game.util.Resources;

public class KeyboardLoggerTestRun extends Run
{
	private Shader color, otherColor;
	private int x = 1920 / 100, y = 1200 / 100 - 1;
	private float[][][] colorsArray;
	private Box box, moveable;
	private boolean escapePressed = false;
	private long t1;
	private LogMessage message;

	public KeyboardLoggerTestRun(String title)
	{
		super(title);
		
		colorsArray = new float[x][y][3];
		for(int i = 0; i < x; i++) for(int j = 0; j < y; j++) colorsArray[i][j] = new float[]{(float) Math.random(), (float) Math.random(), (float) Math.random()};
		
		box = new Box(0, 0, 50, 50, 0);
		color = new Shader("uniformColor");
		color.setUniformf(color.getUniformLoc("color"), 0.5f, 0.5f, 0.0f);
		double[][] points = new double[100][2];
		for(int i = 0; i < 100; i++) points[i] = new double[]{Math.random() * 1920, Math.random() * 1200};
		Bezier bezier = new Bezier(points, 0, 1000);
		bezier.getT().clear().add(new EaseAnim(0, 1000, 1000));
		box.attach(bezier);
		box.setShader(color);
		
		moveable = new Box(50, 500, 50, 50, 0);
		otherColor = new Shader("uniformColor");
		otherColor.setUniformf(color.getUniformLoc("color"), 0.5f, 0.0f, 0.5f);
		moveable.setShader(otherColor);
		
		message = new LogMessage("Permanent Message: " + System.currentTimeMillis(), new Color(255, 255, 0), true);
		Logger.log(message);
		
		t1 = System.currentTimeMillis();
	}

	public boolean runCondition()
	{
		return !escapePressed && GUtil.isOpen();
	}

	public void update(double dt)
	{
		if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) escapePressed = true;
		else
		{
			message.setMessage("Permanent Message: " + System.currentTimeMillis());
			int randX = (int) (Math.random() * colorsArray.length), randY = (int) (Math.random() * colorsArray[0].length);
			if(KeyHandler.pressed(Keyboard.KEY_RIGHT))
			{
				moveable.getCenterX().add(new EaseAnim(0, 30, 200));
				Logger.log(new LogMessage("going right, x = " + moveable.getCenterX().get() + " now.",
			               				  new Color(colorsArray[randX][randY][0],
			               						  	colorsArray[randX][randY][1],
			               						  	colorsArray[randX][randY][2])));
			}
			if(KeyHandler.pressed(Keyboard.KEY_LEFT))
			{
				moveable.getCenterX().add(new EaseAnim(0, 30, -200));
				Logger.log(new LogMessage("going left, x = " + moveable.getCenterX().get() + " now.",
							              new Color(colorsArray[randX][randY][0],
							            		  	colorsArray[randX][randY][1],
							            		  	colorsArray[randX][randY][2])));
			}
			
			box.update();
			moveable.update();
			box.draw();
			moveable.draw();
		};
	}

	public void cleanUp()
	{
		color.clean();
		color.delete();
		GUtil.close();
	}

}