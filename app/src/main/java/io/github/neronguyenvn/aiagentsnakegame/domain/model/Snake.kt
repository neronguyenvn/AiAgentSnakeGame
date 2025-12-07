package io.github.neronguyenvn.aiagentsnakegame.domain.model

data class Snake(
    val head: Coordinate,
    val body: List<Coordinate> = emptyList(),
    val direction: Direction = Direction.RIGHT
)
