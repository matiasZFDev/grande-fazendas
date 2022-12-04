package com.grandemc.fazendas.bukkit.listener.quest

import com.grandemc.fazendas.bukkit.event.QuestCompleteEvent
import com.grandemc.fazendas.bukkit.event.XpGainEvent
import com.grandemc.fazendas.global.respond
import com.grandemc.fazendas.manager.FarmManager
import com.grandemc.fazendas.manager.QuestManager
import com.grandemc.fazendas.manager.StatsManager
import com.grandemc.post.external.lib.global.bukkit.runIfOnline
import com.grandemc.post.external.lib.global.callEvent
import com.grandemc.post.external.lib.global.dottedFormat
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class QuestCompleteListener(
    private val questManager: QuestManager,
    private val farmManager: FarmManager,
    private val statsManager: StatsManager
) : Listener {
    @EventHandler
    fun onComplete(event: QuestCompleteEvent) {
        val xp = statsManager.boostedXp(event.playerId(), questManager.questDoneXp())
        farmManager.farm(event.playerId()).addXp(xp)

        if (questManager.isHistoryQuest(event.questId())) {
            questManager.master(event.playerId()).advanceHistoryProgress()
            event.playerId().runIfOnline {
                respond("missao_historia.completada")
            }
        }

        event.playerId().runIfOnline {
            respond("missao.completada")
            respond("missao.completada_xp") {
                replace("{xp}" to xp.dottedFormat())
            }
        }

        callEvent(XpGainEvent(event.playerId(), xp))
    }
}