package dev.jaims.revivalplugin.listener

import dev.jaims.revivalplugin.RevivalPlugin
import dev.jaims.revivalplugin.const.PlayerState
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent

class PlayerDeathListener(private val plugin: RevivalPlugin) : Listener {

    /**
     * Handle the Player Death Event
     */
    @EventHandler
    fun PlayerDeathEvent.onPlayerDeath() {
        val state = plugin.playerStateManager.getState(entity.uniqueId)
        // only allow revivable if they are alive
        if (state != PlayerState.ALIVE) return

        // respawn instantly without death screen
        plugin.server.scheduler.scheduleSyncDelayedTask(plugin, {
            entity.spigot().respawn()
            plugin.playerStateManager.setRevivable(entity.uniqueId)
        }, 2L)
    }

}