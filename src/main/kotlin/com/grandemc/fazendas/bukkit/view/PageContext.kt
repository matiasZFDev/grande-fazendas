package com.grandemc.fazendas.bukkit.view

import com.grandemc.post.external.lib.view.base.ContextData

class PageContext(val page: Byte) : ContextData {
    fun next(): PageContext = PageContext(page.inc())
    fun previous(): PageContext = PageContext(page.dec())
}