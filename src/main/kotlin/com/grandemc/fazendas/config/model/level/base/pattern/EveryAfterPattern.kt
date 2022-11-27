package com.grandemc.fazendas.config.model.level.base.pattern

class EveryAfterPattern(
    private val afterLevel: Int,
    private val everyLevel: Int
) : LevelPattern {
    override fun apply(level: Int): Int {
        return if (level < afterLevel)
                0
            else
                level - afterLevel / everyLevel
    }
}