package com.grandemc.fazendas.config.model.level.base

import com.grandemc.fazendas.config.model.level.LevelContainer

abstract class ComputedLevels<T, R, U>(
    private val requirements: List<LevelRequirements<R>>,
    private val upgrades: List<LevelUpgrades<U>>
) : LevelContainer<T> {
    override fun level(level: Int): T {
        return newLevelAccumulator().apply {
            for (req in requirements) {
                applyRequirements(req.levelPattern.apply(level), req)
            }

            for (upg in upgrades) {
                applyUpgrades(upg.levelPattern.apply(level), upg)
            }
        }.build()
    }

    abstract fun newLevelAccumulator(): LevelAccumulator<T, R, U>
}