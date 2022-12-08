package com.grandemc.fazendas.manager.controller

import com.grandemc.fazendas.global.firstIndex
import com.grandemc.fazendas.storage.market.model.MarketItem
import com.grandemc.fazendas.storage.market.model.MarketSoldItem
import com.grandemc.post.external.lib.database.base.DatabaseService
import java.util.UUID

class MarketSoldItemController(
    private val itemService: DatabaseService<Int, MarketSoldItem>
) {
    fun getPlayerItems(playerId: UUID): Collection<MarketSoldItem> {
        return itemService.getAll().filter {
            it.sellerId == playerId
        }
    }

    private fun getNextId(): Int {
        if (itemService.getAll().isEmpty())
            return 0
        val marketSize = itemService.getAll().size

        if (itemService.get(marketSize) == null)
            return marketSize

        return itemService.getAll().firstIndex { index, it -> it.id() != index }
    }

    fun saveItem(product: MarketItem) {
        val soldItem = MarketSoldItem(
            getNextId(),
            product.sellerId,
            product.itemId,
            product.amount,
            product.goldPrice
        )
        itemService.insert(soldItem)
    }

    fun collectItem(id: Int): MarketSoldItem {
        val item = itemService.get(id) ?: throw Error("O item vendido #$id não existe.")
        itemService.delete(id)
        return item
    }
}