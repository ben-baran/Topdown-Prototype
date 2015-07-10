package com.barantschik.game.run.topdown;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;

import com.barantschik.game.anim.AnimD;
import com.barantschik.game.anim.ConstAnim;
import com.barantschik.game.anim.SinAnim;
import com.barantschik.game.anim.SpringAnim;
import com.barantschik.game.draw.Box;
import com.barantschik.game.draw.GUtil;
import com.barantschik.game.draw.Polygon;
import com.barantschik.game.log.LogMessage;
import com.barantschik.game.log.Logger;
import com.barantschik.game.physics.Physics;
import com.barantschik.game.run.Scene;
import com.barantschik.game.run.SceneObject;
import com.barantschik.game.util.KeyHandler;
import com.barantschik.game.util.VecOp;

public class Player extends Box
{
	private static final double PLAYER_SIZE_X = 45, PLAYER_SIZE_Y = 60;
	
	private double speedX, speedY;
	private double[] v1 = new double[2], v2 = new double[2];
	private double playerAcceleration = 1, maxSpeed = 16, stopCoef = 0.75;
	private Map<String, LogMessage> messages = new HashMap<String, LogMessage>();
	private double numBullets = 0;
	private AnimD scaleX = new AnimD(1), scaleY = new AnimD(1);
	
	public Player(double centerX, double centerY)
	{
		super(centerX, centerY, PLAYER_SIZE_X, PLAYER_SIZE_Y, 0);
		connectCamera();
		setLayer(-2);
		setImportance(2);
	}
	
	public Player(AnimD centerX, AnimD centerY)
	{
		super(centerX, centerY, new AnimD(PLAYER_SIZE_X), new AnimD(PLAYER_SIZE_Y), new AnimD(0));
		connectCamera();
		setLayer(-2);
		setImportance(2);
	}
	
	public Player(double centerX, double centerY, double sizeX, double sizeY, double theta)
	{
		super(centerX, centerY, sizeX, sizeY, theta);
		connectCamera();
		setLayer(-2);
		setImportance(2);
	}
	
	public Player(AnimD centerX, AnimD centerY, AnimD sizeX, AnimD sizeY, AnimD theta)
	{
		super(centerX, centerY, sizeX, sizeY, theta);
		connectCamera();
		setLayer(-2);
		setImportance(2);
	}
	
	public void connectCamera()
	{
		GUtil.getCamera().getCenterX().add(new ConstAnim(getCenterX()));
		GUtil.getCamera().getCenterY().add(new ConstAnim(getCenterY()));
		GUtil.getCamera().getSizeX().add(new SpringAnim(scaleX, new AnimD(0.4), new AnimD(0.1)));
		GUtil.getCamera().getSizeY().add(new SpringAnim(scaleY, new AnimD(0.4), new AnimD(0.1)));
//		GUtil.getCamera().getTheta().add(new SinAnim(0, 0.05, 0.015));
	}
	
	private void createBullet()
	{
		Logger.log(new LogMessage("Fire!", new Color(1.0f, 0.5f, 0.2f)));
		Bullet b = new Bullet(getCenterX().get(), getCenterY().get(), getTheta().get(), speedX, speedY);
		Scene.put("bullet #" + (++numBullets), b);
	}
	
	public void update()
	{
		if(KeyHandler.pressed(Keyboard.KEY_SPACE)) createBullet();
		
		double theta = GUtil.getCamera().getTheta().get();
		double accelX = playerAcceleration * Math.cos(theta), accelY = playerAcceleration * Math.sin(theta);
		if(KeyHandler.has(Keyboard.KEY_A) && !KeyHandler.has(Keyboard.KEY_D))
		{
			v1[0] -= accelX;
			v1[1] += accelY;
		}
		else if(KeyHandler.has(Keyboard.KEY_D) && !KeyHandler.has(Keyboard.KEY_A))
		{
			v1[0] += accelX;
			v1[1] -= accelY;
		}
		else
		{
			v1[0] *= stopCoef;
			v1[1] *= stopCoef;
		}
		
		if(KeyHandler.has(Keyboard.KEY_W) && !KeyHandler.has(Keyboard.KEY_S))
		{
			v2[0] -= accelY;
			v2[1] -= accelX;
		}
		else if(KeyHandler.has(Keyboard.KEY_S) && !KeyHandler.has(Keyboard.KEY_W))
		{
			v2[0] += accelY;
			v2[1] += accelX;
		}
		else
		{
			v2[0] *= stopCoef;
			v2[1] *= stopCoef;
		}
		
		speedX = v1[0] + v2[0];
		speedY = v1[1] + v2[1];
		double speed = Math.sqrt(speedX * speedX + speedY * speedY);
		if(speed > maxSpeed)
		{
			double normalize = maxSpeed / speed;
			speedX *= normalize;
			speedY *= normalize;
			v1[0] *= normalize;
			v1[1] *= normalize;
			v2[0] *= normalize;
			v2[1] *= normalize;
			speed = maxSpeed;
		}
		
		double[] relativeMouseCoords = GUtil.getCamera().mouseFromCenter();
		getTheta().set(Math.atan2(relativeMouseCoords[1], relativeMouseCoords[0]) + GUtil.getCamera().getTheta().get());
		
		getCenterX().adjust(speedX);
		getCenterY().adjust(speedY);
		
		super.update();
		for(int i = 0; i < Physics.NUM_ITERATIONS_HIGH; i++) for(SceneObject object : Scene.getByTag("wall", "observer"))
		{
			double[] intersection = null;
			if(object instanceof Box) intersection = Physics.intersecting(this, (Box) object);
			else if(object instanceof Polygon) intersection = Physics.intersecting(getCoords(), ((Polygon) object).getCoords());
			if(intersection != null && (intersection[0] != 0 || intersection[1] != 0))
			{
				getCenterX().adjust(intersection[0]);
				getCenterY().adjust(intersection[1]);
				
				double[] v1Proj = VecOp.project(new double[]{accelX, -accelY}, intersection), v2Proj = VecOp.subtract(intersection, v1Proj);
				v1 = VecOp.add(v1, v1Proj);
				v2 = VecOp.add(v2, v2Proj);
				
				speedX = v1[0] + v2[0];
				speedY = v1[1] + v2[1];
				super.update();
			}
		}
		
		speed = Math.sqrt(speedX * speedX + speedY * speedY);
//		scaleX.set(1.5 / (1 + .06 * Math.abs(speed)));
//		scaleY.set(1.5 / (1 + .06 * Math.abs(speed)));
		super.update();
		GUtil.getCamera().update();
	}
	
	public Object getAttr(String name)
	{
		if(name.equals("messages")) return messages;
		return super.getAttr(name);
	}
	
	public double getAttrD(String name)
	{
		if(name.equals("PLAYER_SIZE_X")) return PLAYER_SIZE_X;
		if(name.equals("PLAYER_SIZE_Y")) return PLAYER_SIZE_Y;
		if(name.equals("speedX")) return speedX;
		if(name.equals("speedY")) return speedY;
		if(name.equals("playerAcceleration")) return playerAcceleration;
		if(name.equals("maxSpeed")) return maxSpeed;
		if(name.equals("stopCoef")) return stopCoef;
		if(name.equals("numBullets")) return numBullets;
		return super.getAttrD(name);
	}
}