package com.grandemc.fazendas.init.model

import com.grandemc.fazendas.config.FarmsConfig
import com.grandemc.fazendas.config.IslandConfig
import com.grandemc.fazendas.config.LandsConfig
import com.grandemc.fazendas.config.MaterialsConfig
import com.grandemc.post.external.lib.cache.config.Updatable
import com.grandemc.post.external.lib.cache.config.chunk.base.*

data class ConfigCache(
    val updater: Updatable,
    val configs: Configs
) {
    data class Configs(
        val messages: MessagesChunk,
        val sounds: SoundsChunk,
        val effects: EffectsChunk,
        val menus: MenusChunk,
        val island: IslandConfig,
        val farms: FarmsConfig,
        val lands: LandsConfig,
        val materials: MaterialsConfig,
        val items: ItemsChunk
    )
}