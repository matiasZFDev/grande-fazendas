package com.grandemc.fazendas.manager

import com.grandemc.fazendas.config.IslandConfig
import com.grandemc.fazendas.global.add
import com.grandemc.fazendas.global.toLocation
import com.sk89q.worldedit.BlockVector
import com.sk89q.worldedit.Vector
import org.bukkit.Bukkit
import org.bukkit.Location
import java.util.UUID

class IslandLocationManager(
    private val farmManager: FarmManager,
    private val playerManager: PlayerManager,
    private val islandConfig: IslandConfig,
) {
    fun baseLocation(playerId: UUID? = null, yAxis: Boolean = true): Vector {
        val farmCount = playerId?.let { farmManager.farm(playerId).id() }
            ?: playerManager.allPlayers()
                .filter { it.farm() != null }
                .let { players ->
                    if (players.isEmpty())
                        0
                    else
                        players.maxByOrNull { it.farm()!!.id() }!!.farm()!!.id().inc()
                }
        val locationX = farmCount * islandConfig.get().islandDistance
        return BlockVector(
            locationX,
            if (yAxis) IslandGenerationManager.ISLAND_Y else 0,
            0
        )
    }

    fun islandOrigin(playerId: UUID, yAxis: Boolean = true): Vector {
        val farm = farmManager.farm(playerId)
        return BlockVector(
             farm.id() * (islandConfig.get().islandDistance),
            if (yAxis) IslandGenerationManager.ISLAND_Y else 0,
            0
        )
    }

    fun islandSpawn(playerId: UUID): Location {
        val world = Bukkit.getWorld(islandConfig.get().worldName)
        return islandOrigin(playerId).toLocation(world).add(
            islandConfig.get().spawn
        )
    }
}