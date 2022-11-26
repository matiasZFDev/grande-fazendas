package com.grandemc.fazendas.config

import com.grandemc.fazendas.GrandeFazendas
import com.grandemc.post.external.lib.cache.config.StateConfig
import com.grandemc.post.external.lib.global.bukkit.getByte
import com.grandemc.post.external.lib.global.bukkit.mappedKeys
import com.grandemc.post.external.lib.global.bukkit.mappedSection
import com.grandemc.post.external.lib.global.bukkit.section
import com.grandemc.post.external.lib.global.color
import com.grandemc.post.external.lib.util.CustomConfig
import org.bukkit.configuration.file.FileConfiguration

class LootBoxConfig(customConfig: CustomConfig) : StateConfig<LootBoxConfig.Config>(
    customConfig, GrandeFazendas.CONTEXT
) {
    inner class Config(
        private val boosterFormat: String,
        private val lootBoxes: List<LootBox>
    ) {
        fun boosterFormat(): String {
            return boosterFormat
        }

        fun getLootBox(id: Byte): LootBox? {
            return lootBoxes.find { it.id == id }
        }
    }
    inner class LootBox(
        val id: Byte,
        val name: String,
        val content: List<ChanceBooster>
    )
    inner class ChanceBooster(
        val chance: Double,
        val boost: Double,
        val duration: Int
    )

    override fun fetch(config: FileConfiguration): Config {
        return config.section("caixas").mappedSection {
            LootBox(
                getByte("id"),
                getString("nome").color(),
                section("boosters").mappedSection {
                    ChanceBooster(
                        getDouble("chance"),
                        getDouble("boost"),
                        getInt("duracao")
                    )
                }
            )
        }.let { Config(config.getString("formato_booster").color(), it) }
    }
}