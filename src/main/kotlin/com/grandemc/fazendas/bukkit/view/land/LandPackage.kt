package com.grandemc.fazendas.bukkit.view.land

import com.grandemc.fazendas.config.*
import com.grandemc.fazendas.manager.GoldBank
import com.grandemc.fazendas.manager.IslandManager
import com.grandemc.fazendas.manager.LandManager
import com.grandemc.fazendas.manager.LandPlantManager
import com.grandemc.post.external.lib.view.pack.impl.StatefulPackage

class LandPackage(
    landManager: LandManager,
    goldBank: GoldBank,
    farmsConfig: FarmsConfig,
    fertilizingConfig: FertilizingConfig,
    materialsConfig: MaterialsConfig,
    islandConfig: IslandConfig,
    islandManager: IslandManager,
    landPlantManager: LandPlantManager,
    cropsConfig: CropsConfig
) : StatefulPackage<LandContext>(
    LandMenuContainer::class,
    LandProcessor(
        landManager, goldBank, farmsConfig, fertilizingConfig, materialsConfig,
        islandConfig
    ),
    LandClickHandler(
        landManager, islandManager, goldBank, landPlantManager, cropsConfig
    )
)