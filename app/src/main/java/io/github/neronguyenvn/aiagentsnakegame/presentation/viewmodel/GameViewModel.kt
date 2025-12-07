package io.github.neronguyenvn.aiagentsnakegame.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import io.github.neronguyenvn.aiagentsnakegame.domain.model.Coordinate
import io.github.neronguyenvn.aiagentsnakegame.domain.model.Direction
import io.github.neronguyenvn.aiagentsnakegame.domain.model.Food
import io.github.neronguyenvn.aiagentsnakegame.domain.model.GameState
import io.github.neronguyenvn.aiagentsnakegame.domain.model.GameStatus
import io.github.neronguyenvn.aiagentsnakegame.domain.model.Snake
import io.github.neronguyenvn.aiagentsnakegame.domain.usecase.GameEngine
import io.github.neronguyenvn.aiagentsnakegame.domain.usecase.game.BOARD_SIZE
import io.github.neronguyenvn.aiagentsnakegame.domain.usecase.game.GAMESPEED
import io.github.neronguyenvn.aiagentsnakegame.domain.usecase.game.KEY_BEST_SCORE
import io.github.neronguyenvn.aiagentsnakegame.domain.usecase.game.KEY_LAST_SCORE
import io.github.neronguyenvn.aiagentsnakegame.domain.usecase.game.PREFS_NAME
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val gameEngine = GameEngine()
    private val sharedPreferences = application.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private val _gameState = MutableStateFlow(
        GameState(
            snake = Snake(
                head = Coordinate(x = 5, y = 5),
                direction = Direction.RIGHT,
                body = listOf(Coordinate(x = 4, y = 5), Coordinate(x = 3, y = 5))
            ),
            food = Food(position = Coordinate(x = 10, y = 10)),
            boardSize = BOARD_SIZE,
        )
    )
    val gameState: StateFlow<GameState> = _gameState

    private val _lastScore = MutableStateFlow(0)
    val lastScore: StateFlow<Int> = _lastScore

    private val _bestScore = MutableStateFlow(0)
    val bestScore: StateFlow<Int> = _bestScore

    init {
        _lastScore.value = sharedPreferences.getInt(KEY_LAST_SCORE, 0)
        _bestScore.value = sharedPreferences.getInt(KEY_BEST_SCORE, 0)
    }

    fun startGame() {
        // Reset game state for a new game
        _gameState.value = GameState(
            snake = Snake(
                head = Coordinate(x = 5, y = 5),
                direction = Direction.RIGHT,
                body = listOf(Coordinate(x = 4, y = 5), Coordinate(x = 3, y = 5))
            ),
            food = Food(position = Coordinate(x = 10, y = 10)),
            boardSize = BOARD_SIZE,
        )

        viewModelScope.launch {
            while (_gameState.value.gameStatus == GameStatus.PLAYING) {
                delay(GAMESPEED)
                _gameState.value = gameEngine.tick(_gameState.value)
            }
            // Game Over
            val currentScore = _gameState.value.score
            _lastScore.value = currentScore
            if (currentScore > _bestScore.value) {
                _bestScore.value = currentScore
            }
            with(sharedPreferences.edit()) {
                putInt(KEY_LAST_SCORE, _lastScore.value)
                putInt(KEY_BEST_SCORE, _bestScore.value)
                apply()
            }
        }
    }

    fun onDirectionChange(direction: Direction) {
        val currentDirection = _gameState.value.snake.direction
        if (direction.isOpposite(currentDirection) || direction == currentDirection) {
            return
        }
        Log.d("GameViewModel", "onDirectionChange: $direction")
        _gameState.value = _gameState.value.copy(
            snake = _gameState.value.snake.copy(direction = direction)
        )
    }
}
