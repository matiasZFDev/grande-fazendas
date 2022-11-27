package com.grandemc.fazendas.config.model.level.enchant

import com.grandemc.fazendas.config.model.level.base.LevelAccumulator
import com.grandemc.fazendas.config.model.level.base.LevelRequirements
import com.grandemc.fazendas.config.model.level.base.LevelUpgrades

class EnchantLevelAccumulator : LevelAccumulator<EnchantLevel, Double, Double> {
    private var gold: Double = 0.0
    private var value: Double = 0.0

    override fun applyRequirements(result: Int, requirements: LevelRequirements<Double>) {
        gold += result * requirements.requirements
    }

    override fun applyUpgrades(result: Int, upgrades: LevelUpgrades<Double>) {
        value += result * upgrades.upgrades
    }

    override fun build(): EnchantLevel {
        return EnchantLevel(
            EnchantLevel.Requirements(gold), EnchantLevel.Upgrades(value)
        )
    }
}