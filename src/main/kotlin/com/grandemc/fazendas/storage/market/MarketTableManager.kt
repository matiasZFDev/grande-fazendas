package com.grandemc.fazendas.storage.market

import com.grandemc.fazendas.storage.market.entity.MarketItemTable
import com.grandemc.fazendas.storage.market.model.MarketItem
import com.grandemc.post.external.lib.database.base.TableManager
import java.sql.Connection

class MarketTableManager : TableManager<MarketItem> {
    private val marketItemTable = MarketItemTable("grandefazendas_market")

    override fun insertAll(connection: Connection, values: Collection<MarketItem>) {
        marketItemTable.insertAll(connection, values)
    }

    override fun fetchAll(connection: Connection): Collection<MarketItem> {
        return marketItemTable.fetchAll(connection).values.flatten()
    }
}