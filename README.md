GitHub: 

Compilation instructions: 

Implemented and working properly:

Implemented but not working properly: 

Features not implemented: 

New Java classes: 

Modified Java Classes:
General refactoring
- Created application, board, controller, events and gui packages. 
- Refactored bricks package out from logic package. 
- Refactored Main.java into application package. 
- Refactored Board.java and SimpleBoard.java into board package. 
- Refactored GameController.java and GuiController.java into controller package. 
- Refactored EventSource.java, EventType.java, MoveEvent.java, InputEventListener.java into events package. 
- Refactored GameOverPanel.java and NotificationPanel.java into gui package. 
- Refactored BrickRotator.java, ClearRow.java, DownData.java, MatrixOperations.java, NextShapeInfo.java, Score.java and ViewData.java into logic package.

GuiController.java: 
- Modified method to get PressStart2P.ttf.

Modified files:
window_style.css:
- Edited design such as background colour and board colour.
- Edited fonts to Press Start 2P. 



Unexpected problems:
- Refactoring all classes meant importing required classes into respective classes was expected, but Maven still had errors when running but not compiling; cause of issue was overlooking gameLayout.fxml.
