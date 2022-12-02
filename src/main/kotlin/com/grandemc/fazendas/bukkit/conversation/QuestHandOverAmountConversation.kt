package com.grandemc.fazendas.bukkit.conversation

import com.grandemc.fazendas.GrandeFazendas
import com.grandemc.fazendas.bukkit.view.QuestHandOverView
import com.grandemc.fazendas.bukkit.view.quests.hand_over.HandOverContext
import com.grandemc.fazendas.global.openView
import com.grandemc.fazendas.global.respond
import com.grandemc.fazendas.manager.QuestManager
import com.grandemc.post.external.lib.bukkit.conversation.PlayerPrompt
import org.bukkit.conversations.ConversationContext
import org.bukkit.conversations.Prompt
import org.bukkit.entity.Player

class QuestHandOverAmountConversation(
    private val oldData: HandOverContext
) : PlayerPrompt("cancelar") {
    override fun getPromptText(player: Player): String {
        player.respond("oferenda.iniciar")
        return ""
    }

    override fun handleInput(player: Player, value: String): Prompt? {
        val amount = value.toShort()
        player.openView(QuestHandOverView::class, HandOverContext(amount))
        return null
    }

    override fun isValueValid(ctx: ConversationContext, value: String): Boolean {
        return value.toShortOrNull()?.let {
            it > 0 && it + oldData.amount <= GrandeFazendas.MAX_ITEM_AMOUNT
        } ?: false
    }

    override fun onCancel(player: Player): Prompt? {
        player.openView(QuestHandOverView::class, oldData)
        player.respond("geral.operacao_cancelada")
        return null
    }
}