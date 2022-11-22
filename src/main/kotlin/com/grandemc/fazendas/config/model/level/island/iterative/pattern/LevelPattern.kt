package com.grandemc.fazendas.config.model.level.island.iterative.pattern

interface LevelPattern {
    fun apply(level: Int): Int
}