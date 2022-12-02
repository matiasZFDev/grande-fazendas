package com.grandemc.fazendas.bukkit.view.quests.history

import com.grandemc.fazendas.GrandeFazendas
import com.grandemc.fazendas.bukkit.view.PageContext
import com.grandemc.fazendas.config.QuestsConfig
import com.grandemc.fazendas.global.cut
import com.grandemc.fazendas.manager.FarmManager
import com.grandemc.fazendas.manager.PlayerManager
import com.grandemc.fazendas.manager.QuestManager
import com.grandemc.post.external.lib.cache.config.chunk.base.ItemsChunk
import com.grandemc.post.external.lib.cache.config.model.SlotItem
import com.grandemc.post.external.lib.cache.config.model.menu.MenuItems
import com.grandemc.post.external.lib.global.bukkit.formatLore
import com.grandemc.post.external.lib.global.bukkit.formatLoreList
import com.grandemc.post.external.lib.global.bukkit.formatName
import com.grandemc.post.external.lib.global.bukkit.nms.NBTReference
import com.grandemc.post.external.lib.global.bukkit.nms.addNBTReference
import com.grandemc.post.external.lib.global.newItemProcessing
import com.grandemc.post.external.lib.view.pack.MenuItemsProcessor
import org.bukkit.entity.Player

class QuestHistoryProcessor(
    private val questManager: QuestManager,
    private val farmManager: FarmManager,
    private val itemsConfig: ItemsChunk
) : MenuItemsProcessor<PageContext> {
    override fun process(player: Player, data: PageContext?, items: MenuItems): Collection<SlotItem> {
        requireNotNull(data)
        val islandLevel = farmManager.farm(player.uniqueId).level()
        val questHistoryPosition = questManager.historyProgress(player.uniqueId)
        val currentQuest = questManager.currentQuest(player.uniqueId)
        val questsPerPage = GrandeFazendas.MEDIUM_SLOTS_PATTERN.size
        val paginatedQuests = questManager.history().quests()
            .sortedBy(QuestsConfig.HistoryQuest::position)
            .cut(questsPerPage * data.page, questsPerPage)
        val questItems = paginatedQuests
            .zip(GrandeFazendas.MEDIUM_SLOTS_PATTERN)
            .map { (quest, slot) ->
                val item =
                    if (questHistoryPosition == quest.position)
                        if (currentQuest != null && currentQuest.id() == quest.id)
                            itemsConfig.value("missao_historia_fazendo")
                        else if (islandLevel >= quest.islandLevel)
                            itemsConfig.value("missao_historia_fazer")
                                .addNBTReference(
                                    NBTReference.VIEW,
                                    "gfazendas.quests.history",
                                    "start"
                                )
                        else
                            itemsConfig.value("missao_historia_nivel")
                    else if (quest.position > questHistoryPosition)
                        itemsConfig.value("missao_historia_anterior")
                    else
                        itemsConfig.value("missao_historia_completada")
                val formattedItem = item
                    .formatName("{posicao}" to quest.position.toString())
                    .formatLore(
                        "{missao}" to quest.quest.name(),
                        "{nivel}" to quest.islandLevel.toString()
                    )
                    .formatLoreList(
                        "{<recompensas>}" to quest.quest.rewards().listRewards()
                    )
                SlotItem(slot, formattedItem)
            }
        val baseItems = newItemProcessing(items) {
            if (data.page == (0).toByte())
                remove("anterior")

            if (questsPerPage * data.page + questsPerPage >= paginatedQuests.size)
                remove("seguinte")
        }
        return baseItems + questItems
    }
}