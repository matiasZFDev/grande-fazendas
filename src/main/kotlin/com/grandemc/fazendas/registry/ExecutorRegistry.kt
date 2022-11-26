package com.grandemc.fazendas.registry

import com.grandemc.fazendas.bukkit.task.CropsGrowthTask
import com.grandemc.fazendas.config.CropsConfig
import com.grandemc.fazendas.manager.LandPlantManager
import com.grandemc.fazendas.manager.PlayerManager
import org.bukkit.plugin.Plugin

class ExecutorRegistry(
    private val plugin: Plugin,
    private val cropsConfig: CropsConfig,
    private val playerManager: PlayerManager,
    private val landPlantManager: LandPlantManager
) {
    fun startAll() {
        CropsGrowthTask(plugin, cropsConfig, playerManager, landPlantManager).start()
    }
}