package com.grandemc.fazendas.config.model.quest.type

import com.grandemc.fazendas.config.model.quest.reward.QuestReward
import com.grandemc.fazendas.storage.player.model.FarmQuest
import com.grandemc.fazendas.storage.player.model.QuestType
import com.grandemc.post.external.lib.global.bukkit.formatLore
import com.grandemc.post.external.util.ProgressFormat
import org.bukkit.inventory.ItemStack

class CraftQuest(
    name: String,
    reward: QuestReward,
    private val recipeMaterialId: Byte,
    private val progressFormat: ProgressFormat
) : Quest(name, reward) {
    override fun type(): QuestType {
        return QuestType.CRAFT
    }

    override fun format(progress: Int, item: ItemStack): ItemStack {
        return item.formatLore(
            "{desafio}" to name(),
            "{valor}" to progress.toString(),
            "{objetivo}" to "1",
            "{progresso}" to progressFormat.get(
                1.0,
                progress.toDouble(),
                true
            )
        )
    }

    fun recipeMaterialId(): Byte = recipeMaterialId

    override fun isDone(quest: FarmQuest): Boolean {
        return quest.progress() == 1
    }
}