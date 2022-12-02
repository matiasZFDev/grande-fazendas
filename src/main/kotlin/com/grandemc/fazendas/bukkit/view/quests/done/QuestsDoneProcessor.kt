package com.grandemc.fazendas.bukkit.view.quests.done

import com.grandemc.fazendas.GrandeFazendas
import com.grandemc.fazendas.bukkit.view.PageContext
import com.grandemc.fazendas.global.cut
import com.grandemc.fazendas.manager.QuestManager
import com.grandemc.post.external.lib.cache.config.chunk.base.ItemsChunk
import com.grandemc.post.external.lib.cache.config.model.SlotItem
import com.grandemc.post.external.lib.cache.config.model.menu.MenuItems
import com.grandemc.post.external.lib.global.bukkit.formatLore
import com.grandemc.post.external.lib.global.bukkit.formatName
import com.grandemc.post.external.lib.global.newItemProcessing
import com.grandemc.post.external.lib.view.pack.MenuItemsProcessor
import org.bukkit.entity.Player

class QuestsDoneProcessor(
    private val questManager: QuestManager,
    private val itemsConfig: ItemsChunk
) : MenuItemsProcessor<PageContext> {
    override fun process(
        player: Player, data: PageContext?, items: MenuItems
    ): Collection<SlotItem> {
        requireNotNull(data)
        val mappedQuestsDone = questManager.questsDone(player.uniqueId)
            .fold(mutableMapOf<Byte, Int>()) { acc, cur ->
                acc[cur] = acc[cur]?.inc() ?: 1
                acc
            }
        val questsPerPage = GrandeFazendas.MEDIUM_SLOTS_PATTERN.size
        val paginatedQuests = questManager
            .questsDone(player.uniqueId)
            .cut(questsPerPage * data.page, questsPerPage)
        val questItems = paginatedQuests
            .zip(GrandeFazendas.SLOTS_PATTERN)
            .map { (questId, slot) ->
                val questConfig = questManager.questConfig(questId)
                val item = itemsConfig
                    .value("missao_completada")
                    .formatName("{nome}" to questConfig.name())
                    .formatLore(
                        "{veces}" to mappedQuestsDone[questId]!!.toString()
                    )
                SlotItem(slot, item)
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