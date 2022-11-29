package com.grandemc.fazendas.init.model

import com.grandemc.fazendas.config.*
import com.grandemc.fazendas.config.model.MarketConfig
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
        val items: ItemsChunk,
        val fertilizing: FertilizingConfig,
        val crops: CropsConfig,
        val lootBox: LootBoxConfig,
        val farmHoe: FarmHoeConfig,
        val industry: IndustryConfig,
        val storage: StorageConfig,
        val market: MarketConfig
    )
}