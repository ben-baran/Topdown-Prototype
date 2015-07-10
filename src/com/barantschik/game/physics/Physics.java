package com.barantschik.game.physics;

import com.barantschik.game.draw.Box;
import com.barantschik.game.util.VecOp;

public abstract class Physics
{
	public static int NUM_ITERATIONS_HIGH = 20, NUM_ITERATIONS_LOW = 3;
	
	private static double[][] boxSeparatingAxes(double[][] aCoords, double[][] bCoords)
	{
		double[][] axes = {{aCoords[0][0] - aCoords[0][1], aCoords[1][0] - aCoords[1][1]},
				   		   {aCoords[0][1] - aCoords[0][2], aCoords[1][1] - aCoords[1][2]},
				   		   {bCoords[0][0] - bCoords[0][1], bCoords[1][0] - bCoords[1][1]},
				   		   {bCoords[0][1] - bCoords[0][2], bCoords[1][1] - bCoords[1][2]}};
		return axes;
	}
	
	public static double[] intersecting(Box a, Box b)
	{
		double[][] aCoords = {a.getCoords()[0], a.getCoords()[1]}, bCoords = {b.getCoords()[0], b.getCoords()[1]};
		double[][] axes = boxSeparatingAxes(aCoords, bCoords);
		
		double smallestLength = Double.POSITIVE_INFINITY;
		double[] smallestIntersection = new double[2];
		
		for(double[] axis : axes)
		{
			axis = VecOp.normalize(axis);
			double aMin = Double.POSITIVE_INFINITY, aMax = Double.NEGATIVE_INFINITY,
				   bMin = Double.POSITIVE_INFINITY, bMax = Double.NEGATIVE_INFINITY;
			for(int i = 0; i < aCoords[0].length; i++)
			{
				double aVal = VecOp.dot(axis, new double[]{aCoords[0][i], aCoords[1][i]}), 
					   bVal = VecOp.dot(axis, new double[]{bCoords[0][i], bCoords[1][i]});
				aMin = Math.min(aMin, aVal);
				aMax = Math.max(aMax, aVal);
				bMin = Math.min(bMin, bVal);
				bMax = Math.max(bMax, bVal);
			}
			if(aMin > bMax || aMax < bMin) return null;
			double length1 = aMax - bMin, length2 = bMax - aMin;
			if(length1 < smallestLength && length1 < length2)
			{
				smallestLength = length1;
				length1 = -length1;
				smallestIntersection[0] = axis[0] * length1;
				smallestIntersection[1] = axis[1] * length1;
			}
			else if(length2 < smallestLength)
			{
				smallestLength = length2;
				smallestIntersection[0] = axis[0] * length2;
				smallestIntersection[1] = axis[1] * length2;
			}
		}
		
		return smallestIntersection;
	}
	
	private static double[][] separatingAxes(double[][] aCoords, double[][] bCoords)
	{
		double[][] axes = new double[aCoords[0].length + bCoords[0].length][2];
		for(int i = 0; i < aCoords[0].length - 1; i++)
		{
			axes[i] = new double[]{aCoords[0][i] - aCoords[0][i + 1], aCoords[1][i] - aCoords[1][i + 1]};
		}
		for(int i = 0; i < bCoords[0].length - 1; i++)
		{
			axes[i + aCoords[0].length] = new double[]{bCoords[0][i] - bCoords[0][i + 1], bCoords[1][i] - bCoords[1][i + 1]};
		}
		axes[aCoords[0].length - 1] = new double[]{aCoords[0][aCoords[0].length - 1] - aCoords[0][0],
				                                   aCoords[1][aCoords[0].length - 1] - aCoords[1][0]};
		axes[axes.length - 1] = new double[]{bCoords[0][bCoords[0].length - 1] - bCoords[0][0],
											 bCoords[1][bCoords[0].length - 1] - bCoords[1][0]};
		return axes;
	}
	
	public static double[] intersecting(double[][] a, double[][] b)
	{
		double[][] aCoords = {a[0], a[1]}, bCoords = {b[0], b[1]};
		double[][] axes = separatingAxes(aCoords, bCoords);
		
		double smallestLength = Double.POSITIVE_INFINITY;
		double[] smallestIntersection = new double[2];
		
		for(double[] axis : axes)
		{
			axis = VecOp.perp(VecOp.normalize(axis));
			double aMin = Double.POSITIVE_INFINITY, aMax = Double.NEGATIVE_INFINITY,
				   bMin = Double.POSITIVE_INFINITY, bMax = Double.NEGATIVE_INFINITY;
			for(int i = 0; i < aCoords[0].length; i++)
			{
				double aVal = VecOp.dot(axis, new double[]{aCoords[0][i], aCoords[1][i]});
				aMin = Math.min(aMin, aVal);
				aMax = Math.max(aMax, aVal);
			}
			for(int i = 0; i < bCoords[0].length; i++)
			{
				double bVal = VecOp.dot(axis, new double[]{bCoords[0][i], bCoords[1][i]});
				bMin = Math.min(bMin, bVal);
				bMax = Math.max(bMax, bVal);
			}
			if(aMin > bMax || aMax < bMin) return null;
			double length1 = aMax - bMin, length2 = bMax - aMin;
			if(length1 < smallestLength && length1 < length2)
			{
				smallestLength = length1;
				length1 = -length1;
				smallestIntersection[0] = axis[0] * length1;
				smallestIntersection[1] = axis[1] * length1;
			}
			else if(length2 < smallestLength)
			{
				smallestLength = length2;
				smallestIntersection[0] = axis[0] * length2;
				smallestIntersection[1] = axis[1] * length2;
			}
		}
		return smallestIntersection;
	}

	public static boolean linesIntersecting(double x11, double y11, double x12, double y12, double x21, double y21, double x22, double y22)
	{
		double d1x = x11 - x12, d1y = y11 - y12, d2x = x21 - x22, d2y = y21 - y22;
		double a1 = d1x * (y21 - y11) - d1y * (x21 - x11), a2 = d1x * (y22 - y11) - d1y * (x22 - x11);
		if(a1 > 0 == a2 > 0) return false;
		double b1 = d2x * (y11 - y21) - d2y * (x11 - x21), b2 = d2x * (y12 - y21) - d2y * (x12 - x21);
		return b1 > 0 ^ b2 > 0;
	}
	
	public static boolean intersecting(double x1, double y1, double x2, double y2, double[][] coords)
	{
		for(int i = 0; i < coords[0].length - 1; i++)
		{
			if(linesIntersecting(x1, y1, x2, y2, coords[0][i], coords[1][i], coords[0][i + 1], coords[1][i + 1])) return true;
		}
		if(linesIntersecting(x1, y1, x2, y2, coords[0][coords[0].length - 1], coords[1][coords[0].length - 1], coords[0][0], coords[1][0])) return true;
		return false;
	}
}