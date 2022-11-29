package com.grandemc.fazendas.init.model

import com.grandemc.fazendas.manager.*

class PluginManagers(
    val playerManager: PlayerManager,
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
    val marketManager: MarketManager
)