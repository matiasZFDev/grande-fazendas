package com.grandemc.fazendas.storage.player.model

class QuestMaster(
    private var currentQuest: FarmQuest?,
    private var dailyDoneQuests: Byte,
    private var questHistoryProgress: Byte,
    private val questsDone: MutableList<Byte>,
) {
    fun current(): FarmQuest? = currentQuest
    fun questsDone(): List<Byte> = questsDone
    fun concludeQuest() {
        val quest = currentQuest!!
        quest.complete()
        questsDone.add(quest.id())
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