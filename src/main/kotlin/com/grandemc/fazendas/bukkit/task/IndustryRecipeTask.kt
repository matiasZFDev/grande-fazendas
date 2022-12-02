package com.grandemc.fazendas.bukkit.task

import com.grandemc.fazendas.bukkit.event.IndustryCraftEvent
import com.grandemc.fazendas.config.IndustryConfig
import com.grandemc.fazendas.global.respond
import com.grandemc.fazendas.manager.IndustryManager
import com.grandemc.fazendas.manager.PlayerManager
import com.grandemc.fazendas.manager.StorageManager
import com.grandemc.post.external.lib.global.bukkit.runIfOnline
import com.grandemc.post.external.lib.global.callEvent
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin

class IndustryRecipeTask(
    private val plugin: Plugin,
    private val playerManager: PlayerManager,
    private val industryConfig: IndustryConfig
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

                val recipeMaterialId = industryConfig.get().getById(it.id()).materialId
                it.advance()
                callEvent(IndustryCraftEvent(player.id(), recipeMaterialId))
                player.id().runIfOnline {
                    respond("receita.pronta")
                }
            }
        }
    }
}