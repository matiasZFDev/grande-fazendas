package com.grandemc.fazendas.config.model.quest.type

import com.grandemc.fazendas.config.model.quest.reward.QuestReward
import com.grandemc.fazendas.global.commaFormat
import com.grandemc.fazendas.storage.player.model.FarmQuest
import com.grandemc.fazendas.storage.player.model.QuestType
import com.grandemc.post.external.lib.global.bukkit.formatLore
import com.grandemc.post.external.util.ProgressFormat
import org.bukkit.inventory.ItemStack

class HandOverQuest(
    name: String,
    reward: QuestReward,
    private val materialId: Byte,
    private val amount: Short,
    private val progressFormat: ProgressFormat
) : Quest(name, reward) {
    override fun type(): QuestType {
        return QuestType.HAND_OVER
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

    fun materialId(): Byte = materialId
    fun amount(): Short = amount

    override fun isDone(quest: FarmQuest): Boolean {
        return quest.progress() >= amount
    }
}