package com.grandemc.fazendas.config.model.quest.reward

import com.grandemc.fazendas.bukkit.event.XpGainEvent
import com.grandemc.fazendas.global.respond
import com.grandemc.fazendas.manager.StatsManager
import com.grandemc.fazendas.storage.player.model.FarmPlayer
import com.grandemc.post.external.lib.global.bukkit.runIfOnline
import com.grandemc.post.external.lib.global.callEvent
import com.grandemc.post.external.lib.global.color
import com.grandemc.post.external.lib.global.dottedFormat
import com.grandemc.post.external.lib.global.formatReplace
import com.grandemc.post.external.lib.util.state.MutableState

class XpReward(
    private val xp: Int,
    private val format: String,
    private val statsManager: MutableState<StatsManager>
) : QuestReward {
    override fun send(player: FarmPlayer) {
        val boostedXp = statsManager.get().boostedXp(player.id(), xp)
        player.farm()!!.addXp(boostedXp)
        player.id().runIfOnline {
            respond("missao.recompensa_xp") {
                replace("{xp}" to boostedXp.dottedFormat())
            }
        }
        callEvent(XpGainEvent(player.id(), boostedXp))
    }

    override fun listRewards(): List<String> {
        return listOf(format.formatReplace(
            "{xp}" to xp.dottedFormat()
        ).color())
    }
}