package com.grandemc.fazendas.storage.player.model

class QuestMaster(
    private var currentQuest: FarmQuest?,
    private var dailyDoneQuests: Byte,
    private var questHistoryProgress: Byte,
    private var questsDone: Short,
) {
    fun current(): FarmQuest? = currentQuest
    fun questsDone(): Short = questsDone
    fun concludeQuest() {
        currentQuest!!.complete()
        questsDone++
        currentQuest = null
    }

    fun dailyQuestsDone(): Byte = dailyDoneQuests
    fun questHistoryProgress(): Byte = questHistoryProgress
    fun advanceHistoryProgress() {
        questHistoryProgress++
    }

    fun useDailyQuests() {
        dailyDoneQuests++
    }

    fun startQuest(quest: FarmQuest) {
        currentQuest = quest
    }
}