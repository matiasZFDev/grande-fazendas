package com.grandemc.fazendas.storage.player.entity

import com.grandemc.fazendas.global.getUUID
import com.grandemc.fazendas.global.setUUID
import com.grandemc.fazendas.storage.player.model.FarmBooster
import com.grandemc.fazendas.storage.player.model.FarmPlayer
import com.grandemc.post.external.lib.database.base.DynamicTable
import com.grandemc.post.external.lib.database.base.model.Table
import com.grandemc.post.external.lib.database.buildColumnTable
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.util.UUID

class BoosterTable(
    tableName: String
) : DynamicTable<FarmPlayer, UUID, FarmBooster>(tableName) {
    override fun tableModel(): Table {
        return buildColumnTable(tableName) {
            addColumn("player_id", "BINARY(16) NOT NULL")
            addColumn("boost", "FLOAT NOT NULL")
            addColumn("time_left", "SMALLINT NOT NULL")
        }
    }

    override fun consumeStatement(statement: PreparedStatement, value: FarmPlayer): Boolean {
        if (value.booster() == null)
            return false
        statement.setUUID(1, value.id())
        statement.setFloat(2, value.booster()!!.boost())
        statement.setShort(3, value.booster()!!.timeLeft())
        return true
    }

    override fun consumeResultSet(data: MutableMap<UUID, FarmBooster>, resultSet: ResultSet) {
        data[resultSet.getUUID("player_id")] = FarmBooster(
            resultSet.getFloat("boost"),
            resultSet.getShort("time_left"),
        )
    }
}