package com.grandemc.fazendas.config.model.quest.reward

import com.grandemc.fazendas.config.FertilizingConfig
import com.grandemc.fazendas.global.dottedFormat
import com.grandemc.fazendas.manager.FarmItemManager
import com.grandemc.fazendas.storage.player.model.FarmPlayer
import com.grandemc.post.external.lib.global.bukkit.giveItem
import com.grandemc.post.external.lib.global.bukkit.runIfOnline
import com.grandemc.post.external.lib.global.color
import com.grandemc.post.external.lib.global.formatReplace
import com.grandemc.post.external.lib.util.state.MutableState

class FertilizingReward(
    private val fertilizing: FertilizingConfig.Fertilizing,
    private val amount: Short,
    private val farmItemManager: MutableState<FarmItemManager>,
    private val format: String
) : QuestReward {
    override fun send(player: FarmPlayer) {
        val amount = this.amount.toInt()
        player.id().runIfOnline {
            giveItem(farmItemManager.get().createFertilizing(fertilizing).apply {
                this.amount = amount
            })
        }
    }

    override fun listRewards(): List<String> {
        return listOf(format.formatReplace(
            "{nome}" to fertilizing.name,
            "{quantia}" to amount.dottedFormat()
        ).color())
    }
}