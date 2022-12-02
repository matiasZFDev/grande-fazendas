package com.grandemc.fazendas.storage.player.entity

import com.grandemc.fazendas.global.getCuboid
import com.grandemc.fazendas.storage.player.model.FarmPlayer
import com.grandemc.fazendas.util.cuboid.Cuboid
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
        val ownerId: UUID,
        val id: Int,
        val level: Byte,
        val xp: Int,
        val location: Cuboid
    )

    override fun tableModel(): Table {
        return buildColumnTable(tableName) {
            addColumn("owner_id", "BINARY(16) NOT NULL")
            addColumn("id", "INT NOT NULL", true)
            addColumn("level", "TINYINT NOT NULL", true)
            addColumn("xp", "INT NOT NULL", true)
            addColumn("location_world_id", "BINARY(16) NOT NULL")
            addColumn("location_min_x", "INT NOT NULL")
            addColumn("location_min_y", "INT NOT NULL")
            addColumn("location_min_z", "INT NOT NULL")
            addColumn("location_max_x", "INT NOT NULL")
            addColumn("location_max_y", "INT NOT NULL")
            addColumn("location_max_z", "INT NOT NULL")
            primaryKey("owner_id")
        }
    }

    override fun consumeStatement(statement: PreparedStatement, value: FarmPlayer): Boolean {
        val farm = value.farm() ?: return false
        statement.setBytes(1, value.id().getBytes())
        statement.setInt(2, farm.id())
        statement.setByte(3, farm.level())
        statement.setInt(4, farm.xp())
        statement.setBytes(5, farm.location().min().world.uid.getBytes())
        statement.setInt(6, farm.location().min().blockX)
        statement.setInt(7, farm.location().min().blockY)
        statement.setInt(8, farm.location().min().blockZ)
        statement.setInt(9, farm.location().max().blockX)
        statement.setInt(10, farm.location().max().blockY)
        statement.setInt(11, farm.location().max().blockZ)
        statement.setInt(12, farm.id())
        statement.setByte(13, farm.level())
        statement.setInt(14, farm.xp())
        return true
    }

    override fun consumeResultSet(data: MutableMap<UUID, Data>, resultSet: ResultSet) {
        val ownerId = resultSet.getBytes("owner_id").toUUID()
        data[ownerId] = Data(
            ownerId,
            resultSet.getInt("id"),
            resultSet.getByte("level"),
            resultSet.getInt("xp"),
            resultSet.getCuboid()
        )
    }
}