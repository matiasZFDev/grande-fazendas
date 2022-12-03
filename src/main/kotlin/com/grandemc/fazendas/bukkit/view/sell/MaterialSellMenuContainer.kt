package com.grandemc.fazendas.bukkit.view.sell

import com.grandemc.post.external.lib.cache.config.StateMenuContainer
import com.grandemc.post.external.lib.cache.config.model.menu.MenuData
import com.grandemc.post.external.lib.global.createMenuData
import com.grandemc.post.external.lib.util.CustomConfig
import org.bukkit.configuration.ConfigurationSection

class MaterialSellMenuContainer(
    customConfig: CustomConfig, section: String?, context: String
) : StateMenuContainer(customConfig, context, section) {
    override fun create(section: ConfigurationSection): MenuData {
        return createMenuData(section) {
            val key = "gfazendas.storage.sell"
            addReference("voltar", key, "return")
            addReference("quantia", key , "amount")
            addReference("venda_possivel", key, "sell")
            colorAll()
        }
    }
}