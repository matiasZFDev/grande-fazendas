package com.grandemc.fazendas.config.model.quest.type

import com.grandemc.fazendas.config.model.quest.reward.QuestReward
import com.grandemc.fazendas.global.commaFormat
import com.grandemc.fazendas.storage.player.model.FarmQuest
import com.grandemc.fazendas.storage.player.model.QuestType
import com.grandemc.post.external.lib.global.bukkit.formatLore
import com.grandemc.post.external.lib.global.dottedFormat
import com.grandemc.post.external.util.ProgressFormat
import org.bukkit.inventory.ItemStack

class XpEarnQuest(
    name: String,
    reward: QuestReward,
    private val xp: Int,
    private val progressFormat: ProgressFormat
) : Quest(name, reward) {
    override fun type(): QuestType {
        return QuestType.XP_EARN
    }

    override fun format(progress: Int, item: ItemStack): ItemStack {
        return item.formatLore(
            "{desafio}" to name(),
            "{valor}" to progress.commaFormat(),
            "{objetivo}" to xp.commaFormat(),
            "{progresso}" to progressFormat.get(
                xp.toDouble(),
                progress.toDouble(),
                true
            )
        )
    }

    fun xp(): Int = xp

    override fun isDone(quest: FarmQuest): Boolean {
        return quest.progress() >= xp
    }
}