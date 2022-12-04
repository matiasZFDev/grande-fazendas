package com.grandemc.fazendas.config.model.level.island

data class IslandLevel(
    val requirements: Requirements,
    val upgrades: Upgrades,
) {
    data class Requirements(
        val xp: Int
    )

    data class Upgrades(
        val dailyQuests: Byte
    )
}