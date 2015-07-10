package com.barantschik.game.run.topdown;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;

import com.barantschik.game.draw.Camera;
import com.barantschik.game.draw.GUtil;
import com.barantschik.game.draw.Shader;
import com.barantschik.game.editor.Editor;
import com.barantschik.game.log.LogMessage;
import com.barantschik.game.log.Logger;
import com.barantschik.game.run.Run;
import com.barantschik.game.run.Scene;
import com.barantschik.game.util.KeyHandler;

public class TopDownRun extends Run
{	
	private boolean escapePressed = false;
	private Shader colorShader = GUtil.getColorShader();
	private boolean editorOpen = false;
	private Camera playerC = new Camera(0, 0, 0, 0, 0);
	
	public TopDownRun(String title)
	{
		super(title);
		
		GUtil.setClearColor(0.1386f, 0.1227f, 0.0075f, 1.0f);
		GUtil.setCamera(playerC);
		Player player = new Player(0, 0);
		player.setShader(colorShader);
		player.shaderPut("color", new float[]{0.5f, 0.5f, 0.9f});
		player.getTags().add("player");
		player.getTags().add("movable");
		Scene.put("player", player);
		Scene.put("gestureTracker", new GestureTracker());
		Scene.put("weapon", new Weapon());
		
//		for(int i = 0; i < 300; i++)
//		{			
//			Enemy enemy = new Enemy(100 + i, 100 + i, 12, 4, 0);
//			enemy.setShader(colorShader);
//			enemy.shaderPut("color", new float[]{(float) Math.random(), (float) Math.random(), (float) Math.random()});
//			enemy.getTags().add("enemy");
//			Scene.put("enemy #" + i, enemy);
//		}
		
		Editor editor = new Editor();
		editor.setActive(false);
		Scene.put("editor", editor);
	}

	public boolean runCondition()
	{
		return !escapePressed && GUtil.isOpen();
	}

	public void update(double dt)
	{
		if(KeyHandler.has(Keyboard.KEY_LCONTROL) && KeyHandler.has(Keyboard.KEY_Q)) escapePressed = true;
		if(KeyHandler.has(Keyboard.KEY_LCONTROL) && KeyHandler.pressed(Keyboard.KEY_E))
		{
			editorOpen = !editorOpen;
			if(editorOpen)
			{
				Logger.log(new LogMessage("Entering editor", new Color(0.4f, 0.8f, 0.3f)));
				GUtil.setCamera(playerC.copy());
				Scene.setAllActive(false);
				Scene.get("editor").setActive(true);
			}
			else
			{
				Logger.log(new LogMessage("Exiting editor", new Color(0.4f, 0.8f, 0.3f)));
				GUtil.setCamera(playerC);
				Scene.setAllActive(true);
				Scene.get("editor").setActive(false);
			}
		}
		Scene.update();
		Scene.draw();
	}
	
	public void cleanUp()
	{
		colorShader.clean();
		colorShader.delete();
	}
}