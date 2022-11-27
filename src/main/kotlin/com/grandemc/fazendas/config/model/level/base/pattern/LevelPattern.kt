package com.grandemc.fazendas.config.model.level.base.pattern

interface LevelPattern {
    fun apply(level: Int): Int
}