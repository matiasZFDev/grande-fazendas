package com.grandemc.fazendas.bukkit.event

import org.bukkit.event.HandlerList
import java.util.UUID

class FarmPlantEvent(
    playerId: UUID,
    private val cropId: Byte
) : SourceEvent(playerId) {
    companion object {
        private val HANDLER_LIST: HandlerList = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList = HANDLER_LIST
    }

    override fun getHandlers(): HandlerList {
        return HANDLER_LIST
    }

    fun cropId(): Byte = cropId
}