package com.grandemc.fazendas.bukkit.view.quests.menu

import com.grandemc.post.external.lib.cache.config.StateMenuContainer
import com.grandemc.post.external.lib.cache.config.model.menu.MenuData
import com.grandemc.post.external.lib.global.createMenuData
import com.grandemc.post.external.lib.util.CustomConfig
import org.bukkit.configuration.ConfigurationSection

class QuestsMenuContainer(
    customConfig: CustomConfig, section: String?, context: String
) : StateMenuContainer(customConfig, context, section) {
    override fun create(section: ConfigurationSection): MenuData {
        return createMenuData(section) {
            val key = "gfazendas.quests"
            addReference("completadas", key, "done")
            addReference("entrega_aberta", key, "hand_over")
            addReference("atual_completa", key, "current_quest")
            addReference("diarias", key, "daily")
            addReference("historia", key, "history")
            colorAll()
        }
    }
}