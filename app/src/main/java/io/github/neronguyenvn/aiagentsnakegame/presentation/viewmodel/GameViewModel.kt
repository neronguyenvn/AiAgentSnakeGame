package io.github.neronguyenvn.aiagentsnakegame.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GameViewModel : ViewModel() {

    private val gameEngine = GameEngine()

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

    fun startGame() {
        viewModelScope.launch {
            while (_gameState.value.gameStatus == GameStatus.PLAYING) {
                delay(GAMESPEED)
                _gameState.value = gameEngine.tick(_gameState.value)
            }
        }
    }

    fun onDirectionChange(direction: Direction) {
        val currentDirection = _gameState.value.snake.direction
        if (direction.isOpposite(currentDirection)) {
            return
        }
        Log.d("GameViewModel", "onDirectionChange: $direction")
        _gameState.value = _gameState.value.copy(
            snake = _gameState.value.snake.copy(direction = direction)
        )
    }
}
