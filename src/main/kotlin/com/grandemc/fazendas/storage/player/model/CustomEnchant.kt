package com.grandemc.fazendas.storage.player.model

enum class CustomEnchant(private val configName: String) {
    EXPERIENT("experiente"), REPLANT("replantar"), RADAR("radar");

    companion object {
        fun fromConfigName(configName: String): CustomEnchant {
            return values().first { it.configName == configName }
        }
    }

    fun configName(): String = configName
}