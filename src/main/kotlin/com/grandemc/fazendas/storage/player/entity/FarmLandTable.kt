package com.grandemc.fazendas.storage.player.entity

import com.grandemc.fazendas.global.getCuboid
import com.grandemc.fazendas.global.getUUID
import com.grandemc.fazendas.global.setUUID
import com.grandemc.fazendas.storage.player.model.FarmLand
import com.grandemc.post.external.lib.database.base.DynamicTable
import com.grandemc.post.external.lib.database.base.model.Table
import com.grandemc.post.external.lib.database.buildColumnTable
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.util.LinkedList
import java.util.UUID

class FarmLandTable(
    tableName: String
) : DynamicTable<FarmLandTable.LandInput, UUID, MutableList<FarmLand>>(tableName) {
    class LandInput(
        val ownerId: UUID,
        val land: FarmLand
    )

    override fun tableModel(): Table {
        return buildColumnTable(tableName) {
            addColumn("owner_id", "BINARY(16) NOT NULL")
            addColumn("type_id", "TINYINT NOT NULL")
            addColumn("crop_id", "TINYINT")
            addColumn("level", "TINYINT NOT NULL")
            addColumn("xp", "INT NOT NULL")
            addColumn("reset_countdown", "INT NOT NULL")
            addColumn("can_boost", "BIT NOT NULL")
        }
    }

    override fun consumeStatement(statement: PreparedStatement, value: LandInput): Boolean {
        statement.setUUID(1, value.ownerId)
        statement.setByte(2, value.land.typeId())
        statement.setByte(3, value.land.cropId() ?: -1)
        statement.setByte(4, value.land.level())
        statement.setInt(5, value.land.xp())
        statement.setInt(6, value.land.resetCountdown())
        statement.setBoolean(7, value.land.canBoost())
        return true
    }

    override fun consumeResultSet(data: MutableMap<UUID, MutableList<FarmLand>>, resultSet: ResultSet) {
        data
            .computeIfAbsent(resultSet.getUUID("owner_id")) { LinkedList() }
            .add(FarmLand(
                resultSet.getByte("type_id"),
                if (resultSet.getByte("crop_id") == (-1).toByte())
                    null
                else
                    resultSet.getByte("crop_id"),
                resultSet.getByte("level"),
                resultSet.getInt("xp"),
                resultSet.getInt("reset_countdown"),
                resultSet.getBoolean("can_boost")
            ))
    }
}