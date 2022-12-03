package com.grandemc.fazendas.bukkit.conversation

import com.grandemc.fazendas.bukkit.view.MarketSellView
import com.grandemc.fazendas.bukkit.view.MaterialSellView
import com.grandemc.fazendas.bukkit.view.market.sell.menu.MarketSellContext
import com.grandemc.fazendas.bukkit.view.sell.MaterialSellContext
import com.grandemc.fazendas.global.openView
import com.grandemc.fazendas.global.respond
import com.grandemc.post.external.lib.bukkit.conversation.PlayerPrompt
import org.bukkit.conversations.ConversationContext
import org.bukkit.conversations.Prompt
import org.bukkit.entity.Player

class MaterialSellAmountConversation(
    private val context: MaterialSellContext
) : PlayerPrompt("cancelar") {
    override fun getPromptText(player: Player): String {
        player.respond("armazem.iten_vender_quantia_iniciar")
        return ""
    }

    override fun handleInput(player: Player, value: String): Prompt? {
        val amount = value.toShort()
        player.openView(
            MaterialSellView::class,
            MaterialSellContext(context.materialId, amount)
        )
        player.respond("armazem.iten_vender_quantia_setada")
        return null
    }

    override fun isValueValid(ctx: ConversationContext, value: String): Boolean {
        return value.toShortOrNull()?.let { it >= 0 } ?: false
    }

    override fun onCancel(player: Player): Prompt? {
        player.openView(MaterialSellView::class, context)
        player.respond("geral.operacao_cancelada")
        return null
    }
}