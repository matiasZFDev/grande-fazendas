package com.grandemc.fazendas.init.model

import com.grandemc.post.external.lib.manager.view.MenuContainerManager
import com.grandemc.post.external.lib.manager.view.ViewManager

data class GuiManagers(
    val menuContainerManager: MenuContainerManager,
    val viewManager: ViewManager
)