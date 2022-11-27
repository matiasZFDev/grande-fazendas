package com.grandemc.fazendas.config

import com.grandemc.fazendas.GrandeFazendas
import com.grandemc.fazendas.config.model.level.LevelContainer
import com.grandemc.fazendas.config.model.level.enchant.EnchantLevel
import com.grandemc.fazendas.config.model.level.enchant.EnchantLevelFetcher
import com.grandemc.fazendas.storage.player.model.CustomEnchant
import com.grandemc.post.external.lib.cache.config.StateConfig
import com.grandemc.post.external.lib.global.bukkit.section
import com.grandemc.post.external.lib.util.CustomConfig
import org.bukkit.configuration.file.FileConfiguration

class FarmHoeConfig(customConfig: CustomConfig) : StateConfig<FarmHoeConfig.Config>(
    customConfig, GrandeFazendas.CONTEXT
) {
    inner class Config(
        private val enchants: List<EnchantData>
    ) {
        fun getEnchant(enchant: CustomEnchant): EnchantData {
            return enchants.first { it.enchant == enchant }
        }
    }
    inner class EnchantData(
        val enchant: CustomEnchant,
        val maxLevel: Int,
        val levels: LevelContainer<EnchantLevel>
    )

    override fun fetch(config: FileConfiguration): Config {
        return CustomEnchant.values().map { enchant ->
            config.section(enchant.configName()).run {
                EnchantData(
                    enchant,
                    getInt("nivel_maximo"),
                    EnchantLevelFetcher(section("nivel")).fetch()
                )
            }
        }.let(::Config)
    }
}