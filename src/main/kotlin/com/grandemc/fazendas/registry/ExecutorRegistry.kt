package com.grandemc.fazendas.registry

import com.grandemc.fazendas.bukkit.task.CropsGrowthTask
import com.grandemc.fazendas.bukkit.task.IndustryRecipeTask
import com.grandemc.fazendas.bukkit.task.MarketItemExpiryTask
import com.grandemc.fazendas.config.CropsConfig
import com.grandemc.fazendas.config.IndustryConfig
import com.grandemc.fazendas.manager.*
import org.bukkit.plugin.Plugin

class ExecutorRegistry(
    private val plugin: Plugin,
    private val cropsConfig: CropsConfig,
    private val playerManager: PlayerManager,
    private val landPlantManager: LandPlantManager,
    private val marketManager: MarketManager,
    private val storageManager: StorageManager,
    private val industryConfig: IndustryConfig,
    private val islandManager: IslandManager
) {
    fun startAll() {
        CropsGrowthTask(
            plugin, cropsConfig, playerManager, landPlantManager, islandManager
        ).start()
        IndustryRecipeTask(plugin, playerManager, industryConfig).start()
        MarketItemExpiryTask(plugin, marketManager, storageManager).start()
    }
}