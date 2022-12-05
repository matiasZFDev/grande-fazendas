package com.grandemc.fazendas.manager

import com.grandemc.fazendas.config.IslandConfig
import com.grandemc.fazendas.storage.player.model.FarmUpgradeType
import com.grandemc.post.external.lib.global.ApplyType
import com.grandemc.post.external.lib.global.applyPercentage
import java.util.UUID

class StatsManager(
    private val playerManager: PlayerManager,
    private val farmManager: FarmManager,
    private val upgradesManager: UpgradesManager,
    private val islandConfig: IslandConfig
) {
    private fun upgradeValue(playerId: UUID, type: FarmUpgradeType): Double {
        return upgradesManager.upgradeConfig(playerId, type).value
    }

    fun dailyQuests(playerId: UUID): Byte {
        val farmLevel = farmManager.farm(playerId).level().toInt()
        val farmQuests = islandConfig.get().evolution.levels.level(
            farmLevel
        ).upgrades.dailyQuests
        val upgradeQuests = upgradeValue(playerId, FarmUpgradeType.DAILY_QUESTS).toInt().toByte()
        return farmQuests.plus(upgradeQuests).toByte()
    }

    fun boostedXp(playerId: UUID, xp: Int): Int {
        val boosterBoost = playerManager.player(playerId).booster()?.boost() ?: 0.0f
        val upgradeBoost = upgradeValue(playerId, FarmUpgradeType.XP_BOOST).toFloat()
        return xp.times(boosterBoost + upgradeBoost).toInt()
    }

    fun craftTime(playerId: UUID, time: Int): Int {
        val upgradeBoost = upgradeValue(playerId, FarmUpgradeType.CRAFT_TIME_REDUCTION)
        return time.toDouble().applyPercentage(upgradeBoost, ApplyType.DECREMENT).toInt()
    }

    fun localSell(playerId: UUID, price: Double): Double {
        val upgradeBonus = upgradeValue(playerId, FarmUpgradeType.LOCAL_SELL_BONUS)
        return price.applyPercentage(upgradeBonus)
    }
}