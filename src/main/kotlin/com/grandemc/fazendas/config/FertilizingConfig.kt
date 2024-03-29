package com.grandemc.fazendas.config

import com.grandemc.fazendas.GrandeFazendas
import com.grandemc.post.external.lib.cache.config.StateConfig
import com.grandemc.post.external.lib.global.bukkit.getByte
import com.grandemc.post.external.lib.global.bukkit.intList
import com.grandemc.post.external.lib.global.bukkit.mappedKeys
import com.grandemc.post.external.lib.global.bukkit.section
import com.grandemc.post.external.lib.global.color
import com.grandemc.post.external.lib.util.CustomConfig
import org.bukkit.configuration.file.FileConfiguration

class FertilizingConfig(customConfig: CustomConfig) : StateConfig<FertilizingConfig.Config>(
    customConfig, GrandeFazendas.CONTEXT
) {
    inner class Config(
        private val menuSlots: List<Int>,
        private val fertilizing: List<Fertilizing>
    ) {
        fun menuSlots(): List<Int> = menuSlots

        fun getById(id: Byte): Fertilizing? {
            return fertilizing.find { it.id == id }
        }
    }

    inner class Fertilizing(
        val nameId: String,
        val id: Byte,
        val name: String,
        val boost: Double,
        val xp: Int
    )

    override fun fetch(config: FileConfiguration): Config {
        return config.section("fertilizantes").mappedKeys().map { (key, section) ->
            Fertilizing(
                key,
                section.getByte("id"),
                section.getString("nome").color(),
                section.getDouble("reducao"),
                section.getInt("xp")
            )
        }.let {
            Config(config.intList("menu_slots"), it)
        }
    }
}