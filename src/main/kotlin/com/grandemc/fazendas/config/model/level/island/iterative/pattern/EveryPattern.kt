package com.grandemc.fazendas.config.model.level.island.iterative.pattern

class EveryPattern(private val level: Int) : LevelPattern {
    override fun apply(level: Int): Int {
        return level / this.level
    }
}