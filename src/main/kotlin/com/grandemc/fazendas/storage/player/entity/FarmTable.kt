package com.grandemc.fazendas.storage.player.entity

import com.grandemc.fazendas.storage.player.model.FarmPlayer
import com.grandemc.post.external.lib.database.base.FixedTable
import com.grandemc.post.external.lib.database.base.model.Table
import com.grandemc.post.external.lib.database.buildColumnTable
import com.grandemc.post.external.lib.global.getBytes
import com.grandemc.post.external.lib.global.toUUID
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.util.UUID

class FarmTable(tableName: String) : FixedTable<FarmPlayer, UUID, FarmTable.Data>(tableName) {
    inner class Data(
        val id: Int,
        val level: Short,
        val xp: Int
    )

    override fun tableModel(): Table {
        return buildColumnTable(tableName) {
            addColumn("owner_id", "BINARY(16) NOT NULL")
            addColumn("id", "INT NOT NULL", true)
            addColumn("level", "SMALLINT NOT NULL", true)
            addColumn("xp", "INT NOT NULL", true)
            primaryKey("owner_id")
        }
    }

    override fun consumeStatement(statement: PreparedStatement, value: FarmPlayer): Boolean {
        val farm = value.farm() ?: return false
        statement.setBytes(1, value.id().getBytes())
        statement.setInt(2, farm.id())
        statement.setShort(3, farm.level())
        statement.setInt(4, farm.xp())
        statement.setInt(5, farm.id())
        statement.setShort(6, farm.level())
        statement.setInt(7, farm.xp())
        return true
    }

    override fun consumeResultSet(data: MutableMap<UUID, Data>, resultSet: ResultSet) {
        val ownerId = resultSet.getBytes("owner_id").toUUID()
        data[ownerId] = Data(
            resultSet.getInt("id"),
            resultSet.getShort("level"),
            resultSet.getInt("xp")
        )
    }
}