package com.grandemc.fazendas.bukkit.view.island.menu

import com.grandemc.fazendas.config.CropsConfig
import com.grandemc.fazendas.config.FarmsConfig
import com.grandemc.fazendas.config.IslandConfig
import com.grandemc.fazendas.manager.FarmManager
import com.grandemc.fazendas.manager.IslandManager
import com.grandemc.fazendas.manager.LandManager
import com.grandemc.post.external.lib.view.pack.impl.StatelessPackage

class IslandMainMenuPackage(
    farmManager: FarmManager,
    landManager: LandManager,
    islandConfig: IslandConfig,
    farmsConfig: FarmsConfig,
    cropsConfig: CropsConfig,
    islandManager: IslandManager
) : StatelessPackage(
    IslandMainMenuContainer::class,
    IslandMainMenuProcessor(
        islandManager, farmManager, landManager, islandConfig, farmsConfig,
        cropsConfig
    ),
    IslandMainMenuClickHandler(islandManager)
)