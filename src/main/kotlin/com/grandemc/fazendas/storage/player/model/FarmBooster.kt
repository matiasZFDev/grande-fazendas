package com.grandemc.fazendas.storage.player.model

class FarmBooster(
    private val boost: Float,
    private var timeLeft: Short
) {
    fun boost(): Float = boost
    fun timeLeft(): Short = timeLeft
    fun advance() {
        timeLeft--
    }
    fun isDone(): Boolean = timeLeft <= 0
}