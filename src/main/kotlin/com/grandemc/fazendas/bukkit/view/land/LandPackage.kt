package com.grandemc.fazendas.bukkit.view.land

import com.grandemc.fazendas.config.FarmsConfig
import com.grandemc.fazendas.config.FertilizingConfig
import com.grandemc.fazendas.config.IslandConfig
import com.grandemc.fazendas.config.MaterialsConfig
import com.grandemc.fazendas.manager.GoldBank
import com.grandemc.fazendas.manager.LandManager
import com.grandemc.post.external.lib.view.pack.impl.StatefulPackage

class LandPackage(
    landManager: LandManager,
    goldBank: GoldBank,
    farmsConfig: FarmsConfig,
    fertilizingConfig: FertilizingConfig,
    materialsConfig: MaterialsConfig,
    islandConfig: IslandConfig
) : StatefulPackage<LandContext>(
    LandMenuContainer::class,
    LandProcessor(
        landManager, goldBank, farmsConfig, fertilizingConfig, materialsConfig,
        islandConfig
    ),
    LandClickHandler()
)