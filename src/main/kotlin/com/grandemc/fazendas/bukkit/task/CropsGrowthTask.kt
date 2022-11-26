package com.grandemc.fazendas.bukkit.task

import com.grandemc.fazendas.config.CropsConfig
import com.grandemc.fazendas.global.respond
import com.grandemc.fazendas.manager.LandPlantManager
import com.grandemc.fazendas.manager.PlayerManager
import com.grandemc.post.external.lib.global.bukkit.runIfOnline
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin

class CropsGrowthTask(
    private val plugin: Plugin,
    private val cropsConfig: CropsConfig,
    private val playerManager: PlayerManager,
    private val landPlantManager: LandPlantManager,
) {
    fun start() {
        Bukkit.getScheduler().runTaskTimer(plugin, this::run, 20L, 20L)
    }

    private fun run() {
        playerManager.allPlayers().forEach { player ->
            player.farm()?.let { farm ->
                farm.lands().forEach land@ { land ->
                    if (land.cropId() == null)
                        return@land

                    if (land.resetCountdown() < 0)
                        return@land

                    if (land.resetCountdown() > 0) {
                        land.reduceCountdown()
                        return@land
                    }

                    val cropData = cropsConfig.get().getCrop(land.cropId()!!).let { crop ->
                        if (crop == null) {
                            player.id().runIfOnline {
                                respond("geral.error")
                                closeInventory()
                            }
                            return@land
                        }
                        crop
                    }
                    landPlantManager.growPlantation(player.id(), land, cropData)
                    land.reduceCountdown()
                    land.resetCanBoost()
                    player.id().runIfOnline {
                        respond("plantio.crescido") {
                            replace("{plantacao}" to cropData.name)
                        }
                    }
                }
            }
        }
    }
}