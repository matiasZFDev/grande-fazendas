package com.grandemc.fazendas.npc

import com.grandemc.fazendas.bukkit.view.LandsView
import com.grandemc.fazendas.global.openView
import net.citizensnpcs.api.event.NPCRightClickEvent
import net.citizensnpcs.api.trait.Trait
import net.citizensnpcs.api.trait.TraitName
import org.bukkit.event.EventHandler

@TraitName("lands-menu")
class LandsTrait : Trait("lands-menu") {
    @EventHandler
    fun click(event: NPCRightClickEvent) {
        if (event.npc != getNPC())
            return
        event.clicker.openView(LandsView::class)
    }
}