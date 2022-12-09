package com.grandemc.fazendas.config.model.quest.reward

import com.grandemc.fazendas.GrandeFazendas
import com.grandemc.fazendas.storage.player.model.FarmPlayer
import com.grandemc.post.external.lib.global.bukkit.giveItem
import com.grandemc.post.external.lib.global.bukkit.runIfOnline
import com.grandemc.post.external.lib.global.color
import com.grandemc.post.external.lib.global.formatReplace
import com.grandemc.post.external.util.reward.base.config.chunk.RewardsChunk
import org.bukkit.inventory.ItemStack

class ItemReward(
    private val itemSlot: Int,
    private val config: RewardsChunk<*>,
    private val format: String
) : QuestReward {
    private fun item(): ItemStack {
        return config.get().getItem(GrandeFazendas.REWARDS_KEY, itemSlot).item!!
    }

    override fun send(player: FarmPlayer) {
        player.id().runIfOnline {
            giveItem(item().clone())
        }
    }

    override fun listRewards(): List<String> {
        return listOf(
            format.formatReplace(
                "{nome}" to item().itemMeta.displayName,
                "{quantia}" to item().amount.toString()
            ).color()
        )
    }
}