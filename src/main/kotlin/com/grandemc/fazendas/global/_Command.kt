package com.grandemc.fazendas.global

import org.bukkit.event.player.PlayerCommandPreprocessEvent

fun PlayerCommandPreprocessEvent.getCommand(): String {
    return message.substring(1).let {
        if (it.isEmpty())
            ""
        else
            it.split(" ")[0]
    }
}