package com.grandemc.fazendas.config.model.level.island.iterative

import com.grandemc.fazendas.config.model.level.LevelContainer
import com.grandemc.fazendas.config.model.level.island.IslandLevel
import com.grandemc.fazendas.config.model.level.island.LevelAccumulator

class IterativeIslandLevels(
    private val requirements: List<IslandLevelRequirements>,
    private val upgrades: List<IslandLevelUpgrades>
) : LevelContainer<IslandLevel> {
    override fun level(level: Int): IslandLevel {
        return LevelAccumulator().apply {
            for (req in requirements) {
                addXp(req.levelPattern.apply(level) * req.xp)
            }

            for (upg in upgrades) {
                addDailyQuests(upg.levelPattern.apply(level) * upg.dailyQuests)
            }
        }.build()
    }
}