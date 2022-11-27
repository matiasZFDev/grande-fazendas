package com.grandemc.fazendas.config.model.level.base.pattern

class EveryPattern(private val level: Int) : LevelPattern {
    override fun apply(level: Int): Int {
        return level / this.level
    }
}