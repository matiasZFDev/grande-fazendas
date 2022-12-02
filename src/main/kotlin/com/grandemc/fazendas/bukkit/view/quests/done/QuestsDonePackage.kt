package com.grandemc.fazendas.bukkit.view.quests.done

import com.grandemc.fazendas.bukkit.view.PageContext
import com.grandemc.fazendas.manager.QuestManager
import com.grandemc.post.external.lib.cache.config.chunk.base.ItemsChunk
import com.grandemc.post.external.lib.view.pack.impl.StatefulPackage
import com.grandemc.post.external.lib.view.pack.impl.StatelessPackage

class QuestsDonePackage(
    questManager: QuestManager,
    itemsConfig: ItemsChunk
) : StatefulPackage<PageContext>(
    QuestsDoneMenuContainer::class,
    QuestsDoneProcessor(questManager, itemsConfig),
    QuestsDoneClickHandler()
)