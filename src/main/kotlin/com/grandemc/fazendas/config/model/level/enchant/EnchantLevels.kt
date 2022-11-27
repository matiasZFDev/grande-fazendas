package com.grandemc.fazendas.config.model.level.enchant

import com.grandemc.fazendas.config.model.level.base.ComputedLevels
import com.grandemc.fazendas.config.model.level.base.LevelAccumulator
import com.grandemc.fazendas.config.model.level.base.LevelRequirements
import com.grandemc.fazendas.config.model.level.base.LevelUpgrades

class EnchantLevels(
    requirements: List<LevelRequirements<Double>>, upgrades: List<LevelUpgrades<Double>>
) : ComputedLevels<EnchantLevel, Double, Double>(
    requirements, upgrades
) {
    override fun newLevelAccumulator(): LevelAccumulator<EnchantLevel, Double, Double> {
        return EnchantLevelAccumulator()
    }
}