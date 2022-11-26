package com.grandemc.fazendas.bukkit.view.land_plant

import com.grandemc.fazendas.bukkit.view.land.LandContext
import com.grandemc.fazendas.config.CropsConfig
import com.grandemc.fazendas.config.IslandConfig
import com.grandemc.fazendas.manager.FarmManager
import com.grandemc.fazendas.manager.IslandManager
import com.grandemc.fazendas.manager.LandManager
import com.grandemc.post.external.lib.cache.config.chunk.base.ItemsChunk
import com.grandemc.post.external.lib.view.pack.impl.StatefulPackage

class LandPlantPackage(
    itemsConfig: ItemsChunk,
    cropsConfig: CropsConfig,
    farmManager: FarmManager,
    islandManager: IslandManager,
    landManager: LandManager,
    islandConfig: IslandConfig
) : StatefulPackage<LandContext>(
    LandPlantMenuContainer::class,
    LandPlantProcessor(itemsConfig, cropsConfig, farmManager),
    LandPlantClickHandler(cropsConfig, islandManager, landManager, islandConfig)
)