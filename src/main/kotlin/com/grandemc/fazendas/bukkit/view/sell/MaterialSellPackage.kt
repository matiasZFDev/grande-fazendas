package com.grandemc.fazendas.bukkit.view.sell

import com.grandemc.fazendas.manager.GoldBank
import com.grandemc.fazendas.manager.StatsManager
import com.grandemc.fazendas.manager.StorageManager
import com.grandemc.post.external.lib.view.pack.impl.StatefulPackage
import org.bukkit.conversations.ConversationFactory

class MaterialSellPackage(
    storageManager: StorageManager,
    conversationFactory: ConversationFactory,
    goldBank: GoldBank,
    statsManager: StatsManager
) : StatefulPackage<MaterialSellContext>(
    MaterialSellMenuContainer::class,
    MaterialSellProcessor(storageManager),
    MaterialSellClickHandler(
        conversationFactory, storageManager, goldBank, statsManager
    )
)