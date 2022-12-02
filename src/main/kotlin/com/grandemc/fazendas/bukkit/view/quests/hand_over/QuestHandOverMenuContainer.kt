package com.grandemc.fazendas.bukkit.view.quests.hand_over

import com.grandemc.post.external.lib.cache.config.StateMenuContainer
import com.grandemc.post.external.lib.cache.config.model.menu.MenuData
import com.grandemc.post.external.lib.global.createMenuData
import com.grandemc.post.external.lib.util.CustomConfig
import org.bukkit.configuration.ConfigurationSection

class QuestHandOverMenuContainer(
    customConfig: CustomConfig, section: String?, context: String
) : StateMenuContainer(customConfig, context, section) {
    override fun create(section: ConfigurationSection): MenuData {
        return createMenuData(section) {
            val key = "gfazendas.quests.hand_over"
            addReference("voltar", key, "return")
            addReference("quantia", key, "amount")
            addReference("oferecer_suficiente", key, "offer")
            colorAll()
        }
    }
}