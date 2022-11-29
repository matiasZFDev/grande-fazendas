package com.grandemc.fazendas.bukkit.view.market.category

import com.grandemc.fazendas.manager.MarketManager
import com.grandemc.fazendas.manager.model.MarketFilter
import com.grandemc.post.external.lib.cache.config.StateMenuContainer
import com.grandemc.post.external.lib.cache.config.model.menu.MenuData
import com.grandemc.post.external.lib.global.createMenuData
import com.grandemc.post.external.lib.util.CustomConfig
import org.bukkit.configuration.ConfigurationSection

class MarketCategoryMenuContainer(
    customConfig: CustomConfig, section: String?, context: String
) : StateMenuContainer(
    customConfig, context, section
) {
    override fun create(section: ConfigurationSection): MenuData {
        return createMenuData(section) {
            val key = "gfazendas.market.category"
            MarketFilter.values().forEach {
                addReference(it.configName(), key, "filter")
            }
            addReference("anterior", key, "previous")
            addReference("seguinte", key, "next")
            addReference("voltar", key, "return")
            colorAll()
        }
    }
}