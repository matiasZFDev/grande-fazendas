package com.grandemc.fazendas.npc

import com.grandemc.fazendas.bukkit.view.LandsView
import com.grandemc.fazendas.global.openView
import net.citizensnpcs.api.event.NPCRightClickEvent
import net.citizensnpcs.api.trait.Trait
import net.citizensnpcs.api.trait.TraitName
import org.bukkit.event.EventHandler

@TraitName("fazenda-lands-menu")
class LandsTrait : Trait("fazenda-lands-menu") {
    @EventHandler
    fun click(event: NPCRightClickEvent) {
        if (event.npc != getNPC()) {
            event.isCancelled = true
            return
        }
        event.clicker.openView(LandsView::class)
    }
}