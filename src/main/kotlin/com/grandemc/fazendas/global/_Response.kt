package com.grandemc.fazendas.global

import com.grandemc.post.external.lib.cache.config.response.message.base.SubjectMessage
import com.grandemc.fazendas.provider.GlobalEffectsProvider
import com.grandemc.fazendas.provider.GlobalMessagesProvider
import com.grandemc.fazendas.provider.GlobalSoundsProvider
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

fun CommandSender?.respond(
    respondKey: String,
    messageAction: (SubjectMessage.() -> SubjectMessage)? = null
) {
    if (this == null)
        return

    this.message(respondKey, messageAction)

    if (this is Player) {
        GlobalSoundsProvider.get().value(respondKey)?.send(this)
        GlobalEffectsProvider.get().value(respondKey)?.send(this)
    }
}

fun CommandSender.message(
    messageKey: String,
    messageAction: (SubjectMessage.() -> SubjectMessage)? = null
) {
    GlobalMessagesProvider.get().value(messageKey)
        ?.let {
            if (messageAction != null)
                it.messageAction()
            else
                it
        }?.color()?.send(this)
}