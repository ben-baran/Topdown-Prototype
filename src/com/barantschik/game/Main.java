package com.barantschik.game;

import com.barantschik.game.run.Run;
import com.barantschik.game.run.topdown.TopDownRun;

public class Main
{
	public static void main(String[] args)
	{
		Run runner = new TopDownRun("Test");
		while(runner.runCondition()) runner.update();
		runner.cleanUp();
		System.exit(0);

	}
}
