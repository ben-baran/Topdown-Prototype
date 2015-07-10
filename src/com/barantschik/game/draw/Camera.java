package com.barantschik.game.draw;

import org.lwjgl.input.Mouse;

import com.barantschik.game.anim.AnimD;
import com.barantschik.game.run.Scene;
import com.barantschik.game.util.VecOp;

public class Camera extends Box
{
	private double[][] invTransform = new double[3][3], transform = new double[3][3], localTransform = new double[3][3];
	
	public Camera(double centerX, double centerY, double sizeX, double sizeY, double theta)
	{
		super(centerX, centerY, sizeX, sizeY, theta);
		setImportance(-1);
		Scene.put(this.toString(), this);
	}
	
	public Camera(AnimD centerX, AnimD centerY, AnimD sizeX, AnimD sizeY, AnimD theta)
	{
		super(centerX, centerY, sizeX, sizeY, theta);
		setImportance(-1);
		Scene.put(this.toString(), this);
	}
	
	public void draw(){}
	
	public void update()
	{
		super.update();
		localTransform = VecOp.concat(VecOp.tlMat(getCenterX().get(), getCenterY().get()),
				   		 VecOp.scMat(getSizeX().get(), getSizeY().get()));
		
		invTransform = VecOp.concat(VecOp.tlMat(getCenterX().get(), getCenterY().get()),
									VecOp.rotMat(getTheta().get()),
									VecOp.scMat(getSizeX().get(), getSizeY().get()));
		
		transform = VecOp.concat(VecOp.scMat(1 / getSizeX().get(), 1 / getSizeY().get()),
								 VecOp.rotMat(-getTheta().get()),
								 VecOp.tlMat(-getCenterX().get(), -getCenterY().get()));
	}
	
	public double[][] process(double[][] p)
	{
		return VecOp.mult(transform, p);
	}
	
	public double[] process(double[] p)
	{
		return VecOp.mult(transform, p);
	}
	
	public double[][] invProcess(double[][] p)
	{
		return VecOp.mult(invTransform, p);
	}
	
	public double[] invProcess(double[] p)
	{
		return VecOp.mult(invTransform, p);
	}
	
	public double[] localProcess(double[] p)
	{
		return VecOp.mult(localTransform, p);
	}
	
	public double[] mouseGlobal()
	{
		return mouseGlobal(new double[]{Mouse.getX(), Mouse.getY()});
	}
	
	public double[] mouseGlobal(double[] mouse)
	{
		double[] mouseCoords = invProcess(new double[]{(mouse[0] - GUtil.getX() / 2),
                (GUtil.getY() / 2 - mouse[1]),
                1});
		return mouseCoords;
	}
	
	public double[] mouseOnScreen()
	{
		return mouseOnScreen(new double[]{Mouse.getX(), Mouse.getY()});
	}
	
	public double[] mouseOnScreen(double[] mouse)
	{
		double[] mouseCoords = localProcess(new double[]{(mouse[0] - GUtil.getX() / 2) / getSizeX().get(),
				                                       (mouse[1] - GUtil.getY() / 2) / getSizeY().get(),
				                                       1});
		return new double[]{mouseCoords[0] - getCenterX().get() + GUtil.getX() / 2,
				            GUtil.getY() / 2 - (mouseCoords[1] - getCenterY().get())};
	}
	
	public double[] mouseFromCenter()
	{
		return mouseFromCenter(new double[]{Mouse.getX(), Mouse.getY()});
	}
	
	public double[] mouseFromCenter(double[] mouse)
	{
		double[] mouseCoords = localProcess(new double[]{(mouse[0] - GUtil.getX() / 2) / getSizeX().get(),
                									   (mouse[1] - GUtil.getY() / 2) / getSizeY().get(),
                									   1});
		return new double[]{mouseCoords[0] - getCenterX().get(),
							mouseCoords[1] - getCenterY().get()};	
	}
	
	public Camera copy()
	{
		return new Camera(getCenterX().get(), getCenterY().get(), getSizeX().get(), getSizeY().get(), getTheta().get());
	}
}