package com.grandemc.fazendas.bukkit.command.gfazendas

import com.grandemc.fazendas.GrandeFazendas
import com.grandemc.fazendas.bukkit.view.reward.RewardsView
import com.grandemc.fazendas.global.openView
import com.grandemc.fazendas.global.respond
import com.grandemc.fazendas.provider.GlobalMessagesProvider
import com.grandemc.post.external.lib.command.base.PlayerCommandModule
import com.grandemc.post.external.util.reward.base.view.model.RewardsContext
import org.bukkit.entity.Player

class Rewards : PlayerCommandModule(GlobalMessagesProvider.get()) {
    override fun execute(player: Player, args: Array<String>) {
        if (args.size != 1) {
            player.respond("recompensas.argumentos_invalidos")
            return
        }

        player.openView(RewardsView::class, RewardsContext(GrandeFazendas.REWARDS_KEY))
    }
}