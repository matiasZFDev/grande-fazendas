package com.grandemc.fazendas.manager

import com.grandemc.fazendas.config.FarmsConfig
import com.grandemc.fazendas.config.IslandConfig
import com.grandemc.fazendas.global.respond
import com.grandemc.fazendas.global.toCuboid
import com.grandemc.fazendas.storage.player.model.FarmIndustry
import com.grandemc.fazendas.storage.player.model.PrivateFarm
import com.grandemc.fazendas.storage.player.model.QuestMaster
import com.grandemc.post.external.lib.global.bukkit.runIfOnline
import com.sk89q.worldedit.bukkit.BukkitWorld
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import java.util.UUID

class IslandGenerationManager(
    private val plugin: Plugin,
    private val playerManager: PlayerManager,
    private val buildManager: BuildManager,
    private val locationManager: IslandLocationManager,
    private val islandConfig: IslandConfig,
    private val farmsConfig: FarmsConfig,
    private val successGeneration: (Player?) -> Unit
) {
    private val generationQueue: MutableList<UUID> = mutableListOf()
    private val GENERATION_DELAY: Int = 10
    private var delayCount: Int = 0
    private var generationTask: Int? = null

    companion object {
        const val ISLAND_Y: Int = 10
    }

    fun isGeneratingIsland(playerId: UUID): Boolean {
        return generationQueue.contains(playerId)
    }

    fun newIsland(playerId: UUID) {
        generationQueue.add(playerId)

        if (generationTask == null) {
            generationTask = runGenDelay()
        }
    }

    private fun getNextFarmId(): Int {
        return playerManager.allPlayers()
            .filter { it.farm() != null }
            .maxByOrNull { it.farm()!!.id() }
            ?.farm()?.id()?.inc() ?: 0
    }

    private fun createFarmIsland(playerId: UUID) {
        val player = Bukkit.getPlayer(playerId)
        val world = Bukkit.getWorld(islandConfig.get().worldName)
        val farmId = getNextFarmId()
        val baseLocation = locationManager.baseLocation()
        val baseSchematic = islandConfig.get().baseSchematic
        val farm = PrivateFarm(
            farmId,
            baseSchematic.toCuboid(baseLocation, world),
            1,
            0,
            mutableListOf(),
            QuestMaster(null, 0, 0, 0),
            FarmIndustry()
        )

        playerManager.player(playerId).setFarm(farm)
        player.respond("ilha.gerando_ilha")
        val weWorld = BukkitWorld(world)
        buildManager.pasteSchematic(baseSchematic, baseLocation, weWorld)
        farmsConfig.get().farms.forEach {
            val landLocation = locationManager.islandOrigin(playerId)
            buildManager.pasteSchematic(it.config.baseSchematic, landLocation, weWorld)
        }
        successGeneration(player)
    }

    private fun runGenDelay(): Int {
        return Bukkit.getScheduler().runTaskTimer(
            plugin, this::runDelayTask, 0L, 20L
        ).taskId
    }

    private fun runDelayTask() {
        if (generationTask == null) {
            resetGenerationDelay()
            return
        }

        if (generationQueue.isEmpty()) {
            resetGenerationDelay()
            Bukkit.getScheduler().cancelTask(generationTask!!)
            generationTask = null
            return
        }

        if (delayCount > 0) {
            generationQueue.forEachIndexed { index, it ->
                it.runIfOnline {
                    respond("ilha.gerando_fila") {
                        replace("{posicao}" to index.inc().toString())
                    }
                }
            }
            delayCount--
            return
        }

        createFarmIsland(generationQueue.removeFirst())
        resetGenerationDelay()
    }

    private fun resetGenerationDelay() {
        delayCount = GENERATION_DELAY
    }
}