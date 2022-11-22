package com.grandemc.fazendas.manager

import com.grandemc.fazendas.config.IslandConfig
import com.grandemc.fazendas.global.toLocation
import com.sk89q.worldedit.BlockVector
import com.sk89q.worldedit.Vector
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player
import java.util.UUID

class IslandManager(
    private val playerManager: PlayerManager,
    private val islandConfig: IslandConfig
) {
    private val islandPlayers: MutableSet<UUID> = mutableSetOf()

    fun hasIsland(playerId: UUID): Boolean {
        return playerManager.player(playerId).farm() != null
    }

    fun islandOrigin(playerId: UUID): Vector {
        val farm = playerManager.player(playerId).farm()!!
        return BlockVector(
             farm.id() * (islandConfig.get().islandDistance),
            IslandGenerationManager.ISLAND_Y,
            0
        )
    }

    fun islandSpawn(playerId: UUID): Location {
        val world = Bukkit.getWorld(islandConfig.get().worldName)
        return islandOrigin(playerId).add(islandConfig.get().spawn).toLocation(world)
    }

    fun joinIsland(player: Player) {
        islandPlayers.add(player.uniqueId)
        player.teleport(islandSpawn(player.uniqueId))
    }

    fun insideIsland(playerId: UUID): Boolean {
        return islandPlayers.contains(playerId)
    }

    fun leaveIsland(player: Player) {
        islandPlayers.remove(player.uniqueId)
        player.performCommand(islandConfig.get().leaveCommand)
    }
}