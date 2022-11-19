package com.grandemc.fazendas.storage.player.entity

import com.grandemc.fazendas.global.getCuboid
import com.grandemc.fazendas.global.getLocation
import com.grandemc.fazendas.storage.player.model.FarmPlayer
import com.grandemc.fazendas.util.Cuboid
import com.grandemc.fazendas.util.FixedCuboid
import com.grandemc.post.external.lib.database.base.FixedTable
import com.grandemc.post.external.lib.database.base.model.Table
import com.grandemc.post.external.lib.database.buildColumnTable
import com.grandemc.post.external.lib.global.getBytes
import com.grandemc.post.external.lib.global.toUUID
import org.bukkit.Bukkit
import org.bukkit.CropState
import org.bukkit.craftbukkit.v1_8_R3.block.CraftBlock
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftAgeable
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.util.UUID

class FarmTable(tableName: String) : FixedTable<FarmPlayer, UUID, FarmTable.Data>(tableName) {
    inner class Data(
        val ownerId: UUID,
        val level: Byte,
        val location: Cuboid
    )

    override fun tableModel(): Table {
        return buildColumnTable(tableName) {
            addColumn("owner_id", "BINARY(16) NOT NULL")
            addColumn("level", "TINYINT NOT NULL", true)
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
        statement.setBytes(1, value.id().getBytes())
        statement.setByte(2, value.farm().level())
        statement.setBytes(3, value.farm().location().min().world.uid.getBytes())
        statement.setInt(4, value.farm().location().min().blockX)
        statement.setInt(5, value.farm().location().min().blockY)
        statement.setInt(5, value.farm().location().min().blockZ)
        statement.setInt(6, value.farm().location().max().blockX)
        statement.setInt(7, value.farm().location().max().blockY)
        statement.setInt(8, value.farm().location().max().blockZ)
        statement.setByte(9, value.farm().level())
        return true
    }

    override fun consumeResultSet(data: MutableMap<UUID, Data>, resultSet: ResultSet) {
        val ownerId = resultSet.getBytes("owner_id").toUUID()
        data[ownerId] = Data(
            ownerId,
            resultSet.getByte("level"),
            resultSet.getCuboid()
        )
    }
}