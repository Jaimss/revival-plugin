package dev.jaims.revivalplugin.listener

import dev.jaims.revivalplugin.RevivalPlugin
import dev.jaims.revivalplugin.const.PlayerState
import org.bukkit.Location
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerRespawnEvent
import java.util.*

class PlayerDeathListener(private val plugin: RevivalPlugin) : Listener {

    private val deathLoc = mutableMapOf<UUID, Location>()

    /**
     * Handle the Player Death Event
     */
    @EventHandler
    fun PlayerDeathEvent.onPlayerDeath() {
        val state = plugin.playerStateManager.getState(entity.uniqueId)
        // only allow revivable if they are alive
        if (state != PlayerState.ALIVE) return

        // set the death location
        deathLoc[entity.uniqueId] = entity.location

        // respawn instantly without death screen
        plugin.server.scheduler.scheduleSyncDelayedTask(plugin, {
            entity.spigot().respawn()
            plugin.playerStateManager.setRevivable(entity.uniqueId)
        }, 0L)
    }

    /**
     * Quick handler for making sure they respawn at the correct place.
     */
    @EventHandler
    fun PlayerRespawnEvent.onRespawn() {
        deathLoc[player.uniqueId]?.let { location -> respawnLocation = location }
    }

}