package com.grandemc.fazendas.config.model.level.island

import com.grandemc.fazendas.config.model.level.base.ComputedLevels
import com.grandemc.fazendas.config.model.level.base.LevelAccumulator
import com.grandemc.fazendas.config.model.level.base.LevelRequirements
import com.grandemc.fazendas.config.model.level.base.LevelUpgrades

class IslandLevels(
    requirements: List<LevelRequirements<IslandRequirements>>,
    upgrades: List<LevelUpgrades<IslandUpgrades>>
) : ComputedLevels<IslandLevel, IslandRequirements, IslandUpgrades>(
    requirements, upgrades
) {
    override fun newLevelAccumulator(): LevelAccumulator<IslandLevel, IslandRequirements, IslandUpgrades> {
        return IslandLevelAccumulator()
    }
}