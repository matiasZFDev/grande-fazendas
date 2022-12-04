package com.grandemc.fazendas.registry

import com.grandemc.fazendas.bukkit.task.CropsGrowthTask
import com.grandemc.fazendas.bukkit.task.IndustryRecipeTask
import com.grandemc.fazendas.bukkit.task.MarketItemExpiryTask
import com.grandemc.fazendas.config.CropsConfig
import com.grandemc.fazendas.config.IndustryConfig
import com.grandemc.fazendas.init.model.IslandTopState
import com.grandemc.fazendas.init.model.PluginStates
import com.grandemc.fazendas.manager.*
import com.grandemc.post.external.util.TopExecutor
import net.minecraft.server.v1_8_R3.Position
import org.bukkit.plugin.Plugin

class ExecutorRegistry(
    private val plugin: Plugin,
    private val cropsConfig: CropsConfig,
    private val playerManager: PlayerManager,
    private val landPlantManager: LandPlantManager,
    private val marketManager: MarketManager,
    private val storageManager: StorageManager,
    private val industryConfig: IndustryConfig,
    private val islandManager: IslandManager,
    private val statsManager: StatsManager,
    private val farmManager: FarmManager,
    private val islandTopState: IslandTopState
) {
    fun startAll() {
        CropsGrowthTask(
            plugin, cropsConfig, playerManager, landPlantManager, islandManager
        ).start()
        IndustryRecipeTask(
            plugin, playerManager, industryConfig, statsManager, farmManager,
            storageManager
        ).start()
        MarketItemExpiryTask(plugin, marketManager, storageManager).start()
        TopExecutor(
            plugin,
            islandTopState,
            10,
            10,
            { playerManager.allPlayers().filter { it.farm() != null } },
            { it.farm()?.xp() ?: 0 },
            {
                val farm = it.farm()!!
                PluginStates.IslandTopPosition(
                    it.id(),
                    farm.level().toShort(),
                    farm.xp(),
                    farm.questMaster().questsDone()
                )
            }
        ).start()
    }
}