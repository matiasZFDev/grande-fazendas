package com.grandemc.fazendas.storage.player.model

class FarmUpgrades(private val upgrades: List<FarmUpgrade>) {
    fun level(type: FarmUpgradeType): Byte {
        return upgrades.first { it.type == type }.level
    }

    fun levelUp(type: FarmUpgradeType) {
        upgrades.first { it.type == type }.level++
    }
}