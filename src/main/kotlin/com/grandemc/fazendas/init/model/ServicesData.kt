package com.grandemc.fazendas.init.model

import com.grandemc.fazendas.storage.market.model.MarketItem
import com.grandemc.fazendas.storage.market.model.MarketSoldItem
import com.grandemc.fazendas.storage.player.model.FarmPlayer
import com.grandemc.post.external.lib.database.base.DatabaseService
import java.util.UUID

data class ServicesData(
    val playerService: DatabaseService<UUID, FarmPlayer>,
    val marketService: DatabaseService<Int, MarketItem>,
    val marketSoldService: DatabaseService<Int, MarketSoldItem>
)