package com.grandemc.fazendas.config.model.level.island.iterative

import com.grandemc.fazendas.config.model.level.island.iterative.pattern.LevelPattern

data class IslandLevelUpgrades(
    val levelPattern: LevelPattern,
    val dailyQuests: Int
)