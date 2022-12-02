package com.grandemc.fazendas.config.model.quest.type

import com.grandemc.fazendas.config.model.quest.reward.QuestReward
import com.grandemc.fazendas.storage.player.model.QuestType
import com.grandemc.post.external.lib.global.bukkit.formatLore
import com.grandemc.post.external.util.ProgressFormat
import org.bukkit.inventory.ItemStack

class MarketPostQuest(
    name: String,
    reward: QuestReward,
    private val times: Int,
    private val progressFormat: ProgressFormat
): MarketQuest(name, reward, times) {
    override fun type(): QuestType {
        return QuestType.MARKET_POST
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
}