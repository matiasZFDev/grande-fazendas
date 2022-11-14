package com.grandemc.fazendas.global

import com.grandemc.post.external.lib.view.base.ContextData
import com.grandemc.post.external.lib.view.base.View
import com.grandemc.fazendas.provider.GlobalViewProvider
import org.bukkit.entity.Player
import kotlin.reflect.KClass

fun Player.openView(view: KClass<out View<out ContextData>>, data: ContextData? = null) {
    GlobalViewProvider.get().open(view, this, data)
}

inline fun <reified T : View<out ContextData>> Player.updateView(data: ContextData? = null) {
    if (openInventory == null || openInventory.topInventory.holder::class != T::class)
        return

    openView(T::class, data)
}
