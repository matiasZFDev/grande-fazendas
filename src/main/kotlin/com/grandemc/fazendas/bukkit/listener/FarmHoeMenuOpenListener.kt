package com.grandemc.fazendas.bukkit.listener

import com.grandemc.fazendas.bukkit.view.HoeView
import com.grandemc.fazendas.global.openView
import com.grandemc.post.external.lib.global.bukkit.isRightClick
import com.grandemc.post.external.lib.global.bukkit.nms.NBTReference
import com.grandemc.post.external.lib.global.bukkit.nms.hasReferenceTag
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class FarmHoeMenuOpenListener : Listener {
    @EventHandler
    fun onInteract(event: PlayerInteractEvent) {
        if (!event.hasItem() || !event.isRightClick())
            return

        if (!event.item.hasReferenceTag(NBTReference.ITEM, "gfazendas.farm_tool"))
            return

        event.player.openView(HoeView::class)
    }
}