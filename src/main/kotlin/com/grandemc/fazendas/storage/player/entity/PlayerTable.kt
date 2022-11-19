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

class PlayerTable(tableName: String) : FixedTable<FarmPlayer, UUID, PlayerTable.Data>(tableName) {
    inner class Data(
        val playerId: UUID,
        val gold: Double
    )

    override fun tableModel(): Table {
        return buildColumnTable(tableName) {
            addColumn("player_id", "BINARY(16) NOT NULL")
            addColumn("gold", "DOUBLE NOT NULL", true)
            primaryKey("player_id")
        }
    }

    override fun consumeStatement(statement: PreparedStatement, value: FarmPlayer): Boolean {
        statement.setBytes(1, value.id().getBytes())
        statement.setDouble(2, value.gold())
        statement.setDouble(3, value.gold())
        return true
    }

    override fun consumeResultSet(data: MutableMap<UUID, Data>, resultSet: ResultSet) {
        val playerId = resultSet.getBytes("player_id").toUUID()
        data[playerId] = Data(playerId, resultSet.getDouble("gold"))
    }
}