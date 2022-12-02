package com.grandemc.fazendas.config.model.quest.reward

import com.grandemc.fazendas.storage.player.model.FarmPlayer
import com.grandemc.post.external.lib.global.bukkit.formatLoreList
import com.grandemc.post.external.lib.global.color
import com.grandemc.post.external.lib.global.dottedFormat
import com.grandemc.post.external.lib.global.formatReplace
import org.bukkit.inventory.ItemStack

class XpReward(
    private val xp: Int,
    private val format: String
) : QuestReward {
    override fun send(player: FarmPlayer) {
        player.farm()!!.addXp(xp)
    }

    override fun listRewards(): List<String> {
        return listOf(format.formatReplace(
            "{xp}" to xp.dottedFormat()
        ).color())
    }
}