package com.grandemc.fazendas.npc

import com.grandemc.fazendas.bukkit.view.IndustryView
import com.grandemc.fazendas.global.openView
import net.citizensnpcs.api.event.NPCRightClickEvent
import net.citizensnpcs.api.trait.Trait
import org.bukkit.event.EventHandler

class IndustryTrait : Trait("industry-menu") {
    @EventHandler
    fun click(event: NPCRightClickEvent) {
        if (event.npc != getNPC())
            return
        event.clicker.openView(IndustryView::class)
    }
}