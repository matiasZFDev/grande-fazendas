package com.grandemc.fazendas.storage.player.model

class Quest(
    private val id: Byte,
    private val type: QuestType,
    private var progress: Int,
    private var done: Boolean = false
) {
    fun id(): Byte = id
    fun type(): QuestType = type
    fun progress(): Int = progress
    fun advance(points: Int) {
        progress += points
    }
    fun isDone(): Boolean = done
    fun complete() {
        done = true
    }
}