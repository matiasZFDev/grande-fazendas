package com.grandemc.fazendas.bukkit.task

import com.grandemc.fazendas.bukkit.view.MarketSellingView
import com.grandemc.fazendas.global.respond
import com.grandemc.fazendas.global.updateView
import com.grandemc.fazendas.manager.MarketManager
import com.grandemc.fazendas.manager.StorageManager
import com.grandemc.post.external.lib.global.bukkit.runIfOnline
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin

class MarketItemExpiryTask(
    private val plugin: Plugin,
    private val marketManager: MarketManager,
    private val storageManager: StorageManager
) {
    fun start() {
        Bukkit.getScheduler().runTaskTimer(plugin, this::run, 20L, 20L)
    }

    private fun run() {
        val expiredItemsId = mutableListOf<Int>()

        marketManager.getProducts().forEach {
            if (it.expiryTime() >= 0) {
                it.advance()
                return@forEach
            }
            expiredItemsId.add(it.id())
        }

        expiredItemsId.forEach {
            val product = marketManager.getProduct(it)!!
            marketManager.removeProduct(it)
            storageManager.deposit(product.sellerId, product.itemId, product.amount)
            product.sellerId.runIfOnline {
                respond("mercado.produto_expirado")
                updateView<MarketSellingView>()
            }
        }
    }
}