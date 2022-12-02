package com.grandemc.fazendas.bukkit.view.quests.history

import com.grandemc.post.external.lib.cache.config.StateMenuContainer
import com.grandemc.post.external.lib.cache.config.model.menu.MenuData
import com.grandemc.post.external.lib.global.createMenuData
import com.grandemc.post.external.lib.util.CustomConfig
import org.bukkit.configuration.ConfigurationSection

class QuestHistoryMenuContainer(
    customConfig: CustomConfig, section: String?, context: String
) : StateMenuContainer(customConfig, context, section) {
    override fun create(section: ConfigurationSection): MenuData {
        return createMenuData(section) {
            val key = "gfazendas.quests.history"
            addReference("seguinte", key, "next")
            addReference("anterior", key, "previous")
            addReference("voltar", key, "return")
            colorAll()
        }
    }
}