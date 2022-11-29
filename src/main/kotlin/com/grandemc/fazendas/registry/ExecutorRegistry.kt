package com.grandemc.fazendas.registry

import com.grandemc.fazendas.bukkit.task.CropsGrowthTask
import com.grandemc.fazendas.bukkit.task.IndustryRecipeTask
import com.grandemc.fazendas.bukkit.task.MarketItemExpiryTask
import com.grandemc.fazendas.config.CropsConfig
import com.grandemc.fazendas.manager.LandPlantManager
import com.grandemc.fazendas.manager.MarketManager
import com.grandemc.fazendas.manager.PlayerManager
import com.grandemc.fazendas.manager.StorageManager
import org.bukkit.plugin.Plugin

class ExecutorRegistry(
    private val plugin: Plugin,
    private val cropsConfig: CropsConfig,
    private val playerManager: PlayerManager,
    private val landPlantManager: LandPlantManager,
    private val marketManager: MarketManager,
    private val storageManager: StorageManager
) {
    fun startAll() {
        CropsGrowthTask(plugin, cropsConfig, playerManager, landPlantManager).start()
        IndustryRecipeTask(plugin, playerManager).start()
        MarketItemExpiryTask(plugin, marketManager, storageManager).start()
    }
}