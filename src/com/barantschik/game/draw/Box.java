 package com.barantschik.game.draw;

import java.util.HashMap;
import java.util.Map;

import com.barantschik.game.anim.AnimD;
import com.barantschik.game.anim.Bezier;
import com.barantschik.game.run.SceneObject;
import com.barantschik.game.util.VecOp;

public class Box extends SceneObject
{
	public static final double[][] UNIT_BOX = {{-1, 1, 1, -1},
		   									   {1, 1, -1, -1},
		   									   {1, 1, 1, 1}};
	
	private double[][] coords = UNIT_BOX;
	private boolean moveable = true;
	private AnimD centerX, centerY, sizeX, sizeY, theta;
	private Shader shader;
	private Map<String, int[]> shaderInts = new HashMap<String, int[]>();
	private Map<String, float[]> shaderFloats = new HashMap<String, float[]>();
	
	protected Box(){}
	
	public Box(double centerX, double centerY, double sizeX, double sizeY, double theta)
	{
		this(new AnimD(centerX), new AnimD(centerY), new AnimD(sizeX), new AnimD(sizeY), new AnimD(theta));
	}
	
	public Box(AnimD centerX, AnimD centerY, AnimD sizeX, AnimD sizeY, AnimD theta)
	{
		this.centerX = centerX;
		this.centerY = centerY;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.theta = theta;
		
		setupBox();
	}
	
	protected void setupBox()
	{
		coords = VecOp.concat(VecOp.tlMat(centerX.get(), centerY.get()),
							  VecOp.rotMat(theta.get()),
							  VecOp.scMat(sizeX.get(), sizeY.get()),
							  UNIT_BOX);
		centerX.update();
		centerY.update();
		sizeX.update();
		sizeY.update();
		theta.update();
	}
	
	public void update()
	{
		if(moveable)
		{			
			centerX.update();
			centerY.update();
			sizeX.update();
			sizeY.update();
			theta.update();
			coords = VecOp.concat(VecOp.tlMat(centerX.get(), centerY.get()),
					 			  VecOp.rotMat(theta.get()),
					 			  VecOp.scMat(sizeX.get(), sizeY.get()),
					 			  UNIT_BOX);
		}
	}
	
	public AnimD getCenterX()
	{
		return centerX;
	}

	public void setCenterX(AnimD centerX)
	{
		this.centerX = centerX;
	}

	public AnimD getCenterY()
	{
		return centerY;
	}

	public void setCenterY(AnimD centerY)
	{
		this.centerY = centerY;
	}

	public AnimD getSizeX()
	{
		return sizeX;
	}

	public void setSizeX(AnimD sizeX)
	{
		this.sizeX = sizeX;
	}

	public AnimD getSizeY()
	{
		return sizeY;
	}

	public void setSizeY(AnimD sizeY)
	{
		this.sizeY = sizeY;
	}

	public AnimD getTheta()
	{
		return theta;
	}

	public void setTheta(AnimD theta)
	{
		this.theta = theta;
	}

	public void attach(Bezier bezier)
	{
		centerX.add(bezier.getX());
		centerY.add(bezier.getY());
		theta.add(bezier.getRot());
	}
	
	public void shaderPut(String name, int[] vec)
	{
		shaderInts.put(name, vec);
	}
	
	public void shaderPut(String name, float[] vec)
	{
		shaderFloats.put(name, vec);
	}
	
	public Shader getShader()
	{
		return shader;
	}
	
	public void setShader(Shader shader)
	{
		this.shader = shader;
	}
	
	public void draw()
	{
		for(String name : shaderInts.keySet()) shader.setUniformi(shader.getUniformLoc(name), shaderInts.get(name));
		for(String name : shaderFloats.keySet()) shader.setUniformf(shader.getUniformLoc(name), shaderFloats.get(name));
		GUtil.drawPolyT(coords, shader);
	}
	
	public Object getAttr(String name)
	{
		if(name.equals("shader")) return shader;
		if(name.equals("shaderInts")) return shaderInts;
		if(name.equals("shaderFloats")) return shaderFloats;
		return null;
	}
	
	public int getAttrI(String name)
	{
		return 0;
	}

	public double getAttrD(String name)
	{
		return 0;
	}
	
	public AnimD getAttrAD(String name)
	{
		if(name.equals("centerX")) return centerX;
		if(name.equals("centerY")) return centerY;
		if(name.equals("sizeX")) return sizeX;
		if(name.equals("sizeY")) return sizeY;
		if(name.equals("theta")) return theta;
		return null;
	}

	public double[][] getCoords()
	{
		return coords;
	}
	
	public boolean isMoveable()
	{
		return moveable;
	}
	
	public void setMoveable(boolean moveable)
	{
		this.moveable = moveable;
	}
}