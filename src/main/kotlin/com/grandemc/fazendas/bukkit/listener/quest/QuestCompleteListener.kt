package com.grandemc.fazendas.bukkit.listener.quest

import com.grandemc.fazendas.bukkit.event.QuestCompleteEvent
import com.grandemc.fazendas.global.respond
import com.grandemc.fazendas.manager.QuestManager
import com.grandemc.post.external.lib.global.bukkit.runIfOnline
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class QuestCompleteListener(
    private val questManager: QuestManager
) : Listener {
    @EventHandler
    fun onComplete(event: QuestCompleteEvent) {
        if (questManager.isHistoryQuest(event.playerId(), event.questId())) {
            questManager.master(event.playerId()).advanceHistoryProgress()
            event.playerId().runIfOnline {
                respond("missao_historia.completada")
            }
        }

        event.playerId().runIfOnline {
            respond("missao.completada")
        }
    }
}