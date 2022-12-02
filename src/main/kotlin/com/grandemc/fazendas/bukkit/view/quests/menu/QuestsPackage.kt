package com.grandemc.fazendas.bukkit.view.quests.menu

import com.grandemc.fazendas.config.QuestsConfig
import com.grandemc.fazendas.manager.PlayerManager
import com.grandemc.fazendas.manager.QuestManager
import com.grandemc.fazendas.manager.StatsManager
import com.grandemc.post.external.lib.view.pack.impl.StatelessPackage

class QuestsPackage(
    questManager: QuestManager,
    statsManager: StatsManager,
    questsConfig: QuestsConfig,
    playerManager: PlayerManager
) : StatelessPackage(
    QuestsMenuContainer::class,
    QuestsProcessor(questManager, statsManager),
    QuestsClickHandler(questManager, questsConfig, playerManager, statsManager)
)