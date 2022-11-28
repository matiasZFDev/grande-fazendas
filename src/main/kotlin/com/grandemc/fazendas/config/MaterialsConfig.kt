package com.grandemc.fazendas.config

import com.grandemc.fazendas.GrandeFazendas
import com.grandemc.post.external.lib.cache.config.StateConfig
import com.grandemc.post.external.lib.global.bukkit.*
import com.grandemc.post.external.lib.global.color
import com.grandemc.post.external.lib.util.CustomConfig
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.inventory.ItemStack

class MaterialsConfig(customConfig: CustomConfig) : StateConfig<MaterialsConfig.Config>(
    customConfig, GrandeFazendas.CONTEXT
) {
    inner class Config(
        private val materials: List<StorageMaterial>,
        private val materialsById: Map<Byte, StorageMaterial>
    ) {
        fun getByNameId(nameId: String): StorageMaterial {
            return materials.first { it.nameId == nameId }
        }

        fun getById(id: Byte): StorageMaterial {
            return materials.first { it.id == id }
        }
    }

    inner class StorageMaterial(
        val nameId: String,
        val id: Byte,
        val name: String,
        val item: ItemStack,
        val goldPrice: Double
    )

    override fun fetch(config: FileConfiguration): Config {
        val materials = config.mappedKeys().map { (key, section) ->
            StorageMaterial(
                key, section.getByte("id"),
                section.getString("nome").color(),
                section.buildItem("iten", false),
                section.formatNumber("ouro")
            )
        }
        return Config(
            materials,
            materials.associateBy(StorageMaterial::id)
        )
    }
}