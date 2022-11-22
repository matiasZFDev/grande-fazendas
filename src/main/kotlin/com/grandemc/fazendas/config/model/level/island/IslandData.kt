package com.grandemc.fazendas.config.model.level.island

import com.grandemc.fazendas.config.model.level.LevelContainer

data class IslandData(
    val takenFormat: String,
    val freeFormat: String,
    val lockedFormat: String,
    val maxLevel: Int,
    val levels: LevelContainer<IslandLevel>
)