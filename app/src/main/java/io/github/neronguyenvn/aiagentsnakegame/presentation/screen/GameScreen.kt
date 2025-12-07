package io.github.neronguyenvn.aiagentsnakegame.presentation.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import kotlin.math.abs
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    val (x, y) = dragAmount
                    Log.d("GameScreen", "Drag amount: x=$x, y=$y")
                    if (abs(x) > abs(y)) {
                        when {
                            x > 50 -> gameViewModel.onDirectionChange(Direction.RIGHT)
                            x < -50 -> gameViewModel.onDirectionChange(Direction.LEFT)
                        }
                    } else {
                        when {
                            y > 50 -> gameViewModel.onDirectionChange(Direction.DOWN)
                            y < -50 -> gameViewModel.onDirectionChange(Direction.UP)
                        }
                    }
                }
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
