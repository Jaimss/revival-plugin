package dev.jaims.revivalplugin.listener

import dev.jaims.revivalplugin.RevivalPlugin
import dev.jaims.revivalplugin.const.PlayerState
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent

class EntityMoveListener(private val plugin: RevivalPlugin) : Listener {

    @EventHandler
    fun PlayerMoveEvent.onPlayerMove() {
        val state = plugin.playerStateManager.getState(player.uniqueId)

        if (state != PlayerState.REVIVABLE) return

        isCancelled = true
    }

}