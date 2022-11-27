package com.grandemc.fazendas.config.model.level.base

interface LevelAccumulator<T, R, U> {
    fun applyRequirements(result: Int, requirements: LevelRequirements<R>)
    fun applyUpgrades(result: Int, upgrades: LevelUpgrades<U>)
    fun build(): T
}