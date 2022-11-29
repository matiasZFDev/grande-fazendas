package com.grandemc.fazendas.bukkit.view.market.purchase

import com.grandemc.post.external.lib.cache.config.StateMenuContainer
import com.grandemc.post.external.lib.cache.config.model.menu.MenuData
import com.grandemc.post.external.lib.global.createMenuData
import com.grandemc.post.external.lib.util.CustomConfig
import org.bukkit.configuration.ConfigurationSection

class MarketPurchaseMenuContainer(
    customConfig: CustomConfig, section: String?, context: String
) : StateMenuContainer(customConfig, context, section) {
    override fun create(section: ConfigurationSection): MenuData {
        return createMenuData(section) {
            val key = "gfazendas.market.purchase"
            addReference("info",  key)
            addReference("confirmar", key, "confirm")
            addReference("cancelar", key, "cancel")
            colorAll()
        }
    }
}