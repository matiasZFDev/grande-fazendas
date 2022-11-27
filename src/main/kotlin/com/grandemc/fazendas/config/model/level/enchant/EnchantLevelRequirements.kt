package com.grandemc.fazendas.config.model.level.enchant

import com.grandemc.fazendas.config.model.level.base.LevelRequirements
import com.grandemc.fazendas.config.model.level.base.pattern.LevelPattern

class EnchantLevelRequirements(
    override val levelPattern: LevelPattern,
    override val requirements: Double
) : LevelRequirements<Double>