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
            addColumn("level", "TINYINT NOT NULL")
            addColumn("xp", "INT NOT NULL")
            addColumn("reset_countdown", "INT NOT NULL")
            addColumn("can_boost", "BIT NOT NULL")
            addColumn("location_world_id", "BINARY(16) NOT NULL")
            addColumn("location_min_x", "INT NOT NULL")
            addColumn("location_min_y", "INT NOT NULL")
            addColumn("location_min_z", "INT NOT NULL")
            addColumn("location_max_x", "INT NOT NULL")
            addColumn("location_max_y", "INT NOT NULL")
            addColumn("location_max_z", "INT NOT NULL")
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
        statement.setUUID(8, value.land.location().min().world.uid)
        statement.setInt(9, value.land.location().min().blockX)
        statement.setInt(10, value.land.location().min().blockY)
        statement.setInt(11, value.land.location().min().blockZ)
        statement.setInt(12, value.land.location().max().blockX)
        statement.setInt(13, value.land.location().max().blockY)
        statement.setInt(14, value.land.location().max().blockZ)
        return true
    }

    override fun consumeResultSet(data: MutableMap<UUID, MutableList<FarmLand>>, resultSet: ResultSet) {
        data
            .computeIfAbsent(resultSet.getUUID("owner_id")) { LinkedList() }
            .add(FarmLand(
                resultSet.getCuboid(),
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