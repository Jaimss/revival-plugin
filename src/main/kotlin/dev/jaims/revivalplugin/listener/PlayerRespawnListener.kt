package dev.jaims.revivalplugin.listener

import dev.jaims.revivalplugin.RevivalPlugin
import dev.jaims.revivalplugin.const.PlayerState
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerRespawnEvent

class PlayerRespawnListener(private val plugin: RevivalPlugin) : Listener {

    /**
     * Handle the Player Respawn Event
     */
    @EventHandler
    fun PlayerRespawnEvent.onPlayerRespawn() {
        val state = plugin.playerStateManager.getState(player.uniqueId)

        if (state != PlayerState.REVIVABLE) return

    }

}