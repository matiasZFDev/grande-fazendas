package com.grandemc.fazendas.config.model.quest.type

import com.grandemc.fazendas.config.model.quest.reward.QuestReward
import com.grandemc.fazendas.storage.player.model.FarmQuest
import com.grandemc.fazendas.storage.player.model.QuestType
import com.grandemc.post.external.lib.global.bukkit.formatLoreList
import org.bukkit.inventory.ItemStack

abstract class Quest(
    private val name: String,
    private val reward: QuestReward
) {
    abstract fun type(): QuestType
    abstract fun format(progress: Int, item: ItemStack): ItemStack
    abstract fun isDone(quest: FarmQuest): Boolean
    fun formatQuest(progress: Int, item: ItemStack): ItemStack {
        return format(progress, item).formatLoreList(
            "{<recompensas>}" to reward.listRewards()
        )
    }
    fun name(): String = name
    fun rewards(): QuestReward {
        return reward
    }
}