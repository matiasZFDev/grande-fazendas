package com.grandemc.fazendas.bukkit.view.quests.menu

import com.grandemc.fazendas.global.commaFormat
import com.grandemc.fazendas.manager.QuestManager
import com.grandemc.fazendas.manager.StatsManager
import com.grandemc.fazendas.storage.player.model.QuestType
import com.grandemc.post.external.lib.cache.config.model.SlotItem
import com.grandemc.post.external.lib.cache.config.model.menu.MenuItems
import com.grandemc.post.external.lib.global.bukkit.formatLore
import com.grandemc.post.external.lib.global.newItemProcessing
import com.grandemc.post.external.lib.view.pack.MenuItemsProcessor
import org.bukkit.entity.Player

class QuestsProcessor(
    private val questManager: QuestManager,
    private val statsManager: StatsManager
) : MenuItemsProcessor.Stateless() {
    override fun process(player: Player, items: MenuItems): Collection<SlotItem> {
        val quest = questManager.currentQuest(player.uniqueId)
        val questsDone = questManager.questsDone(player.uniqueId)
        return newItemProcessing(items) {
            modify("completadas") {
                it.formatLore("{quantia}" to questsDone.commaFormat())
            }

            if (quest == null || quest.type() != QuestType.HAND_OVER) {
                remove("entrega_aberta")
                remove("entrega_completa")
            }
            else if (quest.isDone()) {
                remove("entrega_fechada")
                remove("entrega_aberta")
            }

            else {
                remove("entrega_fechada")
                remove("entrega_completa")
            }

            if (quest == null) {
                remove("atual_ativa")
                remove("atual_completa")
            }

            else if (quest.isDone()) {
                val questConfig = questManager.questConfig(quest.id())
                remove("atual_ativa")
                remove("atual_inativa")
                modify("atual_completa") {
                    questConfig.formatQuest(quest.progress(), it)
                }
            }

            else {
                val questConfig = questManager.questConfig(quest.id())
                remove("atual_completa")
                remove("atual_inativa")
                modify("atual_ativa") {
                    questConfig.formatQuest(quest.progress(), it)
                }
            }

            modify("diarias") {
                it.formatLore(
                    "{atual}" to questManager.master(
                        player.uniqueId
                    ).dailyQuestsDone().toString(),
                    "{total}" to statsManager.dailyQuests(
                        player.uniqueId
                    ).toString()
                )
            }

            modify("historia") {
                it.formatLore(
                    "{atual}" to questManager.master(
                        player.uniqueId
                    ).questHistoryProgress().toString(),
                    "{total}" to questManager.history().quests().size.toString()
                )
            }
        }
    }
}