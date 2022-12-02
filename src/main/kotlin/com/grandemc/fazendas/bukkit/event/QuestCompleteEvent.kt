package com.grandemc.fazendas.bukkit.event

import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import java.util.UUID

class QuestCompleteEvent(
    private val playerId: UUID,
    private val id: Byte
) : Event() {
    companion object {
        private val HANDLER_LIST: HandlerList = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList = HANDLER_LIST
    }

    override fun getHandlers(): HandlerList {
        return HANDLER_LIST
    }

    fun playerId(): UUID = playerId
    fun questId(): Byte = id
}