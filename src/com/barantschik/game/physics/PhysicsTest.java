package com.barantschik.game.physics;

import static com.barantschik.game.physics.Physics.*;
import static org.junit.Assert.*;

import org.junit.Test;

import com.barantschik.game.draw.Box;

public class PhysicsTest
{
	@Test
	public void boxIntersectionTest()
	{
		{
			Box a = new Box(0, 0, 1, 1, Math.PI), b = new Box(1, 0, 1, 1, 0);
			double[] intersection = intersecting(a, b);
			assertArrayEquals(intersection, new double[]{-1, 0}, 0.0001);
		}
		
		{
			Box a = new Box(0, 0, 1, 1, Math.PI / 4), b = new Box(1, 0, 1, 1, Math.PI / 4);
			double[] intersection = intersecting(a, b);
			assertEquals(intersection[0], -0.91421356237, 0.0001);
			assertEquals(Math.abs(intersection[1]), 0.91421356237, 0.0001);
		}
	}

	@Test
	public void lineIntersectionTest()
	{
		assertTrue(linesIntersecting(-1, -1, 1, 1, -1, 1, 1, -1));
		assertTrue(!linesIntersecting(0, 0, 0, 1, 1, 0, 1, 1));
	}

}
