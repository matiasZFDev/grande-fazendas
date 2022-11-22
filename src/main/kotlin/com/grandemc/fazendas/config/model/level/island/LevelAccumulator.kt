package com.grandemc.fazendas.config.model.level.island

class LevelAccumulator {
    private var xp: Int = 0

    private var dailyQuests: Int = 0

    fun addXp(xp: Int) {
        this.xp += xp
    }
    fun addDailyQuests(dailyQuests: Int) {
        this.dailyQuests += dailyQuests
    }

    fun build(): IslandLevel {
        return IslandLevel(
            IslandLevel.Requirements(xp),
            IslandLevel.Upgrades(dailyQuests)
        )
    }
}