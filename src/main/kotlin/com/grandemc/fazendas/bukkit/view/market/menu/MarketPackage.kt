package com.grandemc.fazendas.bukkit.view.market.menu

import com.grandemc.fazendas.bukkit.view.PageContext
import com.grandemc.fazendas.config.model.MarketConfig
import com.grandemc.fazendas.manager.MarketManager
import com.grandemc.fazendas.manager.StorageManager
import com.grandemc.post.external.lib.cache.config.chunk.base.ItemsChunk
import com.grandemc.post.external.lib.view.pack.impl.StatefulPackage

class MarketPackage(
    marketManager: MarketManager,
    marketConfig: MarketConfig,
    storageManager: StorageManager,
    itemsConfig: ItemsChunk
) : StatefulPackage<PageContext>(
    MarketMenuContainer::class,
    MarketProcessor(
        marketManager, marketConfig, storageManager, itemsConfig
    ),
    MarketClickHandler()
)