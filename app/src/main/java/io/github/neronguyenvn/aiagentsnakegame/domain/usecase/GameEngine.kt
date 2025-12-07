package io.github.neronguyenvn.aiagentsnakegame.domain.usecase

import io.github.neronguyenvn.aiagentsnakegame.domain.model.Coordinate
import io.github.neronguyenvn.aiagentsnakegame.domain.model.Direction
import io.github.neronguyenvn.aiagentsnakegame.domain.model.Food
import io.github.neronguyenvn.aiagentsnakegame.domain.model.GameState
import io.github.neronguyenvn.aiagentsnakegame.domain.model.GameStatus
import io.github.neronguyenvn.aiagentsnakegame.domain.usecase.game.BOARD_SIZE

class GameEngine {
    fun tick(gameState: GameState): GameState {
        if (gameState.gameStatus != GameStatus.PLAYING) {
            return gameState
        }

        val newHead = when (gameState.snake.direction) {
            Direction.UP -> gameState.snake.head.copy(y = gameState.snake.head.y - 1)
            Direction.DOWN -> gameState.snake.head.copy(y = gameState.snake.head.y + 1)
            Direction.LEFT -> gameState.snake.head.copy(x = gameState.snake.head.x - 1)
            Direction.RIGHT -> gameState.snake.head.copy(x = gameState.snake.head.x + 1)
        }

        if (newHead.x < 0 || newHead.x >= BOARD_SIZE || newHead.y < 0 || newHead.y >= BOARD_SIZE || newHead in gameState.snake.body) {
            return gameState.copy(gameStatus = GameStatus.GAME_OVER)
        }

        val newBody = mutableListOf(gameState.snake.head)
        newBody.addAll(gameState.snake.body.dropLast(if (newHead == gameState.food.position) 0 else 1))

        if (newHead == gameState.food.position) {
            var newFoodPosition: Coordinate
            do {
                newFoodPosition = Coordinate(
                    x = (0 until BOARD_SIZE).random(),
                    y = (0 until BOARD_SIZE).random()
                )
            } while (newFoodPosition == newHead || newFoodPosition in newBody)
            return gameState.copy(
                snake = gameState.snake.copy(head = newHead, body = newBody),
                food = Food(position = newFoodPosition),
                score = gameState.score + 1
            )
        }

        return gameState.copy(
            snake = gameState.snake.copy(head = newHead, body = newBody)
        )
    }
}
