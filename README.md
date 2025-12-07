# AI Agent Snake Game

This is a classic Snake game implemented in Kotlin and Jetpack Compose, with a clean architecture approach. The game is controlled by an AI agent that plays the game automatically.

## Architecture

The project follows the principles of Clean Architecture, with the following packages:

*   **`domain`**: Contains the core game logic, including the game state, models, and the `GameEngine`.
*   **`data`**: Responsible for managing data, such as high scores.
*   **`presentation`**: The UI layer, built with Jetpack Compose, containing the `GameScreen`, `GameViewModel`, and UI components.

## How to Run

1.  Clone the repository.
2.  Open the project in Android Studio.
3.  Run the app on an Android emulator or a physical device.

## Next Steps

*   Add a proper AI agent to play the game.
*   Implement data persistence for high scores.
*   Add sound effects and music.
*   Improve the UI and add more game features.
