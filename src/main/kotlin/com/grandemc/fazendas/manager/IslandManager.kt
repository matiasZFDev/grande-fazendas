package com.grandemc.fazendas.manager

import com.grandemc.fazendas.config.CropsConfig
import com.grandemc.fazendas.config.FarmsConfig
import com.grandemc.fazendas.config.IslandConfig
import com.grandemc.fazendas.global.respond
import com.grandemc.fazendas.manager.helper.IslandEntityManager
import com.grandemc.fazendas.manager.model.IslandSession
import com.grandemc.post.external.lib.global.bukkit.*
import org.bukkit.entity.Player
import java.util.UUID

class IslandManager(
    private val playerManager: PlayerManager,
    private val locationManager: IslandLocationManager,
    private val islandConfig: IslandConfig,
    private val farmItemManager: FarmItemManager,
    farmManager: FarmManager,
    farmsConfig: FarmsConfig,
    landManager: LandManager,
    cropsConfig: CropsConfig
) {
    private val islandPlayers: MutableMap<UUID, IslandSession> = mutableMapOf()
    private val entityManager: IslandEntityManager = IslandEntityManager(
        locationManager, islandConfig, farmsConfig, landManager, farmManager,
        cropsConfig
    )

    fun insideIsland(playerId: UUID): Boolean {
        return islandPlayers.contains(playerId)
    }

    fun hasIsland(playerId: UUID): Boolean {
        return playerManager.player(playerId).farm() != null
    }

    fun joinIsland(player: Player) {
        val islandSpawn = locationManager.islandSpawn(player.uniqueId)
        val farmTool = farmItemManager.createFarmTool(player.uniqueId)
        player.teleport(islandSpawn)
        if (player.inventory.getItemByReference("gfazendas.farm_tool") == null)
            player.giveItem(farmTool)
        val entities = entityManager.createEntities(player, islandSpawn)
        islandPlayers[player.uniqueId] = IslandSession(entities)
        player.respond("ilha.teleportado")
    }

    fun leaveIsland(player: Player, leaveCommand: Boolean = true) {
        islandPlayers[player.uniqueId]!!.entities().clearAll(player)
        islandPlayers.remove(player.uniqueId)
        player.inventory.removeItemByReference("gfazendas.farm_tool")
        player.respond("ilha.saida")
        if (leaveCommand)
            player.performCommand(islandConfig.get().leaveCommand)
    }

    private fun session(playerId: UUID): IslandSession {
        return islandPlayers[playerId] ?: throw Error(
            "Houve um error de posição do jogador ${playerId.offlinePlayer().name} na ilha."
        )
    }

    fun updateLandHologram(playerId: UUID, landId: Byte) {
        if (!insideIsland(playerId))
            return

        playerId.runIfOnline {
            session(uniqueId).entities().updateHologram(this, landId)
        }
    }

    fun islandWorld(): String {
        return islandConfig.get().worldName
    }
}