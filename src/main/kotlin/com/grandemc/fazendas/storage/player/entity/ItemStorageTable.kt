package com.grandemc.fazendas.storage.player.entity

import com.grandemc.fazendas.storage.player.model.ItemStorage
import com.grandemc.fazendas.storage.player.model.StorageItem
import com.grandemc.post.external.lib.database.base.DynamicTable
import com.grandemc.post.external.lib.database.base.model.Table
import com.grandemc.post.external.lib.database.buildColumnTable
import com.grandemc.post.external.lib.global.getBytes
import com.grandemc.post.external.lib.global.toUUID
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.util.UUID

class ItemStorageTable(
    tableName: String
) : DynamicTable<ItemStorageTable.StorageInput, UUID, ItemStorage>(tableName) {
    class StorageInput(
        val ownerId: UUID,
        val item: StorageItem
    )

    override fun tableModel(): Table {
        return buildColumnTable(tableName) {
            addColumn("owner_id", "BINARY(16) NOT NULL")
            addColumn("item_id", "TINYINT NOT NULL")
            addColumn("amount", "SMALLINT NOT NULL")
        }
    }

    override fun consumeStatement(statement: PreparedStatement, value: StorageInput): Boolean {
        statement.setBytes(1, value.ownerId.getBytes())
        statement.setByte(2, value.item.id)
        statement.setShort(3, value.item.amount)
        return true
    }

    override fun consumeResultSet(data: MutableMap<UUID, ItemStorage>, resultSet: ResultSet) {
        data
            .computeIfAbsent(resultSet.getBytes("owner_id").toUUID()) { ItemStorage() }
            .addItem(resultSet.getByte("item_id"), resultSet.getShort("amount"))
    }
}