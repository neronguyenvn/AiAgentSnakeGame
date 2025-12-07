package io.github.neronguyenvn.aiagentsnakegame.domain.usecase

import io.github.neronguyenvn.aiagentsnakegame.domain.model.Coordinate
import io.github.neronguyenvn.aiagentsnakegame.domain.model.Direction
import io.github.neronguyenvn.aiagentsnakegame.domain.model.Food
import io.github.neronguyenvn.aiagentsnakegame.domain.model.GameState
import io.github.neronguyenvn.aiagentsnakegame.domain.model.GameStatus
import io.github.neronguyenvn.aiagentsnakegame.domain.model.Snake
import io.github.neronguyenvn.aiagentsnakegame.domain.usecase.game.GAMESPEED
import io.github.neronguyenvn.aiagentsnakegame.domain.usecase.game.BOARD_SIZE
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GameEngine {
    fun gameLoop(): Flow<GameState> = flow {
        var gameState = GameState(
            snake = Snake(
                head = Coordinate(x = 5, y = 5),
                direction = Direction.RIGHT,
                body = listOf(Coordinate(x = 4, y = 5), Coordinate(x = 3, y = 5))
            ),
            food = Food(position = Coordinate(x = 10, y = 10)),
            boardSize = BOARD_SIZE,
        )
        emit(gameState)

        while (gameState.gameStatus == GameStatus.PLAYING) {
            delay(GAMESPEED)
            val newHead = when (gameState.snake.direction) {
                Direction.UP -> gameState.snake.head.copy(y = gameState.snake.head.y - 1)
                Direction.DOWN -> gameState.snake.head.copy(y = gameState.snake.head.y + 1)
                Direction.LEFT -> gameState.snake.head.copy(x = gameState.snake.head.x - 1)
                Direction.RIGHT -> gameState.snake.head.copy(x = gameState.snake.head.x + 1)
            }

            if (newHead.x < 0 || newHead.x >= BOARD_SIZE || newHead.y < 0 || newHead.y >= BOARD_SIZE || newHead in gameState.snake.body) {
                gameState = gameState.copy(gameStatus = GameStatus.GAME_OVER)
            } else {
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
                    gameState = gameState.copy(
                        snake = gameState.snake.copy(head = newHead, body = newBody),
                        food = Food(position = newFoodPosition),
                        score = gameState.score + 1
                    )
                } else {
                    gameState = gameState.copy(
                        snake = gameState.snake.copy(head = newHead, body = newBody)
                    )
                }
            }
            emit(gameState)
        }
    }
}
