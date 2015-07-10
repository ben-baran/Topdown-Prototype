package com.barantschik.game.editor;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;

import com.barantschik.game.anim.AnimD;
import com.barantschik.game.anim.EaseAnim;
import com.barantschik.game.anim.LinearAnim;
import com.barantschik.game.draw.Box;
import com.barantschik.game.draw.GUtil;
import com.barantschik.game.draw.Polygon;
import com.barantschik.game.log.Logger;
import com.barantschik.game.run.SceneObject;
import com.barantschik.game.util.KeyHandler;

public abstract class PropertyEditor extends Tool
{
	public static final double PROP_WIDTH = 0.5, PROP_LINE_HEIGHT = 0.05, 
			       		 	   WIDTH = PROP_WIDTH * GUtil.getX(), LINE_HEIGHT = PROP_LINE_HEIGHT * GUtil.getY(),
			       		 	   HWIDTH = WIDTH / 2, HLINE_HEIGHT = LINE_HEIGHT / 2;
	public static final Color INACTIVE_1 = new Color(0.0f, 0.0f, 0.0f), INACTIVE_2 = new Color(0.03f, 0.03f, 0.03f), CHOSEN = new Color(0.05f, 0.05f, 0.15f),
					          TEXT_COLOR = new Color(0.9f, 0.7f, 0.9f), EDIT_COLOR = new Color(0.8f, 0.9f, 0.6f);
	private static final double TRANSITION_ANIM_LENGTH = 30;
	
	private ArrayList<PropertySection> sections = new ArrayList<PropertySection>();
	private AnimD startX = new AnimD(GUtil.getX() / 2.0 - HWIDTH), startY = new AnimD(GUtil.getY() / 2 - HLINE_HEIGHT);
	private int selected = 0;
	
	public PropertyEditor(Editor parent, SceneObject object)
	{
		super(parent);
		parent.setInputBlocked(true);
	}

	public void update()
	{
		startX.update();
		startY.update();
		
		if(KeyHandler.has(Keyboard.KEY_LCONTROL) && KeyHandler.pressed(Keyboard.KEY_P)) 
		{
			Logger.log("Exiting property editor");
			getParent().setInputBlocked(false);
			setDone(true);
		}
		
		if(sections.size() > 0)
		{
			double previousHeight = sections.get(selected).getHeight();
			int move = sections.get(selected).update();
			double heightNow = sections.get(selected).getHeight();
			if(heightNow != previousHeight)
			{
				startY.add(new EaseAnim(0, TRANSITION_ANIM_LENGTH, (previousHeight - heightNow) / 2,
		                				EaseAnim.EaseType.QUADRATIC_IN_OUT));
			}
			
			if(move == -1 && selected > 0)
			{
				startY.add(new EaseAnim(0, TRANSITION_ANIM_LENGTH, (sections.get(selected).getHeight() + sections.get(--selected).getHeight()) / 2,
						                EaseAnim.EaseType.QUADRATIC_IN_OUT));
			}
			if(move == 1 && selected < sections.size() - 1)
			{
				startY.add(new EaseAnim(0, TRANSITION_ANIM_LENGTH, -(sections.get(selected).getHeight() + sections.get(++selected).getHeight()) / 2,
						    		    EaseAnim.EaseType.QUADRATIC_IN_OUT));
			}
		}
	}
	
	public void draw()
	{
		double curX = startX.get(), curY = startY.get();
		for(int i = 0; i < sections.size(); i++)
		{
			Color curColor = i % 2 == 0 ? INACTIVE_1 : INACTIVE_2;
			if(i == selected) curColor = CHOSEN;
			sections.get(i).draw(curX, curY, curColor);
			curY += sections.get(i).getHeight();
		}
	}
	
	public static PropertyEditor makePropertyEditor(Editor e, SceneObject object)
	{
		if(object instanceof Polygon) return new PolygonPropertyEditor(e, (Polygon) object);
		if(object instanceof Box) return new BoxPropertyEditor(e, (Box) object);
		return null;
	}

	public void addSection(PropertySection s)
	{
		sections.add(s);
	}
	
	public void addSection(int i, PropertySection s)
	{
		sections.add(i, s);
	}
}