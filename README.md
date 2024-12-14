
# CW2024

This is the repository for Developing Maintainable Software (COMP2042) module coursework.[[GitHub Repository]](https://github.com/MuradOsman1/CW2024 "GitHub Repository")

### Compilation Instructions

Prerequisites  
Java 19 Oracle OpenJDK 23 with JavaFX  
Apache Maven Optional:  
IDE (e.g., IntelliJ IDEA)  
git  
Clone the repository  
Download the source code by cloning the repository:

1.  `git clone https://github.com/MuradOsman1/CW2024.git`
2.  `cd CW2024`

If you don’t have git:

Click on the code button on the GitHub page.  
—> “Download ZIP”.  
Extract the .zip file.  
Build and run  
with Maven installed  
From IntelliJ IDEA  
Import the project  
Set up JDK 19 Oracle OpenJDK 23  
Run the application via the main method

----------

## Features

### Implemented and Working Properly

#### User Interface

A polished UI including:

A main menu with options to play, view instructions, and quit.

Game over and win screens with replay and exit buttons.

Level transitions with unique backgrounds.

Horizontal Movement

The UserPlane can now move horizontally using the arrow keys.

Collision Handler

All collision logic (e.g., projectiles hitting actors) has been centralized in the CollisionHandler utility class, improving readability and maintainability.

Event Listener Functionality

Introduced event listeners for handling game events like level transitions and game over scenarios.

Asteroids in Level Three

Asteroids spawn in Level Three, falling at angled trajectories. Their spawn rate and trajectory can be customized.

Power-Ups in Level Four

Introduced power-ups that enhance the gameplay in Level Four.

#### Refactoring Improvements

Refactored Deprecated Observer class to EventListener.

Input handling moved to the UserPlane class.

Collision detection refactored to ensure modularity and better performance.

Renaming classes and organizing classes into packages.

----------

#### Features Added

Horizontal Movement: Enhanced gameplay by allowing the UserPlane to move in both horizontal and vertical directions.

Collision Handler: Encapsulated collision logic in a dedicated class.

EventListener Functionality: Improved event handling across levels.

Asteroids in Level Three: Added falling obstacles for increased difficulty.

Power-Ups in Level Four: Introduced collectible items to boost gameplay.

UserProjectiles spawn from User not from the left side of the screen.

Better Hitboxes  
Hitboxes are more accurate as blanked spaces from images used are cropped out.

----------

### Unexpected Problems Encountered

Projectile Behavior: Initial implementation caused projectiles to pass through enemies. This was resolved by updating collision detection in CollisionHandler.

Asteroid Trajectory: Implementing angled asteroid movement required tweaking spawn algorithms to ensure consistent difficulty.

Performance Issues: High spawn rates for asteroids and enemies caused frame drops. Optimized by batching updates in the game loop, and lowering spawn rates.

Issues with Loading Level Four: Level Four would not load initially, issue was solved by adding print statements to method to find out if they were called or not. The issue turned out to be the name of the background image was misspelled.

----------

### Java Classes

#### New Java Classes

Asteroid.java

Represents an asteroid with customizable size and speed.

Includes methods for angled movement.

CollisionHandler.java  
Centralized collision detection logic for actors and projectiles.  
Handles damage application and all collisions

EventListener.java  
Interface for handling game events such as level transitions and game over scenarios.

EventNotifier.java  
Provides a mechanism for notifying listeners about game events.

GameEvent.java  
Represents a game event that can occur during gameplay.  
Contains information about the type of event and any associated data.

LevelThree.java  
Third level of the game, includes asteroids.

LevelViewThree.java  
Represents the view for LevelThree.

LevelFour.java  
Fourth level of the game, includes power-ups.

LevelViewFour.java  
Represents the view for LevelFour.

PowerUp.java  
Handles Power-Ups Implementation

FireRatePowerUp.java  
Represents a power-up that increases the fire rate of the users plane.

HealthPowerUp.java  
Represents a power-up that increases the health of the users plane.

MainMenu.java  
A Main Menu was added, it has a Start button, a Quit button and a How-To-Play button, that shows instructions for the game.

BossHealth.java  
Represents the health display for the boss in the game.

#### Modified Java Classes

LevelParent  
Refactored to delegate collision detection and input handling to UserPlane and CollisionHandler classes.  
Integrated asteroid spawning logic for Level Three.

PlayerPlane  
Supports both horizontal and vertical movement.  
Enhanced input handling for intuitive controls.

Controller  
Refactored to use the EventListener instead of the Observer.  
Handles game events and takes appropriate actions based on the event type.

----------

### Unexpected Problems Encountered

Projectile Behavior: Initial implementation caused projectiles to pass through enemies. This was resolved by refining the CollisionHandler logic to accurately detect intersections between user projectiles and enemy actors. Additionally, projectile lifetimes were bounded to prevent unnecessary checks beyond the game area, improving overall efficiency. This was resolved by updating collision detection in CollisionHandler.

Enemy Respawning: Enemies would not respawn after the first wave due to incorrect reset logic in spawnEnemyUnits. This issue was resolved by ensuring the enemy counters reset correctly after each wave, and adding a mechanism to reinitialize the enemy spawn logic dynamically during the game loop, ensuring continuous spawning until the level ends, as evidenced by updates to the updateNumberOfEnemies method in LevelParent.

Asteroid Trajectory: Implementing angled asteroid movement required tweaking spawn algorithms to ensure consistent difficulty. The trajectory was adjusted by introducing random spawn positions and angular calculations to simulate realistic falling behavior. Parameters such as angle and velocity were fine-tuned to maintain challenging but fair gameplay. Additionally, trajectory adjustments ensured that the asteroids remained on-screen for longer durations without becoming too predictable. This was implemented by adjustments in the updatePosition method in the Asteroid class.

Performance Issues: High spawn rates for asteroids and enemies caused frame drops. Optimized by batching updates in the game loop and changing the spawn rates.

Collision Detection Logic: Initial collision detection logic inconsistencies led to projectiles sometimes missing valid targets. This was improved by enhancing the CollisionHandler class to prioritize accurate detection through bounding box intersections, thereby fixing the hit registration problem without affecting game performance.

User Input Lockup: Rare cases were observed where the user plane’s controls became unresponsive. Refactoring input handling to the UserPlane class isolated input control logic, preventing state conflicts and ensuring smoother control transitions.

Asteroid Spawn Logic: Initial spawn logic occasionally resulted in overlapping asteroids or inconsistent spawn timings. This was fixed by introducing precise spawn timers and ensuring random but non-overlapping spawn positions through adjustments in the spawnAsteroids method in LevelThree.

----------

### Planned but Not Implemented

Endless Mode: A continuous play mode with increasing difficulty. Planned early on but dropped due to time constraints and increasing difficulty in implementing

Sound Effects: Adding immersive audio feedback for actions like shooting or collisions. Dropped due to time constraints

Pause Button: A fully functional pause menu to restart or quit the game. The User would press P to pause the game. Dropped due to time contraints.
