package com.grandemc.fazendas.config

import com.grandemc.fazendas.GrandeFazendas
import com.grandemc.fazendas.storage.player.model.FarmUpgradeType
import com.grandemc.post.external.lib.cache.config.StateConfig
import com.grandemc.post.external.lib.global.bukkit.formatNumber
import com.grandemc.post.external.lib.global.bukkit.mappedKeys
import com.grandemc.post.external.lib.global.bukkit.mappedSection
import com.grandemc.post.external.lib.util.CustomConfig
import org.bukkit.configuration.file.FileConfiguration

class UpgradesConfig(customConfig: CustomConfig) : StateConfig<UpgradesConfig.Config>(
    customConfig, GrandeFazendas.CONTEXT
) {
    inner class Config(
        private val upgrades: List<Upgrade>
    ) {
        fun hasLevel(type: FarmUpgradeType, level: Byte): Boolean {
            return upgrades.first { it.type() == type }.hasLevel(level)
        }

        fun level(type: FarmUpgradeType, level: Byte): UpgradeLevel {
            return upgrades.first { it.type() == type }.level(level)
        }
    }
    inner class Upgrade(
        private val type: FarmUpgradeType,
        private val levels: List<UpgradeLevel>
    ) {
        fun type(): FarmUpgradeType = type

        fun hasLevel(level: Byte): Boolean {
            return level.inc() < levels.size
        }

        fun level(level: Byte): UpgradeLevel {
            return levels[level.toInt()]
        }
    }
    inner class UpgradeLevel(
        val cost: Double,
        val value: Double
    )

    override fun fetch(config: FileConfiguration): Config {
        return config.mappedKeys().map { (key, section) ->
            val type = FarmUpgradeType.fromConfigName(key)
            Upgrade(
                type,
                section.mappedSection {
                    UpgradeLevel(
                        formatNumber("custo"),
                        getDouble("valor")
                    )
                }
            )
        }.let(::Config)
    }
}