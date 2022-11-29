package com.grandemc.fazendas.bukkit.view.market.purchase

import com.grandemc.fazendas.manager.GoldBank
import com.grandemc.fazendas.manager.MarketManager
import com.grandemc.fazendas.manager.StorageManager
import com.grandemc.post.external.lib.view.pack.impl.StatefulPackage

class MarketPurchasePackage(
    marketManager: MarketManager,
    storageManager: StorageManager,
    goldBank: GoldBank
) : StatefulPackage<MarketPurchaseContext>(
    MarketPurchaseMenuContainer::class,
    MarketPurchaseProcessor(storageManager),
    MarketPurchaseClickHandler(marketManager, storageManager, goldBank)
)