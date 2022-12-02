package com.grandemc.fazendas.config.model.quest.reward

import com.grandemc.fazendas.config.LootBoxConfig
import com.grandemc.fazendas.global.dottedFormat
import com.grandemc.fazendas.manager.FarmItemManager
import com.grandemc.fazendas.storage.player.model.FarmPlayer
import com.grandemc.post.external.lib.global.bukkit.giveItem
import com.grandemc.post.external.lib.global.bukkit.runIfOnline
import com.grandemc.post.external.lib.global.formatReplace
import com.grandemc.post.external.lib.global.intFormat
import com.grandemc.post.external.lib.global.timeFormat
import com.grandemc.post.external.lib.util.state.MutableState

class BoosterReward(
    private val booster: LootBoxConfig.Booster,
    private val amount: Short,
    private val farmItemManager: MutableState<FarmItemManager>,
    private val format: String
) : QuestReward {
    override fun send(player: FarmPlayer) {
        val amount = this.amount.toInt()
        player.id().runIfOnline {
            giveItem(farmItemManager.get().createBooster(booster).apply {
                this.amount = amount
            })
        }
    }

    override fun listRewards(): List<String> {
        return listOf(format.formatReplace(
            "{boost}" to booster.boost.intFormat(),
            "{duracao}" to booster.duration.timeFormat(),
            "{quantia}" to amount.dottedFormat()
        ))
    }
}