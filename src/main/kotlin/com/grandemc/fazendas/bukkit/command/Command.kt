package com.grandemc.fazendas.bukkit.command

import com.grandemc.post.external.lib.command.base.PaginatedCommandModule
import com.grandemc.post.external.lib.command.base.TabCompleterBase
import com.grandemc.fazendas.provider.GlobalMessagesProvider

val fazendaTabCompleter = TabCompleterBase(false, "ajuda")
val gfazendasTabCompleter = TabCompleterBase(true, "ajuda")
val fazendaHelp = PaginatedCommandModule(2, "ajuda", GlobalMessagesProvider.get())
val gfazendasHelp = PaginatedCommandModule(2, "ajuda_staff", GlobalMessagesProvider.get())
