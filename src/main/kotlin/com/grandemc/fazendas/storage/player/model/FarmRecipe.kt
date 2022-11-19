package com.grandemc.fazendas.storage.player.model

class FarmRecipe(
    private val id: Byte,
    private var timeLeft: Int
) {
    fun id(): Byte = id
    fun timeLeft(): Int = timeLeft
    fun advance() {
        timeLeft--
    }
}