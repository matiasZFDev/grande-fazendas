package com.grandemc.fazendas.global

import com.grandemc.fazendas.util.cuboid.Cuboid
import com.grandemc.fazendas.util.cuboid.FixedCuboid
import com.grandemc.post.external.lib.global.getBytes
import com.grandemc.post.external.lib.global.toUUID
import org.bukkit.Bukkit
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.util.UUID

fun PreparedStatement.setUUID(pos: Int, uuid: UUID) {
    setBytes(pos, uuid.getBytes())
}

fun ResultSet.getUUID(column: String): UUID {
    return getBytes(column).toUUID()
}

fun ResultSet.getCuboid(): Cuboid {
    val world = Bukkit.getWorld(getUUID("location_world_id"))
    return FixedCuboid(
        world.getLocation(
            getInt("location_min_x"),
            getInt("location_min_y"),
            getInt("location_min_z")
        ),
        world.getLocation(
            getInt("location_max_x"),
            getInt("location_max_y"),
            getInt("location_max_z")
        )
    )
}