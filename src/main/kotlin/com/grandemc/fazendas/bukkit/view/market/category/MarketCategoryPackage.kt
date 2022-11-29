package com.grandemc.fazendas.bukkit.view.market.category

import com.grandemc.fazendas.config.model.MarketConfig
import com.grandemc.fazendas.manager.MarketManager
import com.grandemc.fazendas.manager.StorageManager
import com.grandemc.post.external.lib.cache.config.chunk.base.ItemsChunk
import com.grandemc.post.external.lib.view.pack.impl.StatefulPackage

class MarketCategoryPackage(
    marketManager: MarketManager,
    marketConfig: MarketConfig,
    itemsConfig: ItemsChunk,
    storageManager: StorageManager
) : StatefulPackage<MarketCategoryContext>(
    MarketCategoryMenuContainer::class,
    MarketCategoryProcessor(
        marketManager, marketConfig, itemsConfig, storageManager
    ),
    MarketCategoryClickHandler(marketManager)
)