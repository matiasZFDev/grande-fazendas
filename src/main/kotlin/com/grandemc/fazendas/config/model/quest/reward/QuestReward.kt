package com.grandemc.fazendas.config.model.quest.reward

import com.grandemc.fazendas.storage.player.model.FarmPlayer

interface QuestReward {
    fun send(player: FarmPlayer)
    fun listRewards(): List<String>
}