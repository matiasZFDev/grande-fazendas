package com.grandemc.fazendas.registry.command

import com.grandemc.post.external.lib.cache.config.Updatable
import com.grandemc.post.external.lib.command.base.CommandCompound
import com.grandemc.post.external.lib.factory.Factory
import com.grandemc.post.external.lib.global.command.buildCommand
import com.grandemc.post.external.lib.manager.config.ConfigManager
import com.grandemc.post.external.lib.manager.database.DatabaseManager
import com.grandemc.fazendas.bukkit.command.*
import com.grandemc.fazendas.bukkit.command.gfazendas.*
import com.grandemc.fazendas.manager.FarmItemManager
import com.grandemc.fazendas.manager.GoldBank
import com.grandemc.fazendas.manager.PlayerManager
import com.grandemc.fazendas.manager.QuestManager
import com.grandemc.fazendas.provider.GlobalMessagesProvider
import org.bukkit.plugin.java.JavaPlugin

class GFazendasCommands(
    private val plugin: JavaPlugin,
    private val configManager: ConfigManager,
    private val configCacheUpdater: Updatable,
    private val databaseManager: DatabaseManager,
    private val farmItemManager: FarmItemManager,
    private val playerManager: PlayerManager,
    private val goldBank: GoldBank,
    private val questManager: QuestManager
) : Factory<CommandCompound> {
    override fun create(): CommandCompound {
        return buildCommand(plugin, GlobalMessagesProvider.get()) {
            commandName("gfazendas")
            tabCompleter(gfazendasTabCompleter)

            handler {
                noSuchCommandKey("gfazendas")
                bypass(true)

                module("reload", Reload(configManager, configCacheUpdater))
                module("save", Save(databaseManager))
                module("ajuda", gfazendasHelp)
                module("darfertilizante", GiveFertilizing(farmItemManager))
                module("darlootbox", GiveLootBox(farmItemManager))
                module("resetardiarias", ResetDailyQuests(playerManager))
                module("recompensas", Rewards())
                module("darouro", GiveGold(goldBank))
                module("setarmissao", SetQuest(questManager, playerManager))
                module("acabarmissao", FinishQuest(questManager, playerManager))
            }
        }
    }
}