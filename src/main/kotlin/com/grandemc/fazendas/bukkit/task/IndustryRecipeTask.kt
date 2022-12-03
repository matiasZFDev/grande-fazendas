package com.grandemc.fazendas.bukkit.task

import com.grandemc.fazendas.bukkit.event.IndustryCraftEvent
import com.grandemc.fazendas.bukkit.event.XpGainEvent
import com.grandemc.fazendas.config.IndustryConfig
import com.grandemc.fazendas.global.respond
import com.grandemc.fazendas.manager.*
import com.grandemc.post.external.lib.global.bukkit.runIfOnline
import com.grandemc.post.external.lib.global.callEvent
import com.grandemc.post.external.lib.global.dottedFormat
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin

class IndustryRecipeTask(
    private val plugin: Plugin,
    private val playerManager: PlayerManager,
    private val industryConfig: IndustryConfig,
    private val statsManager: StatsManager,
    private val farmManager: FarmManager,
    private val storageManager: StorageManager
) {
    fun start() {
        Bukkit.getScheduler().runTaskTimer(plugin, this::run, 20L, 20L)
    }

    private fun run() {
        playerManager.allPlayers().forEach { player ->
            player.farm()?.industry()?.currentRecipe()?.let {
                if (it.timeLeft() <= -1) {
                    return@let
                }

                if (it.timeLeft() > 0) {
                    it.advance()
                    return@let
                }

                val recipeConfig = industryConfig.get().getById(it.id())
                val craftXp = statsManager.boostedXp(recipeConfig.xp)
                val materialConfig = storageManager.materialData(recipeConfig.materialId)
                farmManager.farm(player.id()).addXp(craftXp)
                it.advance()
                player.id().runIfOnline {
                    respond("receita.pronta")
                    respond("receita.pronta_xp") {
                        replace(
                            "{plantacao}" to materialConfig.name,
                            "{xp}" to craftXp.dottedFormat()
                        )
                    }
                }
                callEvent(IndustryCraftEvent(player.id(), recipeConfig.materialId))
                callEvent(XpGainEvent(player.id(), craftXp))
            }
        }
    }
}