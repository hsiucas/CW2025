# GitHub
https://github.com/hsiucas/CW2025.git

# Compilation instructions:

### Prerequisites
- **Java JDK 23**: Project is configured for Java 23 features.
- **Maven**: Used for dependency management and building. Optional if using the `mvnw` wrapper.

Note: This project is built using Maven and JavaFX. A Maven wrapper (`mvnw`) is included so you don't have to install Maven manually.

### Steps to compile and run
1. **Open Terminal/Command prompt**.
    - Navigate to root directory of the project where `pom.xml` and `mvnw` are located:
        - Windows: `cd C: \Users\YourName\Path\To\Root\Directory`
        - Mac: `cd /Users/YourName/Path/To/Root/Directory`
2. **Clean and compile**.
    - Run the following to download dependencies and compile the project:
        - Windows:  `.\mvnw.cmd clean compile`
        - Mac:      `./mvnw clean compile`
    - On Mac, you are likely to come across this error: "`zsh: permission denied: ./mvnw`". Simply run "`chmod +x mvnw`" and try this step again.
3. **Run the application**.
    - Use the JavaFX Maven plugin to start the game:
        - Windows: `.\mvnw.cmd javafx:run`
        - Mac: `./mvnw javafx:run`
- If using IntelliJ IDEA, you may also open the `pom.xml` file as a project and run `Main.java` located in `com.comp2042.application`. However, the steps above should show no issue.

# Implemented and working properly:
1. **Refactored model-view-controller architecture**
    - Original monolithic code has been refactored into a clean model-view-controller architecture with separate packages for Logic, Model, View and Controllers.
2. **Multiple game modes**
    - `CLASSIC`: Standard *GAMEBOY* style gameplay with increasing speed.
    - `ZEN`: Relaxed gameplay with constant, relaxed speed. Hard drop, hold brick and ghost bricks were implemented for this mode for relaxed gameplay.
    - `SURVIVAL`: Random garbage rows appear at the bottom periodically to challenge the players. Hard drop, hold brick and ghost bricks were also implemented for this mode for exciting gameplay.
    - `4-WAY`: Inspired by Welltris, bricks spawn in the centre of the board and can be moved in all 4 directions on a square grid. Collision and gravity is present in all 4 walls.
3. **Sound system**
    - A `SoundManager` that handles:
    1. Background music specific to game modes and menus.
    2. Sound effects for movement, rotation, locking, clearing lines and game over.
    3. Menu navigation sounds.
    - All audio tracks were taken from the classic *GAMEBOY* Tetris game.
4. **Advanced game mechanics**
    - As aforementioned,
    1. Hold brick: Players can swap current piece with a held piece.
    2. Next brick preview: Displays the 3 upcoming bricks.
    3. Ghosts brick: Shows a shadow where current brick will land.
5. **Enhanced UI/UX**
    - Keyboard navigation
        - Full game can be navigated using keyboard with Arrow Keys, WASD, Enter/Space and more, no mouse is used to replicate a handheld console experience.
    - Custom assets
        - Retro "Press Start 2P" font, custom graphics.
    - Instructions screen
        - Quick tutorial to explain controls for different modes.
    - Pause/Resume function
        - Ability to pause/resume game whenever player wants to.
        
# Implemented but not working properly: 
1. **Focus Traversal on Instructions Screen**
    - Instructions screen was supposed to traverse between 4 pages using keyboard keys. Implementation was scrapped as bug where game was not receiving keyboard signals could not be figured out within time constraints.
2. **Scoreboard**
    - Scoreboard was considered and implemented to enhance gameplay experience and provide motivation for players, but scoreboard only displays for a split second before exiting to menu. Idea was scrapped as error could not be figured out within time constraints.

# Features not implemented: 
1. **Power-Ups**
    - Initial idea was to create a `CHAOS` mode where players could choose which power-ups to use. Ideas included bomb, slow-motion, pick-your-piece and more. Idea was scrapped due to time constraints and difficulty.
2. **Music Progression**
    - Initial idea was to layer music/change music to include more layers as levels progressed to increase excitement as game speeds up. Idea was scrapped due to inexperience in music making.
3. **Life system**
    - Initial idea was to implement lives in `4-WAY` mode. Idea was scrapped as brick spawning in the centre meant that there was no way to get second chances without clearing lines or restarting. Idea can be implemented with more brainstorming, but was too iffy thus left out.

# New Java classes:
### Application and utility (com.comp2042.application)
- `SoundManager`: Manages loading and playing all AudioClip and MediaPlayer resources. Handless logic for looping BGM and SFX.
- `AppNavigator`: Centralises scene switching logic such as loading FXMLs, injecting controllers and removing responsibility from Views.
- `HighScoreManager`: Handles file I/O for scores.txt, sorting scores and maintaining hall of fame.

### Controllers (com.comp2042.controller)
- `PreSplashController`: Displays initial disclaimer/intro screen with timer to inform user to control with keyboard.
- `StartScreenController`: Displays splash screen with simple logic to send to main menu when any key is pressed.
- `MainMenuController`: Handles logic for main menu. Displays three buttons:
    1. `PLAY` to send player to game mode selection.
    2. `HOW-TO` to send player to simple tutorial screen.
    3. `EXIT` to quit programme.
- `InstructionsController`: Simple controller to display how-to and allows exit-to-menu with any key press.
- `GameModeController`: Handles logic for game mode selection screen. Buttons lead to respective game modes.
- `FocusTraverser`: Utility class that applies an event filter to remap WASP and up/down keys to Arrow (left/right) keys and ENTER to SPACE to ensure consistent navigation across all menus.
- `GameConfiguration`: To store constants for game configuration, reducing magic numbers.
- `KeyInputHandler`: Extracted from `GuiController` to handle user key inputs to maintain the Single Responsibility Principle.

### Logic (com.comp2042.logic)
- `BrickHolder`: Encapsulates logic for hold brick mechanic.
- `BrickMover`: Extracted from `SimpleBoard` to encapsulate logic for brick movement.
- `BrickRotator`: Extracted from `SimpleBoard` to encapsulate logic for brick rotation.
- `BrickSpawnHandler`: Calculates spawn coordinates, handling difference between *GAMEBOY* standard spawn and 4-Way centre spawn.
- `BrickLandingHandler`: Handles merging and clearing rows.
- `CollisionDetector`: Extracted from `SimpleBoard` to encapsulate detection of collision.
- `FourWayClearHandler`: Contains algorithm to check for full rows/columns in a cross pattern for 4-WAY mode and clearing them. Remaining bricks fall by gravity by half of screen.
- `GameModeRules`: Interface to define behaviour for new modes.
- `ClassicModeRules`.
- `ZenModeRules`.
- `SurvivalModeRules`.
- `FourWayRules`.

### Model (com.comp2042.model)
- `FourWayBoard`: New board implementation for `4-WAY` mode. Handles logic for 4-directional gravity and collision detection. Creation is as per refactored `SimpleBoard`.
- `BrickColourFactory`: Extracted from `GuiController` to handle Tetromino colour choices.
- `Tetromino`: Abstract class to reduce repetition in brick logic to maintain Don't Repeat Yourself principle.

### View (com.comp2042.view)
- `BoardRenderConfiguration`: Extracted from `GuiController` to handle board rendering.
- `GameLooper`: Extracted from `GuiController` to handle looping of game ticks.
- `GameLoopListener`: Interface.
- `GameOverAnimator`: Simple animation to indicate game is over – replaces `GameOverPanel`.
- `GameRenderer`: Extracted from `GuiController` to handle game rendering, keeping code to Single Responsibility Principle.

# New resources (non-Java classes)
### Resources
- Sounds; BGM and SFX.
- Fonts for title screen and texts.
- FXML layouts for all screens and menus.

# Modified Java Classes:
### General refactoring
- Created new packages. 
- Refactored `bricks` package into `model`.
- Refactored `Main.java` into `application`.
- Refactored `Board.java` and `SimpleBoard.java` into `model.board`. 
- Refactored `GameController.java` into `controller`.
- Refactored `GuiController.java` into `view`. 
- Refactored `EventSource.java`, `EventType.java`, `MoveEvent.java`, `InputEventListener.java` into `model.events`.
- Refactored `BrickRotator.java`, `ClearRow.java`, `DownData.java`, `MatrixOperations.java`, `NextShapeInfo.java`, `Score.java` and `ViewData.java` into `logic`.

### Removed classes
- `GameOverPanel.java`: Game over is replaced with `GameOverAnimation` now.
- `NotificationPanel.java`: Bonus popups were an eyesore, removed to improve gameplay experience. Indications of bonuses are replaced with sound effects.

### GuiController.java: 
- Modified method to get `PressStart2P.ttf`: To match retro theme of the game. 
- Added pause functionality triggered by ESCAPE or P key: To allow players to pause the game without affecting game logic.
- Added preview functionality to preview next brick: To allow players to expect what is the upcoming brick; allow for extension for more than one preview afterwards. 
- Added score feature: To allow players to get their scores. 
- Modified hiding/displaying `gameOverPanel` to hiding/displaying `groupNotification`: To fix pause button hitbox being blocked. 
- Added `@FXML` bindings for new UI elements (Score, Next Brick and Hold Brick). Added `updateSpeed` and game loop management: To support new `FXML`-based layouts and dynamic game updates.

### SimpleBoard.java:
- Modified `currentOffset` to change tetromino spawn point: To match Tetris *GAMEBOY* spawn point. 
- Extracted heavy logic into helper classes (`BrickMover`, `BrickRotator`). Added `BrickHolder` integration: Class was a God Class. Refactoring made it easier to read, debug and implement extensions.

### Main.java:
- Modified window size: To match retro theme of the game.
- Stripped of all game logic and now acts solely as entry point to launch AppNavigator: To adhere to Single Responsibility Principle (SRP) and separate startup logic from game logic.

### GameController.java: 
- Modified board size: To match retro theme of the game.
- Decoupled from UI manipulation, now communicates with GuiController via interfaces. Added support for `SURVIVAL` mode mechanics and `4-WAY` input handling: Original controller was tightly coupled with View, making it hard to extend.

### Modified files:
- `window_style.css`: Edited design elements including background colour, board colour and font to Press Start 2P: To ensure programme runs after updating packages. 
- `gameLayout.fxml`: Edited layout to include implemented features including preview panel, pause button, score. 

# Unexpected problems:
- Refactoring all classes required updating imports; Maven compiled but failed at runtime. Cause of issue was overlooking gameLayout.fxml and was resolved by updating FXML references.
- Preview panel previewed the same brick and did not "refresh." Cause of issue was not implementing "previewPanel" to "refreshBrick" method and was resolved by having the preview panel refresh its bricks. 
- Implementing score feature disabled pause button; soon found out it was not directly due to the feature, rather the score pushed the pause button down, which was then blocked by the gameOverPanel in FXML. Resolved by hiding/showing groupNotification instead of just gameOverPanel to prevent overlapping hitbox. 
- 4-Way Collision Logic where implementing gravity in 4-directions was harder than expected. Initial idea was to simply replicate clearRow logic but gravitational aspects were ovverlooked. Resolved by thinking of the Tetris game's fundamental logics in handling collision, locking and more.