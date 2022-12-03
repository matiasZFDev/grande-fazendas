package com.grandemc.fazendas.manager.model

import com.grandemc.fazendas.config.CropsConfig
import com.grandemc.fazendas.config.FarmsConfig
import com.grandemc.fazendas.config.IslandConfig
import com.grandemc.fazendas.global.Hologram
import com.grandemc.fazendas.global.toLocation
import com.grandemc.fazendas.manager.IslandLocationManager
import com.grandemc.fazendas.manager.LandManager
import com.grandemc.post.external.lib.global.color
import com.grandemc.post.external.lib.global.dottedFormat
import com.grandemc.post.external.lib.global.format
import org.bukkit.entity.Player

class LandHolograms(
    private val holograms: List<Keyable<Byte, Hologram>>,
    private val locationManager: IslandLocationManager,
    private val islandConfig: IslandConfig,
    private val landManager: LandManager,
    private val farmsConfig: FarmsConfig,
    private val cropsConfig: CropsConfig
) {
    fun update(player: Player, landId: Byte) {
        val hasLand = landManager.hasLand(player.uniqueId, landId)
        val islandOrigin = locationManager.islandOrigin(player.uniqueId)

        holograms.first { it.key() == landId }.value().run {
            val farmConfig = farmsConfig.get().getFarmById(landId).config
            val position = farmConfig.hologramPosition
            val hologramsData = islandConfig.get().landHolograms

            if (!hasLand) {
                update(
                    player,
                    hologramsData.blockedHologram
                        .format(
                            "{nome}" to farmConfig.name
                        )
                        .color(),
                    islandOrigin.add(position).toLocation(player.world)
                )
            }

            else {
                val land = landManager.land(player.uniqueId, landId)
                val cropName = if (land.cropId() == null || !land.isResetting())
                    hologramsData.noPlantation
                else
                    cropsConfig.get().getCrop(land.cropId()!!)!!.name

                if (!farmConfig.levels.isMaxLevel(land.level())) {
                    update(
                        player,
                        hologramsData.runningHologram.format(
                            "{nome}" to farmConfig.name,
                            "{nivel}" to land.level().toString(),
                            "{xp}" to land.xp().dottedFormat(),
                            "{plantacao}" to cropName,
                            "{progresso}" to hologramsData.progressFormat.get(
                                farmConfig.levels.level(
                                    land.level()
                                ).evolution!!.xp.toDouble(),
                                land.xp().toDouble()
                            )
                        ).color(),
                        islandOrigin.add(position).toLocation(player.world)
                    )
                }

                else {
                    update(
                        player,
                        hologramsData.maxedHologram.format(
                            "{nome}" to farmConfig.name,
                            "{nivel}" to land.level().toString(),
                            "{xp}" to land.xp().dottedFormat(),
                            "{plantacao}" to cropName
                        ).color(),
                        islandOrigin.add(position).toLocation(player.world)
                    )
                }
            }
        }
    }

    fun clear(player: Player) {
        holograms.forEach { it.value().remove(player) }
    }

    fun sendAll(player: Player) {
        val islandOrigin = locationManager.islandOrigin(player.uniqueId)
        println("origin: $islandOrigin")
        holograms.forEach {
            val position = farmsConfig.get().getFarmById(it.key()).config.hologramPosition
            println(position)
            it.value().send(player, islandOrigin.add(position).toLocation(player.world))
        }
    }
}