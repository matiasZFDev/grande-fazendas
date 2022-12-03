package com.grandemc.fazendas.bukkit.view.fertilizing

import com.grandemc.fazendas.config.CropsConfig
import com.grandemc.fazendas.config.FarmsConfig
import com.grandemc.fazendas.config.FertilizingConfig
import com.grandemc.fazendas.manager.IslandManager
import com.grandemc.fazendas.manager.LandManager
import com.grandemc.fazendas.manager.StatsManager
import com.grandemc.post.external.lib.cache.config.chunk.base.ItemsChunk
import com.grandemc.post.external.lib.view.pack.impl.StatefulPackage

class FertilizingPackage(
    fertilizingConfig: FertilizingConfig,
    farmsConfig: FarmsConfig,
    landManager: LandManager,
    itemsConfig: ItemsChunk,
    cropsConfig: CropsConfig,
    statsManager: StatsManager,
    islandManager: IslandManager
) : StatefulPackage<FertilizingContext>(
    FertilizingMenuContainer::class,
    FertilizingProcessor(
        fertilizingConfig, farmsConfig, landManager, itemsConfig, cropsConfig
    ),
    FertilizingClickHandler(
        fertilizingConfig, landManager, cropsConfig, statsManager, islandManager
    )
)