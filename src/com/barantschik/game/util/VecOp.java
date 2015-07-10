package com.barantschik.game.util;

import java.text.DecimalFormat;
import java.util.ArrayList;

public abstract class VecOp
{
	private static DecimalFormat formatter = new DecimalFormat("#.##");
	
	public static void print(int[][] mat)
	{
		for(int[] v : mat)
		{
			for(int i = 0; i < v.length; i++) System.out.print((i == 0 ? "" : ", ") + v[i]);
			System.out.println();
		}
	}
	
	public static void print(double[][] mat)
	{
		for(double[] v : mat)
		{
			for(int i = 0; i < v.length; i++) System.out.print((i == 0 ? "" : ", ") + formatter.format(v[i]));
			System.out.println();
		}
	}
	
	public static int[] copy(int[] vec)
	{
		int[] copy = new int[vec.length];
		for(int i = 0; i < vec.length; i++) copy[i] = vec[i];
		return copy;
	}
	
	public static double[] copy(double[] vec)
	{
		double[] copy = new double[vec.length];
		for(int i = 0; i < vec.length; i++) copy[i] = vec[i];
		return copy;
	}
	
	public static int[][] copy(int[][] mat)
	{
		int[][] copy = new int[mat.length][mat[0].length];
		for(int i = 0; i < mat.length; i++) for(int j = 0; j < mat[0].length; j++) copy[i][j] = mat[i][j];
		return copy;
	}
	
	public static double[][] copy(double[][] mat)
	{
		double[][] copy = new double[mat.length][mat[0].length];
		for(int i = 0; i < mat.length; i++) for(int j = 0; j < mat[0].length; j++) copy[i][j] = mat[i][j];
		return copy;
	}
	
	public static int[] toInt(double[] vec)
	{
		int[] solution = new int[vec.length];
		for(int i = 0; i < vec.length; i++) solution[i] = (int) vec[i];
		return solution;
	}
	
	public static double[] toDouble(int[] vec)
	{
		double[] solution = new double[vec.length];
		for(int i = 0; i < vec.length; i++) solution[i] = vec[i];
		return solution;
	}
	
	public static int[][] toInt(double[][] mat)
	{
		int[][] solution = new int[mat.length][mat[0].length];
		for(int i = 0; i < mat.length; i++) for(int j = 0; j < mat[0].length; j++) solution[i][j] = (int) mat[i][j];
		return solution;
	}
	
	public static double[][] toDouble(int[][] mat)
	{
		double[][] solution = new double[mat.length][mat[0].length];
		for(int i = 0; i < mat.length; i++) for(int j = 0; j < mat[0].length; j++) solution[i][j] = mat[i][j];
		return solution;
	}
	
	public static int[] add(int[] a, int[] b)
	{
		int[] solution = new int[a.length];
		for(int i = 0; i < a.length; i++) solution[i] = a[i] + b[i];
		return solution;
	}
	
	public static double[] add(int[] a, double[] b)
	{
		return add(toDouble(a), b);
	}
	
	public static double[] add(double[] a, int[] b)
	{
		return add(a, toDouble(b));
	}
	
	public static double[] add(double[] a, double[] b)
	{
		double[] solution = new double[a.length];
		for(int i = 0; i < a.length; i++) solution[i] = a[i] + b[i];
		return solution;
	}
	
	public static int[] subtract(int[] a, int[] b)
	{
		int[] solution = new int[a.length];
		for(int i = 0; i < a.length; i++) solution[i] = a[i] - b[i];
		return solution;
	}
	
	public static double[] subtract(int[] a, double[] b)
	{
		return subtract(toDouble(a), b);
	}
	
	public static double[] subtract(double[] a, int[] b)
	{
		return subtract(a, toDouble(b));
	}
	
	public static double[] subtract(double[] a, double[] b)
	{
		double[] solution = new double[a.length];
		for(int i = 0; i < a.length; i++) solution[i] = a[i] - b[i];
		return solution;
	}
	
	public static int[] mult(int m, int[] vec)
	{
		int[] solution = new int[vec.length];
		for(int i = 0; i < vec.length; i++) solution[i] = m * vec[i];
		return solution;
	}
	
	public static double[] mult(double m, int[] vec)
	{
		return mult(m, toDouble(vec));
	}
	
	public static double[] mult(double m, double[] vec)
	{
		double[] solution = new double[vec.length];
		for(int i = 0; i < vec.length; i++) solution[i] = m * vec[i];
		return solution;
	}
	
	public static double[] perp(int[] vec)
	{
		return perp(toDouble(vec));
	}
	
	public static double[] perp(double[] vec)
	{
		return new double[]{-vec[1], vec[0]};
	}
	
	public static double magnitude(int[] vec)
	{
		return magnitude(toDouble(vec));
	}
	
	public static double magnitude(double[] vec)
	{
		double total = 0;
		for(double d : vec) total += d * d;
		return Math.sqrt(total);
	}
	
	public static double[] normalize(int[] vec)
	{
		return normalize(toDouble(vec));
	}
	
	public static double[] normalize(double[] vec)
	{
		double[] solution = new double[vec.length];
		double invMagnitude = 1 / magnitude(vec);
		for(int i = 0; i < solution.length; i++) solution[i] = vec[i] * invMagnitude;
		return solution;
	}
	
	public static int dot(int[] a, int[] b)
	{
		int solution = 0;
		for(int i = 0; i < a.length; i++) solution += a[i] * b[i];
		return solution;
	}
	
	public static double dot(double[] a, int[] b)
	{
		return dot(a, toDouble(b));
	}
	
	public static double dot(int[] a, double[] b)
	{
		return dot(toDouble(a), b);
	}
	
	public static double dot(double[] a, double[] b)
	{
		double solution = 0;
		for(int i = 0; i < a.length; i++) solution += a[i] * b[i];
		return solution;
	}
	
	public static int[] project(int[] onto, int[] vec)
	{
		return mult(dot(onto, vec) / dot(onto, onto), onto);
	}
	
	public static double[] project(double[] onto, int[] vec)
	{
		return project(onto, toDouble(vec));
	}
	
	public static double[] project(int[] onto, double[] vec)
	{
		return project(toDouble(onto), vec);
	}
	
	public static double[] project(double[] onto, double[] vec)
	{
		return mult(dot(onto, vec) / dot(onto, onto), onto);
	}
	
	public static int[] toOperable(int[] vec)
	{
		int[] solution = new int[vec.length + 1];
		for(int i = 0; i < vec.length; i++) solution[i] = vec[i];
		solution[vec.length] = 1;
		return solution;
	}
	
	public static double[] toOperable(double[] vec)
	{
		double[] solution = new double[vec.length + 1];
		for(int i = 0; i < vec.length; i++) solution[i] = vec[i];
		solution[vec.length] = 1;
		return solution;
	}
	
	public static int[][] toOperable(int[][] mat)
	{
		int[][] solution = new int[mat.length + 1][mat[0].length];
		for(int i = 0; i < mat.length; i++)
		{
			for(int j = 0; j < mat[0].length; j++) solution[i][j] = mat[i][j];
			solution[i][mat.length] = 1;
		}
		return solution;
	}
	
	public static double[][] toOperable(double[][] mat)
	{
		double[][] solution = new double[mat.length + 1][mat[0].length];
		for(int i = 0; i < mat.length; i++)
		{
			for(int j = 0; j < mat[0].length; j++) solution[i][j] = mat[i][j];
			solution[i][mat.length] = 1;
		}
		return solution;
	}
	
	public static int[] mult(int[][] mat, int[] vec)
	{
		int[] solution = new int[mat[0].length];
		for(int i = 0; i < mat[0].length; i++) for(int j = 0; j < mat.length; j++) solution[j] += mat[j][i] * vec[i];
		return solution;
	}
	
	public static double[] mult(double[][] mat, int[] vec)
	{
		return mult(mat, toDouble(vec));
	}
	
	public static double[] mult(int[][] mat, double[] vec)
	{
		return mult(toDouble(mat), vec);
	}
	
	public static double[] mult(double[][] mat, double[] vec)
	{
		double[] solution = new double[mat[0].length];
		for(int i = 0; i < mat[0].length; i++) for(int j = 0; j < mat.length; j++) solution[j] += mat[j][i] * vec[i];
		return solution;
	}

	public static int[][] mult(int[][] mat1, int[][] mat2)
	{
		int[][] solution = new int[mat1.length][mat2[0].length];
		for(int i = 0; i < solution.length; i++) for(int j = 0; j < solution[0].length; j++) for(int k = 0; k < mat2.length; k++) solution[i][j] += mat1[i][k] * mat2[k][j];
		return solution;
	}
	
	public static double[][] mult(double[][] mat1, int[][] mat2)
	{
		return mult(mat1, toDouble(mat2));
	}
	
	public static double[][] mult(int[][] mat1, double[][] mat2)
	{
		return mult(toDouble(mat1), mat2);
	}
	
	public static double[][] mult(double[][] mat1, double[][] mat2)
	{
		double[][] solution = new double[mat1.length][mat2[0].length];
		for(int i = 0; i < solution.length; i++) for(int j = 0; j < solution[0].length; j++) for(int k = 0; k < mat2.length; k++) solution[i][j] += mat1[i][k] * mat2[k][j];
		return solution;
	}

	public static int[][] concat(int[][]... mats)
	{
		int[][] solution = mats[mats.length - 1];
		for(int i = mats.length - 2; i >= 0; i--) solution = mult(mats[i], solution);
		return solution;
	}
	
	public static double[][] concat(double[][]... mats)
	{
		double[][] solution = mats[mats.length - 1];
		for(int i = mats.length - 2; i >= 0; i--) solution = mult(mats[i], solution);
		return solution;
	}
	
	public static int[][] identity(int size)
	{
		int[][] solution = new int[size][size];
		for(int i = 0; i < size; i++) solution[i][i] = 1;
		return solution;
	}
	
	public static double[][] tlMat(double dx, double dy)
	{
		double[][] solution = toDouble(identity(3));
		solution[0][2] = dx;
		solution[1][2] = dy;
		return solution;
	}
	
	public static double[][] rotMat(double theta)
	{
		double[][] solution = toDouble(identity(3));
		double cos = Math.cos(theta), sin = Math.sin(theta);
		solution[0][0] = solution[1][1] = cos;
		solution[0][1] = sin;
		solution[1][0] = -sin;
		return solution;
	}
	
	public static double[][] rotMat(double theta, double centerX, double centerY)
	{
		return concat(tlMat(centerX, centerY), rotMat(theta), tlMat(-centerX, -centerY));
	}
	
	public static double[][] scMat(double scaleX, double scaleY)
	{
		double[][] solution = toDouble(identity(3));
		solution[0][0] = scaleX;
		solution[1][1] = scaleY;
		return solution;
	}
	
	public static double[][] scMat(double scaleX, double scaleY, double centerX, double centerY)
	{
		return concat(tlMat(centerX, centerY), scMat(scaleX, scaleY), tlMat(-centerX, -centerY));
	}
	
	public static double[][] projMat(double x, double y)
	{
		double[][] solution = toDouble(identity(3));
		double invDistSquared = 1 / (x * x + y * y);
		solution[0][0] = x * x * invDistSquared;
		solution[1][0] = solution[0][1] = x * y * invDistSquared;
		solution[1][1] = y * y * invDistSquared;
		return solution;
	}

	public static int[][] transpose(int[][] mat)
	{
		int[][] solution = new int[mat[0].length][mat.length];
		for(int i = 0; i < mat.length; i++) for(int j = 0; j < mat[0].length; j++) solution[j][i] = mat[i][j];
		return solution;
	}
	
	public static double[][] transpose(double[][] mat)
	{
		double[][] solution = new double[mat[0].length][mat.length];
		for(int i = 0; i < mat.length; i++) for(int j = 0; j < mat[0].length; j++) solution[j][i] = mat[i][j];
		return solution;
	}

	public static int[] round(double[] vec, int round)
	{
		int[] solution = new int[vec.length];
		for(int i = 0; i < vec.length; i++) solution[i] = ((int) (vec[i] / round)) * round;
		return solution;
	}
	
	public static int[][] round(double[][] mat, int round)
	{
		int[][] solution = new int[mat.length][mat[0].length];
		for(int i = 0; i < mat.length; i++) for(int j = 0; j < mat[0].length; j++) solution[i][j] = ((int) (mat[i][j] / round)) * round;
		return solution;
	}
}