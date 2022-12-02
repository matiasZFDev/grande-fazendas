package com.grandemc.fazendas.bukkit.event

import org.bukkit.event.Event
import java.util.UUID

abstract class SourceEvent(private val playerId: UUID) : Event() {
    fun playerId(): UUID = playerId
}