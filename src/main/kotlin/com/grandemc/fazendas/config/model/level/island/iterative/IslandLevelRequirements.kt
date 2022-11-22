package com.grandemc.fazendas.config.model.level.island.iterative

import com.grandemc.fazendas.config.model.level.island.iterative.pattern.LevelPattern

data class IslandLevelRequirements(
    val levelPattern: LevelPattern,
    val xp: Int
)