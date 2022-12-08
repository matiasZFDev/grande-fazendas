package com.grandemc.fazendas.storage.market.entity

import com.grandemc.fazendas.global.getUUID
import com.grandemc.fazendas.global.setUUID
import com.grandemc.fazendas.storage.market.model.MarketSoldItem
import com.grandemc.post.external.lib.database.base.DynamicTable
import com.grandemc.post.external.lib.database.base.model.Table
import com.grandemc.post.external.lib.database.buildColumnTable
import java.sql.PreparedStatement
import java.sql.ResultSet

class MarketSoldItemTable(
    tableName: String
) : DynamicTable<MarketSoldItem, Int, MarketSoldItem>(tableName) {
    override fun tableModel(): Table {
        return buildColumnTable(tableName) {
            addColumn("id", "INT NOT NULL")
            addColumn("seller_id", "BINARY(16) NOT NULL")
            addColumn("item_id", "TINYINT NOT NULL")
            addColumn("amount", "SMALLINT NOT NULL")
            addColumn("gold_price", "DOUBLE NOT NULL")
        }
    }

    override fun consumeStatement(statement: PreparedStatement, value: MarketSoldItem): Boolean {
        statement.setInt(1, value.id())
        statement.setUUID(2, value.sellerId)
        statement.setByte(3, value.itemId)
        statement.setShort(4, value.amount)
        statement.setDouble(5, value.goldPrice)
        return true
    }

    override fun consumeResultSet(
        data: MutableMap<Int, MarketSoldItem>, resultSet: ResultSet
    ) {
        val soldId = resultSet.getInt("id")
        data[soldId] = MarketSoldItem(
            soldId,
            resultSet.getUUID("seller_id"),
            resultSet.getByte("item_id"),
            resultSet.getShort("amount"),
            resultSet.getDouble("gold_price")
        )
    }
}