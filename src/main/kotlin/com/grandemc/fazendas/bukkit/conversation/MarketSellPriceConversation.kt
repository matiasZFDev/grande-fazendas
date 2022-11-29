package com.grandemc.fazendas.bukkit.conversation

import com.grandemc.fazendas.bukkit.view.MarketSellView
import com.grandemc.fazendas.bukkit.view.market.sell.menu.MarketSellContext
import com.grandemc.fazendas.global.openView
import com.grandemc.fazendas.global.respond
import com.grandemc.post.external.lib.bukkit.conversation.PlayerPrompt
import com.grandemc.post.external.lib.global.doubleFromFormat
import com.grandemc.post.external.lib.global.isValidNumber
import org.bukkit.conversations.ConversationContext
import org.bukkit.conversations.Prompt
import org.bukkit.entity.Player

class MarketSellPriceConversation(
    private val context: MarketSellContext
) : PlayerPrompt("cancelar") {
    override fun getPromptText(player: Player): String {
        player.respond("mercado_vender.preco_iniciar")
        return ""
    }

    override fun handleInput(player: Player, value: String): Prompt? {
        val price = value.doubleFromFormat()
        player.openView(
            MarketSellView::class,
            MarketSellContext(context.materialId, context.amount, price)
        )
        player.respond("mercado_vender.preco_setado")
        return null
    }

    override fun isValueValid(ctx: ConversationContext, value: String): Boolean {
        return value.isValidNumber()
    }

    override fun onCancel(player: Player): Prompt? {
        player.openView(MarketSellView::class, context)
        player.respond("geral.operacao_cancelada")
        return null
    }
}