package com.grandemc.fazendas.bukkit.view.quests.history

import com.grandemc.fazendas.bukkit.view.PageContext
import com.grandemc.fazendas.manager.FarmManager
import com.grandemc.fazendas.manager.QuestManager
import com.grandemc.post.external.lib.cache.config.chunk.base.ItemsChunk
import com.grandemc.post.external.lib.view.pack.impl.StatefulPackage

class QuestHistoryPackage(
    questManager: QuestManager,
    farmManager: FarmManager,
    itemsConfig: ItemsChunk
) : StatefulPackage<PageContext>(
    QuestHistoryMenuContainer::class,
    QuestHistoryProcessor(questManager, farmManager, itemsConfig),
    QuestHistoryClickHandler(questManager)
)