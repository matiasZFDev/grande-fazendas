package com.grandemc.fazendas.config.model.level.island

import com.grandemc.fazendas.config.model.level.base.LevelAccumulator
import com.grandemc.fazendas.config.model.level.base.LevelRequirements
import com.grandemc.fazendas.config.model.level.base.LevelUpgrades

class IslandLevelAccumulator : LevelAccumulator<IslandLevel, IslandRequirements, IslandUpgrades> {
    private var xp: Int = 0

    private var dailyQuests: Byte = 0

    override fun applyRequirements(
        result: Int, requirements: LevelRequirements<IslandRequirements>
    ) {
        xp += result * requirements.requirements.xp
    }

    override fun applyUpgrades(result: Int, upgrades: LevelUpgrades<IslandUpgrades>) {
        dailyQuests = (dailyQuests + result * upgrades.upgrades.dailyQuests).toByte()
    }

    override fun build(): IslandLevel {
        return IslandLevel(
            IslandLevel.Requirements(xp),
            IslandLevel.Upgrades(dailyQuests)
        )
    }
}