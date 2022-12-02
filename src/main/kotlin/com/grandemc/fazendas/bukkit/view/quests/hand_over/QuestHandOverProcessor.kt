package com.grandemc.fazendas.bukkit.view.quests.hand_over

import com.grandemc.fazendas.config.MaterialsConfig
import com.grandemc.fazendas.config.model.quest.type.HandOverQuest
import com.grandemc.fazendas.global.commaFormat
import com.grandemc.fazendas.manager.QuestManager
import com.grandemc.fazendas.manager.StorageManager
import com.grandemc.post.external.lib.cache.config.model.SlotItem
import com.grandemc.post.external.lib.cache.config.model.menu.MenuItems
import com.grandemc.post.external.lib.global.bukkit.copyNBTs
import com.grandemc.post.external.lib.global.bukkit.displayFrom
import com.grandemc.post.external.lib.global.bukkit.formatLore
import com.grandemc.post.external.lib.global.bukkit.formatName
import com.grandemc.post.external.lib.global.newItemProcessing
import com.grandemc.post.external.lib.view.pack.MenuItemsProcessor
import org.bukkit.entity.Player

class QuestHandOverProcessor(
    private val questManager: QuestManager,
    private val storageManager: StorageManager,
    private val materialsConfig: MaterialsConfig,
) : MenuItemsProcessor<HandOverContext> {
    override fun process(
        player: Player, data: HandOverContext?, items: MenuItems
    ): Collection<SlotItem> {
        requireNotNull(data)
        val quest = questManager.currentQuest(player.uniqueId)!!
        val questConfig = questManager.questConfig(quest.id()) as HandOverQuest
        val materialId = questConfig.materialId()
        val materialConfig = materialsConfig.get().getById(materialId)
        return newItemProcessing(items) {
            modify("material") {
                materialConfig.item
                    .displayFrom(it)
                    .copyNBTs(it)
                    .formatName("{nome}" to materialConfig.name)
            }

            modify("quantia") {
                it.formatLore(
                    "{possui}" to storageManager.material(
                        player.uniqueId, materialId
                    ).commaFormat(),
                    "{quantia}" to data.amount.commaFormat()
                )
            }

            if (storageManager.has(player.uniqueId, materialId, data.amount)) {
                remove("oferecer_insuficiente")
                modify("oferecer_suficiente") {
                    it.formatLore(
                        "{precisos}" to questConfig.amount().commaFormat(),
                        "{atuais}" to quest.progress().commaFormat(),
                        "{logo}" to quest.progress().plus(data.amount).commaFormat()
                    )
                }
            }

            else {
                remove("oferecer_suficiente")
                modify("oferecer_insuficiente") {
                    it.formatLore(
                        "{precisos}" to questConfig.amount().commaFormat(),
                        "{atuais}" to quest.progress().commaFormat(),
                        "{logo}" to quest.progress().plus(data.amount).commaFormat()
                    )
                }
            }
        }
    }
}