package com.grandemc.fazendas.storage.market

import com.grandemc.fazendas.storage.market.entity.MarketSoldItemTable
import com.grandemc.fazendas.storage.market.model.MarketSoldItem
import com.grandemc.post.external.lib.database.base.TableManager
import java.sql.Connection

class MarketSoldTableManager : TableManager<MarketSoldItem> {
    private val marketSoldItemTable = MarketSoldItemTable("grandefazendas_market_sold")

    override fun insertAll(connection: Connection, values: Collection<MarketSoldItem>) {
        marketSoldItemTable.insertAll(connection, values)
    }

    override fun fetchAll(connection: Connection): Collection<MarketSoldItem> {
        marketSoldItemTable.createTable(connection)
        return marketSoldItemTable.fetchAll(connection).values
    }
}