package com.grandemc.fazendas.bukkit.conversation

import com.grandemc.fazendas.bukkit.view.MarketSellView
import com.grandemc.fazendas.bukkit.view.market.sell.menu.MarketSellContext
import com.grandemc.fazendas.global.openView
import com.grandemc.fazendas.global.respond
import com.grandemc.post.external.lib.bukkit.conversation.PlayerPrompt
import org.bukkit.conversations.ConversationContext
import org.bukkit.conversations.Prompt
import org.bukkit.entity.Player

class MarketProductAmountConversation(
    private val context: MarketSellContext
) : PlayerPrompt("cancelar") {
    override fun getPromptText(player: Player): String {
        player.respond("mercado_vender.quantia_iniciar")
        return ""
    }

    override fun handleInput(player: Player, value: String): Prompt? {
        val amount = value.toShort()
        player.openView(
            MarketSellView::class,
            MarketSellContext(context.materialId, amount, context.price)
        )
        player.respond("mercado_vender.quantia_setada")
        return null
    }

    override fun isValueValid(ctx: ConversationContext, value: String): Boolean {
        return value.toShortOrNull()?.let { it >= 0 } ?: false
    }

    override fun onCancel(player: Player): Prompt? {
        player.openView(MarketSellView::class, context)
        player.respond("geral.operacao_cancelada")
        return null
    }
}