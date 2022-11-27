package com.grandemc.fazendas.config.model.level.base

import com.grandemc.fazendas.config.model.level.base.pattern.LevelPattern

interface LevelRequirements<T> {
    val levelPattern: LevelPattern
    val requirements: T
}