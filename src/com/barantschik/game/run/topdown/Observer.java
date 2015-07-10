package com.barantschik.game.run.topdown;

import com.barantschik.game.draw.Box;
import com.barantschik.game.draw.GUtil;
import com.barantschik.game.draw.Polygon;
import com.barantschik.game.physics.Physics;
import com.barantschik.game.run.Scene;
import com.barantschik.game.run.SceneObject;
import com.barantschik.game.util.VecOp;

public class Observer extends Box
{
	private static final double SIZE_X = 90, SIZE_Y = 120;
	private static final float[] COLOR = {0.3f, 0.8f, 0.3f};
	private static final double IDEAL_DISTANCE = 500, BACKUP_RADIUS = 300, LOOK_RADIUS = 900, SPEED = 14.5, FLOCK_DIST = 800000, FLOCK_COEF = .00005;
	
	public Observer(double centerX, double centerY, double theta)
	{
		super(centerX, centerY, SIZE_X, SIZE_Y, theta);
		getTags().add("observer");
		setShader(GUtil.getColorShader());
		shaderPut("color", COLOR);
		setLayer(2);
		setImportance(3);
	}
	
	public void update()
	{
		SceneObject player = Scene.get("player");
		double playerX = player.getAttrAD("centerX").get(), playerY = player.getAttrAD("centerY").get();
		double[] relativePos = {getCenterX().get() - playerX, getCenterY().get() - playerY};
		double dist = VecOp.magnitude(relativePos);
		if(dist < LOOK_RADIUS)
		{
			GUtil.drawLineT(getCenterX().get(), getCenterY().get(), playerX, playerY);
			boolean good = true;
			for(SceneObject object : Scene.getByTag("wall"))
			{
				if((object instanceof Box && Physics.intersecting(getCenterX().get(), getCenterY().get(),
																  playerX, playerY, ((Box) object).getCoords())) || 
				    object instanceof Polygon && Physics.intersecting(getCenterX().get(), getCenterY().get(),
						  											  playerX, playerY, ((Polygon) object).getCoords()))
				{
					good = false;
					break;
				}
			}
			
			if(good)
			{				
				double angle = -Math.atan2(relativePos[1], relativePos[0]);
				getTheta().set(angle);
				if(!getTags().contains("static") && dist > IDEAL_DISTANCE)
				{					
					getCenterX().adjust(-SPEED * Math.sin(Math.PI / 2 + angle));
					getCenterY().adjust(-SPEED * Math.cos(Math.PI / 2 + angle));
				}
//				else if(dist < BACKUP_RADIUS)
//				{
//					getCenterX().adjust(SPEED * Math.sin(Math.PI / 2 + angle));
//					getCenterY().adjust(SPEED * Math.cos(Math.PI / 2 + angle));
//				}
				super.update();
			}
		}
		
		for(int i = 0; i < Physics.NUM_ITERATIONS_LOW; i++) for(SceneObject object : Scene.getByTag("wall", "player", "observer")) if(object != this)
		{
			double[] intersection = null;
			if(object instanceof Box) intersection = Physics.intersecting(this, (Box) object);
			else if(object instanceof Polygon) intersection = Physics.intersecting(getCoords(), ((Polygon) object).getCoords());
			if(intersection != null)
			{
				getCenterX().adjust(intersection[0]);
				getCenterY().adjust(intersection[1]);
				super.update();
			}
		}
		
		super.update();
	}
}