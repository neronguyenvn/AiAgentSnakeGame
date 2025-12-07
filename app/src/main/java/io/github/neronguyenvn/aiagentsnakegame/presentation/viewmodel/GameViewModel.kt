package io.github.neronguyenvn.aiagentsnakegame.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.neronguyenvn.aiagentsnakegame.domain.model.Direction
import io.github.neronguyenvn.aiagentsnakegame.domain.model.GameState
import io.github.neronguyenvn.aiagentsnakegame.domain.usecase.GameEngine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GameViewModel : ViewModel() {

    private val gameEngine = GameEngine()

    private val _gameState = MutableStateFlow<GameState?>(null)
    val gameState: StateFlow<GameState?> = _gameState

    fun startGame() {
        viewModelScope.launch {
            gameEngine.gameLoop().collect {
                _gameState.value = it
            }
        }
    }

    fun onDirectionChange(direction: Direction) {
        _gameState.value = _gameState.value?.copy(
            snake = _gameState.value!!.snake.copy(direction = direction)
        )
    }
}
