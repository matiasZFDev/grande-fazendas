package com.grandemc.fazendas.bukkit.view.island

import com.grandemc.fazendas.config.IslandConfig
import com.grandemc.fazendas.manager.FarmManager
import com.grandemc.post.external.lib.view.pack.impl.StatelessPackage

class IslandPackage(
    farmManager: FarmManager,
    islandConfig: IslandConfig
) : StatelessPackage(
    IslandMenuContainer::class,
    IslandProcessor(farmManager, islandConfig),
    IslandClickHandler(farmManager)
)