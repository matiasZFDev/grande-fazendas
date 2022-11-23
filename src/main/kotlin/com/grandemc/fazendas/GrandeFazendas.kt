package com.grandemc.fazendas

import com.grandemc.post.external.lib.GrandePlugin
import com.grandemc.post.external.lib.manager.config.ConfigManager
import com.grandemc.post.external.lib.manager.database.DatabaseManager
import com.grandemc.fazendas.init.ConfigInitializer
import com.grandemc.fazendas.init.DatabaseInitializer
import com.grandemc.fazendas.init.ServicesInitializer
import com.grandemc.fazendas.init.model.ServicesData
import com.grandemc.fazendas.npc.LandsTrait
import net.citizensnpcs.api.CitizensAPI
import net.citizensnpcs.api.trait.TraitInfo

class GrandeFazendas : GrandePlugin() {
    private lateinit var configManager: ConfigManager
    private lateinit var servicesData: ServicesData
    private lateinit var databaseManager: DatabaseManager

    companion object {
        const val CONTEXT: String = "GrandeFazendas"
    }

    override fun dataPreload() {
        configManager = ConfigInitializer(this).init()
        servicesData = ServicesInitializer().init()
        databaseManager = DatabaseInitializer(this, configManager, servicesData).init()
    }

    override fun dataFetch() {
        databaseManager.fetchData()
    }

    override fun dataPostLoad() {
        CitizensAPI.getTraitFactory().registerTrait(TraitInfo.create(LandsTrait::class.java))
        PluginPostLoad(this, servicesData, configManager, databaseManager, CONTEXT).run()
    }

    override fun dataSave() {
        databaseManager.saveData(false)
    }
}