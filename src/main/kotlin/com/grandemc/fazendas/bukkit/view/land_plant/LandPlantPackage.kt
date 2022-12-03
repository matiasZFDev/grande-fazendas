package com.grandemc.fazendas.bukkit.view.land_plant

import com.grandemc.fazendas.bukkit.view.land.LandContext
import com.grandemc.fazendas.config.CropsConfig
import com.grandemc.fazendas.manager.*
import com.grandemc.post.external.lib.cache.config.chunk.base.ItemsChunk
import com.grandemc.post.external.lib.view.pack.impl.StatefulPackage

class LandPlantPackage(
    itemsConfig: ItemsChunk,
    cropsConfig: CropsConfig,
    farmManager: FarmManager,
    landPlantManager: LandPlantManager,
    landManager: LandManager,
    islandManager: IslandManager,
    statsManager: StatsManager
) : StatefulPackage<LandContext>(
    LandPlantMenuContainer::class,
    LandPlantProcessor(itemsConfig, cropsConfig, farmManager),
    LandPlantClickHandler(
        cropsConfig, landPlantManager, landManager, islandManager, statsManager
    )
)