package com.grandemc.fazendas.storage.player.entity

import com.grandemc.fazendas.storage.player.model.CustomEnchant
import com.grandemc.fazendas.storage.player.model.FarmHoe
import com.grandemc.fazendas.storage.player.model.HoeEnchant
import com.grandemc.fazendas.storage.player.model.FarmPlayer
import com.grandemc.post.external.lib.database.base.FixedTable
import com.grandemc.post.external.lib.database.base.model.Table
import com.grandemc.post.external.lib.database.buildColumnTable
import com.grandemc.post.external.lib.global.getBytes
import com.grandemc.post.external.lib.global.toUUID
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.util.UUID

class HoeTable(tableName: String) : FixedTable<FarmPlayer, UUID, FarmHoe>(tableName) {
    override fun tableModel(): Table {
        return buildColumnTable(tableName) {
            addColumn("owner_id", "BINARY(16) NOT NULL")
            addColumn("collect_count", "DOUBLE NOT NULL", true)
            addColumn("experient", "SMALLINT NOT NULL", true)
            addColumn("replant", "SMALLINT NOT NULL", true)
            addColumn("radar", "SMALLINT NOT NULL", true)
            primaryKey("owner_id")
        }
    }

    override fun consumeStatement(statement: PreparedStatement, value: FarmPlayer): Boolean {
        statement.setBytes(1, value.id().getBytes())
        statement.setDouble(2, value.hoe().collectCount())
        statement.setShort(3, value.hoe().enchantLevel(CustomEnchant.EXPERIENT))
        statement.setShort(4, value.hoe().enchantLevel(CustomEnchant.REPLANT))
        statement.setShort(5, value.hoe().enchantLevel(CustomEnchant.RADAR))
        statement.setDouble(6, value.hoe().collectCount())
        statement.setShort(7, value.hoe().enchantLevel(CustomEnchant.EXPERIENT))
        statement.setShort(8, value.hoe().enchantLevel(CustomEnchant.REPLANT))
        statement.setShort(9, value.hoe().enchantLevel(CustomEnchant.RADAR))
        return true
    }

    override fun consumeResultSet(data: MutableMap<UUID, FarmHoe>, resultSet: ResultSet) {
        data[resultSet.getBytes("owner_id").toUUID()] = FarmHoe(
            listOf(
                HoeEnchant(CustomEnchant.EXPERIENT, resultSet.getShort("experient")),
                HoeEnchant(CustomEnchant.REPLANT, resultSet.getShort("replant")),
                HoeEnchant(CustomEnchant.RADAR, resultSet.getShort("radar")),
            ),
            resultSet.getDouble("collect_count")
        )
    }
}