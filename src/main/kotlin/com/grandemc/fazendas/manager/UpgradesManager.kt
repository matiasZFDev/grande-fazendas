package com.grandemc.fazendas.manager

import com.grandemc.cash.GrandeCash
import com.grandemc.fazendas.config.UpgradesConfig
import com.grandemc.fazendas.storage.player.model.FarmUpgradeType
import com.grandemc.fazendas.storage.player.model.FarmUpgrades
import java.util.UUID

class UpgradesManager(
    private val playerManager: PlayerManager,
    private val upgradesConfig: UpgradesConfig
) {
    private fun upgrades(playerId: UUID): FarmUpgrades {
        return playerManager.player(playerId).upgrades()
    }

    fun maxLevel(playerId: UUID, type: FarmUpgradeType): Boolean {
        val upgradeLevel = upgrades(playerId).level(type)
        return !upgradesConfig.get().hasLevel(type, upgradeLevel)
    }

    fun canUpgrade(playerId: UUID, type: FarmUpgradeType): Boolean {
        val upgradeLevel = upgrades(playerId).level(type)
        val upgradeCost = upgradesConfig.get().level(type, upgradeLevel.inc()).cost
        return GrandeCash.api().has(playerId, upgradeCost)
    }

    fun upgradeConfig(playerId: UUID, type: FarmUpgradeType) : UpgradesConfig.UpgradeLevel {
        val upgradeLevel = upgrades(playerId).level(type)
        return upgradesConfig.get().level(type, upgradeLevel)
    }

    fun nextUpgradeConfig(playerId: UUID, type: FarmUpgradeType) : UpgradesConfig.UpgradeLevel {
        val upgradeLevel = upgrades(playerId).level(type)
        return upgradesConfig.get().level(type, upgradeLevel.inc())
    }

    fun upgrade(playerId: UUID, type: FarmUpgradeType) {
        upgrades(playerId).levelUp(type)
    }
}