package com.grandemc.fazendas.bukkit.task

import com.grandemc.fazendas.bukkit.event.RecipeBakeEvent
import com.grandemc.fazendas.global.respond
import com.grandemc.fazendas.manager.PlayerManager
import com.grandemc.post.external.lib.global.bukkit.runIfOnline
import com.grandemc.post.external.lib.global.callEvent
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin

class IndustryRecipeTask(
    private val plugin: Plugin,
    private val playerManager: PlayerManager
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

                it.advance()
                callEvent(RecipeBakeEvent(player.id(), it.id()))
                player.id().runIfOnline {
                    respond("receita.pronta")
                }
            }
        }
    }
}