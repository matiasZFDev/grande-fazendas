package com.grandemc.fazendas.config.model.quest.reward

import com.grandemc.fazendas.storage.player.model.FarmPlayer
import org.bukkit.inventory.ItemStack

class QuestRewards(private val rewards: List<QuestReward>) : QuestReward {
    override fun send(player: FarmPlayer) {
        rewards.forEach { it.send(player) }
    }

    override fun listRewards(): List<String> {
        return rewards.fold(listOf()) { acc, questReward -> acc + questReward.listRewards() }
    }
}