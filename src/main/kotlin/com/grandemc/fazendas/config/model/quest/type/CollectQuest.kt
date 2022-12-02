package com.grandemc.fazendas.config.model.quest.type

import com.grandemc.fazendas.config.model.quest.reward.QuestReward
import com.grandemc.fazendas.global.commaFormat
import com.grandemc.fazendas.storage.player.model.FarmQuest
import com.grandemc.fazendas.storage.player.model.QuestType
import com.grandemc.post.external.lib.global.bukkit.formatLore
import com.grandemc.post.external.util.ProgressFormat
import org.bukkit.inventory.ItemStack

class CollectQuest(
    name: String,
    reward: QuestReward,
    private val cropId: Byte,
    private val amount: Int,
    private val progressFormat: ProgressFormat
) : Quest(name, reward) {
    override fun type(): QuestType {
        return QuestType.CROP_COLLECT
    }

    override fun format(progress: Int, item: ItemStack): ItemStack {
        return item.formatLore(
            "{desafio}" to name(),
            "{valor}" to progress.commaFormat(),
            "{objetivo}" to amount.commaFormat(),
            "{progresso}" to progressFormat.get(
                amount.toDouble(),
                progress.toDouble(),
                true
            )
        )
    }

    override fun isDone(quest: FarmQuest): Boolean {
        return quest.progress() >= amount
    }

    fun cropId(): Byte {
        return cropId
    }
}