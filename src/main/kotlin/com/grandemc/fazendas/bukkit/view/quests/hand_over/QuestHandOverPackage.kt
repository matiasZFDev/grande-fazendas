package com.grandemc.fazendas.bukkit.view.quests.hand_over

import com.grandemc.fazendas.config.MaterialsConfig
import com.grandemc.fazendas.manager.QuestManager
import com.grandemc.fazendas.manager.StorageManager
import com.grandemc.post.external.lib.view.pack.impl.StatefulPackage
import org.bukkit.conversations.ConversationFactory

class QuestHandOverPackage(
    questManager: QuestManager,
    storageManager: StorageManager,
    materialsConfig: MaterialsConfig,
    conversationFactory: ConversationFactory
) : StatefulPackage<HandOverContext>(
    QuestHandOverMenuContainer::class,
    QuestHandOverProcessor(questManager, storageManager, materialsConfig),
    QuestHandOverClickHandler(questManager, storageManager, conversationFactory)
)