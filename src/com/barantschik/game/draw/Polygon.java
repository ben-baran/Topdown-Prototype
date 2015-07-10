package com.barantschik.game.draw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.barantschik.game.anim.AnimD;
import com.barantschik.game.anim.Bezier;
import com.barantschik.game.log.Logger;
import com.barantschik.game.run.SceneObject;
import com.barantschik.game.util.VecOp;

public class Polygon extends SceneObject
{
	private double[][] unTransformed, coords;
	private AnimD centerX, centerY, scaleX = new AnimD(1), scaleY = new AnimD(1), theta = new AnimD(0);
	private Shader shader;
	private boolean moveable = true;
	private Map<String, int[]> shaderInts = new HashMap<String, int[]>();
	private Map<String, float[]> shaderFloats = new HashMap<String, float[]>();
	
	public Polygon(double[][] unTransformed)
	{
		unTransformed = VecOp.copy(unTransformed);
		this.unTransformed = unTransformed;
		
		double averageX = 0, averageY = 0;
		for(int i = 0; i < unTransformed[0].length; i++)
		{
			averageX += unTransformed[0][i];
			averageY += unTransformed[1][i];
		}
		averageX /= unTransformed[0].length;
		averageY /= unTransformed[0].length;
		
		for(int i = 0; i < unTransformed[0].length; i++)
		{
			unTransformed[0][i] -= averageX;
			unTransformed[1][i] -= averageY;
		}
		
		centerX = new AnimD(averageX);
		centerY = new AnimD(averageY);
		
		coords = VecOp.concat(VecOp.tlMat(centerX.get(), centerY.get()),
			     			  VecOp.rotMat(theta.get()),
			     			  VecOp.scMat(scaleX.get(), scaleY.get()),
			     			  unTransformed);
	}
	
	public Polygon(ArrayList<double[]> points)
	{
		this(VecOp.transpose((double[][]) points.toArray(new double[points.size()][])));
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

	public AnimD getScaleX()
	{
		return scaleX;
	}

	public void setScaleX(AnimD scaleX)
	{
		this.scaleX = scaleX;
	}

	public AnimD getScaleY()
	{
		return scaleY;
	}

	public void setScaleY(AnimD scaleY)
	{
		this.scaleY = scaleY;
	}

	public AnimD getTheta()
	{
		return theta;
	}

	public void setTheta(AnimD theta)
	{
		this.theta = theta;
	}
	
	public void update()
	{
		if(moveable)
		{
			centerX.update();
			centerY.update();
			scaleX.update();
			scaleY.update();
			theta.update();
			coords = VecOp.concat(VecOp.tlMat(centerX.get(), centerY.get()),
				     			  VecOp.rotMat(theta.get()),
				     			  VecOp.scMat(scaleX.get(), scaleY.get()),
				     			  unTransformed);
		}
	}

	public void draw()
	{
		for(String name : shaderInts.keySet()) shader.setUniformi(shader.getUniformLoc(name), shaderInts.get(name));
		for(String name : shaderFloats.keySet()) shader.setUniformf(shader.getUniformLoc(name), shaderFloats.get(name));
		GUtil.drawPolyT(coords, shader);
	}
	
	public Object getAttr(String name)
	{
		if(name.equals("unTransformed")) return unTransformed;
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
		if(name.equals("scaleX")) return scaleX;
		if(name.equals("scaleY")) return scaleY;
		if(name.equals("theta")) return theta;
		return null;
	}
	
	public void attach(Bezier bezier)
	{
		centerX.add(bezier.getX());
		centerY.add(bezier.getY());
		theta.add(bezier.getRot());
	}
	
	public Shader getShader()
	{
		return shader;
	}
	
	public void setShader(Shader shader)
	{
		this.shader = shader;
	}
	
	public void shaderPut(String name, int[] vec)
	{
		shaderInts.put(name, vec);
	}
	
	public void shaderPut(String name, float[] vec)
	{
		shaderFloats.put(name, vec);
	}
	
	public boolean isMoveable()
	{
		return moveable;
	}
	
	public void setMoveable(boolean moveable)
	{
		this.moveable = moveable;
	}

	public double[][] getUntransformed()
	{
		return unTransformed;
	}
	
	public void setUntransformed(double[][] unTransformed)
	{
		this.unTransformed = unTransformed;
	}
	
	public double[][] getCoords()
	{
		return coords;
	}
}