package com.grandemc.fazendas.config.model.level.island

import com.grandemc.fazendas.config.model.level.base.LevelUpgrades
import com.grandemc.fazendas.config.model.level.base.pattern.LevelPattern

data class IslandLevelUpgrades(
    override val levelPattern: LevelPattern,
    override val upgrades: IslandUpgrades
) : LevelUpgrades<IslandUpgrades>