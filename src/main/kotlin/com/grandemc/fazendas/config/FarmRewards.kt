package com.grandemc.fazendas.config

import com.grandemc.fazendas.config.model.reward.FarmReward
import com.grandemc.post.external.lib.util.CustomConfig
import com.grandemc.post.external.util.reward.base.model.GenericReward
import com.grandemc.post.external.util.reward.base.model.GenericRewardsContainer

class FarmRewards(
    customConfig: CustomConfig, rootSection: String
) : GenericRewardsContainer<Boolean?>(customConfig, rootSection) {
    override fun createNewReward(): GenericReward<Boolean?> {
        return FarmReward(null)
    }
}