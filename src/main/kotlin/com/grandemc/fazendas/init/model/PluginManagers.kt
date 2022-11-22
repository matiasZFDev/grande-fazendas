package com.grandemc.fazendas.init.model

import com.grandemc.fazendas.manager.BuildManager
import com.grandemc.fazendas.manager.IslandGenerationManager
import com.grandemc.fazendas.manager.IslandManager
import com.grandemc.fazendas.manager.PlayerManager

class PluginManagers(
    val playerManager: PlayerManager,
    val islandManager: IslandManager,
    val buildManager: BuildManager,
    val islandGenerationManager: IslandGenerationManager
)