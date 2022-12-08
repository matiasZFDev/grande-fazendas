package com.grandemc.fazendas.init.model

import com.grandemc.fazendas.manager.*
import com.grandemc.fazendas.manager.controller.MarketSoldItemController
import com.grandemc.fazendas.manager.task.TaskManager

class PluginManagers(
    val playerManager: PlayerManager,
    val locationManager: IslandLocationManager,
    val islandManager: IslandManager,
    val buildManager: BuildManager,
    val islandGenerationManager: IslandGenerationManager,
    val farmManager: FarmManager,
    val landManager: LandManager,
    val storageManager: StorageManager,
    val goldBank: GoldBank,
    val landPlantManager: LandPlantManager,
    val farmItemManager: FarmItemManager,
    val industryManager: IndustryManager,
    val marketManager: MarketManager,
    val questManager: QuestManager,
    val statsManager: StatsManager,
    val taskManager: TaskManager,
    val upgradesManager: UpgradesManager,
    val marketSoldItemController: MarketSoldItemController
)