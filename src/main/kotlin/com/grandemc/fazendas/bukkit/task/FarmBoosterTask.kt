package com.grandemc.fazendas.bukkit.task

import com.grandemc.fazendas.global.respond
import com.grandemc.fazendas.manager.PlayerManager
import com.grandemc.fazendas.manager.task.TaskManager
import com.grandemc.post.external.lib.global.bukkit.runIfOnline

class FarmBoosterTask(
    private val taskManager: TaskManager,
    private val playerManager: PlayerManager
) {
    fun start() {
        taskManager.runTimer(20L, 20L, false) {
            playerManager.allPlayers().forEach {
                if (it.booster() == null)
                    return@forEach

                if (it.booster()!!.isDone()) {
                    it.setBooster(null)
                    it.id().runIfOnline {
                        respond("booster.expirado")
                    }
                    return@forEach
                }

                it.booster()!!.advance()
            }
        }
    }
}