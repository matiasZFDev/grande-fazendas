package com.grandemc.fazendas.config.model.level.island

import com.grandemc.fazendas.config.model.level.base.LevelRequirements
import com.grandemc.fazendas.config.model.level.base.pattern.LevelPattern

class IslandLevelRequirements(
    override val levelPattern: LevelPattern,
    override val requirements: IslandRequirements
) : LevelRequirements<IslandRequirements>