package com.grandemc.fazendas.bukkit.view.market.sell.menu

import com.grandemc.post.external.lib.cache.config.StateMenuContainer
import com.grandemc.post.external.lib.cache.config.model.menu.MenuData
import com.grandemc.post.external.lib.global.createMenuData
import com.grandemc.post.external.lib.util.CustomConfig
import org.bukkit.configuration.ConfigurationSection

class MarketSellMenuContainer(
    customConfig: CustomConfig, section: String?, context: String
) : StateMenuContainer(customConfig, context, section) {
    override fun create(section: ConfigurationSection): MenuData {
        return createMenuData(section) {
            val key = "gfazendas.market.sell"
            addReference("voltar", key, "return")
            addReference("material_nada", key, "material")
            addReference("material_escolhido", key, "material")
            addReference("escolher_quantia", key, "amount")
            addReference("escolher_preco", key, "price")
            addReference("vender_possivel", key, "post")
            colorAll()
        }
    }
}