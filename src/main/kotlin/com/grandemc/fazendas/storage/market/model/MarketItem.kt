package com.grandemc.fazendas.storage.market.model

import com.grandemc.post.external.lib.database.base.Identifiable
import java.util.UUID

class MarketItem(
    private val id: Int,
    val sellerId: UUID,
    val itemId: Byte,
    val amount: Short,
    val goldPrice: Double,
    private var expiryTime: Int
) : Identifiable<Int> {
    override fun id(): Int {
        return id
    }

    fun expiryTime(): Int = expiryTime
    fun advance() {
        expiryTime--
    }
}