package com.grandemc.fazendas.config.model.quest.type

import com.grandemc.fazendas.config.model.quest.reward.QuestReward
import com.grandemc.fazendas.storage.player.model.FarmQuest

abstract class MarketQuest(
    name: String,
    reward: QuestReward,
    private val times: Int
) : Quest(name, reward) {
    override fun isDone(quest: FarmQuest): Boolean {
        return quest.progress() >= times
    }
}