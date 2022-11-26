package com.grandemc.fazendas.manager

import com.grandemc.fazendas.config.IslandConfig
import com.grandemc.fazendas.global.*
import com.grandemc.fazendas.manager.model.IslandEntities
import com.grandemc.fazendas.manager.model.IslandSession
import com.grandemc.fazendas.npc.LandsTrait
import com.sk89q.worldedit.BlockVector
import com.sk89q.worldedit.Vector
import net.citizensnpcs.api.CitizensAPI
import net.citizensnpcs.api.npc.MemoryNPCDataStore
import net.citizensnpcs.api.npc.NPC
import net.citizensnpcs.api.trait.Trait
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import java.util.UUID

class IslandManager(
    private val playerManager: PlayerManager,
    private val farmManager: FarmManager,
    private val islandConfig: IslandConfig
) {
    private val islandPlayers: MutableMap<UUID, IslandSession> = mutableMapOf()
    private val npcRegistry = CitizensAPI.createAnonymousNPCRegistry(MemoryNPCDataStore())

    fun hasIsland(playerId: UUID): Boolean {
        return playerManager.player(playerId).farm() != null
    }

    fun baseLocation(playerId: UUID? = null, yAxis: Boolean = true): Vector {
        val farmCount = playerId?.let { farmManager.farm(playerId).id() }
            ?: playerManager.allPlayers().lastOrNull {
                it.farm() != null
            }?.farm()?.id()?.inc() ?: 0
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

    private fun islandSpawn(playerId: UUID): Location {
        val world = Bukkit.getWorld(islandConfig.get().worldName)
        return islandOrigin(playerId).add(islandConfig.get().spawn).toLocation(world)
    }

    private fun createHologram(
        player: Player, configNPC: IslandConfig.IslandNPC, origin: Location
    ): Hologram {
        val hologram = player.prepareHologram(configNPC.hologramLines)
        hologram.send(player, origin.add(configNPC.position))
        return hologram
    }

    private fun createNPC(
        configNPC: IslandConfig.IslandNPC,
        origin: Location,
        trait: Trait? = null
    ): NPC {
        val npc = npcRegistry.createNPC(EntityType.PLAYER, configNPC.name)
        val npcLocation = origin.add(configNPC.position)
        npc.spawn(npcLocation)
        npc.data().setPersistent(NPC.NAMEPLATE_VISIBLE_METADATA, false)
        trait?.let { npc.addTrait(it) }
        return npc
    }

    fun joinIsland(player: Player) {
        val islandSpawn = islandSpawn(player.uniqueId)
        player.teleport(islandSpawn)
        val npcs = islandConfig.get().islandNpcs
        val entities = IslandEntities(listOf(
                createHologram(player, npcs.terrains, islandSpawn),
                createHologram(player, npcs.industry, islandSpawn),
                createHologram(player, npcs.quests, islandSpawn)
            ),
            listOf(
                createNPC(npcs.terrains, islandSpawn, LandsTrait()),
                createNPC(npcs.industry, islandSpawn),
                createNPC(npcs.quests, islandSpawn)
            )
        )
        islandPlayers[player.uniqueId] = IslandSession(entities)
    }

    fun insideIsland(playerId: UUID): Boolean {
        return islandPlayers.contains(playerId)
    }

    fun leaveIsland(player: Player) {
        islandPlayers[player.uniqueId]!!.entities().clearAll(player)
        islandPlayers.remove(player.uniqueId)
        player.performCommand(islandConfig.get().leaveCommand)
    }
}