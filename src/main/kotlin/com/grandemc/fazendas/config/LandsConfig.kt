package com.grandemc.fazendas.config

import com.grandemc.fazendas.GrandeFazendas
import com.grandemc.post.external.lib.cache.config.StateConfig
import com.grandemc.post.external.lib.global.bukkit.intList
import com.grandemc.post.external.lib.global.color
import com.grandemc.post.external.lib.util.CustomConfig
import org.bukkit.configuration.file.FileConfiguration

class LandsConfig(customConfig: CustomConfig) : StateConfig<LandsConfig.Config>(
    customConfig, GrandeFazendas.CONTEXT
) {
    inner class Config(
        val menuSlots: List<Int>,
        val requiredItemFormat: String,
        val notSelectedCrop: String
    )

    override fun fetch(config: FileConfiguration): Config {
        return config.run {
            Config(
                intList("menu_slots"),
                getString("formato_iten_requerido").color(),
                getString("sem_plantacao")
            )
        }
    }
}