package com.barantschik.game.editor;

import com.barantschik.game.draw.Polygon;

public class PolygonPropertyEditor extends PropertyEditor
{
	private Polygon polygon;
	private PropertySectionStringUpdate nameEdit;
	
	public PolygonPropertyEditor(Editor parent, Polygon polygon)
	{
		super(parent, polygon);
		
		this.polygon = polygon;
		nameEdit = new PropertySectionStringUpdate("Name", polygon.getName());
		addSection(new PropertySectionText("Editing Polygon"));
		addSection(nameEdit);
	}
	
	public void update()
	{
		polygon.rename(nameEdit.getInput());
		super.update();
	}
}
