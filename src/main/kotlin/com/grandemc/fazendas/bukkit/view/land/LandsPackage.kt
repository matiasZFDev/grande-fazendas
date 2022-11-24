package com.grandemc.fazendas.bukkit.view.land

import com.grandemc.fazendas.config.FarmsConfig
import com.grandemc.fazendas.config.LandsConfig
import com.grandemc.fazendas.manager.*
import com.grandemc.post.external.lib.cache.config.chunk.base.ItemsChunk
import com.grandemc.post.external.lib.view.pack.impl.StatelessPackage

class LandsPackage(
    landsConfig: LandsConfig,
    farmsConfig: FarmsConfig,
    landManager: LandManager,
    goldBank: GoldBank,
    storageManager: StorageManager,
    itemsConfig: ItemsChunk,
    landUpgradeManager: LandUpgradeManager
) : StatelessPackage(
    LandsMenuContainer::class,
    LandsProcessor(
        landsConfig, farmsConfig, landManager, goldBank, storageManager, itemsConfig
    ),
    LandsClickHandler(
        farmsConfig, landUpgradeManager, goldBank, storageManager, landManager
    )
)