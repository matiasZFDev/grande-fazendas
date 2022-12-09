package com.grandemc.fazendas.bukkit.view.reward

import com.grandemc.fazendas.GrandeFazendas
import com.grandemc.fazendas.provider.GlobalViewProvider
import com.grandemc.post.external.lib.global.bukkit.setLore
import com.grandemc.post.external.util.reward.base.model.GenericReward
import com.grandemc.post.external.util.reward.base.model.GenericRewardsContainer
import com.grandemc.post.external.util.reward.singly.view.SinglyRewardsView
import org.bukkit.inventory.ItemStack

class RewardsView(
    rewardsContainer: GenericRewardsContainer<Boolean?>
) : SinglyRewardsView<Boolean?>(
    rewardsContainer, GlobalViewProvider.get(), RewardEditView::class,
    GrandeFazendas.CONTEXT
) {
    override fun createTakenItem(item: ItemStack, reward: GenericReward<Boolean?>): ItemStack {
        return item.setLore(listOf(
            "",
            "&aB. esquerdo para editar",
            "&cB. direito para remover"
        ))
    }
}