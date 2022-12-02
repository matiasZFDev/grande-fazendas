package com.grandemc.fazendas.bukkit.event

import org.bukkit.event.HandlerList
import java.util.UUID

class MarketBuyEvent(playerId: UUID) : SourceEvent(playerId) {
    companion object {
        private val HANDLER_LIST: HandlerList = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList = HANDLER_LIST
    }

    override fun getHandlers(): HandlerList {
        return HANDLER_LIST
    }
}