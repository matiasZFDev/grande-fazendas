package com.grandemc.fazendas.bukkit.event

import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import java.util.UUID

class RecipeBakeEvent(
    private val playerId: UUID,
    private val recipeId: Byte
) : Event() {
    companion object {
        private val HANDLER_LIST: HandlerList = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList = HANDLER_LIST
    }

    override fun getHandlers(): HandlerList {
        return getHandlerList()
    }

    fun playerId(): UUID = playerId
    fun recipeId(): Byte = recipeId
}