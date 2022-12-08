package com.grandemc.fazendas.init

import com.grandemc.post.external.lib.init.Initializer
import com.grandemc.fazendas.init.model.ServicesData
import com.grandemc.fazendas.storage.market.MarketSoldTableManager
import com.grandemc.fazendas.storage.market.MarketTableManager
import com.grandemc.fazendas.storage.player.FarmPlayerTableManager
import com.grandemc.post.external.lib.database.base.impl.DaoDatabaseService
import com.grandemc.post.external.lib.database.base.impl.MapCachedDao

class ServicesInitializer : Initializer<ServicesData> {
    override fun init(): ServicesData {
        return ServicesData(
            DaoDatabaseService(MapCachedDao(), FarmPlayerTableManager()),
            DaoDatabaseService(MapCachedDao(), MarketTableManager()),
            DaoDatabaseService(MapCachedDao(), MarketSoldTableManager())
        )
    }
}