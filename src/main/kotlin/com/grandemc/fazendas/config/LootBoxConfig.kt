package com.grandemc.fazendas.config

import com.grandemc.fazendas.GrandeFazendas
import com.grandemc.post.external.lib.cache.config.StateConfig
import com.grandemc.post.external.lib.global.bukkit.getByte
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

        fun getByLandId(id: Byte): List<LootBox> {
            return lootBoxes.filter { it.landGainId == id }
        }
    }
    inner class LootBox(
        val id: Byte,
        val landGainId: Byte,
        val name: String,
        val content: List<ChanceBooster>
    )
    inner class ChanceBooster(
        val chance: Double,
        val booster: Booster
    )
    class Booster(
        val boost: Double,
        val duration: Int
    )

    override fun fetch(config: FileConfiguration): Config {
        return config.section("caixas").mappedSection {
            LootBox(
                getByte("id"),
                getByte("plantio"),
                getString("nome").color(),
                section("boosters").mappedSection {
                    ChanceBooster(
                        getDouble("chance"),
                        Booster(
                            getDouble("boost"),
                            getInt("duracao")
                        )
                    )
                }
            )
        }.let { Config(config.getString("formato_booster").color(), it) }
    }
}