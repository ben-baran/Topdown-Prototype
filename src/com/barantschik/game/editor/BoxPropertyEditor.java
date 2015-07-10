package com.barantschik.game.editor;

import com.barantschik.game.draw.Box;

public class BoxPropertyEditor extends PropertyEditor
{
	private Box box;
	private PropertySectionStringUpdate nameEdit;
	
	public BoxPropertyEditor(Editor parent, Box box)
	{
		super(parent, box);
		
		this.box = box;
		nameEdit = new PropertySectionStringUpdate("Name", box.getName());
		addSection(new PropertySectionText("Editing Box"));
		addSection(nameEdit);
		addSection(new PropertySectionStringUpdateList("Tags", box.getTags()));
		addSection(new PropertySectionText("End of Box Property Editor"));
	}
	
	public void update()
	{
		box.rename(nameEdit.getInput());
		super.update();
	}
}
