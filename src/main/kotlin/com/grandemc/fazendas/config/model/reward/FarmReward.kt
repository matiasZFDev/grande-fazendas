package com.grandemc.fazendas.config.model.reward

import com.grandemc.post.external.util.reward.base.model.GenericReward
import org.bukkit.inventory.ItemStack

class FarmReward(
    override val item: ItemStack?,
    override val data: Boolean? = null
) : GenericReward<Boolean?> {
}