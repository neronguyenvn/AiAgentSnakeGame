package io.github.neronguyenvn.aiagentsnakegame.domain.model

data class GameState(
    val snake: Snake,
    val food: Food,
    val score: Int = 0,
    val gameStatus: GameStatus = GameStatus.PLAYING,
    val boardSize: Int
)
