package com.barantschik.game.run.topdown;

import java.util.Queue;

import com.barantschik.game.anim.AnimD;
import com.barantschik.game.anim.EaseAnim;
import com.barantschik.game.draw.Box;
import com.barantschik.game.draw.GUtil;
import com.barantschik.game.log.Logger;
import com.barantschik.game.run.Scene;

public class Weapon extends Box
{
	private static final double WEAPON_X = 80, WEAPON_Y = 10;
	private static long WEAPON_BUFFER_TIME = 100;
	
	private boolean switched = false;
	
	public Weapon()
	{
		setCenterX(new AnimD(0));
		setCenterY(new AnimD(0));
		setSizeX(new AnimD(WEAPON_X));
		setSizeY(new AnimD(WEAPON_Y));
		setTheta(new AnimD(0));
		setupBox();
		
		setLayer(-3);
		setImportance(3);
		setShader(GUtil.getWhiteShader());
	}
	
	public void update()
	{
		Box player = (Box) Scene.get("player");
		
		double sin = Math.sin(player.getTheta().get()), cos = Math.cos(player.getTheta().get());
		double[] v1 = {cos, -sin}, v2 = {sin, cos};
		
		if(switched)
		{
			getCenterX().set(player.getCenterX().get() + 50 * v1[0] - 70 * v2[0]);
			getCenterY().set(player.getCenterY().get() + 50 * v1[1] - 70 * v2[1]);
			getTheta().set(player.getTheta().get() + 0.8);
		}
		else
		{
			getCenterX().set(player.getCenterX().get() + 50 * v1[0] + 70 * v2[0]);
			getCenterY().set(player.getCenterY().get() + 50 * v1[1] + 70 * v2[1]);
			getTheta().set(player.getTheta().get() - 0.8);
		}
		
		Queue<GestureType> gestures = ((GestureTracker) Scene.get("gestureTracker")).getGestures();
		if(gestures.size() > 0 && getTheta().getFunctions().size() == 0)
		{
			if(System.currentTimeMillis() - gestures.peek().getTime() < WEAPON_BUFFER_TIME)
			{
				GestureType gesture = gestures.poll();
				if(gesture instanceof PointGesture)
				{
					Logger.log("Start point attack");
					getTheta().add(new EaseAnim(0, 20, 1.6));
					getCenterX().add(new EaseAnim(0, 20, -140 * v2[0]));
					getCenterY().add(new EaseAnim(0, 20, -140 * v2[1]));
				}
				else if(gesture instanceof LineGesture)
				{
					Logger.log("Start line attack");
					getTheta().add(new EaseAnim(0, 30, 1));
				}				
			}
			else gestures.clear();
		}
		
		super.update();
	}
}
