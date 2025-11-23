GitHub: https://github.com/hsiucas/CW2025.git

Compilation instructions: 

Implemented and working properly:
- Custom font (Press Start 2P) applied throughout GUI. 
- Pause/Resume functionality via Escape key and button.
- Preview next brick. 
- Score and bonus feature. 

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
- Modified method to get PressStart2P.ttf: To match retro theme of the game. 
- Added pause functionality triggered by Escape key: To allow players to pause the game without affecting game logic.
- Added preview functionality to preview next brick: To allow players to expect what is the upcoming brick; allow for extension for more than one preview afterwards. 
- Added score feature: To allow players to get their scores. 
- Modified hiding/displaying gameOverPanel to hiding/displaying groupNotification: To fix pause button hitbox. 

SimpleBoard.java:
- Modified currentOffset to change tetromino spawn point: To match Tetris Gameboy spawn point. 

Main.java:
- Modified window size: To match retro theme of the game. 

GameController.java: 
- Modified board size: To match retro theme of the game.

Modified files:
window_style.css:
- Edited design elements including background colour, board colour and font to Press Start 2P: To ensure programme runs after updating packages. 

gameLayout.fxml: 
- Edited layout to include implemented features including preview panel, pause button, score. 

Unexpected problems:
- Refactoring all classes required updating imports; Maven compiled but failed at runtime. Cause of issue was overlooking gameLayout.fxml and was resolved by updating FXML references.
- Preview panel previewed the same brick and did not "refresh." Cause of issue was not implementing "previewPanel" to "refreshBrick" method and was resolved by having the preview panel refresh its bricks. 
- Implementing score feature disabled pause button; soon found out it was not directly due to the feature, rather the score pushed the pause button down, which was then blocked by the gameOverPanel in FXML. Resolved by hiding/showing groupNotification instead of just gameOverPanel to prevent overlapping hitbox. 