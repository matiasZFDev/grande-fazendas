package com.grandemc.fazendas.config.model.level.base

import com.grandemc.fazendas.config.model.level.base.pattern.LevelPattern

interface LevelUpgrades<T> {
    val levelPattern: LevelPattern
    val upgrades: T
}