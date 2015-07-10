package com.barantschik.game.run;

import java.util.ArrayList;

import com.barantschik.game.anim.AnimD;
import com.barantschik.game.log.Logger;
import com.barantschik.game.util.DynamicBag;
import com.barantschik.game.util.Listener;
import com.barantschik.game.util.Pair;

public abstract class SceneObject implements Listener<DynamicBag<String>, String>
{
	private String name = "";
	private DynamicBag<String> tags = new DynamicBag<String>();
	private double layer, importance;
	private boolean active = true, editable = false;
	private ArrayList<String> previousNames = new ArrayList<String>();
	private ArrayList<Pair<Boolean, String>> tagChanges = new ArrayList<Pair<Boolean, String>>();
	
	public abstract void update();
	public abstract void draw();
	public abstract int getAttrI(String name);
	public abstract Object getAttr(String name);
	public abstract double getAttrD(String name);
	public abstract AnimD getAttrAD(String name);
	
	public SceneObject()
	{
		tags.addListener(this);
	}
	
	public DynamicBag<String> getTags()
	{
		return tags;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void rename(String name)
	{
		if(!this.name.equals(name)) previousNames.add(this.name);
		this.name = name;
	}

	public double getLayer()
	{
		return layer;
	}
	
	public void setLayer(double layer)
	{
		this.layer = layer;
	}
	
	public double getImportance()
	{
		return importance;
	}
	
	public void setImportance(double importance)
	{
		this.importance = importance;
	}
	
	public boolean isActive()
	{
		return active;
	}
	
	public void setActive(boolean active)
	{
		this.active = active;
	}

	public boolean isEditable()
	{
		return editable;
	}
	
	public void setEditable(boolean editable)
	{
		this.editable = editable;
	}
	
	public ArrayList<String> getPreviousNames()
	{
		return previousNames;
	}
	
	public ArrayList<Pair<Boolean, String>> getTagChanges()
	{
		return tagChanges;
	}
	
	public void inform(DynamicBag<String> bag, String s)
	{
		if(bag == tags)
		{
			String[] message = s.split(" ");
			tagChanges.add(new Pair<Boolean, String>(message[0].equals("Added"), message.length == 1 ? "" : message[1]));
		}
	}
}