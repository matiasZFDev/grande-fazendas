package com.grandemc.fazendas.manager.helper

import com.grandemc.fazendas.config.CropsConfig
import com.grandemc.fazendas.config.FarmsConfig
import com.grandemc.fazendas.config.IslandConfig
import com.grandemc.fazendas.global.Hologram
import com.grandemc.fazendas.global.add
import com.grandemc.fazendas.global.prepareHologram
import com.grandemc.fazendas.manager.FarmManager
import com.grandemc.fazendas.manager.IslandLocationManager
import com.grandemc.fazendas.manager.LandManager
import com.grandemc.fazendas.manager.model.IslandEntities
import com.grandemc.fazendas.manager.model.KeyableHologram
import com.grandemc.fazendas.manager.model.LandHolograms
import com.grandemc.fazendas.npc.IndustryTrait
import com.grandemc.fazendas.npc.LandsTrait
import com.grandemc.fazendas.npc.MasterTrait
import com.grandemc.fazendas.util.ViewVector
import com.grandemc.post.external.lib.global.color
import com.grandemc.post.external.lib.global.format
import net.citizensnpcs.api.CitizensAPI
import net.citizensnpcs.api.npc.MemoryNPCDataStore
import net.citizensnpcs.api.npc.NPC
import net.citizensnpcs.api.trait.Trait
import org.bukkit.Location
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import java.util.*

class IslandEntityManager(
    private val locationManager: IslandLocationManager,
    private val islandConfig: IslandConfig,
    private val farmsConfig: FarmsConfig,
    private val landManager: LandManager,
    private val farmManager: FarmManager,
    private val cropsConfig: CropsConfig
) {
    private val npcRegistry = CitizensAPI.createAnonymousNPCRegistry(MemoryNPCDataStore())

    private fun createHologramNPC(
        player: Player, configNPC: IslandConfig.IslandNPC, origin: Location
    ): Hologram {
        return createHologram(
            player, configNPC.hologramLines, configNPC.position, origin
        )
    }

    private fun createNPC(
        configNPC: IslandConfig.IslandNPC,
        origin: Location,
        id: Int,
        trait: Trait? = null
    ): NPC {
        val npc = npcRegistry.createNPC(
            EntityType.PLAYER, UUID.randomUUID(), id, configNPC.name
        )
        val npcLocation = origin.add(configNPC.position)
        npc.spawn(npcLocation)
        npc.data().setPersistent(NPC.NAMEPLATE_VISIBLE_METADATA, false)
        trait?.let { npc.addTrait(it) }
        return npc
    }

    private fun createHologram(
        player: Player,
        hologramLines: List<String>,
        position: ViewVector,
        origin: Location
    ): Hologram {
        val hologram = player.prepareHologram(hologramLines)
        hologram.send(player, origin.add(position))
        return hologram
    }

    fun createEntities(player: Player, origin: Location): IslandEntities {
        val id = farmManager.farm(player.uniqueId).id()
        val npcs = islandConfig.get().islandNpcs
        val entities = IslandEntities(listOf(
                createHologramNPC(player, npcs.terrains, origin),
                createHologramNPC(player, npcs.industry, origin),
                createHologramNPC(player, npcs.master, origin)
            ),
            LandHolograms(
                farmsConfig.get().farms.map {
                    KeyableHologram(
                        it.config.id,
                        player.prepareHologram(
                            islandConfig.get().landHolograms.blockedHologram
                                .format(
                                    "{nome}" to it.config.name
                                )
                                .color()
                        )
                    )
                },
                locationManager,
                islandConfig,
                landManager,
                farmsConfig,
                cropsConfig
            ),
            listOf(
                createNPC(npcs.terrains, origin, 1000 + (id * 3) + 1, LandsTrait()),
                createNPC(npcs.industry, origin, 1000 + (id * 3) + 2, IndustryTrait()),
                createNPC(npcs.master, origin, 1000 + (id * 3) + 3, MasterTrait())
            )
        )
        farmsConfig.get().farms.forEach {
            entities.updateHologram(player, it.config.id)
        }
        entities.sendHolograms(player)
        return entities
    }
}