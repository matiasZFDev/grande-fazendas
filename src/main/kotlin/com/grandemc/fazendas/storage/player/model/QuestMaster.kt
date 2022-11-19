package com.grandemc.fazendas.storage.player.model

class QuestMaster(
    private val currentQuest: Quest,
    private var dailyDoneQuests: Byte,
    private val questsDone: MutableList<Byte>,
) {
    fun current(): Quest = currentQuest
    fun questsDone(): List<Byte> = questsDone
    fun concludeQuest(quest: Quest = currentQuest, daily: Boolean) {
        quest.complete()
        questsDone.add(quest.id())

        if (daily)
            dailyDoneQuests++
    }

    fun dailyQuestsDone(): Byte = dailyDoneQuests
}