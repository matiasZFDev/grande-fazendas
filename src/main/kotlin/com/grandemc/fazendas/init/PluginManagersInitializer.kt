package com.grandemc.fazendas.init

import com.grandemc.fazendas.config.FarmsConfig
import com.grandemc.fazendas.config.FertilizingConfig
import com.grandemc.fazendas.config.IslandConfig
import com.grandemc.fazendas.config.MaterialsConfig
import com.grandemc.fazendas.global.respond
import com.grandemc.post.external.lib.init.Initializer
import com.grandemc.fazendas.init.model.PluginManagers
import com.grandemc.fazendas.manager.*
import com.grandemc.fazendas.storage.player.model.FarmPlayer
import com.grandemc.post.external.lib.cache.config.chunk.base.ItemsChunk
import com.grandemc.post.external.lib.database.base.DatabaseService
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import java.util.UUID

class PluginManagersInitializer(
    private val playerService: DatabaseService<UUID, FarmPlayer>,
    private val plugin: Plugin,
    private val islandConfig: IslandConfig,
    private val farmsConfig: FarmsConfig,
    private val materialsConfig: MaterialsConfig,
    private val itemsConfig: ItemsChunk,
    private val fertilizingConfig: FertilizingConfig
) : Initializer<PluginManagers> {
    override fun init(): PluginManagers {
        val playerManager = PlayerManager(playerService)
        val farmManager = FarmManager(playerManager)
        val islandManager = IslandManager(playerManager, farmManager, islandConfig)
        val buildManager = BuildManager()
        val landManager = LandManager(
            farmManager, farmsConfig, buildManager, islandManager, islandConfig
        )
        val successGeneration: (Player?) -> Unit = { player: Player? ->
            player.respond("ilha.criada")
            player?.let { islandManager.joinIsland(it) }
        }
        return PluginManagers(
            playerManager,
            islandManager,
            buildManager,
            IslandGenerationManager(
                plugin, playerManager, buildManager, islandManager, islandConfig,
                farmsConfig, successGeneration
            ),
            farmManager,
            landManager,
            StorageManager(playerManager, materialsConfig),
            GoldBank(playerManager),
            LandPlantManager(islandConfig, islandManager, landManager),
            FarmItemManager(itemsConfig, fertilizingConfig)
        )
    }
}