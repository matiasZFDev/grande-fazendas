package com.grandemc.fazendas.bukkit.view.quests.menu

import com.grandemc.fazendas.bukkit.view.QuestHandOverView
import com.grandemc.fazendas.bukkit.view.QuestHistoryView
import com.grandemc.fazendas.bukkit.view.QuestsView
import com.grandemc.fazendas.config.QuestsConfig
import com.grandemc.fazendas.global.openView
import com.grandemc.fazendas.global.respond
import com.grandemc.fazendas.global.rollUntil
import com.grandemc.fazendas.manager.PlayerManager
import com.grandemc.fazendas.manager.QuestManager
import com.grandemc.fazendas.manager.StatsManager
import com.grandemc.fazendas.storage.player.model.FarmQuest
import com.grandemc.post.external.lib.global.bukkit.nms.NBTReference
import com.grandemc.post.external.lib.global.bukkit.nms.useReferenceIfPresent
import com.grandemc.post.external.lib.view.pack.ViewClickHandler
import com.grandemc.post.external.util.random.RandomUtils
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class QuestsClickHandler(
    private val questManager: QuestManager,
    private val questsConfig: QuestsConfig,
    private val playerManager: PlayerManager,
    private val statsManager: StatsManager
) : ViewClickHandler.Stateless() {
    override fun onClick(player: Player, item: ItemStack, event: InventoryClickEvent) {
        item.useReferenceIfPresent(NBTReference.VIEW, "gfazendas.quests") {
            when (it) {
                "hand_over" -> player.openView(QuestHandOverView::class)
                "history" -> player.openView(QuestHistoryView::class)
                "current_quest" -> {
                    val master = questManager.master(player.uniqueId)
                    val farmPlayer = playerManager.player(player.uniqueId)
                    val questConfig = questManager.currentConfig(farmPlayer.id())
                    master.concludeQuest()
                    questConfig.rewards().send(farmPlayer)
                    player.closeInventory()
                    player.respond("missao.completada")
                }
                "daily" -> {
                    val master = questManager.master(player.uniqueId)
                    val farmPlayer = playerManager.player(player.uniqueId)

                    if (master.dailyQuestsDone() >= statsManager.dailyQuests(player.uniqueId)) {
                        player.respond("missao_diaria.feitas")
                        return@useReferenceIfPresent
                    }

                    master.useDailyQuests()

                    questsConfig.get().dailyQuests()
                        .filter { level ->
                            level.islandLevelRange.isInside(
                                farmPlayer.farm()!!.level().toInt()
                            )
                        }
                        .let { quests ->
                            val quest = quests.rollUntil(1000) { el ->
                                RandomUtils.roll(el.chance)
                            }

                            if (quest == null) {
                                player.closeInventory()
                                player.respond("geral.error")
                                return@useReferenceIfPresent
                            }

                            val farmQuest = FarmQuest(
                                quest.id,
                                quest.quest.type(),
                                0
                            )
                            master.startQuest(farmQuest)
                            player.openView(QuestsView::class)
                            player.respond("missao.iniciada")
                        }
                }
            }
        }
    }
}