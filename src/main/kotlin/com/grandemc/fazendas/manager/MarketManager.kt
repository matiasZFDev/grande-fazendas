package com.grandemc.fazendas.manager

import com.grandemc.fazendas.config.model.MarketConfig
import com.grandemc.fazendas.global.firstIndex
import com.grandemc.fazendas.manager.model.MarketFilter
import com.grandemc.fazendas.storage.market.model.MarketItem
import com.grandemc.post.external.lib.database.base.DatabaseService
import java.util.UUID

class MarketManager(
    private val marketService: DatabaseService<Int, MarketItem>,
    private val marketConfig: MarketConfig
) {
    fun getProducts(): Collection<MarketItem> {
        return marketService.getAll()
    }

    fun getProductsById(id: Byte, filter: MarketFilter): Collection<MarketItem> {
        return marketService.getAll().filter { it.itemId == id }.let(filter::apply)
    }

    private fun getNextId(): Int {
        if (marketService.getAll().isEmpty())
            return 0
        val marketSize = marketService.getAll().size

        if (marketService.get(marketSize) == null)
            return marketSize

        return marketService.getAll().firstIndex { index, it -> it.id() != index }
    }

    fun postItem(playerId: UUID, itemId: Byte, amount: Short, price: Double) {
        val marketItem = MarketItem(
            getNextId(),
            playerId,
            itemId,
            amount,
            price,
            marketConfig.get().expiryTime * 60
        )
        marketService.insert(marketItem)
    }

    fun getPlayerProducts(playerId: UUID): Collection<MarketItem> {
        return marketService.getAll().filter { it.sellerId == playerId }
    }

    fun removeProduct(productId: Int) {
        marketService.delete(productId)
    }

    fun getProduct(id: Int): MarketItem? {
        return marketService.get(id)
    }

    fun tax(): Double {
        return marketConfig.get().tax
    }
}