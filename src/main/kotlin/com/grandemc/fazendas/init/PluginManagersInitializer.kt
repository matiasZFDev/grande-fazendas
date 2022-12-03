package com.grandemc.fazendas.init

import com.grandemc.fazendas.global.respond
import com.grandemc.fazendas.init.model.ConfigCache
import com.grandemc.post.external.lib.init.Initializer
import com.grandemc.fazendas.init.model.PluginManagers
import com.grandemc.fazendas.manager.*
import com.grandemc.fazendas.manager.IslandLocationManager
import com.grandemc.fazendas.storage.market.model.MarketItem
import com.grandemc.fazendas.storage.player.model.FarmPlayer
import com.grandemc.post.external.lib.database.base.DatabaseService
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import java.util.UUID

class PluginManagersInitializer(
    private val playerService: DatabaseService<UUID, FarmPlayer>,
    private val marketService: DatabaseService<Int, MarketItem>,
    private val plugin: Plugin,
    private val configs: ConfigCache.Configs
) : Initializer<PluginManagers> {
    override fun init(): PluginManagers {
        val playerManager = PlayerManager(playerService)
        val farmManager = FarmManager(playerManager)
        val farmItemManager = FarmItemManager(
            configs.items, configs.fertilizing, configs.lootBox, playerManager
        )
        val locationManager = IslandLocationManager(
            farmManager, playerManager, configs.island
        )
        val buildManager = BuildManager()
        val landManager = LandManager(
            farmManager, configs.farms, buildManager, locationManager,
            configs.island
        )
        val islandManager = IslandManager(
            playerManager, locationManager, configs.island, farmItemManager,
            configs.farms, landManager, configs.crops
        )
        val successGeneration: (Player?) -> Unit = { player: Player? ->
            player.respond("ilha.criada")
            player?.let { islandManager.joinIsland(it) }
        }
        val storageManager = StorageManager(playerManager, configs.materials)
        return PluginManagers(
            playerManager,
            locationManager,
            islandManager,
            buildManager,
            IslandGenerationManager(
                plugin, playerManager, buildManager, locationManager, configs.island,
                configs.farms, successGeneration
            ),
            farmManager,
            landManager,
            storageManager,
            GoldBank(playerManager),
            LandPlantManager(configs.island, locationManager, landManager),
            farmItemManager,
            IndustryManager(farmManager, storageManager, configs.industry),
            MarketManager(marketService, configs.market),
            QuestManager(configs.quests, farmManager),
            StatsManager(configs.quests)
        )
    }
}