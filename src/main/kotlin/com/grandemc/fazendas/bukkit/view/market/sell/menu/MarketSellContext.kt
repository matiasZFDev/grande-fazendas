package com.grandemc.fazendas.bukkit.view.market.sell.menu

import com.grandemc.post.external.lib.view.base.ContextData

class MarketSellContext(
    val materialId: Byte?,
    val amount: Short,
    val price: Double
) : ContextData