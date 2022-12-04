package com.grandemc.fazendas.manager

import com.grandemc.fazendas.config.IslandConfig
import java.util.UUID

class StatsManager(
    private val playerManager: PlayerManager,
    private val farmManager: FarmManager,
    private val islandConfig: IslandConfig
) {
    fun dailyQuests(playerId: UUID): Byte {
        val farmLevel = farmManager.farm(playerId).level().toInt()
        return islandConfig.get().evolution.levels.level(farmLevel).upgrades.dailyQuests
    }

    fun boostedXp(playerId: UUID, xp: Int): Int {
        return xp
            .let {
                playerManager.player(playerId).booster()?.let { booster ->
                    (it * booster.boost()).toInt()
                } ?: it
            }
    }
}