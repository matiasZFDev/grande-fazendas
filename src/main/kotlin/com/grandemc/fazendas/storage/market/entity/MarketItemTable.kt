package com.grandemc.fazendas.storage.market.entity

import com.grandemc.fazendas.global.getUUID
import com.grandemc.fazendas.global.setUUID
import com.grandemc.fazendas.storage.market.model.MarketItem
import com.grandemc.post.external.lib.database.base.DynamicTable
import com.grandemc.post.external.lib.database.base.model.Table
import com.grandemc.post.external.lib.database.buildColumnTable
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.util.LinkedList
import java.util.UUID

class MarketItemTable(
    tableName: String
) : DynamicTable<MarketItem, UUID, MutableList<MarketItem>>(tableName) {
    override fun tableModel(): Table {
        return buildColumnTable(tableName) {
            addColumn("id", "INT NOT NULL AUTO_INCREMENT")
            addColumn("seller_id", "BINARY(16) NOT NULL")
            addColumn("item_id", "TINYINT NOT NULL")
            addColumn("amount", "SMALLINT NOT NULL")
            addColumn("gold_price", "DOUBLE NOT NULL")
            addColumn("expiry_time", "INT NOT NULL")
        }
    }

    override fun consumeStatement(statement: PreparedStatement, value: MarketItem): Boolean {
        statement.setUUID(1, value.sellerId)
        statement.setByte(2, value.itemId)
        statement.setShort(3, value.amount)
        statement.setDouble(4, value.goldPrice)
        statement.setInt(5, value.expiryTime)
        return true
    }

    override fun consumeResultSet(
        data: MutableMap<UUID, MutableList<MarketItem>>, resultSet: ResultSet
    ) {
        val sellerId = resultSet.getUUID("seller_id")
        data.computeIfAbsent(sellerId) { LinkedList() }.add(MarketItem(
            resultSet.getInt("id"),
            sellerId,
            resultSet.getByte("item_id"),
            resultSet.getShort("amount"),
            resultSet.getDouble("gold_price"),
            resultSet.getInt("expiry_time")
        ))
    }
}