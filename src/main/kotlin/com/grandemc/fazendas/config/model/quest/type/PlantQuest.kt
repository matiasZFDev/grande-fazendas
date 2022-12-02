package com.grandemc.fazendas.config.model.quest.type

import com.grandemc.fazendas.config.model.quest.reward.QuestReward
import com.grandemc.fazendas.storage.player.model.FarmQuest
import com.grandemc.fazendas.storage.player.model.QuestType
import com.grandemc.post.external.lib.global.bukkit.formatLore
import com.grandemc.post.external.util.ProgressFormat
import org.bukkit.inventory.ItemStack

class PlantQuest(
    name: String,
    reward: QuestReward,
    private val cropId: Byte?,
    private val times: Int,
    private val progressFormat: ProgressFormat
) : Quest(name, reward) {
    override fun type(): QuestType {
        return QuestType.PLANT
    }

    override fun format(progress: Int, item: ItemStack): ItemStack {
        return item.formatLore(
            "{desafio}" to name(),
            "{valor}" to progress.toString(),
            "{objetivo}" to times.toString(),
            "{progresso}" to progressFormat.get(
                times.toDouble(),
                progress.toDouble(),
                true
            )
        )
    }

    fun cropId(): Byte? {
        return cropId
    }

    override fun isDone(quest: FarmQuest): Boolean {
        return quest.progress() > times
    }
}