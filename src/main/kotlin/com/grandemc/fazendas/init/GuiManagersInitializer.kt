package com.grandemc.fazendas.init

import com.grandemc.post.external.lib.init.Initializer
import com.grandemc.post.external.lib.manager.view.MenuContainerManagerImpl
import com.grandemc.post.external.lib.manager.view.ViewManagerImpl
import com.grandemc.fazendas.init.model.GuiManagers

class GuiManagersInitializer : Initializer<GuiManagers> {
    override fun init(): GuiManagers {
        return GuiManagers(
            MenuContainerManagerImpl(),
            ViewManagerImpl()
        )
    }
}