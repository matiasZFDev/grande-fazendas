package com.grandemc.fazendas.init.model

import com.grandemc.post.external.lib.util.state.MutableState
import java.util.UUID

typealias IslandTopState = MutableState<Collection<PluginStates.IslandTopPosition>>
class PluginStates(
    val islandTopState: IslandTopState
) {
    class IslandTopPosition(
        val playerId: UUID,
        val level: Short,
        val xp: Int,
        val questsDone: Short
    )
}