package com.grandemc.fazendas.bukkit.view.reward

import com.grandemc.fazendas.GrandeFazendas
import com.grandemc.fazendas.provider.GlobalViewProvider
import com.grandemc.post.external.lib.global.bukkit.colorMeta
import com.grandemc.post.external.lib.global.bukkit.giveItem
import com.grandemc.post.external.lib.global.bukkit.nms.NBTReference
import com.grandemc.post.external.lib.global.bukkit.nms.addNBTReference
import com.grandemc.post.external.lib.global.bukkit.setMeta
import com.grandemc.post.external.util.reward.base.model.GenericReward
import com.grandemc.post.external.util.reward.base.model.GenericRewardsContainer
import com.grandemc.post.external.util.reward.base.view.GenericRewardsEditView
import com.grandemc.post.external.util.reward.base.view.model.RewardEditContext
import com.grandemc.post.external.util.reward.singly.model.SinglyReward
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

class RewardEditView(
    private val rewardsContainer: GenericRewardsContainer<Boolean?>
) : GenericRewardsEditView<Boolean?>(
    rewardsContainer, GlobalViewProvider.get(),
    RewardsView::class, GrandeFazendas.CONTEXT
) {
    override fun handleClick(
        player: Player,
        reference: String,
        context: RewardEditContext<Boolean?>,
        event: InventoryClickEvent
    ) {
        when (reference) {
            "item" -> {
                val item = context.reward.item ?: return
                player.giveItem(item.clone())
                GlobalViewProvider.get().open(this::class, player, RewardEditContext(
                    context.id,
                    context.slot,
                    SinglyReward(null, context.reward.data)
                ))
            }
        }
    }

    override fun onBottomClick(event: InventoryClickEvent) {
        event.isCancelled = true

        val player = event.whoClicked as Player
        val item = event.currentItem

        if (item == null || item.type == Material.AIR)
            return

        val context = requireNotNull(getContext(player))
        val reward = rewardsContainer.getItem(context.id, context.slot)

        if (reward.item != null)
            player.giveItem(reward.item!!)

        GlobalViewProvider.get().open(
            this::class,
            player,
            RewardEditContext(
                context.id,
                context.slot,
                SinglyReward(
                    event.currentItem.clone(),
                    reward.data
                )
            )
        )
    }

    override fun setItems(
        data: GenericReward<Boolean?>, inventory: Inventory, contextKey: String
    ) {
        val rewardItem = (data.item?.clone() ?: ItemStack(Material.BARRIER).setMeta(
            "&cVazio",
            listOf("&7Escolha um iten.")
        ).colorMeta()).addNBTReference(NBTReference.VIEW, contextKey, "item")
        inventory.setItem(13, rewardItem)
    }
}