package com.grandemc.fazendas.config

import com.boydti.fawe.Fawe
import com.boydti.fawe.FaweAPI
import com.boydti.fawe.`object`.FawePlayer
import com.grandemc.fazendas.GrandeFazendas
import com.grandemc.post.external.lib.cache.config.StateConfig
import com.grandemc.post.external.lib.global.bukkit.intList
import com.grandemc.post.external.lib.util.CustomConfig
import org.bukkit.configuration.file.FileConfiguration

class StorageConfig(customConfig: CustomConfig) : StateConfig<StorageConfig.Config>(
    customConfig, GrandeFazendas.CONTEXT
) {
    inner class Config(
        val menuSlots: List<Int>
    )

    override fun fetch(config: FileConfiguration): Config {
        return Config(config.intList("menu_slots"))
    }
}