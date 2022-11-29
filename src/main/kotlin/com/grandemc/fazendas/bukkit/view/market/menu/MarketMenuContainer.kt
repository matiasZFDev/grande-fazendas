package com.grandemc.fazendas.bukkit.view.market.menu

import com.grandemc.post.external.lib.cache.config.StateMenuContainer
import com.grandemc.post.external.lib.cache.config.model.menu.MenuData
import com.grandemc.post.external.lib.global.createMenuData
import com.grandemc.post.external.lib.util.CustomConfig
import org.bukkit.configuration.ConfigurationSection

class MarketMenuContainer(
    customConfig: CustomConfig, section: String?, context: String
) : StateMenuContainer(
    customConfig, context, section
) {
    override fun create(section: ConfigurationSection): MenuData {
        return createMenuData(section) {
            val key = "gfazendas.market"
            addReference("anterior", key, "previous")
            addReference("seguinte", key, "next")
            addReference("vender", key, "sell")
            addReference("em_venda", key, "selling")
            colorAll()
        }
    }
}