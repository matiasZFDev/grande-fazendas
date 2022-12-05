package com.grandemc.fazendas.bukkit.view.upgrades

import com.grandemc.fazendas.manager.UpgradesManager
import com.grandemc.post.external.lib.cache.config.chunk.base.ItemsChunk
import com.grandemc.post.external.lib.view.pack.impl.StatelessPackage

class UpgradesPackage(
    upgradesManager: UpgradesManager,
    itemsConfig: ItemsChunk
) : StatelessPackage(
    UpgradesMenuContainer::class,
    UpgradesProcessor(upgradesManager, itemsConfig),
    UpgradesClickHandler(upgradesManager)
)