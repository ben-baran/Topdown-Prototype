package com.barantschik.game.util;

import static org.junit.Assert.*;
import org.junit.Test;

public class VectorMathTest
{
	@Test
	public void vecMatMultTest()
	{
		double[] vec = {1, 0};
		assertTrue(VecOp.mult(VecOp.rotMat(Math.PI / 2), VecOp.toOperable(vec))[1] == 1);
	}
	
	@Test
	public void matMatMultTest()
	{
		double[][] m1 = {{1, 2, 3},
						 {4, 5, 6}},
				   m2 = {{1, 2},
				         {3, 4},
				         {5, 6}};
		double[][] solution = VecOp.mult(m1, m2);
		assertTrue(solution[0][0] == 22);
	}

}
