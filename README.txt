Version 0.2

Planned Changes
	Switch to OpenGL 3.2
	Add image rendering
	New property editor features:
		-Default SceneObject operations
		-Edit Shaders directly in property editor
		-Polygon/Box scale, rotate, translate
		-Add animations to attributes
		-Edit navigation nodes
		-Add enemies
		-Add lights
		-Change layer, priority
	Use FBOs to render
	Add light rendering
	Visibility method
	Basic enemy behavior:
		-Paths
		-Use hand-made navigation nodes to navigate
		-Follow player
		-Use light to find
	Create sprite animation library:
		-Various forms
		-Various speeds
		-Branching paths
	Decouple AnimFunc from global time
	Create time-independent animation
	Add simple menu:
		-Start game:
			-Play through selected levels
			-Save progress automatically with checkpoints
		-Options:
			-Resolution
			-Key bindings
			-Help screen
		-Editor
		-Quit
	Undo function for editor
	Save function for editor
	Create zones for player
		-Spawn point: where game begins
		-End zone: where player goes to complete level
		-Safe zone: where player cannot be detected
		-Prohibited zone: where player cannot go

Current Changes
	Added tags and lists to property editor
	Fixed axis-independent collision

Previous Features
	GUtil Library - Immediate Mode Drawing of Polygons, Rectangles, Text etc.
	Camera - Performs matrix operations
	VecOp Library - Vector operation library
	Scenes - Independent updates of SceneObjects, sorting by Layer and Importance
	Editor - With tool class, can create polygons and rectangles, basic property editor
	Shaders - Only simple color shader now
	Animation Library - Can add animations to doubles, updated automatically
	Logger - Instant logging without use of console
	DynamicBag - Updates all listeners about all changes
	KeyHandler and MouseHandler - Hold all information about Keyboard and Mouse
	Physics - Basic physics, implemented with the Separating Axis Theorem. Multiple iterations allow for multiple collisions in one frame.