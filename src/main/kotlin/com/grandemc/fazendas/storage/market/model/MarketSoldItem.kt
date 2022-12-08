package com.grandemc.fazendas.storage.market.model

import com.grandemc.post.external.lib.database.base.Identifiable
import java.util.UUID

class MarketSoldItem(
    private val id: Int,
    val sellerId: UUID,
    val itemId: Byte,
    val amount: Short,
    val goldPrice: Double
) : Identifiable<Int> {
    override fun id(): Int {
        return id
    }
}