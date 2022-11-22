package com.grandemc.fazendas.config.model.level.island.iterative.pattern

class WhenPattern(private val level: Int) : LevelPattern {
    override fun apply(level: Int): Int {
        return if (level < this.level) 0 else 1
    }
}