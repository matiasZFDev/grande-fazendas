package com.grandemc.fazendas.manager

import com.grandemc.fazendas.config.MaterialsConfig
import com.grandemc.fazendas.storage.player.model.ItemStorage
import com.grandemc.fazendas.storage.player.model.StorageItem
import java.util.UUID

class StorageManager(
    private val playerManager: PlayerManager,
    private val materialsConfig: MaterialsConfig
) {
    fun materialData(nameId: String): MaterialsConfig.StorageMaterial {
        return materialsConfig.get().getByNameId(nameId)
    }

    fun materialData(id: Byte): MaterialsConfig.StorageMaterial {
        return materialsConfig.get().getById(id)
    }

    fun materialId(nameId: String): Byte {
        return materialsConfig.get().getByNameId(nameId).id
    }

    private fun storage(playerId: UUID): ItemStorage {
        return playerManager.player(playerId).storage()
    }

    fun has(playerId: UUID, type: Byte, amount: Short): Boolean {
        return storage(playerId).hasAmount(type, amount)
    }

    fun material(playerId: UUID, type: Byte): Short {
        return storage(playerId).getAmount(type)
    }

    fun deposit(playerId: UUID, type: Byte, amount: Short) {
        storage(playerId).addItem(type, amount)
    }

    fun withdraw(playerId: UUID, type: Byte, amount: Short) {
        storage(playerId).removeAmount(type, amount)
    }

    fun items(playerId: UUID): List<StorageItem> {
        return storage(playerId).items()
    }
}