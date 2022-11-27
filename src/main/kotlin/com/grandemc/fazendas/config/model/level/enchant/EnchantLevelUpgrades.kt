package com.grandemc.fazendas.config.model.level.enchant

import com.grandemc.fazendas.config.model.level.base.LevelUpgrades
import com.grandemc.fazendas.config.model.level.base.pattern.LevelPattern

class EnchantLevelUpgrades(
    override val levelPattern: LevelPattern,
    override val upgrades: Double
) : LevelUpgrades<Double>