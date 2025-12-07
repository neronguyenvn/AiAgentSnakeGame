package io.github.neronguyenvn.aiagentsnakegame.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.neronguyenvn.aiagentsnakegame.domain.model.Direction
import io.github.neronguyenvn.aiagentsnakegame.domain.model.GameStatus
import io.github.neronguyenvn.aiagentsnakegame.presentation.component.Food
import io.github.neronguyenvn.aiagentsnakegame.presentation.component.SnakePart
import io.github.neronguyenvn.aiagentsnakegame.presentation.viewmodel.GameViewModel

@Composable
fun GameScreen(
    gameViewModel: GameViewModel = viewModel()
) {
    val gameState = gameViewModel.gameState.collectAsState().value

    if (gameState == null) {
        gameViewModel.startGame()
        return
    }

    BoxWithConstraints {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = { offset ->
                            val x = offset.x
                            val y = offset.y
                            val direction = when {
                                x < constraints.maxWidth / 4 -> Direction.LEFT
                                x > constraints.maxWidth * 3 / 4 -> Direction.RIGHT
                                y < constraints.maxHeight / 2 -> Direction.UP
                                else -> Direction.DOWN
                            }
                            gameViewModel.onDirectionChange(direction)
                        }
                    )
                }
        ) {
            SnakePart(
                modifier = Modifier.offset(
                    (gameState.snake.head.x * 20).dp,
                    (gameState.snake.head.y * 20).dp
                ),
                color = Color.Green
            )
            gameState.snake.body.forEach {
                SnakePart(
                    modifier = Modifier.offset((it.x * 20).dp, (it.y * 20).dp),
                    color = Color.Green.copy(alpha = 0.5f)
                )
            }
            Food(
                modifier = Modifier.offset(
                    (gameState.food.position.x * 20).dp,
                    (gameState.food.position.y * 20).dp
                )
            )
            Text(
                text = "Score: ${gameState.score}",
                modifier = Modifier.padding(16.dp),
                color = Color.White
            )
            if (gameState.gameStatus == GameStatus.GAME_OVER) {
                Text(
                    text = "Game Over",
                    modifier = Modifier.padding(top = 100.dp),
                    color = Color.White
                )
            }
        }
    }
}
